package io.github.chenyilei2016.ac.core.excel.kernel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.collect.Lists;
import io.github.chenyilei2016.ac.core.common.enums.AcExcelTypeEnum;
import io.github.chenyilei2016.ac.core.common.utils.MyJsonUtil;
import io.github.chenyilei2016.ac.core.excel.header.ColumnHeader;
import io.github.chenyilei2016.ac.core.excel.header.ColumnHeaders;
import io.github.chenyilei2016.ac.core.file.DataGroup;
import io.github.chenyilei2016.ac.core.file.FileContext;
import io.github.chenyilei2016.ac.core.file.FileReader;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.util.ZipSecureFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * todo: 分批读取
 * @author chenyilei
 */
@Slf4j
public class AcExcelFileReader implements FileReader {
    static {
        ZipSecureFile.setMinInflateRatio(0.00001);
    }

    private ColumnHeaders columnHeaders;

    private List<ExcelReadListener> readListeners;

    private FileContext fileContext;

    public AcExcelFileReader(ColumnHeaders columnHeaders, FileContext fileContext) {
        this.columnHeaders = columnHeaders;
        this.readListeners = new ArrayList<>();
        this.fileContext = fileContext;
    }

    @Override
    public void read(InputStream inputStream) {
        ExcelTypeEnum excelTypeEnum = AcExcelTypeEnum.recognitionExcelType(inputStream).toEasyExcelType();
        ExcelReader excelReader = EasyExcel.read(inputStream).excelType(excelTypeEnum).build();
        List<ReadSheet> readSheets = excelReader.excelExecutor().sheetList();

        List<ReadSheet> sheetsNeedReads = new ArrayList<>();

        int sheetIndex = 0;
        for (ReadSheet readSheet : readSheets) {
            String sheetName = readSheet.getSheetName();
            if (sheetName != null && sheetName.startsWith("hidden_")) {
                log.warn("ignore sheet, mainJobId:{}, sheetNo:{}, sheetName:{}", fileContext.jobId(), readSheet.getSheetNo(), sheetName);
                continue;
            }
            ExcelReadListener readListener = new ExcelReadListener(fileContext, columnHeaders);
            readListeners.add(readListener);
            readSheet.setCustomReadListenerList(Lists.newArrayList(readListener));
            Integer headerRowCount = columnHeaders.getHeaderRowCount(sheetIndex);
            readSheet.setHeadRowNumber(headerRowCount);
            sheetsNeedReads.add(readSheet);
            sheetIndex++;
        }

        excelReader.read(sheetsNeedReads);
    }

    @Override
    public DataGroup finish() {
        DataGroup dataGroup = new DataGroup();
        List<DataGroup.Data> data = new ArrayList<>();
        for (ExcelReadListener listener : readListeners) {
            DataGroup.Data uploadData = listener.getData();
            if (uploadData != null) {
                data.add(uploadData);
            }
        }
        dataGroup.setData(data);
        return dataGroup;
    }

    @Override
    public void close() {
    }


    @Getter
    @Setter
    @Slf4j
    public static class ExcelReadListener extends AnalysisEventListener<Map<Integer, Object>> {

        private FileContext fileContext;

        private ColumnHeaders columnHeaders;

        private Map<Integer, List<String>> uploadHeaderNameKeys = new HashMap<>(4);

        private DataGroup.Data uploadData;

        private CountDownLatch countDownLatch;

        public ExcelReadListener(FileContext fileContext, ColumnHeaders columnHeaders) {
            this.columnHeaders = columnHeaders;
            this.fileContext = fileContext;
            this.countDownLatch = new CountDownLatch(1);
        }

        @Override
        public void invoke(Map<Integer, Object> data, AnalysisContext context) {
            Map<String, Object> line = new HashMap<>(data.size() * 2);

            for (Map.Entry<Integer, Object> entry : data.entrySet()) {
                Integer column = entry.getKey();
                List<String> headers = uploadHeaderNameKeys.get(column);
                ColumnHeader columnHeader = columnHeaders.getColumnHeaderByHeaderName(headers);
                if (columnHeader != null) {
                    String fieldName = columnHeader.getFieldName();
                    if (columnHeader.getDynamicColumn()) {
                        Object o = line.get(fieldName);
                        if (o == null) {
                            o = new HashMap<>();
                            line.put(fieldName, o);
                        } else {
                            if (o instanceof Map) {
                                Map map = (Map) o;
                                map.put(columnHeader.getDynamicColumnKey(), entry.getValue());
                            }
                        }
                    } else {
                        line.put(fieldName, entry.getValue());
                    }
                }
            }
            for (Map.Entry<Integer, Object> entry : data.entrySet()) {
                line.put(entry.getKey().toString(), entry.getValue());
            }

            DataGroup.Item item = new DataGroup.Item();
            String sheetName = context.readSheetHolder().getSheetName();
            String sheetNo = context.readSheetHolder().getSheetNo().toString();
            Integer rowIndex = context.readRowHolder().getRowIndex();
            String groupName = sheetName + "@" + sheetNo;
            String code = groupName + "@" + rowIndex;
            item.setCode(code);
            item.setValues(line);

            this.uploadData.getItems().add(item);
        }

        @Override
        public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
            if (this.uploadData == null) {
                String sheetName = context.readSheetHolder().getSheetName();
                String sheetNo = context.readSheetHolder().getSheetNo().toString();
                String groupName = sheetName + "@" + sheetNo;
                this.uploadData = new DataGroup.Data();
                this.uploadData.setCode(groupName);
                this.uploadData.setItems(new ArrayList<>());
                Map<String, String> meta = new HashMap<>();
                meta.put(ExcelConstants.sheetNoKey, sheetNo);
                meta.put(ExcelConstants.sheetNameKey, sheetName);
                this.uploadData.setMeta(meta);
            }

            for (Map.Entry<Integer, String> entry : headMap.entrySet()) {
                Integer key = entry.getKey();
                List<String> headers = uploadHeaderNameKeys.get(key);
                if (headers == null) {
                    headers = new ArrayList<>();
                    headers.add(entry.getValue());
                } else {
                    headers.add(entry.getValue());
                }
                uploadHeaderNameKeys.put(key, headers);
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            Map<String, String> meta = this.uploadData.getMeta();
            meta.put(ExcelConstants.sheetUploadHeaders, MyJsonUtil.toJsonString(this.uploadHeaderNameKeys));
            countDownLatch.countDown();
        }

        public DataGroup.Data getData() {
            try {
                this.countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return uploadData;
        }
    }
}

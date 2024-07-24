package io.github.chenyilei2016.ac.core.excel.style;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;
import com.google.common.collect.Lists;
import io.github.chenyilei2016.ac.core.excel.header.ColumnHeaders;
import io.github.chenyilei2016.ac.core.file.DataGroup;
import io.github.chenyilei2016.ac.core.file.FileContext;

import java.util.Collections;
import java.util.List;

/**
 * 默认对象excel的修饰
 */
public class DefaultExcelWriteHandlerProvider implements ExcelWriteHandlerProvider {

    @Override
    public List<WriteHandler> provide(ColumnHeaders columnHeaders, DataGroup.Data data, FileContext fileContext) {
//        /        String runtimeParam = fileContext.getMainTask().getRuntimeParam();
//        Map<String, String> runtimeMap = JsonUtil.toMap(runtimeParam);
//        String fileType = runtimeMap.get("fileType");
//        if ("CSV".equalsIgnoreCase(fileType)) {
//            return Collections.emptyList();
//        }
        if (ExcelTypeEnum.CSV == fileContext.readExcelType()) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(
                new CellSelectorWriteHandler(columnHeaders, data),
                new ColumnWidthStyleStrategy(columnHeaders)
        );
    }
}

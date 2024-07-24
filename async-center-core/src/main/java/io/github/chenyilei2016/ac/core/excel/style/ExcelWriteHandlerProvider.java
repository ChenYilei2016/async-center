package io.github.chenyilei2016.ac.core.excel.style;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;
import io.github.chenyilei2016.ac.core.excel.header.ColumnHeaders;
import io.github.chenyilei2016.ac.core.file.DataGroup;
import io.github.chenyilei2016.ac.core.file.FileContext;
import org.springframework.util.FastByteArrayOutputStream;

import java.util.List;

public interface ExcelWriteHandlerProvider {

    List<WriteHandler> provide(ColumnHeaders columnHeaders, DataGroup.Data data, FileContext fileContext);

    default ExcelWriter provideExcelWriter(ColumnHeaders columnHeaders, FileContext fileContext) {
        FastByteArrayOutputStream output = new FastByteArrayOutputStream(10240);
        ExcelTypeEnum excelTypeEnum = fileContext.readExcelType();
        return EasyExcel.write(output).excelType(excelTypeEnum).build();
    }
}

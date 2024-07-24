package io.github.chenyilei2016.ac.core.file;

import com.alibaba.excel.support.ExcelTypeEnum;
import io.github.chenyilei2016.ac.core.common.enums.AcExcelTypeEnum;
import lombok.Data;

@Data
public class FileContext {


    public ExcelTypeEnum readExcelType() {
        return ExcelTypeEnum.XLSX;
    }
}

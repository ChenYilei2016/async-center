package io.github.chenyilei2016.ac.core.file;

import com.alibaba.excel.support.ExcelTypeEnum;
import io.github.chenyilei2016.ac.core.common.enums.AcExcelTypeEnum;
import lombok.Data;

@Data
public class FileContext {

    /**
     * todo: 一些任务信息
     */
    public String jobId() {
        return "tmp_todo_";
    }

    /**
     * todo: 能够获取具体的excel类型
     */
    public ExcelTypeEnum readExcelType() {
        return ExcelTypeEnum.XLSX;
    }
}

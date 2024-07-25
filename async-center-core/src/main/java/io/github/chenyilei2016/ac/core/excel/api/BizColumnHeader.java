package io.github.chenyilei2016.ac.core.excel.api;

import java.util.List;

/**
 * @author chenyilei
 */
public interface BizColumnHeader {

    List<String> getHeaderName();

    String getHeaderNameKey();

    String getFieldName();

    String getDataType();

    boolean isDynamicColumn();

    String getDynamicColumnKey();

    String getType();

    String getGroupName();

    Integer getGroupIndex();

    boolean isErrorHeader();

    boolean isRequired();

    Integer getColumnWidth();

    List<String> getValues();

    Integer getHeaderRowCount();

}

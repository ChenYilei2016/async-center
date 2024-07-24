package io.github.chenyilei2016.ac.core.excel.header;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenyilei
 */
public interface ColumnHeaders extends Serializable {

    List<ColumnHeader> getColumnHeaders();

    ColumnHeader getColumnHeaderByFieldName(String fieldName);

    ColumnHeader getColumnHeaderByHeaderName(List<String> headerName);

    ColumnHeader getColumnHeaderByHeaderNameKey(String headerNameKey);

    ColumnHeader getHeaderByIndex(Integer index);

    Integer getHeaderRowCount(Integer groupIndex);

    List<ColumnHeader> getHeadersByGroup(Integer groupIndex);

    ColumnHeader getHeaderByGroupAndColumn(Integer groupIndex, Integer columnIndex);

    void fromJson(String json);

    String toJson();
}

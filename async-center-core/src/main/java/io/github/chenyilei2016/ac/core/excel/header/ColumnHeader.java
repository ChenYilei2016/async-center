package io.github.chenyilei2016.ac.core.excel.header;


import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author chenyilei
 */
public interface ColumnHeader extends Comparable<ColumnHeader> {

    String headerSplit = "/:/";

    Integer getIndex();

    void setIndex(Integer headerName);

    List<String> getHeaderName();

    String getHeaderNameKey();

    void setHeaderName(List<String> headerName);

    Integer maxHeaderRowCount();

    String getFieldName();

    void setFieldName(String fieldName);

    String getType();

    void setType(String type);

    Boolean getDynamicColumn();

    void setDynamicColumn(Boolean dynamicColumn);

    String getDynamicColumnKey();

    void setDynamicColumnKey(String dynamicColumnKey);

    void setGroupName(String groupName);

    String getGroupName();

    void setGroupIndex(Integer groupIndex);

    Integer getGroupIndex();

    void setErrorHeader(Boolean errorHeader);

    Boolean getErrorHeader();

    Boolean getIgnoreHeader();

    void setIgnoreHeader(Boolean ignoreHeader);

    Boolean getRequired();

    void setRequired(Boolean required);


    Integer getColumnWidth();

    void setColumnWidth(Integer columnWidth);

    List<String> getValues();

    void setValues(List<String> values);

    @Override
    default int compareTo(@NotNull ColumnHeader o) {
        return this.getIndex().compareTo(o.getIndex());
    }
}

package io.github.chenyilei2016.ac.core.excel.header.impl;

import com.google.common.collect.Lists;
import io.github.chenyilei2016.ac.core.excel.header.ColumnHeader;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author chenyilei
 */
@Getter
@Setter
public class ColumnHeaderImpl implements ColumnHeader {

    private Integer index;

    private List<String> headerName;

    private String headerNameKey;

    private String fieldName;

    private String type;

    private Boolean dynamicColumn;

    private String dynamicColumnKey;

    private String groupName;

    private Integer groupIndex;

    private Boolean errorHeader;

    private Boolean ignoreHeader;

    private Boolean required;

    private Integer columnWidth;

    private List<String> values;

    @Override
    public Integer maxHeaderRowCount() {
        return this.headerName.size();
    }

    @Override
    public void setHeaderName(List<String> headerName) {
        this.headerName = headerName;
        this.setHeaderNameKey(this.headerName);
    }

    public void setHeaderName(String headerName) {
        this.setHeaderName(Lists.newArrayList(headerName));
        this.setHeaderNameKey(this.headerName);
    }

    public void setHeaderNameKey(List<String> headerName) {
        String key = String.join(ColumnHeader.headerSplit, headerName);
        this.setHeaderNameKey(key);
    }

    public void setHeaderNameKey(String headerNameKey) {
        this.headerNameKey = headerNameKey;
    }
}

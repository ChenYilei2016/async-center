package io.github.chenyilei2016.ac.core.excel.header.impl;


import io.github.chenyilei2016.ac.core.common.utils.MyJsonUtil;
import io.github.chenyilei2016.ac.core.excel.header.ColumnHeader;
import io.github.chenyilei2016.ac.core.excel.header.ColumnHeaders;

import java.util.*;

/**
 * @author chenyilei
 */
public class ColumnHeadersImpl implements ColumnHeaders {

    private static final long serialVersionUID = 6643692043129727292L;

    private List<ColumnHeader> columnHeaders;

    private Map<String, ColumnHeader> fieldNameIndexMap;

    private Map<Integer, ColumnHeader> columnIndexIndexMap;

    private Map<String, ColumnHeader> headerNameKeyIndexMap;

    private Map<List<String>, ColumnHeader> headerNameIndexMap;

    private Map<Integer, Map<Integer, ColumnHeader>> groupIndexMap;

    public ColumnHeadersImpl() {
    }


    public ColumnHeadersImpl(List<ColumnHeader> columnHeaders) {
        loadColumnHeaders(columnHeaders);
    }

    private void loadColumnHeaders(List<ColumnHeader> columnHeaders) {
        this.columnHeaders = columnHeaders;
        this.fieldNameIndexMap = new HashMap<>(columnHeaders.size() * 2);
        this.columnIndexIndexMap = new HashMap<>(columnHeaders.size() * 2);
        this.headerNameIndexMap = new HashMap<>(columnHeaders.size() * 2);
        this.headerNameKeyIndexMap = new HashMap<>(columnHeaders.size() * 2);

        List<Integer> groupIndex = new ArrayList<>();
        for (ColumnHeader columnHeader : columnHeaders) {
            this.fieldNameIndexMap.put(columnHeader.getFieldName(), columnHeader);
            this.columnIndexIndexMap.put(columnHeader.getIndex(), columnHeader);
            this.headerNameIndexMap.put(columnHeader.getHeaderName(), columnHeader);
            this.headerNameKeyIndexMap.put(columnHeader.getHeaderNameKey(), columnHeader);

            if (!groupIndex.contains(columnHeader.getGroupIndex())) {
                groupIndex.add(columnHeader.getGroupIndex());
            }
        }

        groupIndex.sort(Comparator.comparingInt(o -> o));
        this.groupIndexMap = new HashMap<>(4);
        if (groupIndex.size() == 1 && groupIndex.get(0) == -1) {
            Map<Integer, ColumnHeader> headerMap = groupIndexMap.computeIfAbsent(0, k -> new HashMap<>());
            for (ColumnHeader columnHeader : columnHeaders) {
                headerMap.put(headerMap.size(), columnHeader);
            }
        } else {
            for (Integer index : groupIndex) {
                if (index == -1) {
                    continue;
                }
                Map<Integer, ColumnHeader> headerMap = groupIndexMap.computeIfAbsent(index, k -> new HashMap<>());
                for (ColumnHeader columnHeader : columnHeaders) {
                    if (columnHeader.getGroupIndex() == -1 || columnHeader.getGroupIndex().equals(index)) {
                        headerMap.put(headerMap.size(), columnHeader);
                    }
                }
            }
        }
    }

    @Override
    public List<ColumnHeader> getColumnHeaders() {
        return this.columnHeaders;
    }

    @Override
    public ColumnHeader getColumnHeaderByFieldName(String fieldName) {
        return this.fieldNameIndexMap.get(fieldName);
    }

    @Override
    public ColumnHeader getColumnHeaderByHeaderName(List<String> headerName) {
        return this.headerNameIndexMap.get(headerName);
    }

    @Override
    public ColumnHeader getColumnHeaderByHeaderNameKey(String headerNameKey) {
        return headerNameKeyIndexMap.get(headerNameKey);
    }


    @Override
    public ColumnHeader getHeaderByIndex(Integer index) {
        return this.columnIndexIndexMap.get(index);
    }

    @Override
    public Integer getHeaderRowCount(Integer groupIndex) {
        Integer max = 1;
        for (ColumnHeader columnHeader : columnHeaders) {
            if ((columnHeader.getGroupIndex() != -1) && !columnHeader.getGroupIndex().equals(groupIndex)) {
                continue;
            }
            Integer headerRowCount = columnHeader.maxHeaderRowCount();
            if (headerRowCount > max) {
                max = headerRowCount;
            }
        }
        return max;
    }

    @Override
    public List<ColumnHeader> getHeadersByGroup(Integer groupIndex) {
        Map<Integer, ColumnHeader> headerMap = this.groupIndexMap.get(groupIndex);
        Collection<ColumnHeader> values = headerMap.values();
        return new ArrayList<>(values);
    }

    @Override
    public ColumnHeader getHeaderByGroupAndColumn(Integer groupIndex, Integer columnIndex) {
        return this.groupIndexMap.get(groupIndex).get(columnIndex);
    }

    @Override
    public void fromJson(String json) {
        List<ColumnHeader> columnHeaderList = MyJsonUtil.toArrayObject(json, ColumnHeader.class);
        loadColumnHeaders(columnHeaderList);
    }

    @Override
    public String toJson() {
        return MyJsonUtil.toJsonString(columnHeaders);
    }

}

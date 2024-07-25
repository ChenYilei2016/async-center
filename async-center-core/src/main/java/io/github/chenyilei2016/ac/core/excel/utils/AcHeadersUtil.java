package io.github.chenyilei2016.ac.core.excel.utils;

import io.github.chenyilei2016.ac.core.annotation.ViewField;
import io.github.chenyilei2016.ac.core.excel.api.BizColumnHeader;
import io.github.chenyilei2016.ac.core.excel.api.BizColumnHeaders;
import io.github.chenyilei2016.ac.core.excel.api.BizDynamicColumnHeader;
import io.github.chenyilei2016.ac.core.excel.api.BizDynamicColumnHeaders;
import io.github.chenyilei2016.ac.core.excel.header.ColumnHeader;
import io.github.chenyilei2016.ac.core.excel.header.ColumnHeaders;
import io.github.chenyilei2016.ac.core.excel.header.impl.ColumnHeaderImpl;
import io.github.chenyilei2016.ac.core.excel.header.impl.ColumnHeadersImpl;
import io.github.chenyilei2016.extension.spi.utils.MyTypeUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author chenyilei
 * @since 2024/07/24 20:36
 */
public class AcHeadersUtil {

    public static <T> ColumnHeaders buildHeaders(BizColumnHeaders bizColumnHeaders,
                                                 Class<T> viewClass,
                                                 BizDynamicColumnHeaders bizDynamicColumnHeaders) {
        ColumnHeaders columnHeaders;
        if (bizColumnHeaders != null) {
            columnHeaders = buildHeaders(bizColumnHeaders, bizDynamicColumnHeaders);
        } else {
            columnHeaders = buildHeaders(viewClass, bizDynamicColumnHeaders);
        }
        for (ColumnHeader columnHeader : columnHeaders.getColumnHeaders()) {
            if (columnHeader.getIgnoreHeader() == null) {
                columnHeader.setIgnoreHeader(false);
            }
        }
        return columnHeaders;
    }

    private static ColumnHeaders buildHeaders(BizColumnHeaders bizColumnHeaders,
                                              BizDynamicColumnHeaders bizDynamicColumnHeaders) {
        List<BizColumnHeader> bizColumnHeaderList = bizColumnHeaders.getBizColumnHeaders();

        List<ColumnHeader> columnHeaders = new ArrayList<>(bizColumnHeaderList.size());

        int index = 1;
        for (BizColumnHeader bizColumnHeader : bizColumnHeaderList) {
            if (bizColumnHeader.isDynamicColumn()) {
                String fieldName = bizColumnHeader.getFieldName();
                BizDynamicColumnHeader bizDynamicColumnHeader = bizDynamicColumnHeaders.getBizDynamicColumnHeaderByFiledName(fieldName);
                List<BizColumnHeader> flatColumnHeaders = bizDynamicColumnHeader.getFlatColumnHeaders();
                for (BizColumnHeader flatColumnHeader : flatColumnHeaders) {
                    ColumnHeaderImpl columnHeader = new ColumnHeaderImpl();
                    String flatColumnFieldName = flatColumnHeader.getFieldName();
                    columnHeader.setFieldName(flatColumnFieldName == null ? bizDynamicColumnHeader.getFieldName() : flatColumnFieldName);
                    columnHeader.setHeaderName(flatColumnHeader.getHeaderName());
                    columnHeader.setType(flatColumnHeader.getType());
                    columnHeader.setIndex(index);
                    columnHeader.setDynamicColumn(true);
                    columnHeader.setDynamicColumnKey(flatColumnHeader.getDynamicColumnKey());
                    Integer groupIndex = flatColumnHeader.getGroupIndex() == null ? bizColumnHeader.getGroupIndex() : flatColumnHeader.getGroupIndex();
                    columnHeader.setGroupIndex(groupIndex);
                    columnHeader.setGroupName(flatColumnHeader.getGroupName());
                    columnHeader.setErrorHeader(flatColumnHeader.isErrorHeader());
                    columnHeader.setRequired(flatColumnHeader.isRequired());
                    columnHeader.setColumnWidth(flatColumnHeader.getColumnWidth());
                    columnHeader.setValues(flatColumnHeader.getValues());
                    columnHeaders.add(columnHeader);
                    index++;
                }
            } else {
                ColumnHeaderImpl columnHeader = new ColumnHeaderImpl();
                columnHeader.setFieldName(bizColumnHeader.getFieldName());
                columnHeader.setHeaderName(bizColumnHeader.getHeaderName());
                columnHeader.setType(bizColumnHeader.getDataType());
                columnHeader.setDynamicColumn(false);
                columnHeader.setIndex(index);
                columnHeader.setGroupIndex(bizColumnHeader.getGroupIndex());
                columnHeader.setGroupName(bizColumnHeader.getGroupName());
                columnHeader.setErrorHeader(bizColumnHeader.isErrorHeader());
                columnHeader.setRequired(bizColumnHeader.isRequired());
                columnHeader.setColumnWidth(bizColumnHeader.getColumnWidth());
                columnHeader.setValues(bizColumnHeader.getValues());
                columnHeaders.add(columnHeader);
                index++;
            }
        }

        ColumnHeadersImpl headers = new ColumnHeadersImpl(columnHeaders);
        return headers;
    }

    private static <T> ColumnHeaders buildHeaders(Class<T> viewClass,
                                                  BizDynamicColumnHeaders bizDynamicColumnHeaders) {
        List<Field> fields = resolveTargetFields(viewClass);

        List<Field> dataFields = fields.stream()
                .filter(f -> Objects.nonNull(f.getAnnotation(ViewField.class)))
                .sorted((left, right) -> {
                    ViewField lAnnotation = left.getAnnotation(ViewField.class);
                    ViewField rAnnotation = right.getAnnotation(ViewField.class);
                    return Integer.compare(lAnnotation.index(), rAnnotation.index());
                }).collect(Collectors.toList());

        List<ColumnHeader> columnHeaders = new ArrayList<>(dataFields.size());
        int index = 1;
        for (Field dataField : dataFields) {
            ViewField viewField = dataField.getAnnotation(ViewField.class);
            if (viewField.isDynamicColumn()) {
                String fieldName = dataField.getName();
                BizDynamicColumnHeader bizDynamicColumnHeader = bizDynamicColumnHeaders.getBizDynamicColumnHeaderByFiledName(fieldName);
                List<BizColumnHeader> flatColumnHeaders = bizDynamicColumnHeader.getFlatColumnHeaders();
                for (BizColumnHeader flatColumnHeader : flatColumnHeaders) {
                    ColumnHeaderImpl columnHeader = new ColumnHeaderImpl();
                    String flatColumnFieldName = flatColumnHeader.getFieldName();
                    columnHeader.setFieldName(flatColumnFieldName == null ? bizDynamicColumnHeader.getFieldName() : flatColumnFieldName);
                    columnHeader.setHeaderName(flatColumnHeader.getHeaderName());
                    columnHeader.setType(flatColumnHeader.getType());
                    columnHeader.setIndex(index);
                    columnHeader.setDynamicColumn(true);
                    columnHeader.setDynamicColumnKey(flatColumnHeader.getDynamicColumnKey());
                    Integer groupIndex = flatColumnHeader.getGroupIndex() == null ? viewField.groupIndex() : flatColumnHeader.getGroupIndex();
                    columnHeader.setGroupIndex(groupIndex);
                    columnHeader.setGroupName(flatColumnHeader.getGroupName());
                    columnHeader.setErrorHeader(flatColumnHeader.isErrorHeader());
                    columnHeader.setRequired(flatColumnHeader.isRequired());
                    columnHeaders.add(columnHeader);
                    index++;
                }
            } else {
                ColumnHeaderImpl columnHeader = new ColumnHeaderImpl();
                columnHeader.setFieldName(dataField.getName());
                columnHeader.setHeaderName(Arrays.asList(viewField.headerName()));
                columnHeader.setType(viewField.type());
                columnHeader.setIndex(index);
                columnHeader.setDynamicColumn(false);
                columnHeader.setGroupIndex(viewField.groupIndex());
                columnHeader.setGroupName(viewField.groupName());
                columnHeader.setErrorHeader(viewField.isErrorHeader());
                columnHeader.setRequired(viewField.isRequired());
                columnHeader.setColumnWidth(viewField.columnWidth());
                columnHeader.setValues(Arrays.asList(viewField.values()));
                columnHeaders.add(columnHeader);
                index++;
            }
        }
        ColumnHeadersImpl headers = new ColumnHeadersImpl(columnHeaders);
        return headers;
    }

    private static List<Field> resolveTargetFields(Class targetClass) {
        List<Field> declaredFields = MyTypeUtils.getDeclaredFields(targetClass);
        List<Field> targetFields = new ArrayList<>(declaredFields.size());
        for (Field declaredField : declaredFields) {
            if (!Modifier.isStatic(declaredField.getModifiers())) {
                targetFields.add(declaredField);
            }
        }
        return targetFields;
    }
}

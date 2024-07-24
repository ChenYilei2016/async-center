package io.github.chenyilei2016.ac.core.excel.style;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.style.column.AbstractHeadColumnWidthStyleStrategy;
import io.github.chenyilei2016.ac.core.excel.header.ColumnHeader;
import io.github.chenyilei2016.ac.core.excel.header.ColumnHeaders;

/**
 * 列宽度策略
 *
 * @author chenyilei
 */
public class ColumnWidthStyleStrategy extends AbstractHeadColumnWidthStyleStrategy {

    private ColumnHeaders columnHeaders;

    public ColumnWidthStyleStrategy(ColumnHeaders columnHeaders) {
        this.columnHeaders = columnHeaders;
    }

    @Override
    protected Integer columnWidth(Head head, Integer columnIndex) {
        ColumnHeader headerByIndex = columnHeaders.getHeaderByIndex(columnIndex + 1);
        final Integer columnWidth = headerByIndex.getColumnWidth();
        return columnWidth == null ? 25 : columnWidth;
    }


}
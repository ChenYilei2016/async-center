package io.github.chenyilei2016.ac.core.excel.api;

import java.util.List;

/**
 * @author chenyilei
 */
public interface BizDynamicColumnHeader {
    String getFieldName();

    List<BizColumnHeader> getFlatColumnHeaders();
}

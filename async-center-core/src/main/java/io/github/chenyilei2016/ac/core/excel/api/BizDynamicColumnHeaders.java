package io.github.chenyilei2016.ac.core.excel.api;

import java.util.List;

/**
 * @author chenyilei
 */
public interface BizDynamicColumnHeaders {

    List<BizDynamicColumnHeader> getBizDynamicColumnHeaders();

    BizDynamicColumnHeader getBizDynamicColumnHeaderByFiledName(String filedName);
}

package io.github.chenyilei2016.ac.core.excel.api.impl;

import io.github.chenyilei2016.ac.core.excel.api.BizDynamicColumnHeader;
import io.github.chenyilei2016.ac.core.excel.api.BizDynamicColumnHeaders;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author chenyilei
 */
@Getter
@Setter
public class BizDynamicColumnHeadersImpl implements BizDynamicColumnHeaders {

    private List<BizDynamicColumnHeader> bizDynamicColumnHeaders;

    @Override
    public BizDynamicColumnHeader getBizDynamicColumnHeaderByFiledName(String filedName) {
        for (BizDynamicColumnHeader bizDynamicColumnHeader : bizDynamicColumnHeaders) {
            if (bizDynamicColumnHeader.getFieldName().equals(filedName)) {
                return bizDynamicColumnHeader;
            }
        }
        return null;
    }
}

package io.github.chenyilei2016.ac.core.excel.api.impl;

import io.github.chenyilei2016.ac.core.excel.api.BizColumnHeader;
import io.github.chenyilei2016.ac.core.excel.api.BizDynamicColumnHeader;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author chenyilei
 */
@Getter
@Setter
public class BizDynamicColumnHeaderImpl implements BizDynamicColumnHeader {

    private String fieldName;

    private List<BizColumnHeader> flatColumnHeaders;

}

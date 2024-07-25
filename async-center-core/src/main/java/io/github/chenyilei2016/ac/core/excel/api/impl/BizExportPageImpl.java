package io.github.chenyilei2016.ac.core.excel.api.impl;

import io.github.chenyilei2016.ac.core.excel.api.BizExportPage;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author chenyilei
 */
@Getter
@Setter
public class BizExportPageImpl implements BizExportPage {

    private static final long serialVersionUID = 2752090113053083891L;

    private Integer no;

    private Integer offset;

    private Integer size;

    private Map<String, String> attributes;
}

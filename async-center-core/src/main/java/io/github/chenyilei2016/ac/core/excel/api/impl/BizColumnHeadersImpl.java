package io.github.chenyilei2016.ac.core.excel.api.impl;

import io.github.chenyilei2016.ac.core.excel.api.BizColumnHeader;
import io.github.chenyilei2016.ac.core.excel.api.BizColumnHeaders;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * BizColumnHeadersImpl
 *
 * @author chenyilei
 */
@Getter
@Setter
public class BizColumnHeadersImpl implements BizColumnHeaders {

    List<BizColumnHeader> bizColumnHeaders;

}

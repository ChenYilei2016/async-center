package io.github.chenyilei2016.ac.core.excel.api;

import java.util.List;

/**
 * @author chenyilei
 */
public interface BizDataGroup<VIEW> {

    List<BizData<VIEW>> getBizData();

}

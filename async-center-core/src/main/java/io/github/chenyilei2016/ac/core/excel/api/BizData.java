package io.github.chenyilei2016.ac.core.excel.api;

import java.util.List;
import java.util.Map;

/**
 * @author chenyilei
 */
public interface BizData<VIEW> {

    String getCode();

    Map<String, String> getMeta();

    List<BizDataItem<VIEW>> getItems();
}

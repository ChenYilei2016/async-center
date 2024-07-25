package io.github.chenyilei2016.ac.core.excel.api;

/**
 * @author chenyilei
 */
public interface BizDataItem<VIEW> {

    String getCode();

    VIEW getData();
}

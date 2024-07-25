package io.github.chenyilei2016.ac.core.excel.api.impl;

import io.github.chenyilei2016.ac.core.excel.api.BizData;
import io.github.chenyilei2016.ac.core.excel.api.BizDataGroup;
import io.github.chenyilei2016.ac.core.excel.api.BizDataItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author chenyilei
 */
@Getter
@Setter
public class BizDataGroupImpl<T> implements BizDataGroup<T> {

    List<BizData<T>> data;

    @Override
    public List<BizData<T>> getBizData() {
        return data;
    }

    @Getter
    @Setter
    public static class Data<T> implements BizData<T> {

        private String code;

        private Map<String, String> meta;

        private List<BizDataItem<T>> items;
    }

    @Getter
    @Setter
    public static class Item<T> implements BizDataItem<T> {

        private String code;

        private T data;
    }

}
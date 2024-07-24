package io.github.chenyilei2016.ac.core.file;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author chenyilei
 * @date 2023/09/28 10:26
 */
@Getter
@Setter
public class DataGroup {

    List<Data> data;

    public DataGroup() {
    }

    @Getter
    @Setter
    public static class Data {

        // sheetName + "@" + sheetNo;
        private String code;

        private List<Item> items;

        private Map<String, String> meta;
    }


    @Getter
    @Setter
    @ToString
    public static class Item {

        //sheetName + "@" + sheetNo + "@" + rowNo
        private String code;

        private String sheetNo;

        //field : value
        //index : value
        private Map<String, Object> values;
    }
}

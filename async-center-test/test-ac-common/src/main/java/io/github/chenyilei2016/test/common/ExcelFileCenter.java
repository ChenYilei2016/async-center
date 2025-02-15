package io.github.chenyilei2016.test.common;

import lombok.SneakyThrows;

import java.io.File;
import java.net.URLDecoder;

/**
 * @author chenyilei
 * @since 2024/07/24 15:59
 */
public abstract class ExcelFileCenter {

    /**
     *  @formatter:off
     *
     *      titlehead
     *  name  age
     *  {}    {}
     *  {}    {}
     *
     * @formatter:on
     */
    public static File 两行head_第一行全占用;

    @SneakyThrows
    public static String simpleGetResponse(String path) {
        return URLDecoder.decode(ExcelFileCenter.class.getClassLoader().getResource(path).getPath(), "utf-8");
    }

    static {
        两行head_第一行全占用 = new File(simpleGetResponse("excel/多行Head.xlsx"));

    }


}

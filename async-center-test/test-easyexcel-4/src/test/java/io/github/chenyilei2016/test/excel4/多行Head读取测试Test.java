package io.github.chenyilei2016.test.excel4;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.listener.PageReadListener;
import io.github.chenyilei2016.test.common.ExcelFileCenter;
import lombok.Data;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author chenyilei
 * @since 2024/07/24 16:03
 */

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
public class 多行Head读取测试Test {

    @Test
    public void 测试读取() {
        List<Object> objects = EasyExcel.read(ExcelFileCenter.两行head_第一行全占用)
                .headRowNumber(2)
                .doReadAllSync();

        System.err.println(objects);
    }


    @Data
    public static class Tmp_使用Class读取 {
        //        @ExcelProperty(value = "titlehead")
//        private String testHeadLine;
        @ExcelProperty(value = "name", index = 0)
        private String name;
        @ExcelProperty(value = "age", index = 1)
        private String age;
    }

    @Test
    public void 使用Class读取_1() {
        List<Object> objects = EasyExcel.read(ExcelFileCenter.两行head_第一行全占用)
                .head(Tmp_使用Class读取.class)
                .headRowNumber(1) // 1 [多行Head读取测试Test.Tmp_使用Class读取(name=name, age=age), 多行Head读取测试Test.Tmp_使用Class读取(name=1, age=age1), 多行Head读取测试Test.Tmp_使用Class读取(name=2, age=age2), 多行Head读取测试Test.Tmp_使用Class读取(name=3, age=age3)]
//                .headRowNumber(2) // 2 [多行Head读取测试Test.Tmp_使用Class读取(name=1, age=age1), 多行Head读取测试Test.Tmp_使用Class读取(name=2, age=age2), 多行Head读取测试Test.Tmp_使用Class读取(name=3, age=age3)]
                .doReadAllSync();
        System.err.println(objects);
    }

    @Test
    public void 使用Class读取_2() {
        EasyExcel.read(ExcelFileCenter.两行head_第一行全占用)
                .head(Tmp_使用Class读取.class)
                .headRowNumber(2)
                .registerReadListener(new PageReadListener(list -> {
                    System.err.println(list);
                }) {
                    @Override
                    public void invokeHead(Map headMap, AnalysisContext context) {
                        super.invokeHead(headMap, context);
                    }

                    @Override
                    public void extra(CellExtra extra, AnalysisContext context) {
                        super.extra(extra, context);
                    }
                }).doReadAll();
    }
}
package io.github.chenyilei2016.ac.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author chenyilei
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewField {

    /**
     * excel header的名称
     */
    String[] headerName();

    String type() default "AUTO";

    int index() default 1;

    /**
     * @ViewField(headerName = "查询参数决定的动态列", isDynamicColumn = true)
     * private Map<String, Object> dynamicColumnByQueryParams;
     * @ViewField(headerName = "一些系统参数决定的动态列，如日期", isDynamicColumn = true)
     * private Map<String, Object> dynamicColumnByDate;
     */
    boolean isDynamicColumn() default false;

    /**
     * @ViewField(headerName = {"不同性别问题", "男性问题1"}, groupName = "男性问题1", groupIndex = 0)
     * private String manQuestion1;
     * @ViewField(headerName = {"不同性别问题", "男性问题2"}, groupName = "男性问题2", groupIndex = 0)
     * private String manQuestion2;
     * @ViewField(headerName = {"不同性别问题", "女性问题1"}, groupName = "女性问题1", groupIndex = 1)
     * private String womenQuestion1;
     * @ViewField(headerName = {"不同性别问题", "女性问题2"}, groupName = "女性问题2", groupIndex = 1)
     * private String womenQuestion2;
     */
    int groupIndex() default -1;

    String groupName() default "";

    boolean isErrorHeader() default false;

    boolean isRequired() default true;

    int columnWidth() default 20;

    /**
     * 限制可选值
     * @ViewField(headerName = {"基本信息", "性别"}, values = {"男", "女", "其他"})
     */
    String[] values() default {};
}

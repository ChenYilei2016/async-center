package io.github.chenyilei2016.ac.core.excel.api.impl;

import io.github.chenyilei2016.ac.core.excel.api.BizUser;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chenyilei
 */
@Getter
@Setter
public class BizUserImpl implements BizUser {

    /**
     * 业务用户ID
     */
    private String bizUserId;

    /**
     * 业务用户姓名
     */
    private String bizUserName;

    /**
     * 业务用户所属租户
     */
    private String bizUserTenant;

    /**
     * 业务用户所属组织
     */
    private String bizUserOrg;

    /**
     * 业务用户key
     */
    private String bizUserKey;

    /**
     * 业务用户自定义扩展属性
     */
    private String bizUserFeature;
}

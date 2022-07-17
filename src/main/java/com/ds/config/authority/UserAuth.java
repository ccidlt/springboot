package com.ds.config.authority;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户权限查询返回结构
 */
@Data
public class UserAuth implements Serializable {
    /**
     * 是否所有数据
     */
    private Boolean all;

    /**
     * 是否无权限
     */
    private Boolean none;

    /**
     * 限制的权限值
     */
    private List<Integer> ids;
}

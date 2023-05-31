package com.ds.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 请求包含分页信息
 * @Author lt
 * @Date 2023/5/31 16:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasePage implements Serializable {
    private static final long serialVersionUID = 231669407856116249L;

    protected int pageNo = 1;

    protected int pageSize = 20;
}

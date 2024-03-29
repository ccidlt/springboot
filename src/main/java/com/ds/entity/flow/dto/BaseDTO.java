package com.ds.entity.flow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: 请求内容基础类
 * @Author lt
 * @Date 2023/6/7 10:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = -512560835068732040L;

    @ApiModelProperty(value = "操作人id")
    @NotNull
    private Long personId;

    @ApiModelProperty(value = "操作人工号")
    @NotBlank
    private String personCode;

    @ApiModelProperty(value = "操作人姓名")
    private String personName;

    @ApiModelProperty(value = "操作人角色编码")
    private String roleCode;

}

package com.ds.entity.flow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: 请求内容（含分页）基础类
 * @Author lt
 * @Date 2023/6/7 10:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BasePageDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = -99134565508581625L;

    @ApiModelProperty("当前页码")
    @NotNull
    private Integer pageNo = 1;

    @ApiModelProperty("每页大小")
    @NotNull
    private Integer pageSize = 20;

}

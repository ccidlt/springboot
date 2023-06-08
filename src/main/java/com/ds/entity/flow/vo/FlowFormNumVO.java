package com.ds.entity.flow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description:
 * @Author lt
 * @Date 2023/6/7 10:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="FlowFormNumVO对象", description="返回单号等信息")
public class FlowFormNumVO implements Serializable {

    private static final long serialVersionUID = -2341253277599268729L;

    @ApiModelProperty(value = "主体信息主键")
    private Long flowId;

    @ApiModelProperty(value = "单子信息主键")
    private Long flowFormId;

    @ApiModelProperty(value = "单号")
    private String formNum;

}

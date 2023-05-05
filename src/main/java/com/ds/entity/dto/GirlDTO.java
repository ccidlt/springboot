package com.ds.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
@ApiModel(value = "girl", description = "豪杰")
public class GirlDTO implements Serializable {

    private static final long serialVersionUID = 3992815327491959081L;

    @ApiModelProperty("主键")
    private int id;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("好汉主键")
    private int boyId;

}

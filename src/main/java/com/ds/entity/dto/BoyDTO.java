package com.ds.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
@ApiModel(value = "boy", description = "好汉")
public class BoyDTO implements Serializable {

    private static final long serialVersionUID = -4665234258675580949L;

    @ApiModelProperty("主键")
    private int id;

    @ApiModelProperty("名称")
    @NotBlank(message = "名称不能为空") //controller中可以用@Validated注解
    private String name;

    @ApiModelProperty("豪杰")
    private List<GirlDTO> girlList = new ArrayList<>();

}

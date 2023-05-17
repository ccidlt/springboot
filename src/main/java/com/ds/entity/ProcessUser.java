package com.ds.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 流程用户表
 * </p>
 *
 * @author lt
 * @since 2023-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ProcessUser对象", description="流程用户表")
public class ProcessUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;


}

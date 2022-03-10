package com.ds.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("boy")
@ApiModel("boy实体类")
public class Boy implements Serializable {

    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("女朋友们")
    @TableField(exist=false)
    private String girls;

    //自动回填创建时间
    @TableField(value="create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;

    //自动回填修改时间
    @TableField(value="update_time",fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value="修改时间")
    private LocalDateTime updateTime;

    //乐观锁
    @Version
    @ApiModelProperty(value="乐观锁")
    private Integer version;

    //逻辑删除
    @TableLogic
    @ApiModelProperty(value="逻辑删除（0-未删除、1-已删除）")
    private Integer isdelete;

    public Boy(int id, String name, String girls) {
        this.id = id;
        this.name = name;
        this.girls = girls;
    }
}

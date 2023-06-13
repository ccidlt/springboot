package com.ds.entity.flow.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.ds.entity.flow.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Description: 基础类
 * @Author lt
 * @Date 2023/6/7 10:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 5752872762962698922L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JSONField(serializeUsing = ToStringSerializer.class)   //防止Long传输到前端丢失精度问题
    private Long id;

    @ApiModelProperty(value = "扩展字段", hidden = true)
    @TableField(value = "expands", typeHandler = FastjsonTypeHandler.class, exist = false)
    private Map<String, Object> expands;

    @ApiModelProperty(value = "0：正常，1：删除")
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDelete;

    @ApiModelProperty(value = "0：启用，1：停用")
    @TableField(fill = FieldFill.INSERT)
    private Integer isDisable;

    @ApiModelProperty(value = "创建人id")
    private Long createId;

    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "最后修改人id")
    private Long updateId;

    @ApiModelProperty(value = "最后修改人姓名")
    private String updateName;

    @ApiModelProperty(value = "最后修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "版本")
    @Version
    private String dataVersion;

    public void insert(BaseDTO baseDTO){
        this.createId = baseDTO.getPersonId();
        this.createName = baseDTO.getPersonName();
        this.dataVersion = "1.0";
    }

    public void update(BaseDTO baseDTO){
        this.updateId = baseDTO.getPersonId();
        this.updateName = baseDTO.getPersonName();
    }

}

package com.ds.entity.flow.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 响应内容基础类
 * @Author lt
 * @Date 2023/6/7 10:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseVO implements Serializable {

    private static final long serialVersionUID = 4712897953572241816L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "0：正常，1：删除")
    private Integer isDelete;

    @ApiModelProperty(value = "0：启用，1：停用")
    private Integer isDisable;

    @ApiModelProperty(value = "创建人id")
    private Long createId;

    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "最后修改人id")
    private Long updateId;

    @ApiModelProperty(value = "最后修改人姓名")
    private String updateName;

    @ApiModelProperty(value = "最后修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(value = "版本")
    private String dataVersion;

}

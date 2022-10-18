package com.ds.entity.table;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.command.BaseModel;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("abc")
@TableName("abc")
@ApiModel(value="abc", description = "abc对象")
public class Abc extends BaseModel implements Serializable {

    private static final long serialVersionUID=1L;

    @Column(type= MySqlTypeConstant.BIGINT, isKey = true, isNull = false, isAutoIncrement=true, comment = "主键")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;

    @Column(type= MySqlTypeConstant.VARCHAR, comment = "姓名")
    @ApiModelProperty("姓名")
    private String name;

    @Column(type= MySqlTypeConstant.DATETIME, comment = "创建时间")
    @ApiModelProperty("创建时间")
    private Date createTime;
}

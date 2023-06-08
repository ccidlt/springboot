package com.ds.entity.flow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 主体暂存信息
 * </p>
 *
 * @author lt
 * @since 2023-06-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FlowDraftDTO对象", description="主体暂存信息")
public class FlowDraftDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = 3877614824880201274L;

    @ApiModelProperty(value = "请假原因")
    @NotBlank
    @Size(max = 255)
    private String leaveReason;

    @ApiModelProperty(value = "审批人员信息")
    @NotEmpty
    private List<FlowFormPersonDraftDTO> flowFormPersonDraftDTOList;

}

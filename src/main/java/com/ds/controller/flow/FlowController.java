package com.ds.controller.flow;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ds.entity.Result;
import com.ds.entity.flow.dto.FlowDraftDTO;
import com.ds.entity.flow.entity.FlowRole;
import com.ds.service.flow.FlowRoleService;
import com.ds.service.flow.FlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author lt
 * @Date 2023/6/7 11:25
 */
@RestController
@RequestMapping("/flow")
@Api(value = "FlowController", tags = {"审批控制类"})
public class FlowController extends FlowExceptionController {

    @Resource
    private FlowRoleService flowRoleService;
    @Resource
    private FlowService flowService;

    @ApiOperation(value = "根据角色编码获取人员信息", notes = "根据角色编码获取人员信息")
    @ApiImplicitParam(name = "roleCode",value = "角色编码", paramType = "path", dataType = "string", required = true)
    @GetMapping("/getPersonByRoleCode/{roleCode}")
    public Result<?> getPersonByRoleCode(@PathVariable("roleCode") String roleCode){
        return Result.ok(flowRoleService.getOne(new QueryWrapper<FlowRole>().lambda().eq(FlowRole::getRoleCode, roleCode).last("limit 1")));
    }

    @ApiOperation(value = "信息暂存", notes = "信息暂存")
    @PostMapping("/saveDraft")
    public Result<?> saveDraft(@Validated @RequestBody FlowDraftDTO flowDraftDTO){
        return Result.ok(flowService.saveDraft(flowDraftDTO));
    }

    @ApiOperation(value = "列表信息", notes = "列表信息")
    @PostMapping("/queryList")
    public Result<?> queryList(){
        return Result.ok(flowService.queryList());
    }

}

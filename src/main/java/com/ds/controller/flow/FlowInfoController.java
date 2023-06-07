package com.ds.controller.flow;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ds.entity.Result;
import com.ds.entity.flow.entity.FlowInfo;
import com.ds.service.flow.FlowInfoService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 主体信息表 前端控制器
 * </p>
 *
 * @author lt
 * @since 2023-06-07
 */
@Api(value = "FlowInfoController", tags = "主体信息表", hidden = true)
@ApiIgnore
@RestController
@RequestMapping("/flowInfo")
public class FlowInfoController {

    @Resource
    private FlowInfoService flowInfoService;

    @ApiOperation(value = "查询", notes = "查询")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "服务器内部错误", response = Result.class)
    })
    public Result<List<FlowInfo>>query(){
        return Result.ok(flowInfoService.list());
    }

    @ApiOperation(value = "查询（主键）", notes = "查询（主键）")
    @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "path", dataType = "int")
    @RequestMapping(value = "/queryById/{id}", method = RequestMethod.GET)
    public Result<FlowInfo> queryById(@PathVariable Integer id){
        return Result.ok(flowInfoService.getById(id));
    }

    @ApiOperation(value = "查询（分页）", notes = "查询（分页）")
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    public Result<Page<FlowInfo>>queryPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        return Result.ok(flowInfoService.page(new Page<>(pageNum,pageSize)));
    }

    @ApiOperation(value = "新增、修改", notes = "新增、修改")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<FlowInfo> save(@RequestBody FlowInfo flowInfo){
        flowInfoService.saveOrUpdate(flowInfo);
        return Result.ok(flowInfoService.getById(flowInfo.getId()));
    }

    @ApiOperation(value = "删除（主键）", notes = "删除（主键）")
    @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "path", dataType = "int")
    @RequestMapping(value = "/deleteById/{id}", method = RequestMethod.GET)
    public Result<Boolean> delete(@PathVariable Integer id){
        return Result.ok(flowInfoService.removeById(id));
    }

    @ApiOperation(value = "删除（批量）", notes = "删除（批量）")
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public Result<Boolean> deleteBatch(@RequestBody List<Integer> ids){
        return Result.ok(flowInfoService.removeByIds(ids));
    }

}


package com.ds.controller.setting;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ds.entity.Girl;
import com.ds.entity.Result;
import com.ds.service.GirlService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lt
 * @since 2023-05-05
 */
@Api(value = "girlController", tags = "豪杰操作")
@RestController
@RequestMapping("/girl")
public class GirlController {

    @Resource
    private GirlService girlService;

    @ApiOperation(value = "查询", notes = "查询")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "服务器内部错误", response = Result.class)
    })
    public Result<List<Girl>>query(){
        return Result.ok(girlService.list());
    }

    @ApiOperation(value = "查询（主键）", notes = "查询（主键）")
    @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "path", dataType = "int")
    @RequestMapping(value = "/queryById/{id}", method = RequestMethod.GET)
    public Result<Girl> queryById(@PathVariable Integer id){
        return Result.ok(girlService.getById(id));
    }

    @ApiOperation(value = "查询（分页）", notes = "查询（分页）")
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    public Result<Page<Girl>>queryPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        return Result.ok(girlService.page(new Page<>(pageNum,pageSize)));
    }

    @ApiOperation(value = "新增、修改", notes = "新增、修改")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<Girl> save(@RequestBody Girl girl){
        girlService.saveOrUpdate(girl);
        return Result.ok(girlService.getById(girl.getId()));
    }

    @ApiOperation(value = "删除（主键）", notes = "删除（主键）")
    @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "path", dataType = "int")
    @RequestMapping(value = "/deleteById/{id}", method = RequestMethod.GET)
    public Result<Boolean> delete(@PathVariable Integer id){
        return Result.ok(girlService.removeById(id));
    }

    @ApiOperation(value = "删除（批量）", notes = "删除（批量）")
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public Result<Boolean> deleteBatch(@RequestBody List<Integer> ids){
        return Result.ok(girlService.removeByIds(ids));
    }

}


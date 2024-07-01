package com.ds.controller.setting;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ds.config.repeatsubmit.RepeatSubmitAnno;
import com.ds.entity.Boy;
import com.ds.service.BoyService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/")
@Api(value = "boyController", tags = "好汉操作")
@ApiSupport(author = "lt")
@Slf4j
@RefreshScope
@RepeatSubmitAnno(seconds = 5)
@Validated
public class BoyController {

    private Logger logger = LoggerFactory.getLogger(BoyController.class);

    @Autowired
    @Qualifier("boyService")
    private BoyService boyService;

    @RequestMapping(value = "/getBoys", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@ApiOperation(value="获取Boy表所有数据", response = Boy.class)
    @ApiOperation(value = "获取Boy表所有数据")
    @ApiResponses({
            @ApiResponse(code = 200, message = "操作成功", response = Boy.class),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500, message = "后端程序报错")
    })
    public List<Boy> getBoys() {
        List<Boy> boys = boyService.getBoys();
        logger.info("获取所有的表数据条数={}", boys != null ? boys.size() : 0);
        log.info("获取所有的表数据条数={}", boys != null ? boys.size() : 0);
        return boys;
    }

    @RequestMapping(value = "/getBoysById", method = RequestMethod.GET)
    @ApiOperation("获取Boy表数据(通过id)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "query", dataType = "int")
    })
    public List<Boy> getBoysByParam(@RequestParam(value = "id",required = true) @NotBlank(message = "id不能为空") Integer id) {
        Boy boy = new Boy();
        boy.setId(id);
        return boyService.queryBoy(boy);
    }

    @RequestMapping(value = "/getBoysByParam", method = RequestMethod.GET)
    @ApiOperation("获取Boy表数据")
    @ApiOperationSupport(ignoreParameters = {"version","isdelete"})
    public List<Boy> getBoysByParam(Boy boyDTO) {
        Boy boy = new Boy();
        boy.setName("杨过");
        return boyService.queryBoy(boy);
    }

    @RequestMapping(value = "/addBoy", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增/修改")
    public Boy addBoy(
            @ApiParam(name = "boy", value = "Boy", required = true)
            @Validated({Boy.save.class})
            @RequestBody(required = true) Boy boy) {
        //boy中有id则为修改，没有为新增
        boyService.saveOrUpdate(boy);
        return boyService.getById(boy.getId());
    }

    @RequestMapping(value = "/saveBoy", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    @ApiOperationSupport(ignoreParameters = {"boy.id"})
    /**
     * value：资源名称，必需项
     * blockHandler / blockHandlerClass：blockHandler 指定函数负责处理 BlockException 异常，可选项。blockHandler 函数默认需要和原方法在同一个类中，通过指定 blockHandlerClass 为对应类的 Class 对象，则可以指定其他类中的函数，但注意对应的函数必需为 static 函数，否则无法解析。
     * fallback /fallbackClass：fallback 指定的函数负责处理业务运行的异常，可选项，fallback 函数可以针对所有类型的异常（除了exceptionsToIgnore里面排除掉的异常类型）进行处理。fallback 函数默认需要和原方法在同一个类中，通过指定 fallbackClass 为对应类的 Class 对象，则可以指定指定为其他类的函数，但注意对应的函数必需为 static 函数，否则无法解析。
     * @SentinelResource 的 fallback 负责业务运行的异常，blockHandler 负责 sentinel 配置的违规。
     * @SentinelResource("saveBoy") 是手动设置资源名。sentinel只会扫描controller层的资源，在Sentinel控制台中配置资源名为/saveBoy；service通过注解设置手动设置资源名
     */
    @SentinelResource(value = "saveBoy") //热点参数限流：value对应资源名
    public Boy saveBoy(
            @ApiParam(name = "boy", value = "Boy", required = true)
            @Validated({Boy.save.class})
            @RequestBody(required = true) Boy boy) {
        return boyService.saveBoy(boy);
    }

}

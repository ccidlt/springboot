package com.ds.controller.flow;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author lt
 * @Date 2023/6/7 11:25
 */
@RestController
@RequestMapping("/flow")
@Api(value = "FlowController", tags = {"审批控制类"})
public class FlowController extends FlowExceptionController {


}

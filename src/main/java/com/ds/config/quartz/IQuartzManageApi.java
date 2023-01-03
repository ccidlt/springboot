package com.ds.config.quartz;

import com.ds.entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/quartz/")
public interface IQuartzManageApi {


    /**
     * 添加一个job任务
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    Result addQuartzJob(@RequestBody QuartzJobAddParam param);

    /**
     * 删除一个job任务
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    Result deleteQuartzJob(@RequestBody QuartzJobDeleteParam param);

}

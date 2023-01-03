package com.ds.config.quartz;

import com.ds.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IQuartzManageController implements IQuartzManageApi {

    private static final String GROUP = "email_group";

    private IQuartzActionService quartzActionService;

    @Autowired
    public void setQuartzActionService(IQuartzActionService quartzActionService) {
        this.quartzActionService = quartzActionService;
    }

    /**
     * 添加一个job任务
     */
    @Override
    public Result addQuartzJob(@RequestBody QuartzJobAddParam param) {
        if (param.getKeyId() != null && !param.getKeyId().isEmpty()) {
            // 模拟每隔5s执行一次
            try {
                quartzActionService.addJob(SendEmailJob.class, param.getKeyId(), GROUP, "0/5 * * * * ? ");
            } catch (Exception e) {
                e.printStackTrace();
                Result.fail();
            }
        }
        return Result.ok();
    }

    /**
     * 删除一个job任务
     */
    @Override
    public Result deleteQuartzJob(@RequestBody QuartzJobDeleteParam param) {
        if (param.getKeyId() != null && !param.getKeyId().isEmpty()) {
            // 模拟每隔5s执行一次
            try {
                quartzActionService.deleteJob(param.getKeyId(), GROUP);
            } catch (Exception e) {
                return Result.fail();
            }
        }
        return Result.ok();
    }

}

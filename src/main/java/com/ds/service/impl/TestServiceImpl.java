package com.ds.service.impl;

import com.ds.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("testService")
@Slf4j
public class TestServiceImpl implements TestService {
    @Override
    @Async(value="asyncExecutor")
    public void asyncTask() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("异步任务执行了");
    }
}

package com.ds.config.fileprogress;

import com.ds.utils.HttpServletUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @Author lt
 * @Date 2023/6/12 15:58
 */
@RestController
public class UploadProgress {
    /**
     * 获取上传进度
     *
     * @return
     */
    @GetMapping(value = "/uploadStatus")
    public Object uploadStatus() {
        HttpServletRequest request = HttpServletUtil.getRequest();
        HttpSession session = request.getSession();
        ProgressEntity percent = (ProgressEntity) session.getAttribute("progressEntity");
        // System.out.println("^^^^"+percent.toString());
        //当前时间
        long nowTime = System.currentTimeMillis();
        //已传输的时间
        long time = (nowTime - percent.getStartTime())/1000+1;
        //传输速度 ;单位：byte/秒
        double velocity =((double) percent.getBytesRead())/(double)time;
        //估计总时间
        double totalTime = percent.getContentLength()/velocity;
        //估计剩余时间
        double timeLeft = totalTime - time;
        //已经完成的百分比
        int percent1 = (int)(100 * (double) percent.getBytesRead() / (double) percent.getContentLength());
        //已经完成数单位:m
        double length = ((double) percent.getBytesRead())/1024/1024;
        //总长度  M
        double totalLength = (double) percent.getContentLength()/1024/1024;
        StringBuffer sb = new StringBuffer();
        //拼接上传信息
        sb.append(percent+":"+length+":"+totalLength+":"+velocity+":"+time+":"+totalTime+":"+timeLeft+":"+percent.getItems());
        System.out.println("百分比====>"+percent1);
        System.out.println("已传输时间====>"+time+"------剩余时间--->"+timeLeft+"======已经完成===="+length);
        return sb.toString();
    }
}

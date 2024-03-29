package com.ds.config.fileprogress;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrator
 *
 * 要获得上传文件的实时详细信息，必须继承org.apache.commons.fileupload.ProgressListener类，
 * 获得信息的时候将进度条对象Progress放在该监听器的session对象中
 */
@Component
public class FileUploadProgressListener implements ProgressListener {

    private HttpSession session;

    public void setSession(HttpSession session){
        this.session=session;
        //保存上传状态
        ProgressEntity progressEntity = new ProgressEntity();
        session.setAttribute("progressEntity", progressEntity);
    }

    @Override
    public void update(long bytesRead, long contentLength, int items) {
        ProgressEntity progressEntity = (ProgressEntity) session.getAttribute("progressEntity");
        //已读取数据长度
        progressEntity.setBytesRead(bytesRead);
        //文件总长度
        progressEntity.setContentLength(contentLength);
        //正在保存第几个文件
        progressEntity.setItems(items);
    }
}



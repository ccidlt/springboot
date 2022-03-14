package com.ds.controller;

import com.ds.entity.Boy;
import com.ds.service.UserService;
import com.ds.utils.Result;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
@Api("接口")
@Slf4j
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @RequestMapping(value="/getBoys",method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("获取Boy表所有数据")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code=500,message="后端程序报错")
    })
    public List<Boy> getBoys(){
        List<Boy> boys = userService.getBoys();
        logger.info("获取所有的表数据条数={}",boys != null ? boys.size() : 0);
        log.info("获取所有的表数据条数={}",boys != null ? boys.size() : 0);
        return boys;
    }

    @RequestMapping(value="/getBoysById",method = RequestMethod.GET)
    @ApiOperation("获取Boy表数据(通过id)")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="主键", required = true, paramType = "query", dataType = "Integer")
    })
    public List<Boy> getBoysByParam(@RequestParam("id") Integer id){
        Boy boy = new Boy();
        boy.setId(id);
        return userService.queryBoy(boy);
    }

    @RequestMapping(value="/getBoysByParam",method = RequestMethod.GET)
    @ApiOperation("获取Boy表数据")
    public List<Boy> getBoysByParam(){
        Boy boy = new Boy();
        boy.setName("杨过");
        return userService.queryBoy(boy);
    }

    @Value("${filePath:F:/file/}")
    String filePath;

    @RequestMapping(value="/upload",method = RequestMethod.POST)
    @ApiOperation("文件上传")
    @ApiImplicitParam(name="file",value="文件",required = true,paramType = "query",dataType = "file")
    public Result upload(MultipartFile file, HttpServletRequest request){
        /*ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        MultipartFile file = multipartHttpServletRequest.getFile("file");*/
        Result result = Result.getResult("1","文件上传成功","a");
        String originalFilename = file.getOriginalFilename();
        String hz = originalFilename.substring(originalFilename.lastIndexOf("."));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(new Date());
        String filedir = filePath+format;
        File file1 = new File(filedir);
        if(!file1.exists()){
            file1.mkdirs();
        }
        String filename = UUID.randomUUID().toString().replace("-","") + hz;
        try {
            file.transferTo(new File(filedir,filename));
            result = Result.getResult("1","文件上传成功",format+"/"+filename);
        } catch (IOException e) {
            e.printStackTrace();
            result = Result.getResult("0","文件上传失败");
        }
        return result;
    }

    @RequestMapping(value="/download",method = RequestMethod.POST)
    @ApiOperation("文件下载")
    @ApiImplicitParam(name="path",value="文件路径",required = true,paramType = "query",dataType = "String")
    public HttpServletResponse download(@RequestParam("path") String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(filePath+path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

}

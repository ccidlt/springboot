package com.ds.controller;

import com.alibaba.fastjson.JSONObject;
import com.ds.config.RedisUtils;
import com.ds.entity.FatherAndSon;
import com.ds.entity.User;
import com.ds.service.MyAspectInterface;
import com.ds.service.TestService;
import com.ds.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


@RestController
@Slf4j
public class TestCtroller {

    @Resource
    TestService testService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    UserService userService;

    @RequestMapping(value="/async",method = RequestMethod.GET)
    public ResponseEntity asyncTask(){
        testService.asyncTask();
        User user = new User();
        user.setName("zhangsan");
        user.setAge(18);
        String userstr = JSONObject.toJSONString(user);
        User user2 = JSONObject.parseObject(userstr,User.class);
        return ResponseEntity.ok("async");
    }

//    @AspectService(operationType="test",operationName="for test")
    @MyAspectInterface(value="test", describe="happy boy")
    @RequestMapping(value="/aspect",method = RequestMethod.GET)
    public String aspectTask(){
        return "aspect";
    }


    @Autowired
    @Qualifier("redisUtils")
    RedisUtils redisUtils;

    @RequestMapping("/login")
    public String login(HttpServletRequest request,HttpServletResponse response){

        System.out.println(request.getContextPath());
        System.out.println(request.getServletPath());
        System.out.println(request.getRequestURI());
        System.out.println(request.getRequestURL());
        System.out.println(request.getRealPath("/"));

        String username = "lisi";

        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length>0){
            for(Cookie cookie : cookies){
                if("username".equals(cookie.getName())){
                    Cookie newcookie = new Cookie("username",null);
                    newcookie.setMaxAge(0);
                    newcookie.setPath("/");
                    break;
                }
            }
        }
        Cookie cookie = new Cookie("username",username);
        cookie.setMaxAge(30*60);//单位秒
        cookie.setPath("/");
        response.addCookie(cookie);

        HttpSession session = request.getSession();
        session.removeAttribute("username");
        session.invalidate();
        session = request.getSession();
        session.setAttribute("username",username);
        session.setMaxInactiveInterval(1800);

        String token = UUID.randomUUID().toString();
        redisUtils.set(token,username,30L, TimeUnit.MINUTES);

        User user = new User();
        user.setName("zhangsan");
        user.setAge(18);
        List<User> list = new ArrayList<>();
        list.add(user);
        redisUtils.set("abc",list,30L, TimeUnit.MINUTES);
        System.out.println(redisUtils.get(token));
        List<User> lists = (List<User>)redisUtils.get("abc");
        if(lists != null && !lists.isEmpty()){
            System.out.println(lists.get(0).getName()+":"+lists.get(0).getAge());
        }

        return token;
    }

    /**
     *
     * @param file @RequestParam(value="upload",required = false)  前端post请求、form表单的enctype="multipart/form-data"、name=upload
     * @param request
     * @param response
     * @return
     */
    public Map<String,Object> uploadFile(MultipartFile file, HttpServletRequest request, HttpServletResponse response){

       /* MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        MultipartFile file = multipartHttpServletRequest.getFile("file");*/

        Map<String,Object> result = new HashMap<>();
        String originalFilename = file.getOriginalFilename();
        if(!originalFilename.endsWith(".pdf")){
            result.put("success",false);
            result.put("message","文件类型错误");
            return result;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(new Date());
        String filepath = request.getRealPath("/")+format;
        File file1 = new File(filepath);
        if(!file1.exists()){
            file1.mkdir();
        }
        String filename = UUID.randomUUID().toString().replace("-","")+".pdf";
        try {
            file.transferTo(new File(filepath,filename));
            String url = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+"/"+format+"/"+filename;
            result.put("success",true);
            result.put("message","文件上传成功");
            result.put("url",url);
        } catch (IOException e) {
            e.printStackTrace();
            result.put("success",false);
            result.put("message",e.getMessage());
        }
        return result;
    }


    @RequestMapping("/getWebInterface")
    public ResponseEntity getWebInterface(@RequestParam Map cc, @RequestParam(value="aa",required = false) String dd, @RequestParam(value="aa",required = false) String[] ee){
        String[] aa = request.getParameterValues("aa");
        String bb = request.getParameter("aa");
        Map<String, String[]> parameterMap = request.getParameterMap();
        return ResponseEntity.ok(Arrays.toString(ee));
        //return ResponseEntity.ok(dd);
        //return ResponseEntity.ok(cc);
        //return ResponseEntity.ok(Arrays.toString(aa));
        //return ResponseEntity.ok(bb);
    }


    public static void main(String[] args) {
        //线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,5,10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), new ThreadPoolExecutor.AbortPolicy());
        for(int i=0;i<5;i++){
            int ii = i;
            //threadPoolExecutor.execute(() -> System.out.println("第"+(ii +1)+"号线程"));
            try {
                String s = threadPoolExecutor.submit(() -> {
                    return "Callable返回值:"+"第"+(ii +1)+"号线程";
                }).get();
                System.out.println(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        //Executors工具类
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for(int i=0;i<5;i++){
            try {
                Thread.sleep(1000);
                System.out.println("mian:"+Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService.execute(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("executorService:"+Thread.currentThread().getName());
            });
        }

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("任务调度");
            }
        };
        try {
            /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = sdf.parse("2021-09-20 20:35:00");
            long initialDelay = date1.getTime() - new Date().getTime();
            long period = 24 * 60 * 60 * 1000;*/
            long initialDelay = 2000;
            long period = 5000;
            scheduledExecutorService.scheduleAtFixedRate(runnable,initialDelay, period, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TreeMap
        Map<String, Object> trreeMap = new TreeMap<String, Object>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        }){
            {
                put("a","a");
                put("b","bb");
                put("c","ccc");
            }
        };
        trreeMap.forEach((k,v) -> System.out.println(v));

        //递归
        FatherAndSon fas0 = new FatherAndSon(0,"xingming",-1);
        FatherAndSon fas1 = new FatherAndSon(1,"zhangsan1",0);
        FatherAndSon fas11 = new FatherAndSon(11,"zhangsan11",1);
        FatherAndSon fas111 = new FatherAndSon(111,"zhangsan111",11);
        FatherAndSon fas12 = new FatherAndSon(12,"zhangsan12",1);
        FatherAndSon fas2 = new FatherAndSon(2,"lisi2",0);
        FatherAndSon fas21 = new FatherAndSon(21,"lisi21",2);
        FatherAndSon fas3 = new FatherAndSon(3,"wangwu3",0);
        FatherAndSon[] fasarr = new FatherAndSon[]{fas0,fas1,fas11,fas111,fas12,fas2,fas21,fas3};
        List<FatherAndSon> faslist = Arrays.asList(fasarr);
        List<FatherAndSon> faslist2 = new ArrayList<>(faslist.size());
        faslist.forEach(x -> {
            FatherAndSon y = new FatherAndSon();
            BeanUtils.copyProperties(x,y);
            faslist2.add(y);
        });
        List<FatherAndSon> fatherAndSons = new ArrayList<>();
        faslist.forEach(fas -> {
            if(fas.getPid() == -1){
                fatherAndSons.add(fas);
                fas.setFass(getChildren(fas.getId(),faslist));
            }
        });
        System.out.println(fatherAndSons);

        //list操作
        System.out.println(faslist2);
        FatherAndSon fas4 = new FatherAndSon(3,"wangwu3",999);
        ArrayList<FatherAndSon> fasolds = new ArrayList<FatherAndSon>(){{
            add(fas4);
        }};
        fasolds.addAll(faslist2);
        List<FatherAndSon> fasnews = new ArrayList<>();
        fasolds.stream().collect(Collectors.groupingBy(fas -> fas.getId()+fas.getName())).forEach((k,v) -> {
            v.stream().reduce((f1,f2) -> {
                FatherAndSon fasnew = new FatherAndSon();
                BeanUtils.copyProperties(f1,fasnew);
                if(f2.getPid() > f1.getPid()){
                    fasnew.setPid(f2.getPid());
                }
                return fasnew;
            }).ifPresent(fasnews::add);
        });
        System.out.println(fasnews);
    }
    private static List<FatherAndSon> getChildren(int id, List<FatherAndSon> faslist) {
        List<FatherAndSon> result = new ArrayList<>();
        faslist.forEach(fas -> {
            if(fas.getPid() == id){
                result.add(fas);
                fas.setFass(getChildren(fas.getId(),faslist));
            }
        });
        return result;
    }

}

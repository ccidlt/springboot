package com.ds.config.rest;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 示例：
 * getForEntity():
 * ResponseEntity<Object> forEntity = restTemplate.getForEntity("http://xxx?a=1", Object.class);
 * Object body = forEntity.getBody();
 *
 * getForEntity():
 * Object body = restTemplate.getForObject("http://xxx?a=1", Object.class);
 *
 * postForEntity():
 * User user = new User();
 * user.setName("鲁大师");
 * ResponseEntity<Object> objectResponseEntity = restTemplate.postForEntity("http://xxx?a=1", user, Object.class);
 * Object body = objectResponseEntity.getBody();
 *
 * postForObject():
 * User user = new User();
 * user.setName("鲁大师");
 * Object body = restTemplate.postForObject("http://xxx?a=1", user, Object.class);
 *
 * 使用POST以表单方式提交:
 * String url = "http://xxx?a=1";
 * //设置请求头, x-www-form-urlencoded格式的数据
 * HttpHeaders httpHeaders = new HttpHeaders();
 * httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
 * //提交参数设置
 * //该类是用来封装请求参数的，是以key-value的形式封装但是以单个key对应多个value的格式传输(也就是是以单个key:[value...]的格式传输的)。
 * //如果像传输单个key对应单个value使用普通的Map传参即可
 * MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
 * map.add("name","鲁大师");
 * //组装请求体
 * HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpHeaders);
 * //发送post请求并打印结果 以String类型接收响应结果JSON字符串
 * String s = restTemplate.postForObject(url, request, String.class);
 *
 * GET有请求头，有参数，responseEntity.getBody()获取响应参数
 * ResponseEntity<String> responseEntity = restTemplate.exchange("http://xxx?a=1", HttpMethod.GET, request, String.class);
 * Object body = responseEntity.getBody();
 *
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    @SentinelRestTemplate(blockHandler = "handleException", blockHandlerClass = ExceptionUtil.class)
    public RestTemplate getRestTemplate() {
        System.out.println();
        return new RestTemplate();
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(150000); // ms
        factory.setConnectTimeout(150000); // ms
        return factory;
    }

}

class ExceptionUtil {
    public static ClientHttpResponse handleException(HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException exception) {
        return null;
    }
}

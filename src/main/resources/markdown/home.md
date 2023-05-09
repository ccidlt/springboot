<center><h2>测试</h2></center>

所属部门：家里蹲

联系邮箱：`xiaoymin@foxmail.com`

项目地址：[xxx](https://xxx)

技术框架：Spring Boot、Spring Cloud、Java、Vue、jQuery


**版本发布说明:**

| 新闻标题                                                     | 发布时间   |
| ------------------------------------------------------------ | ---------- |
| [Knife4j 2.0.7发布,细节优化](https://www.oschina.net/news/119621/knife4j-2-0-7-released) | 2020-11-02 |
| [Knife4j 2.0.6发布,支持OpenAPI3及Auth2认证](https://www.oschina.net/news/119457) | 2020-10-26 |
| [Knife4j 2.0.5发布,性能优化](https://www.oschina.net/news/118621) | 2020-09-14 |
| [Knife4j 2.0.4发布,支持自定义 Host](https://www.oschina.net/news/116766/knife4j-2-0-4-released) | 2020-06-28 |
| [Knife4j 2.0.3发布,支持springdoc和i18n](https://www.oschina.net/news/115921/knife4j-2-0-3-released) | 2020-05-24 |
| 


**备注说明：**

>1、在使用Springfox2.10.5的版本中，如果是单体Spring Boot架构，当给单体架构指定context-path节点时，也就是通常我们所说的basePath,此时springfox会将basePath节点追加给每一个path节点前
>
>2、如果是SpringCloud微服务架构，在最终聚合每个单体的boot服务时，springfox返回的/v2/api-docs接口中，不会把basePath节点追加在path节点前
>针对上面两种情况，Knife4j目前的解决方案是针对每个path节点的接口进行判断，如果该接口startwidth(basePath)，那么代表是单体架构，因此不追加basePath,否则则追加
>但这种情况也会存在问题，假设path节点中存在于basePath同名的情况下比如微服务架构中，basePath的值为/api,path节点的部分接口是以/test,/abc等开头，那么最终拼装basePath的属性后，变成/api/test,/api/abc,但是会存在path节点本来就存在/api开头的情况，正常情况下，拼接后的地址是/api/api,但是由于进行了startwith的判断，会导致Knife4j不会对该接口进行拼装，最终只会展示/api，所以Knife4j的做法是对所有的path节点进行遍历，如果是以basePath开头的，则对basePath不追究，否则追加basePath
>


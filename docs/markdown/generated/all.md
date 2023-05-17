# springboot


<a name="overview"></a>
## 概览
测试系统


### 版本信息
*版本* : 1.0


### 联系方式
*名字* : lt  
*邮箱* : xxx@qq.com


### 许可信息
*服务条款* : /v2/api-docs


### URI scheme
*域名* : localhost:8078  
*基础路径* : /


### 标签

* 好汉操作 : Boy Controller
* 豪杰操作 : Girl Controller




<a name="paths"></a>
## 资源

<a name="42fc2bddb068a5db9309774e416230ea"></a>
### 好汉操作
Boy Controller


<a name="addboyusingpost"></a>
#### 新增/修改
```
POST /addBoy
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|
|**Body**|**boy**  <br>*必填*|Boy|[boy实体类](#6cee3c529071d22ef80f5ae1aa1a0c5f)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[boy实体类](#6cee3c529071d22ef80f5ae1aa1a0c5f)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `application/json`


##### HTTP请求示例

###### 请求 path
```
/addBoy
```


###### 请求 header
```json
"string"
```


###### 请求 body
```json
{
  "createTime" : "string",
  "createUser" : "string",
  "girlId" : 0,
  "girls" : "string",
  "id" : 0,
  "isdelete" : 0,
  "name" : "string",
  "updateTime" : "string",
  "version" : 0
}
```


##### HTTP响应示例

###### 响应 200
```json
{
  "createTime" : "string",
  "createUser" : "string",
  "girlId" : 0,
  "girls" : "string",
  "id" : 0,
  "isdelete" : 0,
  "name" : "string",
  "updateTime" : "string",
  "version" : 0
}
```


<a name="asyncusingget"></a>
#### async
```
GET /async
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[全局统一返回结果](#afd1b7c86c88652432e9c08004366c43)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/async
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
{
  "code" : 0,
  "data" : "object",
  "message" : "string",
  "ok" : true
}
```


<a name="atomicusingget"></a>
#### atomic
```
GET /atomic
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[全局统一返回结果](#afd1b7c86c88652432e9c08004366c43)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/atomic
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
{
  "code" : 0,
  "data" : "object",
  "message" : "string",
  "ok" : true
}
```


<a name="downloadusingpost"></a>
#### 文件下载
```
POST /download
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|
|**Query**|**path**  <br>*必填*|文件路径|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[HttpServletResponse](#httpservletresponse)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/download?path=string
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
"[httpservletresponse](#httpservletresponse)"
```


<a name="getboysusingpost"></a>
#### 获取Boy表所有数据
```
POST /getBoys
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|操作成功|[boy实体类](#6cee3c529071d22ef80f5ae1aa1a0c5f)|
|**201**|Created|无内容|
|**400**|请求参数没填好|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|请求路径没有或页面跳转路径不对|无内容|
|**500**|后端程序报错|无内容|


##### 消耗

* `\*/*`


##### 生成

* `application/json`


##### HTTP请求示例

###### 请求 path
```
/getBoys
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
{
  "createTime" : "string",
  "createUser" : "string",
  "girlId" : 0,
  "girls" : "string",
  "id" : 0,
  "isdelete" : 0,
  "name" : "string",
  "updateTime" : "string",
  "version" : 0
}
```


<a name="getboysbyparamusingget"></a>
#### 获取Boy表数据(通过id)
```
GET /getBoysById
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|
|**Query**|**id**  <br>*必填*|主键|integer (int32)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|< [boy实体类](#6cee3c529071d22ef80f5ae1aa1a0c5f) > array|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/getBoysById?id=0
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
[ {
  "createTime" : "string",
  "createUser" : "string",
  "girlId" : 0,
  "girls" : "string",
  "id" : 0,
  "isdelete" : 0,
  "name" : "string",
  "updateTime" : "string",
  "version" : 0
} ]
```


<a name="getboysbyparamusingget_1"></a>
#### 获取Boy表数据
```
GET /getBoysByParam
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|< [boy实体类](#6cee3c529071d22ef80f5ae1aa1a0c5f) > array|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/getBoysByParam
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
[ {
  "createTime" : "string",
  "createUser" : "string",
  "girlId" : 0,
  "girls" : "string",
  "id" : 0,
  "isdelete" : 0,
  "name" : "string",
  "updateTime" : "string",
  "version" : 0
} ]
```


<a name="getsnowflakeusingpost"></a>
#### getSnowflake
```
POST /getSnowflake
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|integer (int64)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/getSnowflake
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
0
```


<a name="getsnowflakeusingget"></a>
#### getSnowflake
```
GET /getSnowflake
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|integer (int64)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/getSnowflake
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
0
```


<a name="getsnowflakeusingput"></a>
#### getSnowflake
```
PUT /getSnowflake
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|integer (int64)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/getSnowflake
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
0
```


<a name="getsnowflakeusingdelete"></a>
#### getSnowflake
```
DELETE /getSnowflake
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|integer (int64)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/getSnowflake
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
0
```


<a name="getsnowflakeusingpatch"></a>
#### getSnowflake
```
PATCH /getSnowflake
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|integer (int64)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/getSnowflake
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
0
```


<a name="getsnowflakeusinghead"></a>
#### getSnowflake
```
HEAD /getSnowflake
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|integer (int64)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/getSnowflake
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
0
```


<a name="getsnowflakeusingoptions"></a>
#### getSnowflake
```
OPTIONS /getSnowflake
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|integer (int64)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/getSnowflake
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
0
```


<a name="globaltransactionalusingpost"></a>
#### globalTransactional
```
POST /globalTransactional
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|
|**Body**|**b**  <br>*可选*|b|[boy实体类](#6cee3c529071d22ef80f5ae1aa1a0c5f)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[全局统一返回结果](#afd1b7c86c88652432e9c08004366c43)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/globalTransactional
```


###### 请求 header
```json
"string"
```


###### 请求 body
```json
{
  "createTime" : "string",
  "createUser" : "string",
  "girlId" : 0,
  "girls" : "string",
  "id" : 0,
  "isdelete" : 0,
  "name" : "string",
  "updateTime" : "string",
  "version" : 0
}
```


##### HTTP响应示例

###### 响应 200
```json
{
  "code" : 0,
  "data" : "object",
  "message" : "string",
  "ok" : true
}
```


<a name="logstashusingpost"></a>
#### logstash
```
POST /logstash
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|string|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/logstash
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
"string"
```


<a name="logstashusingget"></a>
#### logstash
```
GET /logstash
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|string|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/logstash
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
"string"
```


<a name="logstashusingput"></a>
#### logstash
```
PUT /logstash
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|string|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/logstash
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
"string"
```


<a name="logstashusingdelete"></a>
#### logstash
```
DELETE /logstash
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|string|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/logstash
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
"string"
```


<a name="logstashusingpatch"></a>
#### logstash
```
PATCH /logstash
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|string|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/logstash
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
"string"
```


<a name="logstashusinghead"></a>
#### logstash
```
HEAD /logstash
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|string|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/logstash
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
"string"
```


<a name="logstashusingoptions"></a>
#### logstash
```
OPTIONS /logstash
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|string|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/logstash
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
"string"
```


<a name="saveboyusingpost"></a>
#### 新增/修改
```
POST /saveBoy
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|
|**Body**|**boy**  <br>*必填*|Boy|[boy实体类](#6cee3c529071d22ef80f5ae1aa1a0c5f)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[boy实体类](#6cee3c529071d22ef80f5ae1aa1a0c5f)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `application/json`


##### HTTP请求示例

###### 请求 path
```
/saveBoy
```


###### 请求 header
```json
"string"
```


###### 请求 body
```json
{
  "createTime" : "string",
  "createUser" : "string",
  "girlId" : 0,
  "girls" : "string",
  "id" : 0,
  "isdelete" : 0,
  "name" : "string",
  "updateTime" : "string",
  "version" : 0
}
```


##### HTTP响应示例

###### 响应 200
```json
{
  "createTime" : "string",
  "createUser" : "string",
  "girlId" : 0,
  "girls" : "string",
  "id" : 0,
  "isdelete" : 0,
  "name" : "string",
  "updateTime" : "string",
  "version" : 0
}
```


<a name="transactiontestusingget"></a>
#### 编程式事务测试
```
GET /sqlSession
```


##### 说明
编程式事务测试


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|string|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/sqlSession
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
"string"
```


<a name="transactiontest2usingget"></a>
#### 编程式事务测试
```
GET /transaction
```


##### 说明
编程式事务测试


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|string|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/transaction
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
"string"
```


<a name="transactiontest3usingget"></a>
#### 编程式事务测试
```
GET /transaction2
```


##### 说明
编程式事务测试


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|string|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/transaction2
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
"string"
```


<a name="uploadusingpost"></a>
#### 文件上传
```
POST /upload
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|
|**FormData**|**file**  <br>*必填*|文件|file|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[全局统一返回结果](#afd1b7c86c88652432e9c08004366c43)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `multipart/form-data`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/upload
```


###### 请求 header
```json
"string"
```


###### 请求 formData
```json
"file"
```


##### HTTP响应示例

###### 响应 200
```json
{
  "code" : 0,
  "data" : "object",
  "message" : "string",
  "ok" : true
}
```


<a name="7613b39d4e956f4ca269c256a5337279"></a>
### 豪杰操作
Girl Controller


<a name="deletebatchusingpost"></a>
#### 删除（批量）
```
POST /girl/deleteBatch
```


##### 说明
删除（批量）


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|
|**Body**|**ids**  <br>*必填*|ids|< integer (int32) > array|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[全局统一返回结果«boolean»](#0112457e80f2700eca91f9b55e511568)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/girl/deleteBatch
```


###### 请求 header
```json
"string"
```


###### 请求 body
```json
[ 0 ]
```


##### HTTP响应示例

###### 响应 200
```json
{
  "code" : 0,
  "data" : true,
  "message" : "string",
  "ok" : true
}
```


<a name="deleteusingget"></a>
#### 删除（主键）
```
GET /girl/deleteById/{id}
```


##### 说明
删除（主键）


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|
|**Path**|**id**  <br>*必填*|主键|integer (int32)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[全局统一返回结果«boolean»](#0112457e80f2700eca91f9b55e511568)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/girl/deleteById/0
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
{
  "code" : 0,
  "data" : true,
  "message" : "string",
  "ok" : true
}
```


<a name="queryusingpost"></a>
#### 查询
```
POST /girl/query
```


##### 说明
查询


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[全局统一返回结果«List«Girl»»](#f2b27a2ce9b1f191f53e4005a6a1ff39)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|
|**500**|服务器内部错误|[全局统一返回结果](#afd1b7c86c88652432e9c08004366c43)|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/girl/query
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
{
  "code" : 0,
  "data" : [ {
    "boyId" : 0,
    "createTime" : "string",
    "createUser" : "string",
    "id" : 0,
    "isdelete" : 0,
    "name" : "string",
    "updateTime" : "string",
    "version" : 0
  } ],
  "message" : "string",
  "ok" : true
}
```


###### 响应 500
```json
{
  "code" : 0,
  "data" : "object",
  "message" : "string",
  "ok" : true
}
```


<a name="querybyidusingget"></a>
#### 查询（主键）
```
GET /girl/queryById/{id}
```


##### 说明
查询（主键）


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|
|**Path**|**id**  <br>*必填*|主键|integer (int32)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[全局统一返回结果«Girl»](#31d4090813a9f5675d5aa441b5c0bfdc)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/girl/queryById/0
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
{
  "code" : 0,
  "data" : {
    "boyId" : 0,
    "createTime" : "string",
    "createUser" : "string",
    "id" : 0,
    "isdelete" : 0,
    "name" : "string",
    "updateTime" : "string",
    "version" : 0
  },
  "message" : "string",
  "ok" : true
}
```


<a name="querypageusingpost"></a>
#### 查询（分页）
```
POST /girl/queryPage
```


##### 说明
查询（分页）


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|
|**Query**|**pageNum**  <br>*必填*|pageNum|integer (int32)|
|**Query**|**pageSize**  <br>*必填*|pageSize|integer (int32)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[全局统一返回结果«Page«Girl»»](#5628f9177689b006087780a0e74eb484)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/girl/queryPage?pageNum=0&pageSize=0
```


###### 请求 header
```json
"string"
```


##### HTTP响应示例

###### 响应 200
```json
{
  "code" : 0,
  "data" : {
    "countId" : "string",
    "current" : 0,
    "hitCount" : true,
    "maxLimit" : 0,
    "optimizeCountSql" : true,
    "orders" : [ {
      "asc" : true,
      "column" : "string"
    } ],
    "pages" : 0,
    "records" : [ {
      "boyId" : 0,
      "createTime" : "string",
      "createUser" : "string",
      "id" : 0,
      "isdelete" : 0,
      "name" : "string",
      "updateTime" : "string",
      "version" : 0
    } ],
    "searchCount" : true,
    "size" : 0,
    "total" : 0
  },
  "message" : "string",
  "ok" : true
}
```


<a name="saveusingpost"></a>
#### 新增、修改
```
POST /girl/save
```


##### 说明
新增、修改


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Header**|**token**  <br>*可选*|token令牌|string|
|**Body**|**girl**  <br>*必填*|girl|[Girl](#girl)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[全局统一返回结果«Girl»](#31d4090813a9f5675d5aa441b5c0bfdc)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/girl/save
```


###### 请求 header
```json
"string"
```


###### 请求 body
```json
{
  "boyId" : 0,
  "createTime" : "string",
  "createUser" : "string",
  "id" : 0,
  "isdelete" : 0,
  "name" : "string",
  "updateTime" : "string",
  "version" : 0
}
```


##### HTTP响应示例

###### 响应 200
```json
{
  "code" : 0,
  "data" : {
    "boyId" : 0,
    "createTime" : "string",
    "createUser" : "string",
    "id" : 0,
    "isdelete" : 0,
    "name" : "string",
    "updateTime" : "string",
    "version" : 0
  },
  "message" : "string",
  "ok" : true
}
```




<a name="definitions"></a>
## 定义

<a name="girl"></a>
### Girl

|名称|说明|类型|
|---|---|---|
|**boyId**  <br>*可选*|**样例** : `0`|integer (int32)|
|**createTime**  <br>*可选*|**样例** : `"string"`|string (date-time)|
|**createUser**  <br>*可选*|**样例** : `"string"`|string|
|**id**  <br>*可选*|**样例** : `0`|integer (int32)|
|**isdelete**  <br>*可选*|**样例** : `0`|integer (int32)|
|**name**  <br>*可选*|**样例** : `"string"`|string|
|**updateTime**  <br>*可选*|**样例** : `"string"`|string (date-time)|
|**version**  <br>*可选*|**样例** : `0`|integer (int32)|


<a name="orderitem"></a>
### OrderItem

|名称|说明|类型|
|---|---|---|
|**asc**  <br>*可选*|**样例** : `true`|boolean|
|**column**  <br>*可选*|**样例** : `"string"`|string|


<a name="ce18413631a5e0993e496faa7825bef9"></a>
### Page«Girl»

|名称|说明|类型|
|---|---|---|
|**countId**  <br>*可选*|**样例** : `"string"`|string|
|**current**  <br>*可选*|**样例** : `0`|integer (int64)|
|**hitCount**  <br>*可选*|**样例** : `true`|boolean|
|**maxLimit**  <br>*可选*|**样例** : `0`|integer (int64)|
|**optimizeCountSql**  <br>*可选*|**样例** : `true`|boolean|
|**orders**  <br>*可选*|**样例** : `[ "[orderitem](#orderitem)" ]`|< [OrderItem](#orderitem) > array|
|**pages**  <br>*可选*|**样例** : `0`|integer (int64)|
|**records**  <br>*可选*|**样例** : `[ "[girl](#girl)" ]`|< [Girl](#girl) > array|
|**searchCount**  <br>*可选*|**样例** : `true`|boolean|
|**size**  <br>*可选*|**样例** : `0`|integer (int64)|
|**total**  <br>*可选*|**样例** : `0`|integer (int64)|


<a name="6cee3c529071d22ef80f5ae1aa1a0c5f"></a>
### boy实体类

|名称|说明|类型|
|---|---|---|
|**createTime**  <br>*可选*|创建时间  <br>**样例** : `"string"`|string (date-time)|
|**createUser**  <br>*可选*|创建人  <br>**样例** : `"string"`|string|
|**girlId**  <br>*可选*|girl表id  <br>**样例** : `0`|integer (int32)|
|**girls**  <br>*可选*|女朋友们  <br>**样例** : `"string"`|string|
|**id**  <br>*可选*|主键  <br>**样例** : `0`|integer (int32)|
|**isdelete**  <br>*可选*|逻辑删除（0-未删除、1-已删除）  <br>**样例** : `0`|integer (int32)|
|**name**  <br>*可选*|名称  <br>**样例** : `"string"`|string|
|**updateTime**  <br>*可选*|修改时间  <br>**样例** : `"string"`|string (date-time)|
|**version**  <br>*可选*|乐观锁  <br>**样例** : `0`|integer (int32)|


<a name="afd1b7c86c88652432e9c08004366c43"></a>
### 全局统一返回结果

|名称|说明|类型|
|---|---|---|
|**code**  <br>*可选*|返回码  <br>**样例** : `0`|integer (int32)|
|**data**  <br>*可选*|返回数据  <br>**样例** : `"object"`|object|
|**message**  <br>*可选*|返回消息  <br>**样例** : `"string"`|string|
|**ok**  <br>*可选*|**样例** : `true`|boolean|


<a name="31d4090813a9f5675d5aa441b5c0bfdc"></a>
### 全局统一返回结果«Girl»

|名称|说明|类型|
|---|---|---|
|**code**  <br>*可选*|返回码  <br>**样例** : `0`|integer (int32)|
|**data**  <br>*可选*|返回数据  <br>**样例** : `"[girl](#girl)"`|[Girl](#girl)|
|**message**  <br>*可选*|返回消息  <br>**样例** : `"string"`|string|
|**ok**  <br>*可选*|**样例** : `true`|boolean|


<a name="f2b27a2ce9b1f191f53e4005a6a1ff39"></a>
### 全局统一返回结果«List«Girl»»

|名称|说明|类型|
|---|---|---|
|**code**  <br>*可选*|返回码  <br>**样例** : `0`|integer (int32)|
|**data**  <br>*可选*|返回数据  <br>**样例** : `[ "[girl](#girl)" ]`|< [Girl](#girl) > array|
|**message**  <br>*可选*|返回消息  <br>**样例** : `"string"`|string|
|**ok**  <br>*可选*|**样例** : `true`|boolean|


<a name="5628f9177689b006087780a0e74eb484"></a>
### 全局统一返回结果«Page«Girl»»

|名称|说明|类型|
|---|---|---|
|**code**  <br>*可选*|返回码  <br>**样例** : `0`|integer (int32)|
|**data**  <br>*可选*|返回数据  <br>**样例** : `"[ce18413631a5e0993e496faa7825bef9](#ce18413631a5e0993e496faa7825bef9)"`|[Page«Girl»](#ce18413631a5e0993e496faa7825bef9)|
|**message**  <br>*可选*|返回消息  <br>**样例** : `"string"`|string|
|**ok**  <br>*可选*|**样例** : `true`|boolean|


<a name="0112457e80f2700eca91f9b55e511568"></a>
### 全局统一返回结果«boolean»

|名称|说明|类型|
|---|---|---|
|**code**  <br>*可选*|返回码  <br>**样例** : `0`|integer (int32)|
|**data**  <br>*可选*|返回数据  <br>**样例** : `true`|boolean|
|**message**  <br>*可选*|返回消息  <br>**样例** : `"string"`|string|
|**ok**  <br>*可选*|**样例** : `true`|boolean|






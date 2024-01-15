主要用来添加白名单，判断对应用户或者其他参数是否有权限执行

使用方法：
在对应的application.yml中进行配置白名单的用户或者其他，每一个按照,号进行分割
例如
```yaml
com:
  kevin:
    faced:
      userRoll: wangwu,lisi,laoliu
```
然后在对应的方法上加上注解@Faced

例如
```java
@Faced(key = "userName",returnJson = "{\"code\":\"1111\",\"desc\":\"非白名单可访问用户拦截！\"}")
    @GetMapping("/face")
    public UserInfo face(@RequestParam String userName){
        return new UserInfo(userName,18,"测试用户");
    }
```
这个key是你的参数，用来判断是否在白名单内的，在可以执行，不在返回默认的，默认的就是你传入的returnJson，不传入返回空串。
如果不传入对应的key参数，默认是可以执行
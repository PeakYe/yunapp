### token 生成原理
- 客户端重定向登录页面到auth，携带认证和redirect
- auth判断用户名密码
- 认证成功成token，时效2.5h，重定向到指定页面redirect，并传递token 
- 网关获取token，向auth请求用户信息，缓存下来，时效2h，auth重置缓存时效
- 后续的请求网关将用户信息以令牌的形式发送过去
- 其它资源服务器同理


### 注销流程
- 网关清除token缓存
- 调用auth清理token
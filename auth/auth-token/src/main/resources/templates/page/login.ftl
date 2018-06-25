<html>

<head>
    <title>登录页面</title>
</head>

<form action="/templates/auth/login" method="post">
    <p>用户名：<input type="text" name="username"></p>
    <p>密码：<input type="password" name="password"></p>
    <input type="hidden" name="redirect" value="http://www.baidu.com?token=[token]" >
    <p><button>登录</button></p>
</form>
</html>
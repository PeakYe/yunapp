<html>

<head>
    <title>登录页面</title>
    <script src="https://cdn.bootcss.com/vue/2.4.2/vue.min.js"></script>
</head>

<form action="/auth/login" method="post" id="form">
    <p>{{errorinfo}}</p>
    <p>用户名：<input type="text" name="username"></p>
    <p>密码：<input type="password" name="password"></p>
    <input type="hidden" name="redirect" value="http://www.baidu.com?token=[token]">
    <p>
        <button>登录</button>
    </p>
</form>

<script>
    new Vue({
        el: '#form',
        data: {
            errorinfo: 'error!'
        }
    })

    axios.post('/auth/login','username=admin&password=123')
            .then(res=>{
                console.log(res);
    }).catch(error=>{
        console.log(error);
    });
</script>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>test jsp</title>
    <script src="http://libs.useso.com/js/jquery/1.9.1/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        var accountId = 0;
        var jsonStr = "{\"c\":200,\"id\":1,\"info\":null,\"d\":{\"username\":\"yuruyi\",\"password\":\"111111\",\"plat\":\"edu\"}}";
        function login() {
            $.ajax({
                type: "post",
                cache: false,
                url: "http://localhost:8080/msg/101",
                data: jsonStr,
                success: function (data) {
                    alert(data)
                    var json = eval('(' + data + ')');
                    $("#showAccountId").val(json.d.user_id);
                    accountId=json.d.user_id;
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                }
            });
        }

        function getLogoutData() {
            var jsonStr2 = "{\"c\":200,\"id\":1,\"info\":null,\"d\":{\"user_id\":"+accountId+"}}";
            return jsonStr2;
        }
        function logout() {
            $.ajax({
                type: "post",
                cache: false,
                url: "http://localhost:8080/msg/102",
                data: getLogoutData(),
                success: function (data) {
                    alert(data)
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                }
            });
        }

        $(function(){
            $.ajax({
                url:'http://192.168.3.222:8081/msg/999',
                type:"POST",
                data:{"msg":{
                    "id":1,
                    "c":1,
                    "info":"",
                    "d":{"user_id":0}}
                },
                dataType:"json",
                success: function(data){
                    debugger;
                    alert(data.d)
                    console.log("eeeeeeeeeeeeee")
                }
            });
        })

    </script>
</head>
<body>
${isLogin}<br>
<input type="text" name="username" id="username"><br>
<input type="text" name="password" id="password"><br>
<input type="button" onclick="login();" value="登录"><br>
<input type="text" id="showAccountId"><br>
<a href="javascript:void(0)" onclick="logout()">退出</a>


</body>
</html>

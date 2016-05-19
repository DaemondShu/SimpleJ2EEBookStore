<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <%--<script src="bootstrap/dist/css/bootstrap-theme.min.css"></script>--%>
    <script src="js/jquery-2.1.4.js"></script>
    <script src="bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="js/helpFunction.js"></script>
    <script src="js/user.js"></script>
    <script src="js/jquery.soap.js"></script>


</head>
<body style="text-align: center;">

<BR><BR><BR>
<h1 style="font-size: 30px; text-align: center;" class="inter_welcome">Welcome To Monkey.D.Store</h1>
<BR>
<HR>
<BR>


<form id="loginForm">
    <div style="text-align: center; font-size: 20px">
        <nobr class="inter_username">Username</nobr>
        <BR>
        <input type=TEXT class="form-control" name="username"
               style="width: 30%; height: 35px; margin: 0 auto;"/>
        <BR>
        <nobr class="inter_password">Password</nobr>
        <BR>
        <input type=PASSWORD class="form-control" name="password"
               style="width: 30%; height: 35px; margin: 0 auto;"/> <BR>
        <%--不能直接定义成button，button可能会被form自动转成submit--%>
        <input type=BUTTON class="btn btn-default inter_login" value="Login" onclick="userLogin()"
               style="padding: 3px; margin: 10px 0; font-size: 17px;"/>
        <%--log in </input>--%>
        &nbsp; &nbsp;
        <input type=BUTTON class="btn btn-default inter_guest" value="Guest" onclick="guest()"
               style="padding: 3px; margin: 10px 0; font-size: 17px;"/>

    </div>
</form>
<BR>


<button class="btn btn-default inter_register" data-toggle="modal" data-target="#myModal" style="margin: 0 auto;">
    register
</button>


<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title inter_register_user" id="myModalLabel">Register</h4>
            </div>
            <div class="modal-body">
                <FORM id="SignUpForm" style="text-align: center; font-size: 20px">
                    <nobr class="inter_username">Username</nobr>
                    <input type=TEXT class="form-control" NAME="username"
                           style="width: 40%; height: 35px; margin: 0 auto;"/>
                    <nobr class="inter_password">Password</nobr>
                    <input type=PASSWORD class="form-control" NAME="pwd1"
                           style="width: 40%; height: 35px; margin: 0 auto;"/>
                    <nobr class="inter_password2">Pwd_Confirm</nobr>
                    <input type=PASSWORD class="form-control" NAME="pwd2"
                           style="width: 40%; height: 35px; margin: 0 auto;"/>
                    <BR>
                    <input type=BUTTON class="btn btn-default inter_sign_up" value="Sign Up" onclick="SignUp()"
                           style="padding: 3px; margin: 10px 0; font-size: 17px;"/>


                </FORM>
            </div>
        </div>
    </div>
    <!-- /.modal-content -->
</div>
<!-- /.modal -->

<footer style=" position:absolute;
    bottom: 0px; text-align: center; width: 100%;">
    <a href="javascript: languageSwitch('cn')" style="font-size: 18px"> 中文 </a>
    <a href="javascript: languageSwitch('en')" style="font-size: 18px"> English </a>
</footer>

</body>

<script>
    function languageSwitch(language)
    {
        ajax("Language", "get",
                {
                    language: language
                }, function (jsonStr)
                {
                    dict = JSON.parse(jsonStr);
                    for (var key in dict)
                    {
                        var div = $(".inter_" + key);
                        //modify text or value
                        if (div.val() == undefined || div.val().length == 0) //empty
                            div.text(dict[key]);
                        else div.val(dict[key]);
                    }
                })


    }

    $(document).ready(function ()
    {
        languageSwitch('en');
    })
</script>


</html>
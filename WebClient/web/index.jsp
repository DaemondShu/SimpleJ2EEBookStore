<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="bootstrap/dist/css/bootstrap-theme.min.css"></script>
    <script src="js/jquery-2.1.4.js"></script>
    <script src="bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="js/helpFunction.js"></script>
    <script src="js/user.js"></script>


</head>
<body style="text-align: center;">

<BR><BR><BR>
<h1 style="font-size: 30px; text-align: center;">Welcome To Monkey.D.Store</h1>
<BR>
<HR>
<BR>


<form id="loginForm">
    <div style="text-align: center; font-size: 20px">
        Username: <BR>
        <input type=TEXT class="form-control" name="username"
               style="width: 30%; height: 35px; margin: 0 auto;"/> <BR>
        Password: <BR>
        <input type=PASSWORD class="form-control" name="password"
               style="width: 30%; height: 35px; margin: 0 auto;"/> <BR>
        <%--不能直接定义成button，button可能会被form自动转成submit--%>
        <input type=BUTTON class="btn btn-default" value="Login" onclick="userLogin()"
               style="padding: 3px; margin: 10px 0; font-size: 17px;"/>
        <%--log in </input>--%>
    </div>
</form>
<BR>


<button class="btn btn-default" data-toggle="modal" data-target="#myModal" style="margin: 0 auto;">register</button>


<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Register</h4>
            </div>
            <div class="modal-body">
                <FORM id="SignUpForm" style="text-align: center; font-size: 20px">
                    Username:
                    <input type=TEXT class="form-control" NAME="username"
                           style="width: 40%; height: 35px; margin: 0 auto;"/>
                    Password:
                    <input type=PASSWORD class="form-control" NAME="pwd1"
                           style="width: 40%; height: 35px; margin: 0 auto;"/>
                    Pwd_Confirm:
                    <input type=PASSWORD class="form-control" NAME="pwd2"
                           style="width: 40%; height: 35px; margin: 0 auto;"/>
                    <BR>
                    <input type=BUTTON class="btn btn-default" value="Sign Up" onclick="SignUp()"
                           style="padding: 3px; margin: 10px 0; font-size: 17px;"/>
                </FORM>
            </div>
        </div>
    </div>
    <!-- /.modal-content -->
</div>
<!-- /.modal -->


<div id="reg_info" style="text-align: center; font-size: 20px">


    <%--<%--%>
    <%--String login = request.getParameter("login");--%>

    <%--if (login != null)--%>
    <%--{--%>
    <%--if (login.equals("logfail"))--%>
    <%--out.print("Invalid Username or Password");--%>
    <%--else if (login.equals("regok"))--%>
    <%--out.print("Register Succeed.");--%>
    <%--else if (login.equals("regfail"))--%>
    <%--out.print("Register Failed. Invalid Username,password / username has been used");--%>
    <%--else if (login.equals("pwdfail"))--%>
    <%--out.print("Change Password Failed.");--%>
    <%--}--%>
    <%--%>--%>
    <%----%>
</div>

</body>
</html>
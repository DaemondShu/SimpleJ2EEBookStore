<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="bootstrap/dist/css/bootstrap-theme.min.css"></script>
    <script src="js/jquery-2.1.4.js"></script>
    <script src="bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="http://echarts.baidu.com/build/dist/echarts-all.js"></script>
    <!-- <link href="luck_files/bootstrap.css" rel="stylesheet"> -->
    <link href="luck_files/datepicker.css" rel="stylesheet">
    <script src="luck_files/bootstrap-datepicker.js"></script>

    <title>Insert title here</title>
    <script>

    </script>


</head>
<body>
<%--<%--%>
<%--//session.setAttribute("username", "admin");--%>
<%--Object tmp = session.getAttribute("username");--%>
<%--if (tmp == null) response.sendRedirect("index.jsp=logfail");--%>
<%--String user = tmp.toString();--%>
<%--try--%>
<%--{--%>
<%--String logcheck = request.getParameter("login");--%>
<%--if (logcheck == null || logcheck.equals(""))--%>
<%--response.sendRedirect("index.jsp=logfail");--%>

<%--int login = Integer.parseInt(logcheck);--%>
<%--//int login=1021;--%>
<%--if (user == null || user.equals("") || login != 1021)--%>
<%--{--%>
<%--response.sendRedirect("index.jsp=logfail");--%>
<%--}--%>
<%--} catch (Exception e)--%>
<%--{--%>
<%--response.sendRedirect("index.jsp=logfail");--%>
<%--}--%>
<%--%>--%>
<%--<%--%>
<%--//AccessDB.testS();--%>
<%--//String type = request.getParameter("type");--%>
<%--// 	AccessDB ADB = new AccessDB();--%>
<%--// 	ResultSet rs = ADB.book_queryall(null);--%>
<%--// 	ResultSet rs = null;--%>

<%--// 	String booktable="";--%>
<%--// 	String typelist="";--%>

<%--// 	int i=0;--%>
<%--// 	if (rs!=null)--%>
<%--// 	{--%>
<%--// 	    while (rs.next())--%>
<%--// 	    {--%>
<%--// 	    	String temptype=rs.getString("type");--%>
<%--// 	    	int book_id=rs.getInt("BOOK_ID");--%>
<%--// 			booktable+="<tr id=booktable"+book_id+">";--%>
<%--// 			booktable+="<td>" + book_id + "</td>";--%>
<%--// 			booktable+="<td>" + rs.getString("name") + "</td>";--%>
<%--// 			booktable+="<td>" + temptype + "</td>";--%>
<%--// 			booktable+="<td>$" + rs.getFloat("price") + "</td>";--%>
<%--// 			booktable+="<td><a onclick=\"addToCart("+book_id+")\" href=\"#\"> <span class=\"glyphicon glyphicon-shopping-cart\"> </span> </a><td>";--%>
<%--// 			booktable+="</tr>";--%>
<%--// 			if (typelist.indexOf(temptype)==-1)--%>
<%--// 				typelist+="<li><a href=\"#\" onclick=\"fliterTypes('"+temptype+"')\">"+temptype+"<span class=\"glyphicon glyphicon-chevron-right\"> </span> </a></li>";--%>
<%--// 			i++;--%>
<%--// 	    }--%>
<%--// 	}--%>
<%--%>--%>



<nav class="navbar navbar-default" role="navigation"
     style="margin-bottom: 1px">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Monkey.D.Store</a>
        </div>
        <div>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                        <span class="glyphicon glyphicon-user"></span>

                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="index.jsp">Log out</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid" style="margin-top: 0px">
    <div class="row">
        <div class="col-md-2 sidebar"
             style="background-color: #F8F8F8; margin-bottom: 4px; height: 800px">
            <ul class="nav nav-sidebar" style="margin-left: 10px">
                <li><h4>Management</h4></li>
                <li><a href="#" onclick="showusertable()"> user <span class="glyphicon glyphicon-chevron-right"> </span>
                </a></li>
                <li><a href="#" onclick="showbooktable()"> book <span class="glyphicon glyphicon-chevron-right"> </span>
                </a></li>
                <li><h4>Sales Staticstics</h4></li>
                <li><a href="#" onclick="showuserstat()"> by user <span
                        class="glyphicon glyphicon-chevron-right"> </span> </a></li>
                <li><a href="#" onclick="showtypestat()"> by categories <span
                        class="glyphicon glyphicon-chevron-right"> </span> </a></li>
                <li><a href="#" onclick="showdatestat()"> by date <span
                        class="glyphicon glyphicon-chevron-right"> </span> </a></li>
            </ul>
        </div>

        <table id="usertable" class="table table-hover" style="width:40%">

            <thead>
            <tr>
                <th>ID</th>
                <th>NAME</th>
                <th>Remove</th>
            </tr>
            </thead>
            <tbody id="usertablebody"></tbody>
        </table>

        <div id="booktable">
            <table class="table table-hover" style="width:40%">
                <thead>
                <tr>
                    <th>BOOK ID</th>
                    <th>NAME</th>
                    <th>TYPE</th>
                    <th>PRICE</th>
                    <th>REMOVE</th>
                </tr>
                </thead>
                <tbody id="booktablebody"></tbody>
            </table>
            <button class="btn btn-primary btn-lg" data-toggle="modal"
                    data-target="#myModal" style="margin: 0 auto;">ADD/MODIFY BOOK
            </button>
        </div>

        <div id="userstat">
            <table class="table table-hover" style="width:40%">
                <caption><h3>买家排行榜</h3></caption>
                <thead>
                <tr>
                    <th>USER_ID</th>
                    <th>NAME</th>
                    <th>Total Consumptions</th>
                </tr>
                </thead>
                <tbody id="userstatbody">
                </tbody>
            </table>
        </div>

        <div id="typestat" style="height:500px">
        </div>

        <div class="well" id="datepicker">
            <div class="input-daterange input-group" id="datepick" style="width:60%">
                <span class="input-group-addon">From</span>
                <input type="text" class="input-sm form-control" name="start" id="start" value="2015-01-01"
                       data-date-format="yyyy-mm-dd"/>
                <span class="input-group-addon">to</span>
                <input type="text" class="input-sm form-control" name="end" id="end" value="2016-01-01"
                       data-date-format="yyyy-mm-dd"/>
            </div>
        </div>

        <div id="datestat" style="height:400px;width:700px">
        </div>

    </div>

</div>
<script> showusertable();</script>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">ADD A BOOK /MODIFY A EXISTED BOOK</h4>
            </div>
            <div class="modal-body">
                <div style="text-align: center; font-size: 20px">
                    <!-- 						<input id="dowhat" TYPE=hidden NAME="dowhat" value="chpwd"></input> -->
                    Bookname:
                    <input id="bookname" TYPE=TEXT class="form-control" NAME="bookname"
                           style="width: 40%; height: 35px; margin: 0 auto;">
                    Type:
                    <input id="booktype" TYPE=text class="form-control" NAME="type"
                           style="width: 40%; height: 35px; margin: 0 auto;">
                    Price:
                    <input id="bookprice" TYPE=text class="form-control"
                           NAME="price" style="width: 40%; height: 35px; margin: 0 auto;">
                    <BR>
                    <button id="addbook" class="btn btn-primary" data-dismiss="modal" value="addbook"
                            onclick="addbook()" style="margin: 0 auto; font-size: 17px;">Upload
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $('#start').datepicker()
            .on('changeDate', function () {
                updatedatestat();
            });
    $('#end').datepicker()
            .on('changeDate', function () {
                updatedatestat();
            });
</script>
</body>
</html>
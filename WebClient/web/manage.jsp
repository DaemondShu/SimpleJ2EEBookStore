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
    <%--<script src="http://echarts.baidu.com/build/dist/echarts-all.js"></script>--%>
    <!-- <link href="luck_files/bootstrap.css" rel="stylesheet"> -->
    <link href="luck_files/datepicker.css" rel="stylesheet">
    <script src="luck_files/bootstrap-datepicker.js"></script>
    <script src="js/helpFunction.js"></script>

    <script src="js/manage.js"></script>
    <script src="js/user.js"></script>
    <script src="js/echarts-all.min.js"></script>

    <title>Management page</title>


</head>
<body>


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
    <div class="row-fluid">
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
        <div class="col-md-10">

            <table id="usertable" class="table table-hover" style="width:80%">

                <thead>
                <tr>
                    <th>ID</th>
                    <th>NAME</th>
                    <th>Remove</th>
                </tr>
                </thead>
                <tbody id="userTableBody">
                <tr>
                    <td class="id">id</td>
                    <td class="name">name</td>
                    <td>
                        <a class="remove" href="javascript:void(0);" onclick="delUser()">
                            <span class="glyphicon glyphicon-remove"> </span>
                        </a>
                    </td>
                </tr>
                </tbody>
            </table>

            <div id="booktable">
                <table class="table table-hover" style="width:80%">
                    <thead>
                    <tr>
                        <th>BOOK ID</th>
                        <th>NAME</th>
                        <th>TYPE</th>
                        <th>PRICE</th>
                        <th>REMOVE</th>
                    </tr>
                    </thead>
                    <tbody id="bookTableBody">
                    <td class="bookId">000</td>
                    <td class="bookName">载入中</td>
                    <td class="bookType">type</td>
                    <td class="bookPrice">100</td>
                    <td>
                        <a class="bookRemove" onclick="delBook" href="#">
                            <span class="glyphicon glyphicon-remove"> </span>
                        </a>
                    </td>
                    </tbody>
                </table>
                <button class="btn btn-primary " data-toggle="modal"
                        data-target="#myModal" style="margin: 0 auto;">ADD/MODIFY BOOK
                </button>
            </div>

            <div id="userstat">
                <table class="table table-hover" style="width:80%">
                    <caption><h3>买家排行榜</h3></caption>
                    <thead>
                    <tr>
                        <th>USER_ID</th>
                        <th>NAME</th>
                        <th>Total Consumptions</th>
                    </tr>
                    </thead>
                    <tbody id="userStatBody">
                    <td class="id">id</td>
                    <td class="name">name</td>
                    <td class="total">total</td>
                    </tbody>
                </table>
            </div>

            <div id="typestat" style="width: 700px; height: 500px">
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

            <div id="datestat" style="height:500px;width:700px">
            </div>
        </div>

    </div>

</div>


<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h3 class="modal-title" id="myModalLabel">ADD A BOOK /MODIFY A EXISTED BOOK</h3>
            </div>
            <div class="modal-body">
                <form id="bookForm" style="text-align: center; font-size: 20px">
                    Bookname:
                    <input type=TEXT class="form-control" name="name"
                           style="width: 40%; height: 35px; margin: 0 auto;">
                    Type:
                    <input type=text class="form-control" name="type"
                           style="width: 40%; height: 35px; margin: 0 auto;">
                    Price:
                    <input type=text class="form-control" name="price"
                           NAME="price" style="width: 40%; height: 35px; margin: 0 auto;">
                    <BR>
                    <input type=button class="btn btn-primary" data-dismiss="modal" value="Upload"
                           onclick="addBook()" style="margin: 0 auto; font-size: 17px;">
                    </input>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function ()
    {
        initTable();

        showusertable();


    });


    $('#start').datepicker()
            .on('changeDate', function ()
            {
                updatedatestat();
            });
    $('#end').datepicker()
            .on('changeDate', function ()
            {
                updatedatestat();
            });
</script>
</body>
</html>
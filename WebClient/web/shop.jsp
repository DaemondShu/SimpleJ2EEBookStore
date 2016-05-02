<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <%--<script src="bootstrap/dist/css/bootstrap-theme.min.css"></script>--%>
    <script src="js/jquery-2.1.4.js"></script>
    <script src="bootstrap/dist/js/bootstrap.min.js"></script>

    <script src="js/helpFunction.js"></script>
    <script src="js/shop.js"></script>
    <script src="js/user.js"></script>


    <title>Shop</title>
</head>
<body>


<nav class="navbar navbar-default" role="navigation"
     style="margin-bottom: 1px">
    <div class="container-fluid">
        <div class="navbar-header col-md-2 ">
            <a class="navbar-brand" href="#">Monkey.D.Store</a>
        </div>

        <div>
            <div class="navbar-form navbar-left " role="search" style="padding: 0px 0px">
                <div class="form-group">
                    <input id="searchcontent" type="text" class="form-control"
                           placeholder="book_name">
                </div>
                <button onclick="search()" class="btn btn-default">Search</button>
            </div>


            <ul class="nav navbar-nav navbar-right">
                <li class="user_opt">
                    <a href="chatRoom.html">
                        <span class="glyphicon glyphicon-comment"></span>
                        ChatRoom
                    </a>
                </li>
                <li class="dropdown user_opt">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                       onclick="printCart()" aria-expanded="false">
                        <span class="glyphicon glyphicon-shopping-cart"></span>
                        Shopping Cart
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu " role="menu">
                        <li>
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>BookId</th>
                                    <th>Name</th>
                                    <th>Unit-Price</th>
                                    <th>Number</th>
                                </tr>
                                </thead>
                                <tbody id="bookcart">

                                </tbody>
                            </table>
                        </li>
                    </ul>
                </li>


                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                        <span class="glyphicon glyphicon-user"></span>
                        <nobr id="username">user</nobr>
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li class="user_opt"><a href="javascript:void(0);" onclick="showOrder()"> my order</a>
                        <li><a href="javascript:void(0);" onclick="logout()">Log out</a></li>
                        <li class="user_opt"><a href="javascript:void(0);" data-toggle="modal" data-target="#myModal"
                               style="margin: 0 auto;">Modify Password</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid" style="margin-top: 0px">
    <div class="row">
        <div class="col-md-2 sidebar"
             style="background-color: #F8F8F8; margin-bottom: 4; height: 1024px; ">
            <ul class="nav nav-sidebar" style="margin-left: 10px" id="typeBar">
                <%--<li><h4>CATAGORIES</h4></li>--%>

                <li><a href="#" onclick="filterTypes()">type1 <span
                        class="glyphicon glyphicon-chevron-right"> </span> </a></li>

            </ul>
        </div>
        <div class="col-md-8">
            <div id="bookt">
                <table class="table table-hover" style="width: 35%">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>NAME</th>
                        <th>TYPE</th>
                        <th>PRICE</th>
                        <th>DETAIL</th>
                        <th class="user_opt">ADDTOCART</th>
                    </tr>
                    </thead>
                    <tbody id="booktable">

                    <tr id="book">
                        <td class="bookId">000</td>
                        <td class="bookName">载入中</td>
                        <td class="bookType">type</td>
                        <td class="bookPrice">100</td>
                        <td>
                            <a class="bookDetail" onclick="getDetail()" href="#"> <span
                                    class="glyphicon glyphicon-list-alt"> </span>
                            </a>
                        </td>
                        <td class="user_opt">
                            <a class="bookButton" onclick="addToCart()" href="#"> <span
                                class="glyphicon glyphicon-shopping-cart"> </span>
                        </a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>


            <div id="orderTable">
                <table class="table table-hover" style="width: 40%">
                    <thead>
                    <tr>
                        <th>order_ID</th>
                        <th>NAME</th>
                        <th>PRICE</th>
                        <th>Date</th>
                        <th>Cancel</th>
                    </tr>
                    </thead>
                    <tbody id="orderTableBody">
                    <tr>
                        <td class="id"> id</td>
                        <td class="name"> 名</td>
                        <td class="price"></td>
                        <td class="date"></td>
                        <td>
                            <a class="cancel" onclick="delOrder()" href="javascript:void(0);">
                                <span class="glyphicon glyphicon-remove"> </span>
                            </a>
                        </td>
                    </tr>

                    </tbody>
                </table>
            </div>

        </div>
    </div>
</div>


<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Modify the Password</h4>
            </div>
            <div class="modal-body">
                <FORM id="pwdForm"
                      style="text-align: center; font-size: 20px">
                    Username:<input TYPE=TEXT class="form-control"
                                    NAME="username" style="width: 40%; height: 35px; margin: 0 auto;">
                    Oldpassword:<input TYPE=PASSWORD class="form-control"
                                       NAME="pwd1" style="width: 40%; height: 35px; margin: 0 auto;">
                    Newpassword:<input TYPE=PASSWORD class="form-control"
                                       NAME="pwd2" style="width: 40%; height: 35px; margin: 0 auto;">
                    <BR> <input id="reg" class="btn btn-default" TYPE=button onclick="chPwd()"
                                value="confirm"
                                style="padding: 3px; margin: 10px 0; font-size: 17px;">
                </FORM>
            </div>
        </div>
    </div>
</div>


<script>
    $(document).ready(function ()
    {
        checkUser();
        initBookTable();

        $("#orderTable").hide();
        $("#bookt").fadeIn("slow");
        //alert($("#username").html());
    })


</script>

</body>
</html>
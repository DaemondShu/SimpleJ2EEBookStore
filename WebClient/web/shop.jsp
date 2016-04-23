<%@page import="dataAccess.AccessDB" %>
<%@page import="java.util.List" %>
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


    <title>Insert title here</title>
    <script>


        function flitertypes(str) {
            $("#ordertable").hide();
            $("#bookt").fadeIn("slow");
            booktable = document.getElementById("booktable");
            for (var i = 0; i < booktable.rows.length; i++) {
                book_id = booktable.rows[i].cells[0].innerHTML;
                if (booktable.rows[i].cells[2].innerHTML != str) {
                    $("#booktable" + book_id).hide();
                }
                else {
                    $("#booktable" + book_id).fadeIn("slow");
                }
            }
        }

        function search() {
            $("#ordertable").hide();
            $("#bookt").fadeIn("slow");
            var searchcontent = document.getElementById("searchcontent").value;
            var booktable = document.getElementById("booktable");

            for (var i = 0; i < booktable.rows.length; i++) {
                str = booktable.rows[i].cells[1].innerHTML;
                book_id = booktable.rows[i].cells[0].innerHTML;
                if (str.indexOf(searchcontent) < 0) {
                    $("#booktable" + book_id).hide();
                }
                else {
                    $("#booktable" + book_id).fadeIn("slow");
                }
            }
        }

        function addtosession(bookid) {
            var username = $("#username").text();
            $.post("ShoppingAdd",
                    {
                        usercart: username + "cart",
                        bookid: bookid
                    }
                    ,
                    function (data, status) {
                    });
        }

        function setsession(newdata) {
            var username = $("#username").text();
            $.post("ShoppingSet",
                    {
                        usercart: username + "cart",
                        data: newdata
                    },
                    function (data, status) {
                    });
        }

        function addtocart(bookid) {
            addtosession(bookid);
        }

        function printcart() {
            var tmp = "";
            var username = $("#username").text();
            $.post("ShoppingGet",
                    {
                        usercart: username + "cart",
                    },
                    function (session) {
                        //var session=data;
                        if (session != null) var arr = session.split(";");
                        else var arr = new Array();
                        var cart = new Array();
                        var k = 0;
                        var text = "";
                        for (var i = 0; i < arr.length; i++)
                            if (arr[i] != "") {
                                bookid = parseInt(arr[i]);
                                if (cart[bookid] == null)
                                    cart[bookid] = 1;
                                else cart[bookid]++;
                            }

                        var tprice = 0;
                        for (var key in cart) {
                            text += "<tr id=bookid" + key + ">";
                            text += "<td>" + key + "</td>";

                            book = document.getElementById("booktable" + key);
                            text += "<td>" + book.cells[1].innerHTML + "</td>";
                            tprice += cart[key] * parseFloat(book.cells[3].innerHTML);
                            text += "<td>" + book.cells[3].innerHTML + "</td>";
                            text += "<td>" + cart[key] + "</td>";
                            //text+="<td><span onclick=\"delone("+key+")\" class=\"glyphicon glyphicon-remove\"></span></td>";
                            text += "</tr>";
                        }
                        text += "<tr>" + "\
	    <td> <button type=\"button\" class=\"btn btn-default\" onclick=\"buy()\">BUY</button>  </td> \
	    <td> <button type=\"button\" class=\"btn btn-default\" onclick=\"clearr()\">CLEAR</button>  </td>\
	    <td> <h5>total$:</h5> </td>\
	    <td> <h4 id=\"totalprice\">$" + tprice + "</h4> </td> </tr>";
                        $("#bookcart").html(text);
                    }
            );
        }

        function clearr() {
            setsession("");
        }

        function buy() {
            var username = $("#username").text();

            $.post("ShoppingBuy",
                    {
                        usercart: username + "cart",
                    },
                    function (data, status) {

                    });
        }

        function showorder() {
            $("#ordertable").fadeIn("slow");
            $("#bookt").hide();
            var username = $("#username").text();
            $.post("ShoppingOrder",
                    {
                        username: username
                    },
                    function (data, status) {
                        $("#ordertablebody").html(data);
                    });
        }

        function delorder(id) {
            $.post("ShoppingDelorder",
                    {
                        orderid: id
                    },
                    function (data, status) {
                        showorder();
                    });
        }

        // function setcookie(name,value,expiresHours)
        // {
        // 	var cookieString=name+"="+escape(value);
        // 	if(expiresHours>0)
        // 	{
        // 		var date=new Date();
        // 		date.setTime(date.getTime+expiresHours*3600*1000);
        // 		cookieString=cookieString+"; expires="+date.toGMTString();
        // 	}
        // 	document.cookie=cookieString;
        // }
        // function getcookie(c_name)
        // {
        // 	if (document.cookie.length>0)
        // 	{
        // 		c_start=document.cookie.indexOf(c_name + "=");
        // 		if (c_start!=-1)
        // 	  	{
        // 			c_start=c_start + c_name.length+1;
        // 			c_end=document.cookie.indexOf(";",c_start);
        // 			if (c_end==-1) c_end=document.cookie.length;
        // 			return unescape(document.cookie.substring(c_start,c_end));
        // 		}
        // 	}
        // 	return "";
        // }

        // function deletecookie(name)
        // {
        // 	var date=new Date();
        // 	date.setTime(date.getTime()-10000);
        // 	document.cookie=name+"=v; expires="+date.toGMTString();
        // }


    </script>


</head>
<body>
<%
    //session.setAttribute("username", "user1");
    Object tmp = session.getAttribute("username");
    if (tmp == null) response.sendRedirect("login.jsp=logfail");

    String user = tmp.toString();
    try
    {
        String logcheck = request.getParameter("login");
        if (logcheck == null || logcheck.equals(""))
            response.sendRedirect("login.jsp=logfail");

        int login = Integer.parseInt(logcheck);
        //int login=1021;
        if (user == null || user.equals("") || login != 1021)
        {
            response.sendRedirect("login.jsp=logfail");
        }
    } catch (Exception e)
    {
        response.sendRedirect("login.jsp=logfail");
    }
%>
<%
    //String type = request.getParameter("type");
    AccessDB ADB = new AccessDB();
    List<Object[]> rs = ADB.book_queryall(null);

    String booktable = "";
    String typelist = "";

    int i = 0;
    if (rs != null)
    {
        for (Object[] obj : rs)
        {
            String temptype = obj[2].toString();
            String book_id = obj[0].toString();
            booktable += "<tr id=booktable" + book_id + ">";
            booktable += "<td>" + book_id + "</td>";
            booktable += "<td>" + obj[1].toString() + "</td>";
            booktable += "<td>" + temptype + "</td>";
            booktable += "<td>" + obj[3].toString() + "</td>";
            booktable += "<td><a onclick=\"addtocart(" + book_id + ")\" href=\"#\"> <span class=\"glyphicon glyphicon-shopping-cart\"> </span> </a><td>";
            booktable += "</tr>";
            if (typelist.indexOf(temptype) == -1)
                typelist += "<li><a href=\"#\" onclick=\"flitertypes('" + temptype + "')\">" + temptype + "<span class=\"glyphicon glyphicon-chevron-right\"> </span> </a></li>";
            i++;
        }
    }
%>

<a id="username" style="display: none"><%=user%>
</a>

<nav class="navbar navbar-default" role="navigation"
     style="margin-bottom: 1px">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Monkey.D.Store</a>
        </div>

        <div>
            <div class="navbar-form navbar-left" role="search"
                 style="margin-left: 20px">
                <div class="form-group">
                    <input id="searchcontent" type="text" class="form-control"
                           placeholder="book_name">
                </div>
                <button onclick="search()" class="btn btn-default">Search</button>
            </div>


            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown"><a href="login.jsp"
                                        class="dropdown-toggle" data-toggle="dropdown" role="button"
                                        onclick="printcart()" aria-expanded="false"><span
                        class="glyphicon glyphicon-shopping-cart"></span>Shopping Cart <span
                        class="caret"></span></a>
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


                <li class="dropdown"><a href="#" class="dropdown-toggle"
                                        data-toggle="dropdown" role="button" aria-expanded="false"><span
                        class="glyphicon glyphicon-user"></span> <%=user %> <span
                        class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#" onclick="showorder()"> my order</a>
                        <li><a href="login.jsp">Log out</a></li>
                        <li><a href="#" data-toggle="modal" data-target="#myModal"
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
             style="background-color: #F8F8F8; margin-bottom: 4px; height: 1024px">
            <ul class="nav nav-sidebar" style="margin-left: 10px">
                <li><h4>CATAGORIES</h4></li>
                <%=typelist %>
            </ul>
        </div>

        <div id="bookt">
            <table class="table table-hover" style="width: 40%">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>NAME</th>
                    <th>TYPE</th>
                    <th>PRICE</th>
                    <th>ADDTOCART</th>
                </tr>
                </thead>
                <tbody id="booktable">
                <%=booktable %>
                </tbody>
            </table>
        </div>


        <div id="ordertable">
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
                <tbody id="ordertablebody">

                </tbody>
            </table>
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
                <FORM METHOD=POST ACTION="UserChpwd"
                      style="text-align: center; font-size: 20px">
                    Username:<input id="username" TYPE=TEXT class="form-control"
                                    NAME="username" style="width: 40%; height: 35px; margin: 0 auto;">
                    Oldpassword:<input id="oldpwd" TYPE=PASSWORD class="form-control"
                                       NAME="pwd1" style="width: 40%; height: 35px; margin: 0 auto;">
                    Newpassword:<input id="newpwd" TYPE=PASSWORD class="form-control"
                                       NAME="pwd2" style="width: 40%; height: 35px; margin: 0 auto;">
                    <BR> <input id="reg" class="btn btn-default" TYPE=SUBMIT
                                value="confirm"
                                style="padding: 3px; margin: 10px 0; font-size: 17px;">
                </FORM>
            </div>
        </div>
    </div>
</div>
<script>
    $("#ordertable").hide();
    $("#bookt").fadeIn("slow");
</script>

</body>
</html>
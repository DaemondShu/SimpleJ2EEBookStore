/**
 * Created by monkey_d_asce on 16-4-24.
 */

var userObj = undefined;

function checkUser()
{
    var jsonStr = getCookie("user");

    if (jsonStr == null || jsonStr == "")
    {
        window.location = "../index.jsp"; //TODO tips
        return;
    }

    userObj = JSON.parse(jsonStr);
    $("#username").html(userObj.name);

    if (userObj.principal == "guest")
    {
        $(".user_opt").hide();
    }
}

function getUserName()
{
    return userObj.name;
}


function filterTypes(str)
{
    $("#orderTable").hide();
    $("#bookt").fadeIn("slow");

    booktable = document.getElementById("booktable");


    // if (str == "CATAGORIES")
    //     for (var i = 0; i < booktable.rows.length; i++)
    //     {
    //         book_id = booktable.rows[i].cells[0].innerHTML;
    //         $("#book" + book_id).fadeIn("slow");
    //     }
    //
    // else

    for (var i = 0; i < booktable.rows.length; i++)
    {
        book_id = booktable.rows[i].cells[0].innerHTML;
        if (booktable.rows[i].cells[2].innerHTML != str)
        {
            $("#book" + book_id).hide();
        }
        else
        {
            $("#book" + book_id).fadeIn("slow");
        }
    }
}


function initTypeList(typeList)
{
    var typeBar = $("#typeBar");
    var singleTypeHtml = typeBar.html();
    typeBar.html("<li><h4>CATAGORIES</h4></li>");
    var len = typeList.length;
    for (var i = 0; i < len; i++)
    {
        typeBar.append(singleTypeHtml);
        var item = typeBar.find("a").eq(i);
        var type = typeList[i];
        item.attr("onclick", "filterTypes('" + type + "')");
        item.html(type + "<span class='glyphicon glyphicon-chevron-right'> </span>");

    }


}


var singleBookHtml = undefined;


function displayBook(jsonStr)
{
    //alert(jsonStr);
    var bookTable = $("#booktable");
    var bookArray = JSON.parse(jsonStr);
    var len = bookArray.length;

    //bookTable = $("#booktable");
    var typeList = [];

    var temp = (userObj.principal == "guest");
    for (var i = 0; i < len; i++)
    {
        var book = bookArray[i];

        if (temp && book.type == "vip")
            continue;

        bookTable.append(singleBookHtml);
        var item = bookTable.find("tr").eq(i);

        item.attr("id", "book" + book.bookId);
        item.find(".bookId").html(book.bookId);
        item.find(".bookType").html(book.type);
        item.find(".bookName").html(book.name);
        item.find(".bookPrice").html(book.price);
        item.find(".bookDetail").attr("onclick", "getDetail(" + book.bookId + ")");
        item.find(".bookButton").attr("onclick", "addToCart(" + book.bookId + ")");

        if (!isExist(typeList, book.type))
            typeList.push(book.type);
    }
    if (temp)
    {
        $(".user_opt").hide();
    }
    initTypeList(typeList);
}


function initBookTable(useWebService)
{
    useWebService = useWebService == undefined ? true : false;

    var bookTable = $("#booktable");
    if (singleBookHtml == undefined)
        singleBookHtml = bookTable.html();

    bookTable.html("");


    if (useWebService) //soap webservice
    {
        $.soap({
            url: 'BookService',
            soap12: false,
            appendMethodToURL: false,
            data: {}, //data 必须有，否则他就不发送了，没有参数就为空
            method: 'table',
            context: document.body,
            success: function (wsResponse)
            {
                var dom = wsResponse.toXML();
                var ss = $(dom).find("return");
                displayBook(ss.text());
            },
            error: defaultError
        });
    }
    else  // servlet
    {
        var data = {action: "table"};
        ajax("Book", "get", data, displayBook);
    }

}


function search()
{
    $("#orderTable").hide();
    $("#bookt").fadeIn("slow");
    var searchcontent = document.getElementById("searchcontent").value;
    var booktable = document.getElementById("booktable");

    for (var i = 0; i < booktable.rows.length; i++)
    {
        str = booktable.rows[i].cells[1].innerHTML;
        book_id = booktable.rows[i].cells[0].innerHTML;
        if (str.indexOf(searchcontent) < 0)
        {
            $("#book" + book_id).hide();
        }
        else
        {
            $("#book" + book_id).fadeIn("slow");
        }
    }
}


//----------------------------Shop-----------------------

function addToSession(bookId)
{
    var username = $("#username").text();

    ajax("Shop", "post",
        {
            action: "cartInsert",
            userCart: username + "cart",
            bookId: bookId
        },
        emptyCallBack, emptyCallBack);

    /*
    $.post("ShoppingAdd",
        {
            usercart: username + "cart",
            bookid: bookid
        }
        ,
        function (data, status)
        {
        });
     */

}

function setSession(newdata)
{
    var username = $("#username").text();


    ajax("Shop", "post",
        {
            action: "cartSet",
            userCart: username + "cart",
            cartData: newdata
        },
        emptyCallBack, emptyCallBack);
}

function addToCart(bookid)
{
    addToSession(bookid);
}

function printCart()
{
    var tmp = "";
    var username = $("#username").text();
    ajax("Shop", "get",
        {
            action: "cartGet",
            userCart: username + "cart",
        },
        function (session)
        {
            //var session=data;
            if (session != null) var arr = session.split(";");
            else var arr = new Array();
            var cart = new Array();
            var k = 0;
            var text = "";
            for (var i = 0; i < arr.length; i++)
                if (arr[i] != "")
                {
                    bookid = parseInt(arr[i]);
                    if (cart[bookid] == null)
                        cart[bookid] = 1;
                    else cart[bookid]++;
                }

            var tprice = 0;
            for (var key in cart)
            {
                text += "<tr id='bookid'" + key + ">";
                text += "<td>" + key + "</td>";

                book = document.getElementById("book" + key);
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

function clearr()
{
    setSession("");
}

function buy()
{
    //var username = $("#username").text();


    ajax("Shop", "post",
        {
            action: "buy",
            userCart: userObj.name + "cart",
            username: userObj.name
        },
        function ()
        {
            alert("checkout success");
        });
}

var orderItemHtml = undefined;
function showOrder()
{
    if (userObj == undefined)
        return;

    $("#orderTable").fadeIn("slow");
    $("#bookt").hide();
    var username = $("#username").text();

    ajax("Shop", "get",
        {
            action: "getOrder",
            username: userObj.name
        },
        function (jsonStr)
        {
            var jsonArr = JSON.parse(jsonStr);
            var orderTable = $("#orderTableBody");

            if (orderItemHtml == undefined)
                orderItemHtml = orderTable.html();
            orderTable.html("");

            var len = jsonArr.length;
            var i = 0;
            for (i = 0; i < len; i++)
                orderTable.append(orderItemHtml);
            for (i = 0; i < len; i++)
            {
                var obj = jsonArr[i];
                var tr = orderTable.find("tr").eq(i);
                tr.find(".id").html(obj.orderId);
                tr.find(".name").html(obj.name);
                tr.find(".price").html(obj.price);
                tr.find(".date").html(obj.time);
                tr.find(".cancel").attr("onclick", "delOrder(" + obj.orderId + ")");
            }

        });

}

function delOrder(id)
{
    ajax("Shop", "post",
        {
            action: "delOrder",
            orderId: id
        }, function ()
        {
            showOrder();
        });
}


function getDetail(id)
{
    ajax("Book", "get",
        {
            action: "detail",
            bookId: id
        }, function (data)
        {
            alert(data);
        })
}
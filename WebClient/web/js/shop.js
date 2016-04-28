/**
 * Created by monkey_d_asce on 16-4-24.
 */



function filterTypes(str)
{
    $("#ordertable").hide();
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

function initBookTable()
{
    var bookTable = $("#booktable");
    var singleBookHtml = bookTable.html();
    bookTable.html("");


    var data = {action: "table"};
    ajax("Book", "get", data,
        function (jsonStr)
        {
            //alert(jsonStr);
            var bookArray = JSON.parse(jsonStr);
            var len = bookArray.length;
            for (var i = 0; i < len; i++)
                bookTable.append(singleBookHtml);
            //bookTable = $("#booktable");
            var typeList = [];

            for (var i = 0; i < len; i++)
            {
                var item = bookTable.find("tr").eq(i);
                var book = bookArray[i];
                item.attr("id", "book" + book.bookId);
                item.find(".bookId").html(book.bookId);
                item.find(".bookType").html(book.type);
                item.find(".bookName").html(book.name);
                item.find(".bookPrice").html(book.price);
                item.find(".bookButton").attr("onclick", "addToCart(" + book.bookId + ")");

                if (!isExist(typeList, book.type))
                    typeList.push(book.type);
            }

            initTypeList(typeList);

        });
}


function search()
{
    $("#ordertable").hide();
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
    $.post("Shop",
        {
            action: "cartSet",
            userCart: username + "cart",
            cartData: newdata
        },
        function (data, status)
        {
        });
}

function addToCart(bookid)
{
    addToSession(bookid);
}

function printcart()
{
    var tmp = "";
    var username = $("#username").text();
    $.post("ShoppingGet",
        {
            usercart: username + "cart",
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

function clearr()
{
    setSession("");
}

function buy()
{
    var username = $("#username").text();

    $.post("ShoppingBuy",
        {
            usercart: username + "cart",
        },
        function (data, status)
        {

        });
}

function showorder()
{
    $("#ordertable").fadeIn("slow");
    $("#bookt").hide();
    var username = $("#username").text();
    $.post("ShoppingOrder",
        {
            username: username
        },
        function (data, status)
        {
            $("#ordertablebody").html(data);
        });
}

function delorder(id)
{
    $.post("ShoppingDelorder",
        {
            orderid: id
        },
        function (data, status)
        {
            showorder();
        });
}
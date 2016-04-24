/**
 * Created by monkey_d_asce on 16-4-24.
 */



function flitertypes(str)
{
    $("#ordertable").hide();
    $("#bookt").fadeIn("slow");
    booktable = document.getElementById("booktable");
    for (var i = 0; i < booktable.rows.length; i++)
    {
        book_id = booktable.rows[i].cells[0].innerHTML;
        if (booktable.rows[i].cells[2].innerHTML != str)
        {
            $("#booktable" + book_id).hide();
        }
        else
        {
            $("#booktable" + book_id).fadeIn("slow");
        }
    }
}

function initBookTable()
{
    
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
            $("#booktable" + book_id).hide();
        }
        else
        {
            $("#booktable" + book_id).fadeIn("slow");
        }
    }
}

function addtosession(bookid)
{
    var username = $("#username").text();
    $.post("ShoppingAdd",
        {
            usercart: username + "cart",
            bookid: bookid
        }
        ,
        function (data, status)
        {
        });
}

function setsession(newdata)
{
    var username = $("#username").text();
    $.post("ShoppingSet",
        {
            usercart: username + "cart",
            data: newdata
        },
        function (data, status)
        {
        });
}

function addtocart(bookid)
{
    addtosession(bookid);
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
    setsession("");
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
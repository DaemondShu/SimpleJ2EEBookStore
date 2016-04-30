/**
 * Created by monkey_d_asce on 16-4-30.
 */

var singleUserHtml = undefined;
var singleBookHtml = undefined;
var singleUStatHtml = undefined;


function updateusertable()
{

    ajax("User", "get",
        {
            action: "table"
        },
        function (data)
        {
            var userTableBody = $("#userTableBody");
            if (singleUserHtml == undefined)
            {
                singleUserHtml = userTableBody.html();
            }
            userTableBody.html("");
            var userArr = JSON.parse(data);
            var len = userArr.length;
            //skip admin
            for (var i = 1; i < len; i++)
            {
                userTableBody.append(singleUserHtml);
            }
            for (var i = 1; i < len; i++)
            {
                var obj = userArr[i];
                var tr = userTableBody.find("tr").eq(i - 1); //admin
                tr.find(".id").html(obj.userId);
                tr.find(".name").html(obj.name);
                tr.find(".remove").attr("onclick", "delUser(" + obj.userId + ")")
            }

        });

}


function updatebooktable()
{
    ajax("Book", "get",
        {
            action: "table"
        },
        function (jsonStr)
        {
            //alert(jsonStr);
            var bookTableBody = $("#bookTableBody");
            if (singleBookHtml == undefined)
                singleBookHtml = bookTableBody.html();
            bookTableBody.html("");

            var bookArray = JSON.parse(jsonStr);
            var len = bookArray.length;


            for (var i = 0; i < len; i++)
                bookTableBody.append(singleBookHtml);
            //bookTable = $("#booktable");
            var typeList = [];

            for (var i = 0; i < len; i++)
            {
                var item = bookTableBody.find("tr").eq(i);
                var book = bookArray[i];
                //item.attr("id", "book" + book.bookId);
                item.find(".bookId").html(book.bookId);
                item.find(".bookType").html(book.type);
                item.find(".bookName").html(book.name);
                item.find(".bookPrice").html(book.price);
                item.find(".bookRemove").attr("onclick", "delBook(" + book.bookId + ")");

            }

        });
}
function updateuserstat()
{

    ajax("SaleStat", "get",
        {
            action: "userStat"
        },
        function (data)
        {
            var userStatBody = $("#userStatBody");
            if (singleUStatHtml == undefined)
            {
                singleUStatHtml = userStatBody.html();
            }
            userStatBody.html("");
            var userArr = JSON.parse(data);

            var len = userArr.length;
            //skip admin
            for (var i = 0; i < len; i++)
            {
                userStatBody.append(singleUStatHtml);
            }
            for (var i = 0; i < len; i++)
            {
                var obj = userArr[i];
                var tr = userStatBody.find("tr").eq(i); //admin
                tr.find(".id").html(obj.userId);
                tr.find(".name").html(obj.name);
                tr.find(".total").html(obj.total);
            }

        });


}


function updatetypestat()
{

    ajax("SaleStat", "get",
        {
            action: "typeStat"

        }, function (data)
        {
            arr = data.split(";");

            var j = 0;
            var xx = new Array();
            var xyy = new Array();
            for (var i = 0; i < arr.length - 1; i = i + 2)
            {
                j = i / 2;
                xx[j] = arr[i];
                xyy[j] =
                {
                    value: parseInt(arr[i + 1]),
                    name: arr[i]
                };
            }

            var myChart = echarts.init(document.getElementById('typestat'));
            option = {
                title: {
                    text: '各种类书本销售情况分布',
                    x: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    x: 'left',
                    data: xx
                },
                series: [
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius: ['50%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            normal: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                show: true,
                                textStyle: {
                                    fontSize: '30',
                                    fontWeight: 'bold'
                                }
                            }
                        },
                        labelLine: {
                            normal: {
                                show: false
                            }
                        },
                        data: xyy
                    }
                ]
            };
            myChart.setOption(option);
        });


    //   });
}

function updatedatestat()
{
    ajax("SaleStat", "get",
        {
            action: "dateStat"
        },
        function (data)
        {
            //alert(data);
            arr = data.split(";");
            var j = 0;
            var xx = new Array();
            var yy = new Array();
            var start_time = $('#start').val();
            var end_time = $('#end').val();
            for (var i = 0; i < arr.length - 1; i = i + 2)
            {
                //j=i/2;
                if (arr[i] >= start_time && arr[i] <= end_time)
                {
                    xx[j] = arr[i];
                    yy[j] = parseInt(arr[i + 1]);
                    j++;
                }

            }
            var myChart = echarts.init(document.getElementById('datestat'));

            option = {
                title: {
                    text: '总销售记录曲线',
                    subtext: '忽略销量为0的日子'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: ['日销售量']
                },
                toolbox: {
                    show: true,
                    feature: {
                        dataZoom: {},
                        dataView: {readOnly: true},
                        magicType: {type: ['line', 'bar']},
                        saveAsImage: {}
                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: xx
                },
                yAxis: {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} °C'
                    }
                },
                series: [
                    {
                        name: '日销售量',
                        type: 'line',
                        data: yy,
                        markPoint: {
                            data: [
                                {type: 'max', name: '最大值'},
                                {type: 'min', name: '最小值'}
                            ]
                        },
                        markLine: {
                            data: [
                                {type: 'average', name: '平均值'}
                            ]
                        }
                    }
                ]
            };
            myChart.setOption(option);
        })
}

function initTable()
{
    $("#usertable").hide();
    $("#booktable").hide();
    $("#userstat").hide();
    $("#typestat").hide();
    $("#datestat").hide();
    $("#datepicker").hide();
}



function showusertable()
{
    updateusertable();
    $("#usertable").fadeIn("slow");
    $("#booktable").hide();
    $("#userstat").hide();
    $("#typestat").hide();
    $("#datestat").hide();
    $("#datepicker").hide();
}

function showbooktable()
{
    updatebooktable();
    $("#usertable").hide();
    $("#booktable").fadeIn("slow");
    $("#userstat").hide();
    $("#typestat").hide();
    $("#datestat").hide();
    $("#datepicker").hide();
}

function showuserstat()
{
    updateuserstat();
    $("#usertable").hide();
    $("#booktable").hide();
    $("#userstat").fadeIn("slow");
    $("#typestat").hide();
    $("#datestat").hide();
    $("#datepicker").hide();
}

function showtypestat()
{
    updatetypestat();
    $("#usertable").hide();
    $("#booktable").hide();
    $("#userstat").hide();
    $("#typestat").show();
    $("#datestat").hide();
    $("#datepicker").hide();
}

function showdatestat()
{
    updatedatestat();
    $("#usertable").hide();
    $("#booktable").hide();
    $("#userstat").hide();
    $("#typestat").hide();
    $("#datepicker").fadeIn();
    $("#datestat").fadeIn("slow");

}


function delBook(id)
{
    ajax("Book", "post",
        {
            action: "del",
            bookId: id
        },
        function ()
        {
            msg("success!", 1);
            updatebooktable();
        });
}

function addBook()
{
    var bookForm = $("#bookForm");
    var data = bookForm.serializeObject();
    data.action = "add";

    ajax("Book", "post", data,
        function ()
        {
            msg("success!", 1);
            updatebooktable();
        });


}

function delUser(id)
{
    ajax("User", "post",
        {
            action: "del",
            userId: id
        }, function ()
        {
            updateusertable();
        });
}
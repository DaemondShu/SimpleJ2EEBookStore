/**
 * Created by monkey_d_asce on 16-4-30.
 */

function updateusertable()
{

    ajax("User", "get", {},
        function (data)
        {
            $("#usertablebody").html(data);
        });

}


function updatebooktable()
{
    $.post("BookTable",
        {},
        function (data, status)
        {
            $("#booktablebody").html(data);
        });
}
function updateuserstat()
{
    $.post("StatByUser",
        {},
        function (data, status)
        {
            $("#userstatbody").html(data);
        });
}

function updatetypestat()
{
    $.post("StatByType",
        {},
        function (data, status)
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
                    text: '书籍各类目销售额对比',
                    x: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    x: 'left',
                    data: xx
                },
                calculable: true,
                series: [
                    {
                        name: '书籍类目',
                        type: 'pie',
                        radius: '55%',
                        center: ['30%', '60%'],
                        data: xyy
                    }
                ]
            };
            myChart.setOption(option);
        });
}

function updatedatestat()
{
    $.post("StatByDate",
        {},
        function (data, status)
        {
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
                    text: '销售记录折线图'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: ['日总销售额']
                },
                calculable: true,
                toolbox: {
                    show: true,
                    feature: {
                        magicType: {show: true, type: ['line', 'bar']},
                    }
                },
                xAxis: [
                    {
                        type: 'category',
                        boundaryGap: false,
                        data: xx
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        axisLabel: {
                            formatter: '{value} $'
                        }
                    }
                ],
                series: [
                    {
                        name: '日总销售额',
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
    $("#typestat").fadeIn("slow");
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

function deluser(id)
{
    $.post("UserDel",
        {
            userid: id.toString()
        },
        function (data, status)
        {
            if (data != "ok") alert("delete failed");
            else updateusertable();
        });
}

function delbook(id)
{
    $.post("BookDel",
        {
            bookid: id.toString()
        },
        function (data, status)
        {
            if (data != "ok") alert("delete failed");
            else updatebooktable();
        });
}

function addbook()
{
    $.post("BookAdd",
        {
            name: $("#bookname")[0].value,
            type: $("#booktype")[0].value,
            price: $("#bookprice")[0].value
        },
        function (data, status)
        {
            if (data != "ok") alert("Add failed");
            else
            {
                updatebooktable();
                //$("#myModal").fadeOut();
            }
        });
}
var websocket = null;
var singleChat = undefined;

function connect()
{

    var wsURI = window.location.protocol == 'http:'
        ? 'ws://' + window.location.host + '/bookstore/chat'
        : 'wss://' + window.location.host + '/bookstore/chat';

    websocket = new WebSocket(wsURI);


    websocket.onopen = function ()
    {

        sendMessage(
            {
                action: "setUser",
                username: getUserName()
            });
    };
    websocket.onmessage = function (event)
    {

        var dataObj = JSON.parse(event.data);
        switch (dataObj.action)
        {
            case "users":
                updateUsers(dataObj.users);
                break;
            case "chat":
                addChat(dataObj);
                break;

        }
        // log the event
        //displayMessage('The response was received! ' + event.data, 'success');
    };
    websocket.onerror = function (event)
    {
        alert("error");
        // log the event
        //displayMessage('Error! ' + event.data, 'error');
    };
    websocket.onclose = function ()
    {
        //alert("onclose");
        //displayMessage('The connection was closed or timed out. Please click the Open Connection button to reconnect.');
        // document.getElementById('sayHello').disabled = true;
    };
}

function disconnect()
{
    if (websocket !== null)
    {
        websocket.close();
        websocket = null;
    }
    // log the event
}


function chat()
{
    var text = $("#chatText").val();
    if (text == undefined || text == "")
        return;
    sendMessage(
        {
            action: "chat",
            value: text
        });
}

function sendMessage(obj)
{
    if (websocket == null)
    {
        msg("cannot connect remote websocket.", 1);
        return;
    }
    websocket.send(JSON.stringify(obj));
}


var singleHtml = undefined;

function updateUsers(userList)
{
    var userBar = $("#userBar");
    if (singleHtml == undefined)
        singleHtml = userBar.html();

    userBar.html("<li><h4>&nbsp Online Users</h4></li>");
    var len = userList.length;
    for (var i = 0; i < len; i++)
    {
        userBar.append(singleHtml);
        var item = userBar.find("a").eq(i);
        var type = userList[i];
        item.html(type);

    }
}


function addChat(obj)
{

    if (singleChat == undefined)
    {
        singleChat = $("#chatArea").html();
        $("#chatArea").html("");
    }

    var chatArea = $("#chatArea");
    chatArea.prepend(singleChat);
    var newChat = chatArea.find("div:first");
    newChat.fadeIn("slow");
    newChat.find(".leftimg").html(obj.user);
    newChat.find(".speech").html(obj.text);
}



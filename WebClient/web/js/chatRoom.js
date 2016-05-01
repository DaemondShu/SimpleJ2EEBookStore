var websocket = null;

function connect()
{

    var wsURI = window.location.protocol == 'http:'
        ? 'ws://' + window.location.host + '/bookstore/chat'
        : 'wss://' + window.location.host + '/bookstore/chat';

    websocket = new WebSocket(wsURI);

    websocket.onopen = function ()
    {
        alert("onopen");


        //setUser
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
            //case "":

        }
        // log the event
        //displayMessage('The response was received! ' + event.data, 'success');
    };
    websocket.onerror = function (event)
    {
        alert("onerror");
        // log the event
        //displayMessage('Error! ' + event.data, 'error');
    };
    websocket.onclose = function ()
    {
        alert("onclose");
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

function sendMessage(obj)
{
    if (websocket == null)
    {
        msg("cannot connect remote websocket.", 1);
        return;
    }
    websocket.send(JSON.stringify(obj));
}


function updateUsers()
{

}




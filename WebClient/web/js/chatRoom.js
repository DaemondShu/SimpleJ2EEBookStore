var websocket = null;

function connect()
{

    var wsURI = window.location.protocol == 'http:'
        ? 'ws://' + window.location.host + '/bookstore/chat'
        : 'wss://' + window.location.host + '/bookstore/chat';

    websocket = new WebSocket(wsURI);

    websocket.onopen = function ()
    {
        displayStatus('Open');
        document.getElementById('sayHello').disabled = false;
        displayMessage('Connection is now open. Type a name and click Say Hello to send a message.');
    };
    websocket.onmessage = function (event)
    {
        // log the event
        displayMessage('The response was received! ' + event.data, 'success');
    };
    websocket.onerror = function (event)
    {
        // log the event
        displayMessage('Error! ' + event.data, 'error');
    };
    websocket.onclose = function ()
    {
        displayStatus('Closed');
        displayMessage('The connection was closed or timed out. Please click the Open Connection button to reconnect.');
        document.getElementById('sayHello').disabled = true;
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

function sendMessage()
{
    if (websocket !== null)
    {
        var content = document.getElementById('name').value;
        websocket.send(content);
    } else
    {
        displayMessage('WebSocket connection is not established. Please click the Open Connection button.', 'error');
    }
}

function displayMessage(data, style)
{
    var message = document.getElementById('hellomessage');
    message.setAttribute("class", style);
    message.value = data;
}


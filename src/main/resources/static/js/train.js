var visible = false;
var greetingSent = false;

var intent = {};
var dialogue;
var showIntent = false;

function getTime() {
    var today = new Date();
    return today.getHours() + ":" + today.getMinutes();
}

function sendIntent(userInput) {
    intent.text = userInput;
    console.log('Sending user input to server');
    dialogue.text = userInput;
    stompClient.send("/app/train/parseDialogue", {}, JSON.stringify(dialogue));
}

function connect() {
    var socket = new SockJS('/intent-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        var sessionId = /\/([^\/]+)\/websocket/.exec(socket._transport.url)[1];
        console.log("connected, session id: " + sessionId);

        dialogue = {
            'sessionId': sessionId
        };

        stompClient.subscribe('/topic/dialogue/' + sessionId, function (hint) {
            dialogue = JSON.parse(hint.body);
            addBotResponse(dialogue);

        });

    });
}

function showChatbot() {

    visible = !visible;
    if (visible) {
        $('#smartbot').show();

        async function displayGreeting() {
            addBotText('Hello, how may I help you?');
            greetingSent = true;
        }

        if (!greetingSent) {
            displayGreeting()
        }
    } else {
        $('#smartbot').hide();
    }
}

function addUserInput(text) {

    $('#smartbotBody').append('<div class="messageBox outgoing"><div class="messageText">' + text + '</div><div class="messageTime">' + getTime() + '</div></div>');

}

function addBotText(text) {

    $('#smartbotBody').append('<div class="messageBox incoming"><div class="messageText">' + text + '</div><div class="messageTime">' + getTime() + '</div></div>');

}

function addMessageButton(option) {
    $('#smartbotBody').append('<input type="button" class="message-button" onclick="sendIntent(this.value)" value=' + option.id + '>');
}

function scroll_window() {
    var element = document.getElementById("smartbotBody");
    element.scrollTop = element.scrollHeight - element.clientHeight;

}

function addBotResponse(dialogue) {

    if (dialogue.lastNluEvent != null) {

        let nluInfo = dialogue.lastNluEvent;
        if (nluInfo != null) {
            intent = nluInfo.bestIntent.intent + " " + nluInfo.bestIntent.confidence;

            slots = nluInfo.slots;
            if (slots != null) {
                slots.forEach(function (slot) {
                    text = slot.key + ":" + slot.value + "(" + slot.confidence + ")";
                })
            }

        }


    }

    if (dialogue.text != null) {
        addBotText(dialogue.text);
    }

    let options = dialogue.history[dialogue.history.length - 1].options;
    if (options != null) {
        $('#smartbotBody').append('<div class="messageBox incoming">');
        options.forEach(function (option) {
            addMessageButton(option);
        });
        $('#smartbotBody').append('</div>');
    }
    scroll_window();

}


function getData(dataUrl, callback) {

    $.ajax({
        dataType: "json",
        url: dataUrl,
        processData: false,
        success: callback
    });

}

$(document).ready(function () {

    connect();

    $('#user-input').keyup(function (e) {
        if (e.keyCode === 13) {
            var userInput = $('#user-input').val();
            $('#user-input').val('');
            addUserInput(userInput);
            scroll_window();
            sendIntent(userInput);
            console.log('user entered: ' + userInput);
        }
    });

});
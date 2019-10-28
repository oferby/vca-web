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
            event = dialogue.history[dialogue.history.length - 1]
            addBotResponse(event);

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

function addBotEvent(event) {

    eventBody = '<div class="messageBox incoming"><div class="messageText">' + event.text + '<br>'

        if (event.imageInfoList != null) {
            eventBody += '<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel"><ol class="carousel-indicators">'
            for (i=0; i<event.imageInfoList.length; i++) {
                eventBody += `<li data-target="#carouselExampleIndicators" data-slide-to="${i}" class="${i==0?'active':''}"></li>`
            }

            eventBody += '</ol><div class="carousel-inner">'
            event.imageInfoList.forEach(function(img,idx) {
                eventBody += `<div class="carousel-item ${idx==0?'active':''}">`
                eventBody += `<img class="d-block w-25" src="${img.imageUrl}" alt="${img.title!=null?img.title:''}"></div>`
            })

            eventBody += '</div></div><a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev"><span class="carousel-control-prev-icon" aria-hidden="true"></span><span class="sr-only">Previous</span></a><a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next"><span class="carousel-control-next-icon" aria-hidden="true"></span><span class="sr-only">Next</span></a></div>'
        }


    eventBody+= '<div class="messageTime">' + getTime() + '</div></div>'

    $('#smartbotBody').append(eventBody);

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

function addBotResponse(event) {

    if (event.text != null) {
        addBotEvent(event);
    }

    let options = event.options;
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
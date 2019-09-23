var app;
var dialogue_list;
var dialogue_lines;
var dialogue_dict = {};

var dataUrl = '/data/dialogues/';

var stompClient;
var activeDialogue;
var subscription;

function connect() {
    var socket = new SockJS('/monitor-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        stompClient.subscribe('/topic/dialogue/monitor/connect', function (msg) {
            sessionId  = msg.body;
            console.log('got connect message for session: ' + sessionId);
            dialogue = {'id': sessionId}
            dialogue_dict[sessionId] =  dialogue
            dialogue_list.unshift(dialogue);


        });
        stompClient.subscribe('/topic/dialogue/monitor/disconnect', function (msg) {
            sessionId  = msg.body;
            console.log('got disconnect message for session: ' + sessionId)
            delete dialogue_dict[sessionId];
            indx = dialogue_list.findIndex(dialogue => dialogue.id == sessionId);
            dialogue_list.splice(indx, 1);

        });

        stompClient.subscribe('/topic/dialogue/monitor/summary', function (msg) {
            dialogueSummary = JSON.parse(msg.body);
            dialogue_dict[dialogueSummary.id] = dialogueSummary;

            indx = dialogue_list.findIndex(dialogue => dialogue.id == dialogueSummary.id);
            dialogue_list.splice(indx, 1, dialogueSummary);

        });

    });
}

function subscribe_to_dialogue(sessionId) {
    this.subscription = this.stompClient.subscribe('/topic/dialogue/monitor/data/' + sessionId, function(msg) {

    });
}

Vue.component('dialogue-item', {
    props: ['dialogue'],
    template: '<div class="dialogue-item"><span class="status" v-bind:class="dialogue.confidenceString"></span><span onclick="show_dialogue(this)">{{dialogue.id}}</span></div>'
  });

function show_dialogue(caller) {
    console.log('clicked: ' + caller.textContent)
    $('#smartbotBody').empty();
    session = caller.textContent;
    getData(dataUrl + session, after_get_lines);

    if (this.subscription != null) {
        this.subscription.unsubscribe();
    }

    this.subscribe_to_dialogue(session);

}

function getData(url, callback) {

    $.ajax({
      dataType: "json",
      url: url,
      success: callback
    });

};

function add_dialogue_to_map(value) {
    dialogue_dict[value.id] = value;
}


function success(data){
    console.log('got data');

    data.forEach(add_dialogue_to_map);
    dialogue_list = Object.values(dialogue_dict).sort()

    app = new Vue({
      el: '#app',
      data: {
        dialogue_list: dialogue_list
      }
    })
};

function after_get_lines(data) {
    console.log('got lines data');

    if (data.history != null) {
        data.history.forEach(handleEvent);
    }

}

function handleEvent(event) {

    if (event.type == 'UserUtter') {
        addUserInput(event.text, event.localDateTime);

        if (event.nluEvent != null) {
            addIntentInput(event.nluEvent.bestIntent.intent + ": " + event.nluEvent.bestIntent.confidence)
        }

    } else if (event.type == "BotUtterEvent") {
        addBotText(event.text, event.localDateTime);
    }

}

function addUserInput(text, time) {

    $('#smartbotBody').append('<div class="messageBox outgoing"><div class="messageText">'+text+'</div><div class="messageTime">'+time+'</div></div>');

}

function addIntentInput(text) {

    $('#smartbotBody').append('<div class="messageBox outgoing intent"><div class="messageText">'+text+'</div></div>');

}

function addBotText(text, time) {

    $('#smartbotBody').append('<div class="messageBox incoming"><div class="messageText">'+text+'</div><div class="messageTime">'+time+'</div></div>');

}

$( document ).ready(function() {
    console.log( "ready!" );
    getData(dataUrl, success);
    connect();
});

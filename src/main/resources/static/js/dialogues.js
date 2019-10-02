var app;
var dialogue_list;

var dataUrl = '/data/dialogues/';
var actionDataUrl = '/data/actions/';

var stompClient;
var activeDialogue;
var subscription;

function scroll_window() {
    var element = document.getElementById("smartbotBody");
    element.scrollTop = element.scrollHeight - element.clientHeight;

}

function connect() {
    var socket = new SockJS('/monitor-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        stompClient.subscribe('/topic/dialogue/monitor/connect', function (msg) {
            sessionId = msg.body;
            console.log('got connect message for session: ' + sessionId);
            dialogue = {'id': sessionId};
            dialogue_list.unshift(dialogue);


        });
        stompClient.subscribe('/topic/dialogue/monitor/disconnect', function (msg) {
            sessionId = msg.body;
            console.log('got disconnect message for session: ' + sessionId);
            indx = dialogue_list.findIndex(dialogue => dialogue.id === sessionId);
            dialogue_list.splice(indx, 1);

        });

        stompClient.subscribe('/topic/dialogue/monitor/summary', function (msg) {
            dialogueSummary = JSON.parse(msg.body);

            indx = dialogue_list.findIndex(dialogue => dialogue.id === dialogueSummary.id);
            dialogue_list.splice(indx, 1, dialogueSummary);

        });

    });
}

Vue.component('dialogue-item', {
    props: ['dialogue'],
    template: '<div class="dialogue-item"><span class="status" v-bind:class="dialogue.confidenceString"></span><span onclick="show_dialogue(this)">{{dialogue.id}}</span></div>'
});

function subscribe_to_dialogue(sessionId) {
    subscription = this.stompClient.subscribe('/topic/dialogue/monitor/data/' + sessionId, function (msg) {
        dialogue = JSON.parse(msg.body);
        console.log('got message for session: ' + dialogue.sessionId);
        $('#smartbotBody').empty();
        after_get_lines(dialogue);
    });

}

function show_dialogue(caller) {
    console.log('clicked: ' + caller.textContent);
    $('#smartbotBody').empty();
    sessionId = caller.textContent;
    getData(dataUrl + sessionId, after_get_lines);

    if (subscription != null) {
        subscription.unsubscribe();
    }

    this.subscribe_to_dialogue(sessionId);

}

function after_get_lines(data) {
    console.log('got lines data');

    activeDialogue = data;

    if (data.history != null) {
        data.history.forEach(handleEvent);

        if (data.properties != null) {
            best_action = data.properties['best_action'];
            if (best_action != null) {
                addBestActionText(best_action);
            }
        }
    }

    if (activeDialogue.training) {
        $('#actionWindow').empty();
        show_actions();
    } else {
        $('#actionWindow').empty();
    }

    scroll_window();
}


function show_actions() {
    console.log('show action list for training.');
    getData(actionDataUrl, afterGetActions);
}

function afterGetActions(data) {
    console.log('got action list');
    $('#actionWindow').append("<div id='save-button-div'><input type='button' value='Save' onclick='saveDialogueToGraph()'></div>");
    // $('#actionWindow').append("<div id='actionWindow-left class='split left'></div><div id='actionWindow-left class='split left'></div>
    data.sort();
    $('#actionWindow').append("<table>");
    data.forEach(function (value) {
        $('#actionWindow').append("<tr><td class='action-item' onclick='addAction(this)'>" + value.id + "</td><td class='action-item-text'>"+ value.textSet[0] + "</td></tr>");
    });
    $('#actionWindow').append("</table>");

}

function saveDialogueToGraph() {
    console.log('saving dialogue to graph');
    stompClient.send("/app/train/saveDialogue", {}, JSON.stringify(activeDialogue));
}

function addAction(value) {
    console.log('Sending bot action to server');
    actionId = value.innerText;
    activeDialogue.text = actionId;
    stompClient.send("/app/train/addAction", {}, JSON.stringify(activeDialogue));
}

function handleEvent(event) {

    if (event.type === 'UserUtter') {
        addUserInput(event.text, event.localDateTime);

        if (event.nluEvent != null) {
            addIntentInput(event.nluEvent.bestIntent.intent + ": " + event.nluEvent.bestIntent.confidence);

            slots = event.nluEvent.slots;
            if (slots != null) {
                slots.forEach(function (slot) {
                    text = slot.key + ":" + slot.value + "(" + slot.confidence + ")";
                    addIntentInput(text)
                })
            }

        }

    } else if (event.type === "BotUtterEvent") {
        addBotText(event.text, event.localDateTime);
    }


}

function addUserInput(text, time) {

    $('#smartbotBody').append('<div class="messageBox outgoing"><div class="messageText">' + text + '</div><div class="messageTime">' + time + '</div></div>');

}

function addIntentInput(text) {

    $('#smartbotBody').append('<div class="messageBox outgoing intent"><div class="messageText">' + text + '</div></div>');

}

function addBotText(text, time) {

    $('#smartbotBody').append('<div class="messageBox incoming"><div class="messageText">' + text + '</div><div class="messageTime">' + time + '</div></div>');

}

function addBestActionText(text) {

    $('#smartbotBody').append('<div class="messageBox incoming best-action"><div class="messageText"><span>'+text+'</span></div></div>');

}

function success(data) {
    console.log('got data');

    dialogue_list = [...data.sort()];

    app = new Vue({
        el: '#app',
        data: {
            dialogue_list: dialogue_list
        }
    })
}

function getData(url, callback) {

    $.ajax({
        dataType: "json",
        url: url,
        success: callback
    });

}

$(document).ready(function () {
    console.log("ready!");
    getData(dataUrl, success);
    connect();
});

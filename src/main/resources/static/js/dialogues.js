var app;
var dialogue_list;
var dialogue_lines;

var dataUrl = '/data/dialogues/';

Vue.component('dialogue-item', {
    props: ['dialogue'],
    template: '<div class="dialogue-item"><span class="status" v-bind:class="dialogue.confidenceString"></span><span onclick="show_dialogue(this)">{{dialogue.id}}</span></div>'
  });

function show_dialogue(caller) {
    console.log('clicked: ' + caller.textContent)
    $('#smartbotBody').empty();
    session = caller.textContent;
    getData(dataUrl + session, after_get_lines);

}

function getData(url, callback) {

    $.ajax({
      dataType: "json",
      url: url,
      success: callback
    });

};

function success(data){
    console.log('got data')
    dialogue_list = data
    app = new Vue({
      el: '#app',
      data: {
        dialogue_list: dialogue_list
      }
    })
}

function after_get_lines(data) {
    console.log('got lines data');

    data.history.forEach(handleEvent);

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
});

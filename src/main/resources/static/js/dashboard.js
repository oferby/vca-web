var app;
var dialogue_list;
var action_list;
var action_removal_list = [];

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

Vue.component('action-tag', {
    props: ['action'],
    template: '<span class="badge badge-pill badge-danger"><i class="fas fa-grip-horizontal" onclick="addAction("{{ index }}" )></i> {{action}}</span>'
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

            if (data.properties['graph_location'] === '-1') {
                app.saveableState=true;
                notifyUser("Detached from graph")
                /*
                $('#add-action-button').prop('disabled', false);
                $('#graph-status').text("Detached from graph!");
                */
            } else {
                app.saveableState=false;
//                $('#add-action-button').prop('disabled', true);
//                $('#graph-status').text("");
            }
        }
    }

    show_actions();
//    if (activeDialogue.training) {
//        show_actions();
//    } else {
//        app.action_list.splice(0, app.action_list.length)
//    }

    scroll_window();
}

function show_actions() {
    console.log('show action list for training.');
    getData(actionDataUrl, afterGetActions);
}

function afterGetActions(data) {
    console.log('got action list');
    app.action_list = [...data.sort()];
    app.onSearchFilterChange();
    /*
    app.action_list.forEach(action => {
        action.textSet.forEach(ts => {
            app.flat_action_list.push(ts);
        })
    });
    */
}

function saveDialogueToGraph() {
    console.log('saving dialogue to graph');
    action_removal_list.sort().reverse().forEach(f => { activeDialogue.history.splice(f,1)});
    stompClient.send("/app/train/saveDialogue", {}, JSON.stringify(activeDialogue));
}

function addAction(value) {
    console.log('Sending bot action to server');
    actionId = app.flat_action_codes[value];
    activeDialogue.text = actionId;
    stompClient.send("/app/train/addAction", {}, JSON.stringify(activeDialogue));
}

function handleEvent(event,id) {

    if (event.type === 'UserUtter') {
        addUserInput(event,id);
//        if (event.nluEvent != null) {
//            addIntentInput(event.nluEvent.bestIntent.intent + ": " + event.nluEvent.bestIntent.confidence);
//
//            slots = event.nluEvent.slots;
//            if (slots != null) {
//                slots.forEach(function (slot) {
//                    text = slot.key + ":" + slot.value + "(" + slot.confidence + ")";
//                    addIntentInput(text)
//                })
//            }
//
//        }

    } else if (event.type === "BotUtterEvent" || event.type === "BotDefaultUtterEvent") {
        addBotText(event, id);
    }

}

function addUserInput(event,id) {
    eventBody = `<div class='messageBox outgoing' id='msgbox_${id}'>`

    eventBody += `<div class="btn-toolbar rounded float-right mt-2"><button class="btn btn-light btn-circular btn-sm mr-1" onclick="removeActionToggle('${id}',this)"><i class="fas fa-trash"></i></button></div>`;
    eventBody += `<div class="messageText">${event.text}</div>`;
    slots = null
    if (event.nluEvent != null) {
        eventBody += `<div class="badge badge-pill badge-danger mb-2">${event.nluEvent.bestIntent.intent} <div class="badge badge-pill badge-light mb-2">${event.nluEvent.bestIntent.confidence.toFixed(3)}%</div></div><br>`;
        slots = event.nluEvent.slots;
    }
    if (slots != null) {
        slots.forEach(slot => {
            eventBody += `<div class="badge badge-pill badge-secondary">${slot.key}<br>${slot.value} <div class="badge badge-pill badge-light mb-2">${slot.confidence.toFixed(3)}%</div></div><br>`;
        })
    }
    eventBody += `<div class="messageTime">${event.localDateTime}</div></div>`;
    $('#smartbotBody').append(eventBody);
}


/*
<div class="messageBox incoming" id='5'>
    <div class="btn-toolbar rounded float-right mt-2">
        <button class="btn btn-light btn-circular btn-sm mr-1"><i class="fas fa-pen"></i></button>
        <button class="btn btn-light btn-circular btn-sm mr-1"><i class="fas fa-check"></i></button>
    </div>
    <div class="messageText">some boring text</div>
    <div class="messageTime">12/12/12 43:43</div>
</div>
*/

//addBotText({ "event" : { "text" : "bla bla", "localDateTime" : "27364876234"} },5)

function removeActionToggle(id,caller) {
    if (action_removal_list.includes(id)) {
        action_removal_list.splice(action_removal_list.indexOf(id),1);
        caller.firstElementChild.classList = "fas fa-trash";
    } else {
        action_removal_list.push(id);
        caller.firstElementChild.classList = "fas fa-undo";
    }

    $(`#msgbox_${id}`).toggleClass("messagebox-removing");
}

function addBotText(event,id) {
    eventBody = `<div class="messageBox incoming" id='msgbox_${id}'>`
    eventBody += `<div class="btn-toolbar rounded float-right mt-2"><button class="btn btn-light btn-circular btn-sm mr-1" onclick="removeActionToggle('${id}',this)"><i class="fas fa-trash"></i></button></div>`;
    eventBody += `<div class="messageText">${event.text}<br>`;
    if (event.options != null) {
        event.options.forEach(o => {
            eventBody += `<div class="badge badge-pill badge-light mr-2">${o.text}</div>`;
        })
    }
    if (event.skillId != null) {
        eventBody += `<br><div class="badge badge-pill badge-secondary mr-2">${event.skillId}</div>`;
    }
    eventBody += `</div><div class="messageTime">${event.localDateTime}</div></div>`;
    $('#smartbotBody').append(eventBody);
}

function editAction(caller) {
    $(`#${caller}`).toggleClass("messagebox-editing");
    notifyUser("Select action to replace the current one");
}

function addBestActionText(text) {

    $('#smartbotBody').append('<div class="messageBox incoming best-action"><div class="messageText"><span>' + text + '</span></div></div>');

}

function success(data) {
    console.log('got data');

    dialogue_list = [...data.sort()];

    app = new Vue({
        el: '#app',
        data: {
            dialogue_list: dialogue_list,
            action_list: [],
            flat_action_list: [],
            flat_action_codes: [],
            searchFilter: "",
            userNotification: "",
            filterChanged: false,
            saveableState: false
        },
        methods: {
              onSearchFilterChange: function() {
                console.log("onFilterChange");
                this.filterChanged = true;
                this.flat_action_list=[];
                this.flat_action_codes=[];
                filterActions(this.searchFilter, this.action_list, this.flat_action_list, this.flat_action_codes);
              },
			  onSearchFilterClear: function() {
				this.intentFilter="";
                this.filterChanged = true
                toggleFilterClearButton()
                return true
			  }
        }
    })
}

function filterActions(filterInput, dictInput, tagsOutput, tagCodes) {
    if (filterInput.length>0) {

		var cleanFilterVec = filterInput.replace(/["']/g, "").replace(/[,.]/g, " ").toLowerCase().split(" ");
		dictInput.forEach(action => {
			action.textSet.forEach(ts => {
                // clear all special characters from textset word
                cts = ts.replace(/["'.,]/g,"").toLowerCase();
                allFound=true;
                for (i=0 ; i < cleanFilterVec.length ; i++) {
                    f = cleanFilterVec[i];
                    if (cts.indexOf(f)<0) {
                        allFound=false;
                        break;
                    }
                }
                if (allFound) {
                    tagsOutput.push(ts);
                    tagCodes.push(action.id);
                }
            })
        })
    }
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

function toggleFilterClearButton() {
  $('.has-clear input[type="text"]').on('input propertychange', function() {
    var $this = $(this);
    var visible = Boolean($this.val());
    $this.siblings('.form-control-clear').toggleClass('hidden', !visible);
  }).trigger('propertychange');

  $('.form-control-clear').click(function() {
    $(this).siblings('input[type="text"]').val('')
      .trigger('propertychange').focus();
  });
}

function notifyUser(msg) {
	app.userNotification = msg;
	$('.user-notif').fadeIn();
	setTimeout(function() { $('.user-notif').fadeOut('slow')}, 3000);
}

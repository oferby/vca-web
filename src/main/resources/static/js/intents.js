var intents_dict = {};
var example_dict = {}
var index = 0;
var app;
var dataUrl = '/data/intents/';

Vue.component('intent-item', {
    props: ['intents'],
    template: '<div class="intent-item"><input v-bind:id="intents.intent" type="text" v-model="intents.intent" onclick="show_examples(this)"><input type="button" value="Save"><input type="button" value="X" v-on:click="$emit(\'remove\')"></div>'
  });

Vue.component('example-item', {
    props: ['example', 'intent_label'],
    template: '<div class="intent-item"><input v-bind:id="example" type="text" v-model="example" ><input type="button" value="Save"><input type="button" value="X" v-on:click="$emit(\'remove\')"></div>'
  });

function remove(id, indx) {
    console.log('remove item: ' + id)
    app.intent_list.splice(indx, 1)
    delete intents_dict[id]
    nextIntent = app.intent_list[0]
    intentObject = intents_dict[nextIntent]
    show_example_for_intent(nextIntent)
    this.deleteData(dataUrl + id)
}

function show_examples(caller) {
    console.log('show example event');
    intent = intents_dict[caller.id];
    show_example_for_intent(intent)
}

function show_example_for_intent(intent) {
    app.example_list = intent.textSet;
    app.intent_label = intent.intent;
}


function addExample() {

    var example = $("#input_example").val()

    intentObject = intents_dict[app.intent_label]
    intentObject.textSet.unshift(example)
    updateData(dataUrl+'/intents', intentObject)

}

function removeExample(indx){
    intentObject = intents_dict[app.intent_label]
    intentObject.textSet.splice(indx, 1)
    updateData(dataUrl+'/intents', intentObject)
}

function successAfterUpdate(data) {
    console.log( "getting updated data from server." );
}

function successAfterAddNew(intent) {
    console.log( "getting data from server after add new: " + intent );
    intents_dict[data.intent] = intent
    app.intent_list.unshift(intent)
    show_example_for_intent(intent)
}

function addIntent() {
    var text = $("#search_text").val()
    if (text == '') return
    data = { "intent": text, textSet: []}
    this.addNew(dataUrl, data, successAfterAddNew)
}

function addNew(url, data, callback) {
    var jsonData = JSON.stringify(data)
    $.ajax({
        dataType: "json",
        processData: false,
        contentType: 'application/json',
        url: url,
        data: jsonData,
        method: "POST",
        success: callback
    });


}

function updateData(url, data, callback) {
    var jsonData = JSON.stringify(data)
    $.ajax({
     dataType: "json",
     processData: false,
     contentType: 'application/json',
     url: url,
     data: jsonData,
     method: "PUT",
     success: callback
    });

 }

 function deleteData(url, data, callback) {

     $.ajax({
         dataType: "json",
         processData: false,
         contentType: 'application/json',
         url: url,
         data: data,
         method: "DELETE",
         success: callback
     });

 }

function getData(callback) {

    $.ajax({
      dataType: "json",
      url: dataUrl,
      success: callback
    });

};


function add_intent_to_map(value){
   intents_dict[value.intent] = value;
}


function success(data){
    console.log( "got data: " + data);

    data.forEach(add_intent_to_map);
    sorted_intent_list = Object.values(intents_dict).sort()
    app = new Vue({
      el: '#app',
      data: {
        intent_list: sorted_intent_list,
        example_list: sorted_intent_list[0].textSet,
        intent_label: sorted_intent_list[0].intent
      }
    })

}

$( document ).ready(function() {
    console.log( "ready!" );
    getData(success);
});

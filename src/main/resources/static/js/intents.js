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
    props: ['example'],
    template: '<div class="intent-item"><input v-bind:id="example" type="text" v-model="example" ><input type="button" value="Save"><input type="button" value="X" v-on:click="$emit(\'remove\')"></div>'
  });

function remove(id, indx) {
    console.log('remove item: ' + id)
    this.intents_data.splice(indx, 1)
    this.deleteData(dataUrl + id)
}

function show_examples(caller) {
    console.log('show example event');
    intent = intents_dict[caller.id];
    app.example_list = intent.textSet;
}

function successAfterUpdate(data) {
    console.log( "getting updated data from server." );
}

function successAfterAddNew(data) {
    console.log( "getting data from server after add new: " + data );
//    intents_data = data
    app.intent_list.unshift(data)
}

function addIntent() {
    var text = $("#search_text").val()
    data = { "intent": text, textSet: []}
    var jsonData = JSON.stringify(data)
    this.addNew(dataUrl, jsonData, successAfterAddNew)
}

function addNew(url, data, callback) {

    $.ajax({
        dataType: "json",
        processData: false,
        contentType: 'application/json',
        url: url,
        data: data,
        method: "POST",
        success: callback
    });


}

function updateData(url, data, callback) {

     $.ajax({
         dataType: "json",
         processData: false,
         contentType: 'application/json',
         url: url,
         data: data,
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

    app = new Vue({
      el: '#app',
      data: {
        intent_list: Object.values(intents_dict).sort(),
        example_list: []
      }
    })

}

$( document ).ready(function() {
    console.log( "ready!" );
    getData(success);
});

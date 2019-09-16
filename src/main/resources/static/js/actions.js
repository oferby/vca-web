var actions_dict = {};
var index = 0;
var app;
var dataUrl = '/data/actions/';
var sorted_action_list = []

Vue.component('action-item', {
    props: ['action'],
    template: '<div class="action-item"><input v-bind:id="action.id" type="text" v-model="action.id" onclick="show_examples(this)"><input type="button" value="Save"><input type="button" value="X" v-on:click="$emit(\'remove\')"></div>'
  });

Vue.component('example-item', {
    props: ['example', 'action_label'],
    template: '<div class="action-item"><input v-bind:id="example" type="text" v-model="example" ><input type="button" value="Save"><input type="button" value="X" v-on:click="$emit(\'remove\')"></div>'
  });

function remove(id, indx) {
    console.log('remove item: ' + id)
    app.action_list.splice(indx, 1)
    delete actions_dict[id]
    nextAction = app.action_list[0]
    actionObject = actions_dict[nextAction]
    show_example_for_action(nextAction)
    this.deleteData(dataUrl + id)
}

function show_examples(caller) {
    console.log('show example event');
    action = actions_dict[caller.id];
    show_example_for_action(action)
}

function show_example_for_action(action) {
    app.example_list = action.textSet;
    app.action_label = action.id;
}


function addExample() {

    var example = $("#input_example").val()

    actionObject = actions_dict[app.action_label]
    actionObject.textSet.unshift(example)
    updateData(dataUrl+'/actions', actionObject)

}

function removeExample(indx){
    actionObject = actions_dict[app.action_label]
    actionObject.textSet.splice(indx, 1)
    updateData(dataUrl+'/actions', actionObject)
}

function successAfterUpdate(data) {
    console.log( "getting updated data from server." );
}

function successAfterAddNew(action) {
    console.log( "getting data from server after add new: " + action );
    actions_dict[data.action] = action
    app.action_list.unshift(action)
    show_example_for_action(action)
}

function addAction() {
    var text = $("#search_text").val()
    if (text == '') return
    $("#search_text").val("")
    data = { "id": text, textSet: []}
    this.addNew(dataUrl, data, successAfterAddNew)
}


function getData(url, callback) {

    $.ajax({
      dataType: "json",
      url: url,
      success: callback
    });

};

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

function add_action_to_map(value){
   actions_dict[value.id] = value;
}

function add_to_list(key) {
    action = actions_dict[key]
    sorted_action_list.push(action)
}

function success(data){
    console.log( "got data from server");

    data.forEach(add_action_to_map);
    keys = Object.keys(actions_dict).sort()
    keys.forEach(add_to_list)

    app = new Vue({
      el: '#app',
      data: {
        action_list: sorted_action_list,
        example_list: sorted_action_list[0].textSet,
        action_label: sorted_action_list[0].id
      }
    })

}

$( document ).ready(function() {
    console.log( "ready!" );
    getData(dataUrl, success);
});
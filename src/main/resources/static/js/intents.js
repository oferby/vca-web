var intents_data;
var index = 0;
var app;
var dataUrl = '/data/intents/'

Vue.component('intent-item', {
  props: ['intents'],
  template: '<div class="intent-item"><input type="text" v-model="intents.intent"><input type="button" value="Save"><input type="button" value="X" v-on:click="$emit(\'remove\')"></div>'
})

function remove(id, indx) {
    console.log('remove item: ' + id)
    this.intents_data.splice(indx, 1)
    this.deleteData(dataUrl + id)
}

function success(data){
    console.log( "got data: " + data);
    intents_data = data
    app = new Vue({
      el: '#app',
      data: {
        intent_list: intents_data
      }
    })

}

function successAfterUpdate(data) {
    console.log( "getting updated data from server." );
}

function successAfterAddNew(data) {
    console.log( "getting data from server after add new: " + data );
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

function getData() {

    $.ajax({
      dataType: "json",
      url: dataUrl,
      success: success
    });

};

$( document ).ready(function() {
    console.log( "ready!" );
    getData();
});

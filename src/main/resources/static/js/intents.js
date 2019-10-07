var intents_dict = {};
var textset_dict = {};
var index = 0;
var app;
var dataUrl = backendServer + '/data/intents/';

Vue.component('intent-item', {
    props: ['intents'],
    template: '<div class="row form-group"><div class="col-sm-1"><button class="btn" @click="$emit(\'remove\')"><i class="fa fa-remove"></i></button></div><div class="col-sm-3"><button class="btn btn-primary" v-bind:id="intents.intent" v-model="intents.intent" onclick="show_examples(this)">{{intents.intent}}</button></div></div>'

//    <input class="form-control" readonly v-bind:id="intents.intent" type="text" v-model="intents.intent" onclick="show_examples(this)"></div></div>'
  });

  //<input type="button" value="X" v-on:click="$emit(\'remove\')">

Vue.component('example-item', {
    props: ['example', 'intent_label'],
    template: '<div class="intent-item"><input v-bind:id="example" type="text" data-role="tagsinput" v-model="example" ><button class="btn"><i class="fa fa-save"></i> Save</button><button class="btn" @click="$emit(\'remove\')"><i class="fa fa-remove"></i> Remove</button></div>'
  });
//  template: '<button class="btn btn-primary btn-xs" v-bind:id="example" v-model="example" onclick="show_examples(this)">{{example}}</button>'
  //    template: '<div class="intent-item"><input v-bind:id="example" type="text" v-model="example" ><button class="btn"><i class="fa fa-save"></i> Save</button><button class="btn" @click="$emit(\'remove\')"><i class="fa fa-remove"></i> Remove</button></div>'

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
    $("#search_text").val("")
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
   // build the first index - from intents to textset
   intents_dict[value.intent] = value;

   // build the second index - from textset to intents
   value.textSet.forEach(function (ts) {
     if (ts in textset_dict) {
       textset_dict[ts].push(value.intent);
     } else {
       textset_dict[ts] = [ value.intent ];
     }
   })
}

function success(data){
    data.forEach(add_intent_to_map);
    sorted_intent_list = Object.values(intents_dict).sort()
    app = new Vue({
            el: '#app',
            data: {
              intentFilter: "",
              //intent_list: [""],
              //example_list: "",
              //intent_label: "",
              intent_list: sorted_intent_list,
              example_list: sorted_intent_list[0].textSet,
              intent_label: sorted_intent_list[0].intent,
            },
            computed: {
              filterIntentsByTextsetKeywords() {
                  // filter intents by intent name
                  // return this.intent_list.filter(el => {return el.intent.toLowerCase().includes(this.intentFilter.toLowerCase())})

                  // filter intents by textset keywords
                  // construct a set for all intents that have a word in their textset, which contains the search filter
                  const filteredTextSet = new Set()
                  for (ts in textset_dict) {
                    if (ts.toLowerCase().includes(this.intentFilter.toLowerCase())) {
                      textset_dict[ts].forEach(val => {
                        filteredTextSet.add(val);
                      });
                    }
                  }
                  // using the set, filter the intent list
                  return this.intent_list.filter(el => {return filteredTextSet.has(el.intent)})
                }
              }
            })
}

$( document ).ready(function() {
    console.log( "ready!" );
    getData(success);
});

var qa_data;
var qa_paragraph;
var qa_questions;
var index = 0;
var app;


function next() {

    if (qa_data.length > index + 1 ) {
        index+=1;
        qa_paragraph.text = qa_data[index].paragraph;
        qa_questions.list = qa_data[index].questions;
    }

}

function back() {
    if (index > 0) {
        index-=1;
        qa_paragraph.text = qa_data[index].paragraph;
        qa_questions.list = qa_data[index].questions;


    }

}

function newQA(){

    qa_data.push({paragraph: "", questions: []})
    index = qa_data.length - 1;
    qa_paragraph.text = qa_data[index].paragraph;
    qa_questions.list = qa_data[index].questions;
}


function update() {
    var jsonData = JSON.stringify(qa_data[index]);

    var id = qa_data[index].id;
    if (typeof id == "undefined") {
        var url = '/data/qa/';
        addNew(url, jsonData, successAfterAddNew)

    } else {
        var url = '/data/qa/' + id;
        updateData(url, jsonData, successAfterUpdate);

    }

}


Vue.component('qa-item', {
  props: ['qa_par'],
  template: '<div class="qa-item"><textarea class="qa_paragraph" v-model="qa_par.text"></textarea><button v-on:click="$emit(\'update\', qa_par.text)">V</button></div>'
})

Vue.component('qa-questions', {
    props: ['qa_q'],
    template: '<div><textarea class="qa_questions" v-model="qa_q" placeholder="Write your question here."></textarea><button v-on:click="$emit(\'remove\')">X</button><button v-on:click="$emit(\'update\', qa_q)">V</button></div>'

});


Vue.component('qa-new-question', {
    props: ['q'],
    template: '<div><textarea class="qa_questions" v-model="q" placeholder="Write your question here."></textarea><button v-on:click="$emit(\'add\', q)">Add</button></div>'
});

function success(data){
    console.log( "success!" );
    qa_data = data;
    qa_paragraph = {text: qa_data[index].paragraph}
    qa_questions = {list : qa_data[index].questions}

    app = new Vue({
      el: '#app',
      data: {
        qap: qa_paragraph,
        qaq: qa_questions
      }
    })

}

function successAfterUpdate(data) {

    console.log( "getting updated data from server." );

}

function successAfterAddNew(data) {

    console.log( "getting data from server after add new: " + data );

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

function getData() {

    $.ajax({
      dataType: "json",
      url: "/data/qa",
      success: success
    });

};

$( document ).ready(function() {
    console.log( "ready!" );
    getData();
});

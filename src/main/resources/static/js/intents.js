var intents_dict = {};
var textset_dict = {};
var index = 0;
var app;
var dataUrl = backendServer + '/data/intents/';

Vue.component('intent-item', {
    props: ['intents'],
    template: '<div class="btn-group" role="group"><button class="btn btn-primary" @click="$emit(\'remove\')"><i class="glyphicon glyphicon-remove"></i></button><button class="btn btn-primary" v-bind:id="intents" v-model="intents" onclick="show_examples(this)">{{intents}}</button></div>'
//    template: '<div class="row form-group"><div class="col-sm-1"><button class="btn" @click="$emit(\'remove\')"><i class="glyphicon glyphicon-remove"></i></button></div><div class="col-sm-3"><button class="btn btn-primary" v-bind:id="intents" v-model="intents" onclick="show_examples(this)">{{intents}}</button></div></div>'
  });

Vue.component('example-item', {
    props: ['example'],
    template: '<div class="intent-item"><input v-bind:id="example" type="text" data-role="tagsinput" v-model="example" ><button class="btn"><i class="glyphicon glyphicon-ok"></i> Save</button><button class="btn" @click="$emit(\'remove\')"><i class="fa fa-remove"></i> Remove</button></div>'
  });

function remove(id, indx) {
    alert("todo");
    console.log('remove item: ' + id)
    app.intent_list.splice(indx, 1)
    delete intents_dict[id]
    nextIntent = app.intent_list[0]
    intentObject = intents_dict[nextIntent]
    app.setExampleList(nextIntent)
    this.deleteData(dataUrl + id)
}

function addExample() {

    var example = $("#input_example").val()

    intentObject = intents_dict[app.selectedIntent]
    intentObject.textSet.unshift(example)
    updateData(dataUrl+'/intents', intentObject)

}
function removeExample(indx){
    intentObject = intents_dict[app.selectedIntent]
    intentObject.textSet.splice(indx, 1)
    updateData(dataUrl+'/intents', intentObject)
}
function successAfterUpdate(data) {
    console.log( "getting updated data from server." );
}
function successAfterAddNew(intent) {
    alert("todo")
    console.log( "getting data from server after add new: " + intent );
    intents_dict[data.intent] = intent
    app.intent_list.unshift(intent)
    app.setExampleList(intent)
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

 function show_examples(caller) {
     console.log('show example event');
     intent = intents_dict[caller.id];
     selectedIntent = intent.intent;
     app.setExampleList(intent);
 }

function init(data){
    data.forEach(add_intent_to_map);
//    sorted_intent_list =  Object.keys(intents_dict).map(function(e) {return intents_dict[e].intent}).sort();
    sorted_intent_list =  Object.keys(intents_dict).sort();
    app = new Vue({
            el: '#app',
            data: {
              intentFilter: "",
              intentsList: sorted_intent_list,
//              example_list: intents_dict[sorted_intent_list[0]].textSet,
              selectedIntent: "",
              filterChanged: true,
              resultsCount: "0",
              tag: "",
              tags: [],
              tagsValidation: [{
                classes: 'min-length',
                rule: tag => tag.text.length <= 1
              }]
            },
            methods: {
              setExampleList (intent) {
                console.log("setExampleList");
				this.tags = []
				filterIntents(this.intentFilter, intent.textSet, undefined , this.tags)
              },
              onTagsChange: function(newTags) {
                console.log("onTagsChange");
                intentObject = intents_dict[app.selectedIntent]
                intentObject.textSet = newTags.map(t => t.text)
                updateData(dataUrl+'/intents', intentObject, successAfterUpdate)
                return true
              },
              onFilterChange: function() {
                console.log("onFilterChange");
                this.filterChanged = true
                toggleFilterClearButton()
                return true
              },
			  onFilterClear: function() {
				this.intentFilter="";
                this.filterChanged = true
                toggleFilterClearButton()
                return true				  
			  }
            },
            computed: {
				filterIntentsByTextsetKeywords: function() {
						console.log("filterIntentsByTextsetKeywords( filterChanged = "+this.filterChanged+")");

						if (!this.filterChanged)
							return this.intentsList
						else
							this.intentsList = sorted_intent_list

						this.tags = []

						// filter intents by textset keywords
						// construct a set for all intents that have a word in their textset, which contains the search filter
						const filteredTextSet = new Set()
						
						filterIntents(this.intentFilter, Object.keys(textset_dict), textset_dict, this.tags, filteredTextSet);
						

						// using the set, filter the intent list
						this.filterChanged=false
						var new_list = this.intentsList.filter(function(el) {return filteredTextSet.has(el)})
						this.intentsList = new_list
						this.resultsCount = this.tags.length
						return this.intentsList
					}
				}
            })



$( document ).ready(function() {
    console.log( "ready!" );
    getData(init);

});

function filterIntents(filterInput, dictInput, textsetInput, tagsOutput, textsetOutput) {
	// impl: break the filter to words ([ ,.]), remove (["']) and do an "AND" search
	if (filterInput.length>0) {
		
		var cleanFilterVec = filterInput.replace(/["']/g, "").replace(/[,.]/g, " ").toLowerCase().split(" ");
		dictInput.forEach(ts => {
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
				
				if (textsetInput != undefined && textsetOutput != undefined) {
					textsetInput[ts].forEach(val => {
						textsetOutput.add(val);
					});
				}
			}								
		});
	}
}

function add_intent_to_map(value){
  console.log("add_intent_to_map");
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
function getData(callback) {
  console.log("getData");
    $.ajax({
      dataType: "json",
      url: dataUrl,
      success: callback
    });

};

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

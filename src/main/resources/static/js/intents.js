var intents_dict = {};
var textset_dict = {};
var index = 0;
var app;
var dataUrl = backendServer + '/data/intents/';

function trimEmptyTextNodes (el) {
  for (let node of el.childNodes) {
    if (node.nodeType === Node.TEXT_NODE && node.data.trim() === '') {
      node.remove()
    }
  }
}

Vue.directive('trim-whitespace', {
  inserted: trimEmptyTextNodes,
  componentUpdated: trimEmptyTextNodes
})

Vue.component('intent-item', {
    props: ['intents'],
    template: '<span><span class="label label-primary" v-bind:id="intents" v-model="intents" onclick="onSelectIntent(this)" >{{ intents }} <i class="glyphicon glyphicon-remove" @click="$emit(\'remove\')"></i></span></span>'
//    template: '<div class="btn-group label" role="group"><button class="btn btn-primary" @click="$emit(\'remove\')"><i class="glyphicon glyphicon-remove"></i></button><button class="btn btn-primary" v-bind:id="intents" v-model="intents" onclick="onSelectIntent(this)" >{{intents}}</button></div>'
  });

Vue.component('example-item', {
    props: ['example'],
    template: '<div class="intent-item"><input v-bind:id="example" type="text" data-role="tagsinput" v-model="example" ><button class="btn"><i class="glyphicon glyphicon-ok"></i> Save</button><button class="btn" @click="$emit(\'remove\')"><i class="fa fa-remove"></i> Remove</button></div>'
  });

function remove(id, indx) {
    alert("todo; remove");
	return
    console.log('remove item: ' + id)
    app.intent_list.splice(indx, 1)
    delete intents_dict[id]
    nextIntent = app.intent_list[0]
    intentObject = intents_dict[nextIntent]
    app.setExampleList(nextIntent)
    this.deleteData(dataUrl + id)
}

function blurIntent() {
	app.selectedIntent=""
	app.onFilterChange()
}

function addExample() {

    var example = $("#input_example").val()

    intentObject = intents_dict[app.selectedIntent]
    intentObject.textSet.unshift(example)
    updateData(dataUrl+'/intents', intentObject)

}
function removeExample(indx){
    alert("todo; removeExample");

    intentObject = intents_dict[app.selectedIntent]
    intentObject.textSet.splice(indx, 1)
    updateData(dataUrl+'/intents', intentObject)
}
function successAfterUpdate(data) {
    console.log( "getting updated data from server." );
}
function successAfterAddNew(intent) {
    alert("todo successAfterAddNew")
    console.log( "getting data from server after add new: " + intent );
    intents_dict[data.intent] = intent
    app.intent_list.unshift(intent)
    app.setExampleList(intent)
}
function addIntent() {
    alert("todo addIntent")
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

function onUpdateDataError(e) {
	console.log("!! ajax updated failed");
}

function updateData(url, data, onSuccess, onError) {
	if (onError == undefined)
		onError = onUpdateDataError
	
    var jsonData = JSON.stringify(data)
    $.ajax({
     dataType: "json",
     processData: false,
     contentType: 'application/json',
     url: url,
     data: jsonData,
     method: "PUT",
         success: onSuccess,
		 error: onError
    });

 }
function deleteData(url, data, onSuccess, onError) {

     $.ajax({
         dataType: "json",
         processData: false,
         contentType: 'application/json',
         url: url,
         data: data,
         method: "DELETE",
         success: onSuccess,
		 error: onError
     });

 }

 function onSelectIntent(caller) {
     console.log('intent selected');
     intent = intents_dict[caller.id];
     app.selectedIntent = intent.intent;
     app.setExampleList(intent);
 }
 
function onUpdateDataSuccessful(updatedIntent, fnNextCallback) {
	intents_dict[updatedIntent.intent] = updatedIntent;
	fnNextCallback();
}
	
function notifyUser(msg) {
	app.userNotification = msg;
	$('#user-notif').fadeIn();
	setTimeout(function() { $('#user-notif').fadeOut('slow')}, 3000);
}


function init(data){
    data.forEach(add_intent_to_map);
    sorted_intent_list =  Object.keys(intents_dict).sort();
    app = new Vue({
            el: '#app',
            data: {
              intentFilter: "",
              intentsList: sorted_intent_list,
              selectedIntent: "",
              filterChanged: true,
              resultsCount: "0",
			  userNotification: "",
              tag: "",
              tags: [],
              tagsValidation: [{
                classes: 'min-length',
                rule: tag => tag.text.length <= 2
              }]
            },
            methods: {
				setExampleList (intent) {
					console.log("setExampleList");
					this.tags = []
					filterIntents(this.intentFilter, intent.textSet, undefined , this.tags)
					this.resultsCount = this.tags.length
				},
				onTagsChange: function(newTags) {
					console.log("onTagsChange");
				},
				onTagAdd: function(tagAdd){
					if (this.selectedIntent == "") {
						notifyUser("no intent selected: cannot add tag");
						return;
					}
				  
					// validate new tag
					if (tagAdd.tag.text.length < 3) {
						notifyUser("Tag must be at least 3 charaters long");
						return;
					}
				  
					if (intents_dict[this.selectedIntent].textSet.includes(tagAdd.tag.text)) {
						notifyUser("Tag already exists");
						return;
					}
					
					modIntent = intents_dict[this.selectedIntent]
					modIntent.textSet.push(tagAdd.tag.text);
					updateData(dataUrl + modIntent.intent, modIntent, function() { onUpdateDataSuccessful(modIntent, tagAdd.addTag); });
				},
				onTagDel: function(tagDel){
					if (this.selectedIntent == "") {
						// remove all intents that are using this tag
						textset_dict[tagDel.tag.text].forEach(intent => {
							var modIntent = intents_dict[intent];
							modIntent.textSet.splice(intents_dict[intent].textSet.indexOf(tagDel.tag.text),1);
							updateData(dataUrl + modIntent.intent, modIntent, function() { onUpdateDataSuccessful(modIntent, tagDel.deleteTag); });
						});
					} else {
						// remove tag only from currently selected intent
						var modIntent = intents_dict[this.selectedIntent];
						modIntent.textSet.splice(intents_dict[intent].textSet.indexOf(tagDel.tag.text),1);
						updateData(dataUrl + modIntent.intent, modIntent, function() { onUpdateDataSuccessful(modIntent, tagDel.deleteTag); });
					}
				},
				onTagSave: function(tagSave){
					// validate new tag
					if (tagSave.tag.text.length < 3) {
						notifyUser("Tag must be at least 3 charaters long");
						return;
					}
					
					if (this.selectedIntent != "") {					
						if (intents_dict[this.selectedIntent].textSet.includes(tagSave.tag.text)) {
							notifyUser("Tag already exists");
							return;
						}						
						textset_dict[tagSave.tag.text].forEach(intent => {
							var modIntent = intents_dict[intent];
							modIntent.textSet.splice(intents_dict[intent].textSet.indexOf(tagSave.tag.text),1);
							modIntent.textSet.push(tagSave.tag.text);
							updateData(dataUrl + modIntent.intent, modIntent, function() { onUpdateDataSuccessful(modIntent, tagSave.saveTag); });
						});						
					} else {
						if (textset_dict[tagSave.tag.text] != undefined) {
							notifyUser("Tag already exists");
							return;
						}
						
						modIntent = intents_dict[this.selectedIntent]
						modIntent.textSet.splice(intents_dict[intent].textSet.indexOf(tagSave.tag.text),1);
						modIntent.textSet.push(tagSave.tag.text);
						updateData(dataUrl + modIntent.intent, modIntent, function() { onUpdateDataSuccessful(modIntent, tagSave.saveTag); });
					}
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
						
						if (this.intentFilter.length>0) {
							this.intentsList = this.intentsList.filter(function(el) {return filteredTextSet.has(el)})
						} else {
							this.intentsList = sorted_intent_list
						}
						
						this.resultsCount = this.tags.length
						return this.intentsList
					}
				}
            })
}



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
	else {
		dictInput.forEach(ts => {
			tagsOutput.push(ts);
		})
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

$(document).keyup(function(e) {
     if (e.key === "Escape") { 
        blurIntent();
    }
});

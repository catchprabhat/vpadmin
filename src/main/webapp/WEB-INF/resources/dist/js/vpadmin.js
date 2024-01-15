$( document ).ready(function () {
	
	$("#dashboardLink").click(function () {
			 LoadAjaxContent1(js_site_url('dashboard'));
	});
	
	toastr.options = {
			  "closeButton": false,
			  "debug": false,
			  "newestOnTop": false,
			  "progressBar": true,
			  "positionClass": "toast-top-right",
			  "preventDuplicates": false,
			  "onclick": null,
			  "showDuration": "1000",
			  "hideDuration": "1000",
			  "timeOut": "5000",
			  "extendedTimeOut": "1000",
			  "showEasing": "swing",
			  "hideEasing": "linear",
			  "showMethod": "fadeIn",
			  "hideMethod": "fadeOut"
			}
});

function LoadAjaxContent1(url){
	//$('.preloader').show();
	
	//console.log(url);
	
	$.ajax({
		mimeType: 'text/html; charset=utf-8', // ! Need set mimeType only when run from local file
		url: url,
		type: 'GET',
		success: function(data) {
			$('#contentWrapper').html(data);
			$('#contentWrapper').show();
			//$('.preloader').hide();
		},
		error: function (jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
		},
		dataType: "html",
		async: true
	});
}
//-----------------------------------------------------------------------------------------------------//
$(document).ready(function () {
	$('body').on('click', '.show-sidebar',  function (e) {
		e.preventDefault();
	});
	
	var ajax_url = location.hash.replace(/^#/, '');
	if (ajax_url.length > 1) {
		//LoadAjaxContent(ajax_url);
		
		var linksToHref = $('a[href="'+ajax_url+'"]');
		selectCurrentTreeNode(linksToHref);
	}
	
	//$('.sidebar-menu').on('click', 'a', function (e) {
	$('.treeview-menu').on('click', 'a', function (e) {
		e.preventDefault();
		selectCurrentTreeNode($(this));
		/*	
		//Remove style for other
		$('.treeview-menu').find( "li.active" ).removeClass( "active" );
		//Add style for current selected
		$(this).parent().addClass( "active" );
		
		if ($(this).hasClass('ajax-link')) {
			e.preventDefault();
			
			var url = $(this).attr('href');
			window.location.hash = url;
			LoadAjaxContent(url);
		}
		if ($(this).attr('href') == '#') {
			e.preventDefault();
		}*/
	});//.sidebar-menu ends
	
});

function selectCurrentTreeNode(linksToHref){
	//Remove style for other
	$('.treeview-menu').find( "li.active" ).removeClass( "active" );
	//Add style for current selected
	$(linksToHref).parent().addClass( "active" );
	
	if ($(linksToHref).hasClass('ajax-link')) {
		//e.preventDefault();
		
		var url = $(linksToHref).attr('href');
		window.location.hash = url;
		LoadAjaxContent(url);
	}
	if ($(linksToHref).attr('href') == '#') {
		//e.preventDefault();
	}
}

//
//  Function for load content from url and put in $('.ajax-content') block
//
function LoadAjaxContent(url){
	//$('.preloader').show();
	//console.log("url:" + url);
	$.ajax({
		mimeType: 'text/html; charset=utf-8', // ! Need set mimeType only when run from local file
		url: js_site_url(url),
		type: 'GET',
		success: function(data) {
			$('#contentWrapper').html(data);
			//$('.preloader').hide();
		},
		error: function (jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
		},
		dataType: "html",
		async: true
	});
}


//
//  Function for load content from url and put in $('.ajax-content') block
//
function loadAjaxContentWithCallbackFunction(url, urlParamData, callback, responseType){
	
	if(null == responseType){
		responseType = "html";
	}	
	//console.log(responseType);
	//$('.preloader').show();
	$.ajax({
		mimeType: 'text/html; charset=utf-8', // ! Need set mimeType only when run from local file
		url: js_site_url(url),
		data: urlParamData,
		type: 'GET',
		success: callback,
		error: function (jqXHR, textStatus, errorThrown) {
			console.log(errorThrown);
		},
		dataType: responseType, //null == responseType ? "html": "json",
		async: true
	});
}

function createDataTableInstance(tableId){
 var table_instance;
 if(null != tableId){
	 
	 var dontSort = [];
	    $('#' + tableId +' thead th').each( function () {
	        if ( $(this).hasClass( 'no_sort' )) {
	            dontSort.push( { "bSortable": false } );
	        } else {
	            dontSort.push( null );
	        }
	    } );
	    
	    table_instance = $('#' + tableId).dataTable({
        "bPaginate": true,
        "bFilter": true,
        "bSort": true,
        "bInfo": true,
        "bAutoWidth": false,
        "aoColumns": dontSort,

        "iDisplayLength": 10
        ,"bLengthChange": true
        ,"aLengthMenu": [
                        [5, 10, 20, 100, -1],
                        [5, 10, 20, 100, "All"]
                    ]
         , select: {
             style: 'single'
         }
    });
 }
 return table_instance;
}


function showMessage(msg, error){
	
	if(error){
		toastr["error"](msg, "Error");
	}
	else{
		toastr["success"](msg, "Success");
	}
	
}

function loadControllerMethod(controller_url){
	//var controller_url = "dashboard/map";
	LoadAjaxContent(controller_url)
}
function printObjProperties(jsObj){
	if(null != jsObj && jsObj instanceof Object){
	 $.each(jsObj, function(key, element) {
		 printObjProperties(key);
   	   console.log('key: ' + key + '\n' + 'value: ' + element);
   	});
	}
}

function createDatepickerInstance(input_field_Id, default_today, startDate){
	
	var nowTemp = new Date();
	var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
	
	var date_obj = $('#' + input_field_Id).datepicker({
	    todayBtn: "linked"
	    //,todayHighlight: true,
	    ,autoclose: true
	    ,startDate: startDate
	    
	});
	if(null != default_today && default_today ===true){
		$('#' + input_field_Id).datepicker('update', new Date());
	}
	
	return date_obj;
}

function mask(div_id){
	if(div_id){
		//var overlay = $('<div class="overlay"></div><div class="loading-img"></div>');
		//$("#" + div_id).append(overlay);
		
		$("#" + div_id).append('<div class="overlay"><i class="fa fa-refresh fa-spin"></i></div>');
		//$("#" + div_id).css('cursor','not-allowed');
	}
}

function unmask(div_id){
	if(div_id){
		$( "#" + div_id ).find('.overlay').remove();
		//$("#" + div_id).css('cursor','auto');
	}
}

function has_unique_rows(str, tableID){
	var unq_rows = "true";
	
    $('#' + tableID + ' tr').each(function(){
        $(this).children('td').each(function(){
            if ( $(this).html() == str ){
                //console.log('found');
            	showMessage("Duplicate record found.", true);
            	unq_rows = "false";
            	return;
            }
        });
    }); 
    return unq_rows;
}

function check_for_numbers(ob) {
	  var invalidChars = /[^0-9]/gi;
	  if(invalidChars.test(ob.value)) {
	      ob.value = ob.value.replace(invalidChars,"");
	   }
}
function printDiv(div_id){
	var content = document.getElementById(div_id);
	var pri = document.getElementById("ifmcontentstoprint").contentWindow;
	pri.document.open();
	pri.document.write(content.innerHTML);
	pri.document.close();
	pri.focus();
	pri.print();
}
/*function format_currency_inr(input_inr){
	var formatted_inr_string = "";
	
	if(input_inr){
		input_inr=input_inr.toString();
		var afterPoint = '';
		if(input_inr.indexOf('.') > 0)
		   afterPoint = input_inr.substring(input_inr.indexOf('.'),input_inr.length);
		input_inr = Math.floor(input_inr);
		input_inr=input_inr.toString();
		var lastThree = input_inr.substring(input_inr.length-3);
		var otherNumbers = input_inr.substring(0,input_inr.length-3);
		if(otherNumbers != '')
		    lastThree = ',' + lastThree;
		formatted_inr_string = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree + afterPoint;
	}
	return formatted_inr_string;
}*/
/*
function download_bom_excel(bom_excel_report_link_id){
	console.log(' bom_excel_report_link_id = '+ bom_excel_report_link_id);
	if(bom_excel_report_link_id){
		$('#'+bom_excel_report_link_id)[0].click(); // Works too!!!
	}
	
}*/
function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function updateDatePickerDateToTodaysDate(datePickerId){
	var nowTemp = new Date();
	var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
	$('#'+datePickerId).datepicker('update', now);	
}
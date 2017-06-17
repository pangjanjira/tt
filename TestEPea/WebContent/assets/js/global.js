//UR58060060 Phase 3.2 CADM Function
var canModify;
var msgSessionExpire = "Session Expired, Please <a href='login.jsp'>Login</a> Again.";
var msgNoPermission = "You do not have permission to view this page. <br/> Please click <a href='index.jsp'>back</a> to main page.";
var userName;
var ocCode;
var authFlag;

var INDEX_VIEW = "F00000";
var CONTROL_ACCOUNT_VIEW = "F00101";
var CONTROL_ACCOUNT_MODIFY = "F00102";
var ACCOUNT_ALS_VIEW = "F00401";
var ACCOUNT_ALS_MODIFY = "F00402";
var APPROVAL_VIEW = "F00201";
var APPROVAL_MODIFY = "F00202";
var REVIEW_VIEW = "F00501";
var REVIEW_MODIFY = "F00502";
var REQUEST_VIEW = "F00301";
var REQUEST_NEW_ISSUE = "F00302";

//----------------------------------------------------------
//authorization functions
//----------------------------------------------------------

function checkViewAuthorize(funcCode){
    $.ajax({
        url: "SessionControl",
        data: {"object": "UserProfile", "funcCode": funcCode},
        statusCode: {404: function() {alert("page not found");}},
        method: 'POST',
        async: false
	}).done(function(data) {
    	console.log("data : " + data);
        var userProfile = $.parseJSON(data);
        userName = userProfile[0];
        ocCode = userProfile[1];
        authFlag = userProfile[2];
        console.log("userName: " + userName);
        console.log("ocCode: " + ocCode);
        console.log("authFlag: " + authFlag);
        if (userName == "") {
        	//Session Expired
            alertModal("MESSAGE", msgSessionExpire,"");
            return;
        } else if (authFlag == "N") {
        	//No authorization
            alertModal("MESSAGE", msgNoPermission,"");
            return;
        }
	});
}

function checkModifyAuthorize(funcCode){
	var result = "N";
    $.ajax({
        url: "SessionControl",
        data: {"object": "UserProfile", "funcCode": funcCode},
        statusCode: {404: function() {alert("page not found");}},
        method: 'POST',
        async: false
	}).done(function(data) {
    	var userProfile = $.parseJSON(data);
    	result = userProfile[2];
    	canModify = result;
	});
}

function logout(){
	$.ajax({
		url: "SessionControl",
		data : {"object": "Logout"},
		method: 'POST'
	}).done(function() {
		window.location = "/EPea/login.jsp";
	});
}

//----------------------------------------------------------
//display functions
//----------------------------------------------------------

function displayMenu(funcCode){
	$("li.panel").removeClass("active");
	$("li.panel .accordion-toggle").data("toggle","collapse");
	if(funcCode == INDEX_VIEW){
		//index
		$("#index").addClass("active");
		$("#index .accordion-toggle").addClass("expand");
		$("#index ul").removeClass("collapse").addClass("expand");
	}else if(funcCode == APPROVAL_VIEW){
		//approval
		$("li.panel").removeClass("active");
		$("li.panel .accordion-toggle").data("toggle","collapse");
		$("#requestMonitor").addClass("active");
		$("#requestMonitor .accordion-toggle").addClass("expand");
		$("#requestMonitor ul").removeClass("collapse").addClass("expand");
	}else if(funcCode == REVIEW_VIEW){
		//review
                $("li.panel").removeClass("active");
                $("li.panel .accordion-toggle").data("toggle","collapse");
                $("#requestReview").addClass("active");
                $("#requestReview .accordion-toggle").addClass("expand");
                $("#requestReview ul").removeClass("collapse").addClass("expand");
		//nothing
	}else if(funcCode == REQUEST_VIEW){
		//request
		$("li.panel").removeClass("active");
		$("li.panel .accordion-toggle").data("toggle","collapse");
		$("#requestInquiry").addClass("active");
		$("#requestInquiry .accordion-toggle").addClass("expand");
		$("#requestInquiry ul").removeClass("collapse").addClass("expand");
	}else if(funcCode == ACCOUNT_ALS_VIEW){
		//account als
		$("li.panel").removeClass("active");
		$("li.panel .accordion-toggle").data("toggle","collapse");
		$("#accountAls").addClass("active");
		$("#accountAls .accordion-toggle").addClass("expand");
		$("#accountAls ul").removeClass("collapse").addClass("expand");
	}else if(funcCode == CONTROL_ACCOUNT_VIEW){
		//control account
		$("li.panel").removeClass("active");
		$("li.panel .accordion-toggle").data("toggle","collapse");
		$("#controlAccount").addClass("active");
		$("#controlAccount .accordion-toggle").addClass("expand");
		$("#controlAccount ul").removeClass("collapse").addClass("expand");
        }else if(funcCode == REQUEST_NEW_ISSUE){
		//control account
		$("li.panel").removeClass("active");
		$("li.panel .accordion-toggle").data("toggle","collapse");
		$("#requestNewIssue").addClass("active");
		$("#requestNewIssue .accordion-toggle").addClass("expand");
		$("#requestNewIssue ul").removeClass("collapse").addClass("expand");
	}
}

function alertModal(txtHeader, txtContent, txtFooter){
    if (txtHeader!="") {
        $("#alertHeader").html(txtHeader);
    }
    if (txtContent!="") {
        $("#alertContent").html(txtContent);
    }
    if (txtFooter!="") {
        $("#alertFooter").html(txtFooter);
    }
    $("#alertModal").modal({
        backdrop: 'static'
    });
}

//----------------------------------------------------------
//formatting functions
//----------------------------------------------------------

function getDateFromString(input){
	  var parts = input.split('/');
	  // new Date(year, month [, day [, hours[, minutes[, seconds[, ms]]]]])
	  return new Date(parts[0], parts[1]-1, parts[2]); // Note: months are 0-based
}

function datetimeFormat(dtStr){
	console.log(dtStr);
	var dt = new Date(dtStr);
	console.log(dt);
	
	var day = dt.getDate();
	var month = dt.getMonth() + 1;
	var year = dt.getFullYear();
	var hour = dt.getHours();
	var minute = dt.getMinutes();
	var second = dt.getSeconds();
	
	return year + "-"
		+ ("0"+month).slice(-2) + "-"
		+ ("0"+day).slice(-2) + " "
		+ ("0"+hour).slice(-2) + ":"
		+ ("0"+minute).slice(-2) + ":"
		+ ("0"+second).slice(-2);
}

function currencyFormat(number){
    var decimalplaces = 2;
    var decimalcharacter = ".";
    var thousandseparater = ",";
    number = parseFloat(number);
    var sign = number < 0 ? "-" : "";
    var formatted = new String(number.toFixed(decimalplaces));
    if( decimalcharacter.length && decimalcharacter != "." ) {
        formatted = formatted.replace(/\./,decimalcharacter);
    }
    var integer = "";
    var fraction = "";
    var strnumber = new String(formatted);
    var dotpos = decimalcharacter.length ? strnumber.indexOf(decimalcharacter) : -1;
    if( dotpos > -1 )
    {
        if( dotpos ) {
            integer = strnumber.substr(0,dotpos);
        }
        fraction = strnumber.substr(dotpos+1);
    }
    else {
        integer = strnumber;
    }
    if( integer ) {
        integer = String(Math.abs(integer));
    }
    while( fraction.length < decimalplaces ) {
        fraction += "0";
    }
    temparray = new Array();
    while( integer.length > 3 )
    {
        temparray.unshift(integer.substr(-3));
        integer = integer.substr(0,integer.length-3);
    }
    temparray.unshift(integer);
    integer = temparray.join(thousandseparater);
    return sign + integer + decimalcharacter + fraction;
}
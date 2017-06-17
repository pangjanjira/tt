//UR58060060 Phase 3.2 CADM Function
var viewFuncCode = ACCOUNT_ALS_VIEW;
var modifyFuncCode = ACCOUNT_ALS_MODIFY;

$(document).ready(function () {
	//check user authorization
    checkViewAuthorize(viewFuncCode);
    checkModifyAuthorize(modifyFuncCode);
    //page initialize
    displayMenu(viewFuncCode);
    initialPage();
});

function initialPage(){
    $("#iUserName").val(userName);
    $("#iCreateBy").val(userName);
    $("#iUpdateBy").val(userName);
    if (canModify === "Y") {
    	$("#maintainFormTab").show();
    }else{
    	$("#maintainFormTab").hide();
    }
    setMessageCode();
    $('#dataTables-result').dataTable();
}

function setMessageCode(){
    //Get MessageControl into Option
    $.ajax({
        url: "ParameterControl",
        data: {
            "action": "ShowMessageCode"
        },
        statusCode: {
            404: function() {
                alert("page not found!!");
            }
        },
        method: 'POST'
    }).done(function(data) {
        var jsonData = $.parseJSON(data);
        var msg = $('#addMessageCode');
        msg.append('<option value="">----- message code -----</option>');
        $.each(jsonData, function(index, value) {
            msg.append('<option value="' + value.msgCode + '" >' + value.msgCode + " - " + value.msgDescription + '</option>');
        });
        $('#addMessageCode').change(function() {
            var msgDescription = $("#addMessageCode option:selected").text();
            if(msgDescription === ""){
                $('#iMsgDescription').val("");
            }else{
                try{
                    $('#iMsgDescription').val(msgDescription.split(" - ")[1]);
                }catch(err){
                    $('#iMsgDescription').val(msgDescription);
                }
            }
        });
    });
}

$("#inquiryForm").submit(function(e) {
    e.preventDefault();
    var jsonObj = new Object();
    jsonObj.taxID = $("#iTaxID").val();
    jsonObj.accountNo = $("#iAccountNo").val();
    jsonObj.ocCode = $("#iOCCode").val();
    getMonitorList(JSON.stringify(jsonObj));
});

$("#refreshMonitorListBtn").click(function(e){
    e.preventDefault();
    var jsonObj = new Object();
    jsonObj.taxID = $("#iTaxID").val();
    jsonObj.accountNo = $("#iAccountNo").val();
    jsonObj.ocCode = $("#iOCCode").val();
    getMonitorList(JSON.stringify(jsonObj));
});

$("#createForm").submit(function(e) {
    var acctNo = $("#sAccountNo").val();
    var acctNoComfirm = $("#sAccountNoConfirm").val();
    var taxId = $("#sTaxID").val();
    var taxIdComfirm = $("#sTaxIDConfirm").val();
    e.preventDefault();
    if($('#mode').val()==="create"){
        var jsonObj = new Object();
        jsonObj.addAccountNo = acctNo;
        jsonObj.addAccountName = $("#").val();
        jsonObj.addTaxID = taxId;
        jsonObj.addCreateBy = $("#iCreateBy").val();
        jsonObj.addBranch = $("#sBranchinfo").val();
        jsonObj.addOcCode = $("#sOCCodeinfo").val();
        jsonObj.addBank = $("#").val();
        jsonObj.addLineAmt = $("#").val();
        jsonObj.addAvaliableAmt = $("#").val();
        
        if(acctNo !== acctNoComfirm ){
             alertModal("MESSAGE Error","Account and Confirm Account must be the same.","<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
        } else if(taxId !== taxIdComfirm){
            alertModal("MESSAGE Error","Tax ID and Confirm Tax ID must be the same.","<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
        }else{
            getCreateAcctALS(JSON.stringify(jsonObj));
            $("#submitBtn").attr('disabled', true);
        }
    } else if($('#mode').val()==="update") {
    	if(taxId !== taxIdComfirm){
            alertModal("MESSAGE Error","Tax ID and Confirm Tax ID must be the same.","<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
        }else{
	        var jsonObj = new Object();
	        jsonObj.id = $("#updateId").val(); //hiddend
	        jsonObj.addAccountNo = $("#sAccountNo").val();
	        jsonObj.addAccountName = $("#sAccountName").val();
	        jsonObj.addBranch = $("#sBranchinfo").val();
	        jsonObj.addOcCode = $("#sOCCodeinfo").val();
	        jsonObj.addTaxID = $("#sTaxID").val();
	        jsonObj.addCreateBy = $("#iCreateBy").val();
	        jsonObj.addBank = $("#sBank").val();
	        jsonObj.addLineAmt = $("#sLineAmtinfo").val();
	        jsonObj.addAvaliableAmt = $("#sAvaliableAmtinfo").val();
	        getUpdateAcctALS(JSON.stringify(jsonObj));
	        $("#submitBtn").attr('disabled', true);
	        //resetForm();
        }
    }
});

function getMonitorList(jsonStr) {
	toggleTab("datalist");
    $("#inquiryLoading").show();
    $.ajax({
        url: "AccountALSInquiryRequestControl",
        data: {
            "action": "InquiryRequest",
            "jsonRequest": jsonStr
        },
        statusCode: {
            404: function() {
                alert("page not found!!!");
            }
        },
        method: 'POST'
    }).done(function(data) {
    	var jsonData = $.parseJSON(data);
        var dt = $('#dataTables-result').DataTable();
        
        dt.clear();
        $.each(jsonData, function(index, value) {
            var tr = $("<tr>").attr("style", "white-space:nowrap;");
            tr.append($("<td>").attr("style","text-align:center;font-size:small;") //data-target='#detailModal'
                .append("<button type='button' id='detailBtn' class='btn btn-info btn-circle' data-toggle='modal'  onclick='javascript:showDetail(" + value.id + ");'><i class='icon-search'></i></button>"));
            
            tr.append($("<td>").attr("style", "text-align:center;").append(value.taxId));
            tr.append($("<td>").attr("style", "text-align:center;").append(value.accountNo));
            tr.append($("<td>").attr("style", "text-align:left;").append(value.accountName));
            tr.append($("<td>").attr("style", "text-align:right;").append(currencyFormat(value.lineAmt)));
            tr.append($("<td>").attr("style", "text-align:right;").append(currencyFormat(value.avaliableAmt)));
            tr.append($("<td>").attr("style", "text-align:center;").append(value.ocCode));
            tr.append($("<td>").attr("style", "text-align:center;").append(value.branch));
            
            if (canModify === "N"){
                tr.append($("<td>").attr("style", "text-align:center;").append(""));
            }else{
            	var acctNo = '"' + value.accountNo + '"';
                var createBy = value.createBy;
                if(createBy === "SYSTEM"){
                    tr.append($("<td>").attr("style", "text-align:center;")
                        .append(""));
                }else{
                    tr.append($("<td>").attr("style", "text-align:center;")
                        .append("<button type='button' class='btn btn-info btn-circle' data-toggle='modal'  onclick='javascript:updateItem(" + value.id + ");'><i class='icon-edit'></i></button>&nbsp;&nbsp;")
                        .append("<button type='button' class='btn btn-info btn-circle' data-toggle='modal'  onclick='javascript:deleteItem(" + value.id + "," + acctNo + ");'><i class='icon-trash'></i></button>"));
                }
            }
            dt.row.add(tr);
        });
        dt.draw();
        $("#inquiryLoading").hide();
    });
    
}

function showDetail(id){
    var jsonObj = new Object();
    jsonObj.id = id;
    getDataforDetail(JSON.stringify(jsonObj));
    $("#inquiryLoading").show();
    clearForm();
}

function getDataforDetail(jsonStr){
    $.ajax({
        url: "AccountALSInquiryRequestControl",
        data : {
            "action": "getAccALSDetail",
            "jsonDataDetail": jsonStr
        },        
        statusCode: {
            404: function() {
                alert( "page not found" );
            }
        },
        method: 'POST'
    }).done(function(data) {
        
        var jsonData = $.parseJSON(data)[0];
        
        $("#taxID").text(jsonData.taxId);
        $("#ocCode").text(jsonData.ocCode);
        $("#accountNo").text(jsonData.accountNo);
        $("#branch").text(jsonData.branch);
        $("#createBy").text(jsonData.createBy);
        $("#createDateTime").text(jsonData.createDtm);
        $("#updateBy").text(jsonData.updateBy);
        $("#updateDateTime").text(jsonData.updateDtm);
        $("#purpose").text(jsonData.purpose);
        $("#subPurpose").text(jsonData.subPurpose);
        
        $("#accountName").text(jsonData.accountName);
        $("#lineAmount").text(currencyFormat(jsonData.lineAmt));
        $("#creditAmount").text(currencyFormat(jsonData.avaliableAmt));

        $("#detailModal").modal();
         
        $("#inquiryLoading").hide();
    });
}

function toggleTab(tabType) {
    if (tabType === "form") {
        $("#dataListTabBtn").removeClass('active');
        $("#dataListTab").attr('class', 'tab-pane fade');
        $("#maintainFormTabBtn").attr('class', 'active');
        $("#maintainFormTab").attr('class', 'tab-pane fade in active');
    } else {
        $("#maintainFormTabBtn").removeClass('active');
        $("#maintainFormTab").attr('class', 'tab-pane fade');
        $("#dataListTabBtn").attr('class', 'active');
        $("#dataListTab").attr('class', 'tab-pane fade in active');
    }
}

function setForm(data) {
    $("#maintainFormTitle").html("<h4>Update Account ALS</h4>");
    $("#mode").val("update");
    $("#updateId").val(data[0].id);
    $("#sAccountNo").val(data[0].accountNo);
    $("#sAccountNo").attr('readonly', true);
    $("#sAccountNoConfirm").val(data[0].accountNo);
    $("#sAccountNoConfirm").attr('readonly', true);
    
    $("#sTaxID").val(data[0].taxId);
    
    $("#sTaxIDConfirm").val(data[0].taxId);
   
    
    $("#sOCCodeinfo").html(data[0].ocCode);
    $("#sBranchinfo").html(data[0].branch);
    $("#sLineAmtinfo").val(data[0].lineAmt);
    $("#sAvaliableAmtinfo").val(data[0].avaliableAmt);
    $("#createByinfo").html(data[0].createBy);
    $("#createDatetime").html(data[0].createDtm);
    $("#updateByinfo").html(data[0].updateBy);
    $("#updateDatetime").html(data[0].updateDtm);
    $("#createUpdateInfo").show();
}

function updateItem(id){
    //alert("Updateitem!!!" + id);
    toggleTab("form");
    var jsonObj = new Object();
    jsonObj.id = id;
    getDataforUpdate(JSON.stringify(jsonObj));
}

function getDataforUpdate(jsonStr) {
    $("#inquiryLoading").show();
    $.ajax({
        url: "AccountALSInquiryRequestControl",
        data: {
            "action": "InquiryforUpdate",
            "jsonData": jsonStr
        },
        statusCode: {
            404: function() {
                alert("page not found");
            }
        },
        method: 'POST'
    }).done(function(data) {
        var jsonData = $.parseJSON(data);
        setForm(jsonData);
        //alert("Json Data for update : " + jsonData[0].accountNo);
        $("#inquiryLoading").hide();
    });
}

function getUpdateAcctALS(jsonStr) {
    $("#inquiryLoading").show();
    $.ajax({
        url: "AccountALSInquiryRequestControl",
        data: {
            "action": "UpdateRequest",
            "jsonUpdate": jsonStr
        },
        statusCode: {
            404: function() {
                alert("page not found");
            }
        },
        method: 'POST'
    }).done(function(data) {
        //var jsonData = $.parseJSON(data);
        //setForm(jsonData);
        $("#inquiryLoading").hide();
        $("#inquiryLoadingCreate").show();
        if(data === '"success"'){
        	$("#inquiryLoadingCreate").hide();
            alertModal("MESSAGE","Update Account ALS successfully.","<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
            $("#inquiryBtn").submit();
            $("#submitBtn").attr('disabled', false);
            resetForm();
            toggleTab("datalist");
        }else if(data === '"duplicate"'){ 
        	$("#inquiryLoadingCreate").hide();
            alertModal("ERROR", "The input Account and Tax ID is existed, please input new value.","<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
            $("#submitBtn").attr('disabled', false);
        }else if(data === '"notexisted"'){
        	$("#inquiryLoadingCreate").hide();
        	alertModal("ERROR", "The input Tax ID is not existed in ALS, please input new value.","<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
            $("#submitBtn").attr('disabled', false);
        }else{
        	$("#inquiryLoadingCreate").hide();
            alertModal("ERROR", data,"<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
            $("#submitBtn").attr('disabled', false);
        }
    });
}

function deleteItem(id,acctNo){
    
    //alert("DeleteItem!!!" + id  + "acct NO :" + acctNo + "userLogOn : " +  $("#iUserName").val());
    if (confirm("Confirm to delete this account ALS  '" + acctNo + "'  ?")) {
        var jsonObj = new Object();
        jsonObj.id = id;
        jsonObj.accountNo = acctNo;
        jsonObj.userLogOn = $("#iUserName").val();
        getDeleteItem(JSON.stringify(jsonObj));
    }
}

function getDeleteItem(jsonStr){
    $.ajax({
        url: "AccountALSInquiryRequestControl",
        data : {
            "action": "deleteAccountALS",
            "jsonDelete": jsonStr
        },        
        statusCode: {
            404: function() {
                alert( "page not found" );
            }
        },
        method: 'POST',
        async: false
        
    }).done(function(data) {
        $("#inquiryLoading").hide();
       if(data === '"success"'){
           alertModal("MESSAGE","Delete account ALS successfully","<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
           $("#inquiryBtn").submit();
           
       }else{
           alertModal("ERROR", data,"<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
       }   
    });
}

function getUpdateControl(jsonStr) {
    $("#inquiryLoading").show();
    $.ajax({
        url: "AccountALSInquiryRequestControl",
        data: {
            "action": "UpdateRequest",
            "jsonUpdate": jsonStr
        },
        statusCode: {
            404: function() {
                alert("page not found");
            }
        },
        method: 'POST'
    }).done(function(data) {
        //var jsonData = $.parseJSON(data);
        //setForm(jsonData);
        $("#inquiryLoading").hide();
        if(data === '"success"'){
            alertModal("MESSAGE","Update control account successfully","<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
            $("#inquiryBtn").submit();
            resetForm();
            toggleTab("datalist");
        }else if(data === '"duplicate"'){ 
            alertModal("ERROR", "The input account and message code is existed, please input new value","<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
        }else{
            alertModal("ERROR", data,"<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
        }
    });
}

function getCreateAcctALS(jsonStr) {
	$("#inquiryLoadingCreate").show();
//    console.log(jsonStr);
    $.ajax({
        url: "AccountALSInquiryRequestControl",
        data: {
            "action": "CreateRequest",
            "jsonCreate": jsonStr
        },
        statusCode: {
            404: function() {
                alert("page not found");
            }
        },
        method: 'POST'
    }).done(function(data) {
        //alert(data);
        var taxId = data.split(",");
        var dup = taxId[0].substring(1,taxId[0].length);
        //alert(dup);
        if(data === '"success"'){
            alertModal("MESSAGE","Create Account ALS successfully.","<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
            $("#inquiryBtn").submit();
            resetForm();
            toggleTab("datalist");
            $("#submitBtn").attr('disabled', false);
            $("#inquiryLoadingCreate").hide();
        }else if(dup === "duplicate"){ 
            alertModal("ERROR", "The input Account and Tax ID is existed, please input new value.","<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
            $("#inquiryLoadingCreate").hide();
            $("#submitBtn").attr('disabled', false);
        }else{
            alertModal("ERROR", data,"<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
            //$("#inquiryLoading").hide();
            $("#inquiryLoadingCreate").hide();
            $("#submitBtn").attr('disabled', false);
        }
    });
}

function clearForm(){
    $("#taxID").val("");
    $("#ocCode").val("");
    $("#accountNo").text("");
    $("#branch").text("");
    $("#createBy").text("");
    $("#createDateTime").text("");
    $("#updateBy").text("");
    $("#updateDateTime").text("");
    $("#purpose").text("");
    $("#subPurpose").text("");
    
    
    $("#approvalLog").html("");
}

function resetForm() {
    $("#maintainFormTitle").html("<h4>Create Account ALS</h4>");
    $("#mode").val("create");
    $("#sAccountNo").val("");
    $("#sAccountNo").attr('readonly', false);
    $("#sAccountNoConfirm").val("");
    $("#sAccountNoConfirm").attr('readonly', false);
    
    $("#sTaxID").val("");
    
    $("#sTaxIDConfirm").val("");
    
    
    $("#sOCCodeinfo").html("");
    $("#sBranchinfo").html("");
    $("#createByinfo").html("");
    $("#createDatetime").html("");
    $("#updateByinfo").html("");
    $("#updateDatetime").html("");
    $("#createUpdateInfo").hide();
}
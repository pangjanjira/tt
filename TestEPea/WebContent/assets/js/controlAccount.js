//UR58060060 Phase 3.2 CADM Function
var viewFuncCode = CONTROL_ACCOUNT_VIEW;
var modifyFuncCode = CONTROL_ACCOUNT_MODIFY;

$(document).ready(function () {
	//check user authorization
    checkViewAuthorize(viewFuncCode);
    checkModifyAuthorize(modifyFuncCode);
    //page initialize
    displayMenu(viewFuncCode);
    initialPage();
});

function initialPage(){
    $("#iCreateBy").val(userName);
    $("#iUpdateBy").val(userName);
    if (canModify === "Y") {
        $("#maintainFormTab").show();
    }else{
    	$("#maintainFormTab").hide();
    }
    $("#inquiryLoading").hide();
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
                alert("page not found");
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
    jsonObj.account = $("#iAccount").val();
    jsonObj.messageCode = $("#iMessageCode").val();
    getMonitorList(JSON.stringify(jsonObj));
    toggleTab("datalist");
});

function getMonitorList(jsonStr) {
    $("#inquiryLoading").show();
    $.ajax({
        url: "ControlAccountInquiryRequestControl",
        data: {
            "action": "InquiryRequest",
            "jsonRequest": jsonStr
        },
        statusCode: {
            404: function() {
                alert("page not found");
            }
        },
        method: 'POST'
    }).done(function(data) {
        var jsonData = $.parseJSON(data);
        var dt = $('#dataTables-result').DataTable();
        dt.clear();
        $.each(jsonData, function(index, value) {
            var tr = $("<tr>").attr("style", "white-space:nowrap;");
            tr.append($("<td>").attr("style", "text-align:center;").append(value.accountNo));
            tr.append($("<td>").attr("style", "text-align:center;").append(value.msgCode));
            tr.append($("<td>").attr("style", "text-align:left;").append(value.msgDescription));
            //if (auth.authLevel === "1"){
            if (canModify === "N"){
                tr.append($("<td>").attr("style", "text-align:center;").append(""));
            }else{
            	var acctNo = '"' + value.accountNo + '"';
                tr.append($("<td>").attr("style", "text-align:center;")
                    .append("<button type='button' class='btn btn-info btn-circle' data-toggle='modal'  onclick='javascript:updateItem(" + value.id + ");'><i class='icon-edit'></i></button>&nbsp;&nbsp;")
                    .append("<button type='button' class='btn btn-info btn-circle' data-toggle='modal'  onclick='javascript:deleteItem(" + value.id + "," + acctNo + ");'><i class='icon-trash'></i></button>"));
            }
            dt.row.add(tr);
        });
        dt.draw();
        $("#inquiryLoading").hide();
    });
}

$("#createForm").submit(function(e) {
    e.preventDefault();
    if($('#mode').val()==="create"){
        var jsonObj = new Object();
        jsonObj.addAccount = $("#addAccount").val();
        jsonObj.addMessageCode = $("#addMessageCode").val();
        jsonObj.addMessageDescription = $("#iMsgDescription").val();
        jsonObj.iCreateBy = $("#iCreateBy").val();
        getCreateControl(JSON.stringify(jsonObj));
    } else if($('#mode').val()==="update") {
        var jsonObj = new Object();
        jsonObj.id = $("#updateId").val();
        jsonObj.addAccount = $("#addAccount").val();
        jsonObj.addMessageCode = $("#addMessageCode").val();
        jsonObj.updateBy = $("#iUpdateBy").val();
        jsonObj.msgDesc = $("#iMsgDescription").val();
        getUpdateControl(JSON.stringify(jsonObj));
    }
});

function getCreateControl(jsonStr) {
    $("#inquiryLoading").show();
    $.ajax({
        url: "ControlAccountInquiryRequestControl",
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
        if(data === '"success"'){
            alertModal("MESSAGE","Create control account successfully","<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
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

function resetForm() {
    $("#maintainFormTitle").html("<h4>Create Control Account</h4>");
    $("#mode").val("create");
    $("#addAccount").attr('readonly', false);
    $("#addAccount").val("");
    $("#addMessageCode").val("");
    $("#updateId").val("0");
    $("#iMsgDescription").val("");
    //$("#iCreateBy").val("");
    $("#icreateDtm").val("");
    //$("#iUpdateBy").val("");
    $("#iUpdateDatetime").val("");
    $("#createBy").html("");
    $("#createDatetime").html("");
    $("#updateBy").html("");
    $("#updateDatetime").html("");
    $("#createUpdateInfo").hide();
}

function setForm(data) {
    $("#maintainFormTitle").html("<h4>Update Control Account</h4>");
    $("#mode").val("update");
    $("#addAccount").val(data[0].accountNo);
    $("#addAccount").attr('readonly', true);
    $("#addMessageCode").val(data[0].msgCode);
    $("#updateId").val(data[0].id);
    $("#iMsgDescription").val(data[0].msgDescription);
    $("#createBy").html(data[0].create_by);
    $("#createDatetime").html(data[0].create_dtm_str);
    $("#updateBy").html(data[0].update_by);
    $("#updateDatetime").html(data[0].update_dtm_str);
    $("#createUpdateInfo").show();
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
function updateItem  (id){
    toggleTab("form");
    var jsonObj = new Object();
    jsonObj.id = id;
    getDataforUpdate(JSON.stringify(jsonObj));
    }
    
function getDataforUpdate(jsonStr) {
    $("#inquiryLoading").show();
    $.ajax({
        url: "ControlAccountInquiryRequestControl",
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


function getUpdateControl(jsonStr) {
    $("#inquiryLoading").show();
    $.ajax({
        url: "ControlAccountInquiryRequestControl",
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
 
function deleteItem(id,account) {
    
    if (confirm("Confirm to delete this control account  '" + account + "'  ?")) {
        var jsonObj = new Object();
        jsonObj.id = id;
        jsonObj.update_by = $("#iUpdateBy").val();
        getDeleteItem(JSON.stringify(jsonObj));
        
    }
}

    
  function getDeleteItem(jsonStr) {
     $("#inquiryLoading").show();
    $.ajax({
        url: "ControlAccountInquiryRequestControl",
        data: {
            "action": "DeleteRequest",
            "jsonDelete": jsonStr
        },
        statusCode: {
            404: function() {
                alert("page not found");
            }
        },
        method: 'POST',
        async: false
    }).done(function(data) {
       //console.log(data.toString());
       $("#inquiryLoading").hide();
       if(data === '"success"'){
           alertModal("MESSAGE","Delete control account successfully","<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
           $("#inquiryBtn").submit();
       }else{
           alertModal("ERROR", data,"<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
       }     
    });
}


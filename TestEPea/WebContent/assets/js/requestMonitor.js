//UR58060060 Phase 3.2 CADM Function
var viewFuncCode = APPROVAL_VIEW;
var modifyFuncCode = APPROVAL_MODIFY;

$(document).ready(function() {
    //check user authorization
    checkViewAuthorize(viewFuncCode);
    checkModifyAuthorize(modifyFuncCode);
    //page initialize
    displayMenu(viewFuncCode);
    initialPage();
});

function initialPage() {
    $("#iApprovalBy").val(userName);
    getMonitorList();

    $('#dataTables-result').dataTable({
        "sDom": '<"datatable-scroll"t><"F"ilp>',
        "dom": '<"top"i>rt<"bottom"flp><"clear">',
        "bProcessing": true,
        "bScrollCollapse": true,
        "bFilter": false,
        "bJQueryUI": false
    });

    $("#inquiryBtn").click(function() {
        $("#inquiryBtn").attr("disabled", true);
        $("#inquiryResultPanel").show();
        $("#inquiryBtn").attr("disabled", false);
        return true;

    });

    $("#iExpireDateFrom").datepicker({
        format: 'dd/mm/yyyy'
    });
    $("#iExpireDateTo").datepicker({
        format: 'dd/mm/yyyy'
    });
}

function showDetail(id) {
    $("#inquiryLoading").show();
    clearForm();
    toggleValidation();
    $.ajax({
        url: "RequestMonitoringRequestControl",
        data: {
            "action": "getMonitorDetail",
            "id": id
        },
        statusCode: {
            404: function() {
                alert("page not found");
            }
        },
        method: 'POST'
    }).done(function(data) {
        var gpGuarantee = $.parseJSON(data)[0];
        var alsAccount = $.parseJSON(data)[3];

        $('#iAlsAccount').append($("<option>").attr("value", "").text("- ALS Account -"));
        $.each(alsAccount, function(index, value) {
            $('#iAlsAccount').append($("<option>").attr("value", value).text(value));
        });
        $('#iAlsAccount').val(gpGuarantee.accountNo);
        $("#iReviewAccount").val(gpGuarantee.accountNo);

        var reviewStatus = "AP";
        if (gpGuarantee.reviewStatus !== "Approved") {
            reviewStatus = "RJ";
        }
        $("#iApprovalStatus").val(reviewStatus);
        $("#iReviewStatus").val(reviewStatus);

        $("#gpGuaranteeId").val(gpGuarantee.id);

        $("#txnStatus").text(gpGuarantee.transactionStatus);
        $("#processDateTime").text(datetimeFormat(gpGuarantee.processDate));
        $("#msgCode").text(gpGuarantee.caMsgCode);
        $("#msgDesc").text(gpGuarantee.caMsgDescription);
        $("#alsStatus").text(gpGuarantee.statusLG + " - " + gpGuarantee.statusDesc);
        $("#alsMsg").text(gpGuarantee.xmlOutput);
        $("#lgNo").text(gpGuarantee.lgNo);
        $("#alsAccount").text(gpGuarantee.accountNo);
        $("#txRef").text(gpGuarantee.txRef);
        $("#dtm").text(gpGuarantee.dtm);
        $("#projNo").text(gpGuarantee.projNo);
        $("#projName").text(gpGuarantee.projName);
        $("#vendorTaxId").text(gpGuarantee.vendorTaxId);
        $("#vendorName").text(gpGuarantee.vendorName);
        $("#bondType").text(gpGuarantee.bondType);
        $("#deptCode").text(gpGuarantee.deptCode);
        $("#seqNo").text(gpGuarantee.SeqNo);
        $("#considerDesc").text(gpGuarantee.considerDesc);
        $("#considerMoney").text(gpGuarantee.considerMoney);
        $("#guaranteeAmt").text(currencyFormat(gpGuarantee.guaranteeAmt));
        $("#compId").text(gpGuarantee.compId);
        $("#userId").text(gpGuarantee.userId);
        $("#endDate").text(gpGuarantee.endDate);
        $("#startDate").text(gpGuarantee.startDate);
        $("#projAmt").text(currencyFormat(gpGuarantee.projAmt));
        $("#projOwnName").text(gpGuarantee.ProjOwnName);
        $("#costCenter").text(gpGuarantee.costCenter);
        $("#costCenterName").text(gpGuarantee.costCenterName);
        $("#contractNo").text(gpGuarantee.contractNo);
        $("#contractDate").text(gpGuarantee.contractDate);
        $("#guaranteePrice").text(currencyFormat(gpGuarantee.guaranteePrice));
        $("#guaranteePercent").text(currencyFormat(gpGuarantee.guaranteePercent));
        $("#advanceGuaranteePrice").text(currencyFormat(gpGuarantee.advanceGuaranteePrice));
        $("#advancePayment").text(currencyFormat(gpGuarantee.advancePayment));
        $("#worksGuaranteePrice").text(currencyFormat(gpGuarantee.worksGuaranteePrice));
        $("#worksGuaranteePercent").text(currencyFormat(gpGuarantee.worksGuaranteePercent));
        $("#collectionPhase").text(gpGuarantee.collectionPhase);
        $("#documentNo").text(gpGuarantee.documentNo);
        $("#documentDate").text(gpGuarantee.documentDate);
        $("#expireDate").text(gpGuarantee.expireDate);
        $("#extendDate").text(gpGuarantee.extendDate); //UR58120031 extendDate add by Tana L. @26022016

        $("#requestType").text(gpGuarantee.issueTypeDesc);
        
        //add by Malinee T. UR58100048 @20151224 
        $("#egpAckStatus").text(gpGuarantee.egpAckStatus);
        $("#egpAckDtm").text(gpGuarantee.egpAckDtmStr);
        $("#egpAckTranxId").text(gpGuarantee.egpAckTranxId);
        $("#egpAckCode").text(gpGuarantee.egpAckCode);
        $("#egpAckMsg").val(gpGuarantee.egpAckMsg);

        var reviewLog = $.parseJSON(data)[4];
        console.log(reviewLog);
        $.each(reviewLog, function(index, value) {
            var div = $("<div>").addClass("form-group");

            div.append($("<label>").addClass("control-label col-lg-1").text("Status"));
            div.append($("<div>").addClass("col-lg-1").attr("style", "padding-top:8px;")
                    .append($("<span>").addClass("form-control-static").text(value.reviewStatus)));
            div.append($("<label>").addClass("control-label col-lg-1").text("Account"));
            div.append($("<div>").addClass("col-lg-2").attr("style", "padding-top:8px;")
                    .append($("<span>").addClass("form-control-static").text(value.accountNo)));
            div.append($("<label>").addClass("control-label col-lg-1").text("Reason"));
            div.append($("<div>").addClass("col-lg-2").attr("style", "padding-top:8px;")
                    .append($("<span>").addClass("form-control-static").text(value.reviewReason)));
            div.append($("<label>").addClass("control-label col-lg-1").html("Dtm<br/>User"));
            div.append($("<div>").addClass("col-lg-3").attr("style", "padding-top:8px;")
                    .append($("<span>").addClass("form-control-static").html(value.reviewDtm + "<br/>" + value.reviewBy)));

            $("#reviewLog").append(div); 
        });

        var approvalLog = $.parseJSON(data)[1];
        console.log(approvalLog);
        $.each(approvalLog, function(index, value) {
            var div = $("<div>").addClass("form-group");

            div.append($("<label>").addClass("control-label col-lg-1").text("Status"));
            div.append($("<div>").addClass("col-lg-1").attr("style", "padding-top:8px;")
                    .append($("<span>").addClass("form-control-static").text(value.approveStatus)));
            div.append($("<label>").addClass("control-label col-lg-1").text("Account"));
            div.append($("<div>").addClass("col-lg-2").attr("style", "padding-top:8px;")
                    .append($("<span>").addClass("form-control-static").text(value.accountNo)));
            div.append($("<label>").addClass("control-label col-lg-1").text("Reason"));
            div.append($("<div>").addClass("col-lg-2").attr("style", "padding-top:8px;")
                    .append($("<span>").addClass("form-control-static").text(value.approveReason)));
            div.append($("<label>").addClass("control-label col-lg-1").html("Dtm<br/>User"));
            div.append($("<div>").addClass("col-lg-3").attr("style", "padding-top:8px;")
                    .append($("<span>").addClass("form-control-static").html(value.approveDtm + "<br/>" + value.approveBy)));

            $("#approvalLog").append(div);
        });
        
      //UR58120031 eddReviewLog add by Tana L. @15022016
        var eedReviewLog = $.parseJSON(data)[5];
        console.log(eedReviewLog);
        var eedCount = 0;
        $.each(eedReviewLog, function(index, value) {
            var div = $("<div>").addClass("form-group");

            div.append($("<label>").addClass("control-label col-lg-1").text("Status"));
            div.append($("<div>").addClass("col-lg-1").attr("style", "padding-top:8px;")
                    .append($("<span>").addClass("form-control-static").text(value.reviewStatus)));
            div.append($("<label>").addClass("control-label col-lg-1").html("Account<br/>Reason"));
            div.append($("<div>").addClass("col-lg-2").attr("style", "padding-top:8px;")
                    .append($("<span>").addClass("form-control-static").html(value.accountNo + "<br/>" + value.reviewReason)));
            div.append($("<label>").addClass("control-label col-lg-1").html("Old<br/>New"));
            div.append($("<div>").addClass("col-lg-2").attr("style", "padding-top:8px;")
                    .append($("<span>").addClass("form-control-static").html(value.oldEndDate + "<br/>" + value.newEndDate)));
            div.append($("<label>").addClass("control-label col-lg-1").html("Dtm<br/>User"));
            div.append($("<div>").addClass("col-lg-3").attr("style", "padding-top:8px;")
                    .append($("<span>").addClass("form-control-static").html(value.reviewDtm + "<br/>" + value.reviewBy)));

            $("#eedReviewLog").append(div);
            eedCount++;
                     
        });
        if(eedCount == 0) {
        	$("#eedReviewBlog").hide();
            
        }
        else {
        	$("#eedReviewBlog").show();
        }
        
        //UR58120031 eddApprovalLog add by Tana L. @15022016
        var eedApprovalLog = $.parseJSON(data)[6];
        console.log(eedApprovalLog);
        var eedCount = 0;
    	$.each(eedApprovalLog, function(index, value) {
            var div = $("<div>").addClass("form-group");

            div.append($("<label>").addClass("control-label col-lg-1").text("Status"));
            div.append($("<div>").addClass("col-lg-1").attr("style", "padding-top:8px;")
                    .append($("<span>").addClass("form-control-static").text(value.approveStatus)));
            div.append($("<label>").addClass("control-label col-lg-1").html("Account<br/>Reason"));
            div.append($("<div>").addClass("col-lg-2").attr("style", "padding-top:8px;")
                    .append($("<span>").addClass("form-control-static").html(value.accountNo + "<br/>" + value.approveReason)));
            div.append($("<label>").addClass("control-label col-lg-1").html("Old<br/>New"));
            div.append($("<div>").addClass("col-lg-2").attr("style", "padding-top:8px;")
                    .append($("<span>").addClass("form-control-static").html(value.oldEndDate + "<br/>" + value.newEndDate)));
            div.append($("<label>").addClass("control-label col-lg-1").html("Dtm<br/>User"));
            div.append($("<div>").addClass("col-lg-3").attr("style", "padding-top:8px;")
                    .append($("<span>").addClass("form-control-static").html(value.approveDtm + "<br/>" + value.approveBy)));

            $("#eedApprovalLog").append(div);
            eedCount++;
            
        });
        
    	if(eedCount == 0) {
        	$("#eedApprovalBlog").hide();
        }
    	else {
    		$("#eedApprovalBlog").show();
    	}

        $("#detailModal").modal();
        if (canModify != "Y") {
            // No Authorize to Approve
            $("#approveActionForm").hide();
        }
        $("#inquiryLoading").hide();

    });
}

function clearForm() {
    $("#iAlsAccount").empty();
    $("#iApprovalStatus").val("RJ");
    $("#iApprovalReason").val("");
    $("#txnStatus").text("");
    $("#processDateTime").text("");
    $("#msgCode").text("");
    $("#msgDesc").text("");
    $("#alsStatus").text("");
    $("#alsMsg").text("");
    $("#lgNo").text("");
    $("#alsAccount").text("");
    $("#txRef").text("");
    $("#dtm").text("");
    $("#projNo").text("");
    $("#projName").text("");
    $("#vendorTaxId").text("");
    $("#vendorName").text("");
    $("#bondType").text("");
    $("#deptCode").text("");
    $("#seqNo").text("");
    $("#considerDesc").text("");
    $("#considerMoney").text("");
    $("#guaranteeAmt").text("");
    $("#compId").text("");
    $("#userId").text("");
    $("#endDate").text("");
    $("#startDate").text("");
    $("#projAmt").text("");
    $("#projOwnName").text("");
    $("#costCenter").text("");
    $("#costCenterName").text("");
    $("#contractNo").text("");
    $("#contractDate").text("");
    $("#guaranteePrice").text("");
    $("#guaranteePercent").text("");
    $("#advanceGuaranteePrice").text("");
    $("#advancePayment").text("");
    $("#worksGuaranteePrice").text("");
    $("#worksGuaranteePercent").text("");
    $("#collectionPhase").text("");
    $("#documentNo").text("");
    $("#documentDate").text("");
    $("#expireDate").text("");
    
    //add by Malinee T. UR58100048 @20151224 
    $("#egpAckStatus").text("");
    $("#egpAckDtm").text("");
    $("#egpAckTranxId").text("");
    $("#egpAckCode").text("");
    $("#egpAckMsg").val("");

    $("#reviewLog").html("");
    $("#approvalLog").html("");
    //UR58120031 Clear extensionLog add by Tana L. @15022016
    $("#eedReviewLog").html("");
    $("#eedApprovalLog").html("");
}

$("#inquiryBtn").click(function() {
    getMonitorList();
});

$("#exportBtn").click(function() {
    getExcelReport();
});

$("#iApprovalStatus").change(function() {
    toggleValidation();
});

$("#validateBtn").click(function() {
	//UR59040034 Add eGP Pending Review & Resend Response function
	//Add validate select null account
	if (($("#iAlsAccount").val() == "") && ($("#iApprovalStatus").val() === "AP")) {
        alert("Please select account for approval.");
    } else {
	    if (($('#iAlsAccount').val() !== $("#iReviewAccount").val()) || ($('#iApprovalStatus').val() !== $("#iReviewStatus").val())) {
	        confirmModal("Confirm", "Confirm to process request approval?");
	    } else {
	    	//UR58120031 Phase 2 (protect appprove from multiple screen)
	    	$.ajax({
	            url: "RequestMonitoringRequestControl",
	            data: {
	                "action": "checkDupApproval",
	                "id": $("#gpGuaranteeId").val()
	            },
	            statusCode: {
	                404: function() {
	                    alert("page not found");
	                }
	            },
	            method: 'POST'
	        }).done(function(data) {
	        	if (data == "DUPLICATE") {
	        		alert("Duplicate Approval !");
	        	}
	        	else {	
		            $("#approveForm").submit();
		            //------- Add by Tana L. @12042016
		            $("#validateBtn").blur();
	        	}
	        });
	    }
    }
});

function getMonitorList() {
    if (($("#iExpireDateFrom").val().trim() != "") && ($("#iExpireDateTo").val().trim() == "")) {
        alertModal("MESSAGE", "Please Enter the Expire Date To", "<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
        return;
    }

    if (($("#iExpireDateFrom").val().trim() == "") && ($("#iExpireDateTo").val().trim() != "")) {
        alertModal("MESSAGE", "Please Enter the Expire Date From", "<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
        return;
    }

    if (($("#iExpireDateFrom").val().trim() != "") && ($("#iExpireDateTo").val().trim() != "")) {
        var parts = $("#iExpireDateFrom").val().trim().split("/");
        var startDate = new Date(parseInt(parts[2], 10),
                parseInt(parts[1], 10) - 1,
                parseInt(parts[0], 10));

        parts = $("#iExpireDateTo").val().trim().split("/");
        var endDate = new Date(parseInt(parts[2], 10),
                parseInt(parts[1], 10) - 1,
                parseInt(parts[0], 10));

        if (endDate < startDate) {
            alertModal("MESSAGE", "'Expire Date To' must be after or same as 'Expire Date From'.", "<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
            return false;
        }
    }
    $("#inquiryLoading").show();
    $.ajax({
        url: "RequestMonitoringRequestControl",
        data: {
            "action": "InquiryMonitor",
            "taxId": $("#iTaxID").val(),
            "accountNo": $("#iAccountNo").val(),
            "expireDateFrom": $("#iExpireDateFrom").val(),
            "expireDateTo": $("#iExpireDateTo").val(),
            "egpStatus": $("#iEgpStatus").val(), //add by Malinee T. UR58100048 @20151224
            "requestType" : $("#iRequestType").val() //UR58120031 Request Type add by Tana L. @15022016
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
            var tr = $("<tr>");
            tr.append($("<td>").attr("style", "text-align:center;font-size:small;") //data-target='#detailModal'
                    .append("<button type='button' id='detailBtn' class='btn btn-info btn-circle' data-toggle='modal'  onclick='javascript:showDetail(" + value.id + ");'><i class='icon-pencil'></i></button>"));
            
          //UR58120031 Parent Detail add by Tana L. @18022016
//            var issueType = value.issueType;
//            if(issueType != "0004") {
//            	tr.append($("<td>").attr("style", "text-align:center;font-size:small;") //data-target='#detailModal'
//                        .append("<button type='button' class='btn btn-info btn-circle' data-toggle='modal'  onclick='javascript:showParentDetail(" + value.id + ");'><i class='icon-search'></i></button>"));       
//            }
//            else {
//            	tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(""));
//            }
            
            var txRefTemp = value.txRef;
            var txRef = value.txRef;
            try {
                txRef = txRefTemp.replace(/ /g, '');
            } catch (err) {
                console.writeline('[' + txRef + '] error: ' + err.message);
            }
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(txRef));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.issueTypeDesc)); //UR58120031 requestType add by Tana L. @18022016
            tr.append($("<td>").attr("style", "text-align:left;font-size:smaller;width:120px;").append(value.vendorName));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.accountNo));
            tr.append($("<td>").attr("style", "text-align:right;font-size:smaller;").append(currencyFormat(value.guaranteeAmt)));
            //tr.append($("<td>").attr("style","text-align:center;font-size:smaller;").append(value.msgCode));
            var msgCodeStr = value.caMsgCode;
            if (msgCodeStr != '') {
                msgCodeStr = msgCodeStr + ' - ' + value.caMsgDescription;
            }
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(msgCodeStr));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.expireDate));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.extendDate)); //UR58120031 extendDate add by Tana L. @11022016
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.dtm));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.projNo));
            tr.append($("<td>").attr("style", "text-align:left;font-size:smaller;width:150px;").append(value.projName));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.startDate));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.endDate));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.bondType));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.xmlOutput));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.lgNo));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.vendorTaxId));

            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.reviewStatus));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.reviewReason));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.reviewBy));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.reviewDtm));

            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.ocCode));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.branch));
            
            //add by Malinee T. UR58100048 @20151225
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.egpAckStatus));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.egpAckMsg));

            dt.row.add(tr);
        });
        dt.draw();
        $("#inquiryLoading").hide();
    });
}

$("#approveForm").submit(function(e) {
    $("#submitLoading").show();
    e.preventDefault();
    $.ajax({
        url: "RequestMonitoringRequestControl",
        data: {
            "action": "checkTimeOffHostALS"
        },                
        statusCode: {
            404: function() {
                alert("page not found");
            }
        },
        method: 'POST'
    }).done(function(data) {
        $("#submitLoading").hide();
        if (data === "ONLINE") {
            $("#detailModal").modal('hide');
            $.ajax({
                url: "GPGuaranteeRequestControl",
                data: {
                    "requestor": "Approval",
                    "iGpGuaranteeId": $("#gpGuaranteeId").val(),
                    "iApprovalStatus": $("#iApprovalStatus").val(),
                    "iApprovalReason": $("#iApprovalReason").val(),
                    "iApprovalBy": $("#iApprovalBy").val(),
                    "iAccount": $("#iAlsAccount").val(),
                    "iEgpStatus": $("#iEgpStatus").val(), //add by Malinee T. UR58100048 @20151224
                },
                statusCode: {
                    404: function() {
                        //alert("page not found");
                    }
                },
                method: 'POST'
            }).done(function(data) {
            });
            if ($("#iApprovalStatus").val() === "AP") {
                alertModal("MESSAGE", "The approved request is sent to ALS successfully", "<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
            } else {
                alertModal("MESSAGE", "The canceled request is processing, please see the result in the inquiry screen", "<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
            }
            var delayMonitor = setInterval(function() {
                getMonitorList();
                clearInterval(delayMonitor);
            }, 1000);
        } else if (data === "OFFLINE") {
            $("#detailModal").modal('hide');
            alertModal("MESSAGE", "ALS is offline, please wait until ALS is online to process.", "<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>Close</button>");
        } else {
            $("#detailModal").modal('hide');
            alertModal("ERROR", data, "<button type='button' class='btn btn-alert btn-sm' data-dismiss='modal'>Close</button>");
        }
    });
});

function resetForm() {
    $("#maintainFormTitle").html("<h4>Create Control Account</h4>");
    $("#mode").val("create");
    $("#account").val("");
    $("#messageCode").val("");
    $("#messageDesc").val("");
    $("#createBy").html("");
    $("#createDatetime").html("");
    $("#updateBy").html("");
    $("#updateDatetime").html("");
    $("#createUpdateInfo").hide();
}

function toggleTab(tabType) {
    if (tabType == "form") {
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

function toggleValidation() {
    if ($("#iApprovalStatus").val() === "AP") {
        $("#iApprovalReason").prop('required', false);
    } else {
        $("#iApprovalReason").prop('required', true);
    }

}

function getExcelReport() {
    if (($("#iExpireDateFrom").val().trim() != "") && ($("#iExpireDateTo").val().trim() == "")) {
        alertModal("MESSAGE", "Please Enter the Expire Date To", "<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
        return;
    }

    if (($("#iExpireDateFrom").val().trim() == "") && ($("#iExpireDateTo").val().trim() != "")) {
        alertModal("MESSAGE", "Please Enter the Expire Date From", "<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
        return;
    }

    if (($("#iExpireDateFrom").val().trim() != "") && ($("#iExpireDateTo").val().trim() != "")) {
        var parts = $("#iExpireDateFrom").val().trim().split("/");
        var startDate = new Date(parseInt(parts[2], 10),
                parseInt(parts[1], 10) - 1,
                parseInt(parts[0], 10));

        parts = $("#iExpireDateTo").val().trim().split("/");
        var endDate = new Date(parseInt(parts[2], 10),
                parseInt(parts[1], 10) - 1,
                parseInt(parts[0], 10));

        if (endDate < startDate) {
            alertModal("MESSAGE", "'Expire Date To' must be after or same as 'Expire Date From'.", "<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
            return false;
        }
    }
    $("#inquiryLoading").show();
    $.ajax({
        url: "RequestMonitoringRequestControl",
        async: false,
        data: {
            "action": "exportExcel",
            "username": userName,
            "taxId": $("#iTaxID").val(),
            "accountNo": $("#iAccountNo").val(),
            "expireDateFrom": $("#iExpireDateFrom").val(),
            "expireDateTo": $("#iExpireDateTo").val(),
            "egpStatus": $("#iEgpStatus").val(), //add by Malinee T. UR58100048 @20151224
            "requestType" : $("#iRequestType").val() //UR58120031 Request Type add by Tana L. @15022016
        },
        statusCode: {
            404: function() {
                alert("page not found");
            }
        },
        success: function(result) {
        },
        method: 'POST'
    }).done(function(data) {
        $("#inquiryLoading").hide();
        fileDownload(data);
    });
}

function fileDownload(filename) {
    var curLocation = window.location.href;
    var path = curLocation.replace("request_monitor.jsp", "");
    path = path + 'reportfile/' + filename;
    window.open(path,'_blank');
    //$.fileDownload(path, {
    //    successCallback: function(url) {
    //    },
    //    failCallback: function(responseHtml, url) {
    //        alertModal("MESSAGE", "Please try again later.", "<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
    //    }
    //});
}

function confirmModal(txtHeader, txtContent) {
    if(confirm(txtContent)){
        $("#approveForm").submit();
    }else{
        //do nothing
    }
    /*
    if (txtHeader != "") {
        $("#alertHeader").html(txtHeader);
    }
    if (txtContent != "") {
        $("#alertContent").html(txtContent);
    }
    if (txtFooter != "") {
        var txtFooter = "<button type='button' class='btn btn-primary btn-sm' data-dismiss='modal' id='okBtn'>OK</button> <button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>Cancel</button>";
        $("#alertFooter").html(txtFooter);
    }
    $("#alertModal").modal({
        backdrop: 'static',
    });

    $('#alertModal').modal({backdrop: 'static', keyboard: false})
            .one('click', '#okBtn', function() {
                var zIndex = 1040 + (10 * $('.modal.visible').length);
                $(this).css('z-index', zIndex);
                setTimeout(function(){
                    $('.modal-backdrop').not('.modal-stack').css('z-index', zIndex - 1).addClass('modal-stack');
                },0);
                $("#approveForm").submit();
            });
            */
}
//UR58120031 showParentDetail add by Tana L. @18022016
//function showParentDetail(id) {
//    $.ajax({
//        url: "RequestMonitoringRequestControl",
//        data: {
//            "action": "getParentId",
//            "id": id
//        },
//        statusCode: {
//            404: function() {
//                alert("page not found");
//            }
//        },
//        method: 'POST'
//    }).done(function(data) {
//    	if (data == "Not Found") {
//    		alert("page not found");
//    	}
//    	else {
//    		showDetail(data);
//    	}
//    });
//}

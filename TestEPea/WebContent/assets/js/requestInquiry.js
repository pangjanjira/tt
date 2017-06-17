//UR58060060 Phase 3.2 CADM Function
var viewFuncCode = REQUEST_VIEW;
var modifyFuncCode = "";

$(document).ready(function() {
    //check user authorization
    checkViewAuthorize(viewFuncCode);
    //page initialize
    displayMenu(viewFuncCode);
    initialPage();
});

function initialPage() {
    $('#dataTables-result').dataTable({
        "sDom": '<"datatable-scroll"t><"F"ilp>',
        "dom": '<"top"i>rt<"bottom"flp><"clear">',
        "bProcessing": true,
        "bScrollCollapse": true,
        "bFilter": false,
        "bJQueryUI": false
    });

    $("#iDtm").datepicker({
        format: 'dd/mm/yyyy'
    });
    $("#iApprovalDtmFrom").datepicker({
        format: 'dd/mm/yyyy'
    });
    $("#iApprovalDtmTo").datepicker({
        format: 'dd/mm/yyyy'
    });
    $("#iExpireDateFrom").datepicker({
        format: 'dd/mm/yyyy'
    });
    $("#iExpireDateTo").datepicker({
        format: 'dd/mm/yyyy'
    });

    $("#block-validate")[0].reset();
}

function getInquiryList(jsonStr) {
    $("#inquiryLoading").show();
    console.log(jsonStr);
    $.ajax({
        url: "RequestInquiryRequestControl",
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
        console.log(data);
        var jsonData = $.parseJSON(data);
        console.log(jsonData);
        var dt = $('#dataTables-result').DataTable();
        dt.clear();
        $.each(jsonData, function(index, value) {
            var tr = $("<tr>");
            tr.append($("<td>").attr("style", "text-align:center;font-size:small;") //data-target='#detailModal'
                    .append("<button type='button' class='btn btn-info btn-circle' data-toggle='modal'  onclick='javascript:showDetail(" + value.id + ");'><i class='icon-search'></i></button>"));
            
            //UR58120031 Parent Detail add by Tana L. @18022016
            var issueType = value.issueType;
            if(issueType != "0004") {
            	tr.append($("<td>").attr("style", "text-align:center;font-size:small;") //data-target='#detailModal'
                        .append("<button type='button' class='btn btn-info btn-circle' data-toggle='modal'  onclick='javascript:showParentDetail(" + value.id + ");'><i class='icon-search'></i></button>"));       
            }
            else {
            	tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(""));
            }
            
            var txRefTemp = value.txRef;
            var txRef = value.txRef;
            try {
                txRef = txRefTemp.replace(/ /g, '');
            } catch (err) {
                console.writeline('[' + txRef + '] error: ' + err.message);
            }
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(txRef));
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
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.extendDate)); //UR58120031 extendDate add by Tana L. @09022016
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.dtm));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.projNo));
            tr.append($("<td>").attr("style", "text-align:left;font-size:smaller;width:150px;").append(value.projName));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.startDate));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.endDate));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.bondType));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.xmlOutput));

            //tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.approveStatus));
            //tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(datetimeFormat(value.approveDtm)));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.issueTypeDesc));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.lgNo));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.vendorTaxId));
            //tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.approveBy));

            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.reviewStatus));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.reviewReason));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.reviewBy));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.reviewDtm));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.approveStatus));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.approveReason));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.approveBy));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.approveDtm));

            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.ocCode));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.branch));
            
            //add by Malinee T. UR58100048 @20151224 
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.egpAckStatus));
            tr.append($("<td>").attr("style", "text-align:center;font-size:smaller;").append(value.egpAckMsg));
            
            dt.row.add(tr);
        });
        dt.draw();
        $("#inquiryLoading").hide();
    });
}


$("#block-validate").submit(function(e) {

    e.preventDefault();
    if (($("#iApprovalDtmFrom").val().trim() != "") && ($("#iApprovalDtmTo").val().trim() == "")) {
        alertModal("MESSAGE", "Please Enter the Approval Dtm To", "<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
        return;
    }

    if (($("#iApprovalDtmFrom").val().trim() == "") && ($("#iApprovalDtmTo").val().trim() != "")) {
        alertModal("MESSAGE", "Please Enter the Approval Dtm From", "<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
        return;
    }

    if (($("#iApprovalDtmFrom").val().trim() != "") && ($("#iApprovalDtmTo").val().trim() != "")) {

        var parts = $("#iApprovalDtmFrom").val().trim().split("/");
        var startDate = new Date(parseInt(parts[2], 10),
                parseInt(parts[1], 10) - 1,
                parseInt(parts[0], 10));

        parts = $("#iApprovalDtmTo").val().trim().split("/");
        var endDate = new Date(parseInt(parts[2], 10),
                parseInt(parts[1], 10) - 1,
                parseInt(parts[0], 10));

        if (endDate < startDate) {
            alertModal("MESSAGE", "'Approval Dtm To' must be after or same as 'Approval Dtm From'.", "<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
            return;
        }
    }

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

    var jsonObj = new Object();
    jsonObj.txRef = $("#iTxRef").val();
    jsonObj.dtm = $("#iDtm").val();
    jsonObj.vendorTaxId = $("#iVendorTaxID").val();
    jsonObj.vendorName = $("#iVendorName").val();
    jsonObj.lgNo = $("#iLgNo").val();
    jsonObj.bondType = $("#iBondType").val();
    jsonObj.projNo = $("#iProjNo").val();
    jsonObj.requestType = $("#iRequestType").val();
    jsonObj.alsStatus = $("#iAlsStatus").val();
    jsonObj.approveStatus = $("#iAppvStatus").val();
    jsonObj.approveBy = $("#iApproveBy").val();
    jsonObj.approveDtmFrom = $("#iApprovalDtmFrom").val();
    jsonObj.approveDtmTo = $("#iApprovalDtmTo").val();
    jsonObj.accountNo = $("#iAccountNo").val();
    jsonObj.expireDate = $("#iExpireDate").val();
    jsonObj.egpStatus = $("#iEgpStatus").val(); //add by Malinee T. UR58100048 @20151224 
    
    //alert (JSON.stringify(jsonObj));
    getInquiryList(JSON.stringify(jsonObj));
});

function showDetail(id) {
    $("#inquiryLoading").show();
    clearForm();
    $("#resendBtn").show();
    
    $.ajax({
        url: "RequestInquiryRequestControl",
        data: {
            "action": "getInquiryDetail",
            "id": id
        },
        statusCode: {
            404: function() {
                alert("page not found");
            }
        },
        method: 'POST'
    }).done(function(data) {
        console.log(data);
        var gpGuarantee = $.parseJSON(data)[0];
        
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

        $("#txRef2").text(gpGuarantee.txRef);
        $("#dtm2").text(gpGuarantee.dtm);
        $("#projNo2").text(gpGuarantee.projNo);
        $("#vendorTaxId2").text(gpGuarantee.vendorTaxId);
        $("#bondType2").text(gpGuarantee.bondType);
        $("#seqNo2").text(gpGuarantee.SeqNo);

        $("#bankCode").text(gpGuarantee.bankCode);
        $("#bankAddr").text(gpGuarantee.bankAddr);
        $("#branchCode").text(gpGuarantee.branchCode);
        $("#branchName").text(gpGuarantee.branchName);

        $("#approveStatus").text(gpGuarantee.approveStatus);
        $("#approveAmt").text(currencyFormat(gpGuarantee.appvAmt));
        $("#approveReason").text(gpGuarantee.approveReason);
        $("#approveDtm").text(datetimeFormat(gpGuarantee.approveDtm));
        $("#approveBy").text(gpGuarantee.approveBy);
        
        //add by Malinee T. UR58100048 @20151224 
        $("#egpAckStatus").text(gpGuarantee.egpAckStatus);
        $("#egpAckDtm").text(gpGuarantee.egpAckDtmStr);
        $("#egpAckTranxId").text(gpGuarantee.egpAckTranxId);
        $("#egpAckCode").text(gpGuarantee.egpAckCode);
        $("#egpAckMsg").val(gpGuarantee.egpAckMsg);
        
        //UR59040034 Add eGP Pending Review & Resend Response function
        $("#resendCount").text(gpGuarantee.resendCount);

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

        var logEBXML = $.parseJSON(data)[2];
        console.log(logEBXML);
        
        //UR59040034 Add eGP Pending Review & Resend Response function
        //Fix Missing "<"
        if(logEBXML.xmlInput !== null && logEBXML.xmlInput !== undefined && logEBXML.xmlInput !== "") {
        	$("#logXmlInput").val("<"+logEBXML.xmlInput);
        }
        $("#logInputDatetime").text(logEBXML.inputDatetimeStr);
        
        if(logEBXML.xmlResponseToEbxml !== null && logEBXML.xmlResponseToEbxml !== undefined && logEBXML.xmlResponseToEbxml !== "") {
        	$("#logXmlResponseToEbxml").val("<"+logEBXML.xmlResponseToEbxml);
        }
        $("#logResponseToEbxmlDatetime").text(logEBXML.responseToEbxmlDatetimeStr);
        
        if(logEBXML.xmlOutput !== null && logEBXML.xmlOutput !== undefined && logEBXML.xmlOutput !== "") {
        	$("#logXmlOutput").val("<"+logEBXML.xmlOutput);
        }
        $("#logOutputDatetime").text(logEBXML.outputDatetimeStr);
        
        if(logEBXML.xmlResponseFromEbxml !== null && logEBXML.xmlResponseFromEbxml !== undefined && logEBXML.xmlResponseFromEbxml !== "") {
        	$("#logXmlResponseFromEbxml").val("<"+logEBXML.xmlResponseFromEbxml);
        }
        $("#logResponseFromEbxmlDatetime").text(logEBXML.responseFromEbxmlDatetimeStr);
        
        $("#logAlsOnline").text(logEBXML.alsOnline);
          
        //UR59040034 Add eGP Pending Review & Resend Response function
        console.log("lastestExtend : "+gpGuarantee.latestExtend); 
        if((gpGuarantee.approveStatus == "Pending Approve") || (gpGuarantee.reviewStatus == "Pending Approve") ||
        		(gpGuarantee.issueType == "0004" && gpGuarantee.approveStatus == "Approved" && gpGuarantee.transactionStatus != "Success") ||
        		(gpGuarantee.issueType == "0007" && gpGuarantee.latestExtend == false)
        		) {
        	
        	$("#resendBtn").hide();
        }else {
        	checkResendAuthorize();
        }

        $("#detailModal").modal();
        if (0) { // No Authorize to Approve
            $("#approveActionForm").hide();
        }
        $("#inquiryLoading").hide();
    });
}

function clearForm() {
    $("#iApprovalStatus").text("");
    $("#iApprovalReason").text("");
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
    
  //UR58120031 Clear extensionLog add by Tana L. @11022016
    $("#eedReviewLog").html("");
    $("#eedApprovalLog").html("");
    
  //UR59040034 Add eGP Pending Review & Resend Response function
    $("#resendCount").text("");
}

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

function viewItem() {
    toggleTab("form");
    setForm(account);
}

//UR59040034 Add eGP Pending Review & Resend Response function
//Add Resend XML Output button
$("#resendBtn").click(function() {
	$.ajax({
        url: "GPGuaranteeRequestControl",
        //async: false,
        data: {
            "requestor": "resend",
            "iGpGuaranteeId": $("#gpGuaranteeId").val(),
        },
        statusCode: {
            404: function() {
                alert("page not found");
            }
        },
        method: 'POST'
    }).done(function(data) {
    });
	$("#detailModal").modal('hide');
	alertModal("MESSAGE", "The request is resend XML Ouput.", "<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
});


$("#exportBtn").click(function(e) {

    e.preventDefault();
    if (($("#iApprovalDtmFrom").val().trim() != "") && ($("#iApprovalDtmTo").val().trim() == "")) {
        alertModal("MESSAGE", "Please Enter the Approval Dtm To", "<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
        return;
    }

    if (($("#iApprovalDtmFrom").val().trim() == "") && ($("#iApprovalDtmTo").val().trim() != "")) {
        alertModal("MESSAGE", "Please Enter the Approval Dtm From", "<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
        return;
    }

    if (($("#iApprovalDtmFrom").val().trim() != "") && ($("#iApprovalDtmTo").val().trim() != "")) {

        var parts = $("#iApprovalDtmFrom").val().trim().split("/");
        var startDate = new Date(parseInt(parts[2], 10),
                parseInt(parts[1], 10) - 1,
                parseInt(parts[0], 10));

        parts = $("#iApprovalDtmTo").val().trim().split("/");
        var endDate = new Date(parseInt(parts[2], 10),
                parseInt(parts[1], 10) - 1,
                parseInt(parts[0], 10));

        if (endDate < startDate) {
            alertModal("MESSAGE", "'Approval Dtm To' must be after or same as 'Approval Dtm From'.", "<button type='button' class='btn btn-info btn-sm' data-dismiss='modal'>OK</button>");
            return;
        }
    }

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

    var jsonObj = new Object();
    jsonObj.txRef = $("#iTxRef").val();
    jsonObj.dtm = $("#iDtm").val();
    jsonObj.vendorTaxId = $("#iVendorTaxID").val();
    jsonObj.vendorName = $("#iVendorName").val();
    jsonObj.lgNo = $("#iLgNo").val();
    jsonObj.bondType = $("#iBondType").val();
    jsonObj.projNo = $("#iProjNo").val();
    jsonObj.requestType = $("#iRequestType").val();
    jsonObj.alsStatus = $("#iAlsStatus").val();
    jsonObj.approveStatus = $("#iAppvStatus").val();
    jsonObj.approveBy = $("#iApproveBy").val();
    jsonObj.approveDtmFrom = $("#iApprovalDtmFrom").val();
    jsonObj.approveDtmTo = $("#iApprovalDtmTo").val();
    jsonObj.accountNo = $("#iAccountNo").val();
    jsonObj.expireDate = $("#iExpireDate").val();
    
    jsonObj.egpStatus = $("#iEgpStatus").val(); //add by Malinee T. UR58100048 @20151224 
    //alert (JSON.stringify(jsonObj));
    getExcelReport(JSON.stringify(jsonObj));
});

function getExcelReport(jsonStr) {
    $("#inquiryLoading").show();
    $.ajax({
        url: "RequestInquiryRequestControl",
        async: false,
        data: {
            "action": "exportExcel",
            "username": userName,
            "jsonRequest": jsonStr
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
    var path = curLocation.replace("request_inquiry.jsp", "");
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
//UR58120031 showParentDetail add by Tana L. @18022016
function showParentDetail(id) {
    $.ajax({
        url: "RequestInquiryRequestControl",
        data: {
            "action": "getParentId",
            "id": id
        },
        statusCode: {
            404: function() {
                alert("page not found");
            }
        },
        method: 'POST'
    }).done(function(data) {
    	if (data == "") {
    		alert("Parent Transaction not found!");
    	}
    	else {
    		showDetail(data);
    	}
    });
}

function checkResendAuthorize(){
    $.ajax({
        url: "SessionControl",
        data: {"object": "isApprover"},
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
        	$("#resendBtn").hide();
        } else if (authFlag == "N") {
        	//No authorization
        	$("#resendBtn").hide();
        }
	});
}

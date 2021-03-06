﻿<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->

 <!-- BEGIN HEAD -->
<head>
      <meta charset="UTF-8" />
    <title>SCB E-Guarantee</title>
     <meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
     <!--[if IE]>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <![endif]-->
    <!-- GLOBAL STYLES -->
    <!-- GLOBAL STYLES -->
    <link rel="stylesheet" href="assets/plugins/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" href="assets/css/main.css" />
    <link rel="stylesheet" href="assets/css/theme.css" />
    <link rel="stylesheet" href="assets/css/MoneAdmin.css" />
    <link rel="stylesheet" href="assets/plugins/Font-Awesome/css/font-awesome.css" />
    <!--END GLOBAL STYLES -->

    <!-- PAGE LEVEL STYLES -->
     <link rel="stylesheet" href="assets/plugins/validationengine/css/validationEngine.jquery.css" />
     <link href="assets/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet" />
     
	<style type="text/css">
	<!-- 
		.datatable-scroll {
		    overflow-x: auto;
		    overflow-y: visible;
		}
	--> 
	</style>
    <!-- END PAGE LEVEL  STYLES -->
   <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>
     <!-- END HEAD -->

     <!-- BEGIN BODY -->
<body class="padTop53 ">

   <!-- MAIN WRAPPER -->
    <div id="wrap">

<!-- ######################################################################## -->
		<%@ include file ="header.jsp" %>
<!-- ######################################################################## -->

        <!-- MENU SECTION -->
		<%@ include file ="menu.jsp" %>
        <!--END MENU SECTION -->

<!-- ######################################################################## -->

        <!--PAGE CONTENT -->
        <div id="content">
             
            <div class="inner">

<!-- ######################################################################## -->

                <!-- BEGIN PAGE TITLE -->
                <!-- //UR58120031 Extend Expiry Date add by Tana L. @08022016 -->
                <div class="row">
                    <div class="col-lg-12">
                        <h3 style="color:#666666;"> <i class=" icon-search"></i> Request Inquiry </h3>
                        <h4>SETUP / CANCEL / CLAIM / EXTEND EXPIRY DATE Request Inquiry</h4>
                    </div>
                </div>
                <hr />
                <!-- END PAGE TITLE -->
                
                <!-- ALERT FORM -->
                    <div class="modal fade" id="alertModal">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">                                     
                                    <h4 class="modal-title" id="alertHeader"></h4>
                                </div>
                                <div class="modal-body" id="alertContent">
                                </div>
                                <div class="modal-footer" id="alertFooter" style="text-align:right;">
                                </div>
                            </div><!-- /.modal-content -->
                        </div><!-- /.modal-dialog -->
                    </div><!-- /.modal -->                  
                    <!-- END ALERT FORM -->
<!-- ######################################################################## -->
                <!-- BEGIN INQUIRY FORM -->
                <div class="row">
                    <div class="col-lg-12">
                        <div class="box">
                            <header>
                                <div class="icons"><i class="icon-th-large"></i></div>
                                <h5>Inquiry</h5>
                                <div class="toolbar">
                                    <ul class="nav">
                                        <li>
                                            <div class="btn-group">
                                                <a class="accordion-toggle btn btn-xs minimize-box" data-toggle="collapse" href="#inquiryFormBox">
                                                    <i class="icon-chevron-up"></i>
                                                </a>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </header>
                            <div id="inquiryFormBox" class="accordion-body collapse in body">
                                <form action="#" class="form-horizontal" id="block-validate">
                                    <div class="form-group">
                                    	<div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">Tx Ref</label>
                                        <div class="col-lg-3">
                                            <input type="text" id="iTxRef" name="iTxRef" class="form-control" maxlength="20"/>
                                        </div>
                                        <label class="control-label col-lg-2">Request Date</label>
                                        <div class="col-lg-3">
                                        	<input type="text" id="iDtm" name="iDtm" class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                    	<div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">Vendor Tax ID</label>
                                        <div class="col-lg-3">
                                            <input type="text" id="iVendorTaxID" name="iVendorTaxID" class="form-control" pattern="[0-9]{13}" title="Number 13 Digits Only" maxlength="13"/>
                                        </div>
                                        <label class="control-label col-lg-2">Vendor Name</label>
                                        <div class="col-lg-3">
                                        	<input type="text" id="iVendorName" name="iVendorName" class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                    	<div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">Account No.</label>
                                        <div class="col-lg-3">
                                            <input type="text" id="iAccountNo" name="iAccountNo" class="form-control" />
                                        </div>
                                        <label class="control-label col-lg-2">&nbsp;</label>
                                        <div class="col-lg-3">&nbsp;</div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                    	<div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">Expire Date From</label>
                                        <div class="col-lg-3">
                                            <input type="text" id="iExpireDateFrom" name="iExpireDateFrom" class="form-control" />
                                        </div>
                                        <label class="control-label col-lg-2">To</label>
                                        <div class="col-lg-3">
                                        	<input type="text" id="iExpireDateTo" name="iExpireDateTo" class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    
                                    <div class="form-group">
                                    	<div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">LG No.</label>
                                        <div class="col-lg-3">
                                            <input type="text" id="iLgNo" name="iLgNo" class="form-control" pattern="[0-9]{14}" title="Number 14 Digits Only" maxlength="14"/>
                                        </div>
                                        <label class="control-label col-lg-2">Bond Type</label>
                                        <div class="col-lg-3">
	                                        <select name="iBondType" id="iBondType" class="form-control">
	                                            <option value="">----- ALL -----</option>
	                                            <option value="01">01 - หลักประกันซอง</option>
	                                            <option value="02">02 - หลักประกันสัญญา</option>
	                                            <option value="03">03 - หลักประกันจ่ายเงินล่วงหน้า</option>
	                                            <option value="04">04 - หลักประกันผลงาน</option>
	                                            <option value="05">05 - หลักประกันการจ่ายเงินล่วงหน้าก่อนการ</option>
	                                        </select>
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <!-- //UR58120031 Extend0007 add by Tana L. @08022016 -->
                                    <div class="form-group">
                                    	<div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">Proj No</label>
                                        <div class="col-lg-3">
                                            <input type="text" id="iProjNo" name="iProjNo" class="form-control" pattern="[0-9]{11}" title="Number 11 Digits Only" maxlength="11" />
                                        </div>
                                        <label class="control-label col-lg-2">Request Type</label>
                                        <div class="col-lg-3">
	                                        <select name="iRequestType" id="iRequestType" class="form-control">
	                                            <option value="">----- ALL -----</option>
	                                            <option value="0004">Setup</option>
	                                            <option value="0005">Cancel</option>
	                                            <option value="0006">Claim</option>
	                                            <option value="0007">Extend Expiry Date</option>
	                                        </select>
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                    	<div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">ALS Status</label>
                                        <div class="col-lg-3">
	                                        <select name="iAlsStatus" id="iAlsStatus" class="form-control">
	                                            <option value="">----- ALL -----</option>
	                                            <option value="01">Approved</option>
	                                            <option value="03">Rejected</option>
	                                            <option value="05">Claimed</option>
	                                            <option value="06">Canceled</option>
	                                            <option value="07">Extend Expiry Date</option>
	                                        </select>
                                        </div>
                                        <label class="control-label col-lg-2">&nbsp;</label>
                                        <div class="col-lg-3">&nbsp;</div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                    	<div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">Approval Status</label>
                                        <div class="col-lg-3">
	                                        <select name="iAppvStatus" id="iAppvStatus" class="form-control">
	                                            <option value="">----- ALL -----</option>
	                                            <option value="AP">Approved</option>
	                                            <option value="RJ">Rejected</option>
	                                        </select>
                                        </div>
                                        <label class="control-label col-lg-2">Approval User</label>
                                        <div class="col-lg-3">
                                            <input type="text" id="iApproveBy" name="iApproveBy" class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                    	<div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">Approval Dtm From</label>
                                        <div class="col-lg-3">
                                        	<input type="text" id="iApprovalDtmFrom" name="iApprovalDtmFrom" class="form-control" />
                                        </div>
                                        <label class="control-label col-lg-2">To</label>
                                        <div class="col-lg-3">
                                        	<input type="text" id="iApprovalDtmTo" name="iApprovalDtmTo" class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    
                                    <!-- add by Malinee T. UR58100048 @20151224  -->
                                    <div class="form-group">
									    <div class="col-lg-1">&nbsp;</div>
									    <label class="control-label col-lg-2">eGP Acknowledge Status</label>
									    <div class="col-lg-3">
									        <select name="iEgpStatus" id="iEgpStatus" class="form-control">
									           <option value="">----- ALL -----</option>
									            <option value="SC">Success</option>
									            <option value="NS">Fail</option>
									        </select>
									    </div>
									    <label class="control-label col-lg-2">&nbsp;</label>
									    <div class="col-lg-3">&nbsp;</div>
									    <div class="col-lg-1">&nbsp;</div>
									</div>
                                    <!-- end  -->
                                    
                                    <div class="form-group">
                                        <div class="form-actions no-margin-bottom col-lg-12" style="text-align:center;">
                                            <input id="inquiryBtn" name="inquiryBtn" type="submit" value="Search" class="btn btn-primary btn-sm" />
                                            <input id="exportBtn" name="exportBtn" type="submit" value="Export Excel" class="btn btn-success btn-sm" />
                                        </div>                                        
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                
                <!-- BEGIN REQUEST DETAIL -->
				<div class="col-lg-12">
					<div class="modal fade" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog" style="width:800px;">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
									<h4 class="modal-title" id="H2">Request Detail</h4>
								</div>
								<div class="modal-body">
									<form role="form" class="form-horizontal">
										<div class="alert alert-success">
											<div class="form-group">
												<label class="control-label col-lg-2">TxRef</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="txRef"></span></div>
												<label class="control-label col-lg-2">Dtm</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="dtm"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Proj No</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="projNo"></span></div>
												<label class="control-label col-lg-2">Vendor Tax ID</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="vendorTaxId"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Bond Type</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="bondType"></span></div>
												<label class="control-label col-lg-2">Seq No</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="seqNo"></span></div>
											</div>
										</div>
										<div class="alert alert-info">
											<div class="form-group">
												<div class="col-lg-12"><b>Latest Approval  Status</b></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Approve Status</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="approveStatus"></span></div>
												<label class="control-label col-lg-2">Approved Amount</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="approveAmt"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Reason</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="approveReason"></span></div>
												<label class="control-label col-lg-2">&nbsp;</label>    
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">&nbsp;</span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Approved Datetime</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="approveDtm"></span></div>
												<label class="control-label col-lg-2">Approved By</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="approveBy"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Bank Code</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="bankCode"></span></div>
												<label class="control-label col-lg-2">Bank Addr</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="bankAddr"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Branch Code</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="branchCode"></span></div>
												<label class="control-label col-lg-2">Branch Name</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="branchName"></span></div>
											</div>
										</div>
										<!-- BEGIN REQUEST DETAIL -->
										<div class="alert alert-warning">
											<div class="form-group">
												<div class="col-lg-12"><b>Transaction Info</b></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Txn Status</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="txnStatus"></span></div>
												<label class="control-label col-lg-2">Process Datetime</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="processDateTime"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Msg Code</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="msgCode"></span></div>
												<label class="control-label col-lg-2">Msg Desc</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="msgDesc"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">ALS Status</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="alsStatus"></span></div>
												<label class="control-label col-lg-2">ALS Msg</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="alsMsg"></span></div>
											</div>
										</div>
										<div class="alert alert-info" style="background-color: #EFEFEF;border-color: #CDCDCD;color: #666666;">
											<div class="form-group">
												<div class="col-lg-12"><b>Request Info</b></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Request Type</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="requestType"></span></div>
												<label class="control-label col-lg-2">&nbsp;</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">&nbsp;</span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">LG No.</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="lgNo"></span></div>
												<label class="control-label col-lg-2">ALS Account</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="alsAccount"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">TxRef</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="txRef2"></span></div>
												<label class="control-label col-lg-2">Request Dtm</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="dtm2"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Proj No</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="projNo2"></span></div>
												<label class="control-label col-lg-2">Proj Name</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="projName"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Vendor Tax ID</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="vendorTaxId2"></span></div>
												<label class="control-label col-lg-2">Vendor Name</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="vendorName"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Bond Type</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="bondType2"></span></div>
												<label class="control-label col-lg-2">Dept Code</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="deptCode"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Seq No</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="seqNo2"></span></div>
												<label class="control-label col-lg-2">Consider Desc</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="considerDesc"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Consider Money</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="considerMoney"></span></div>
												<label class="control-label col-lg-2">Guarantee Amt</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="guaranteeAmt"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Comp ID</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="compId"></span></div>
												<label class="control-label col-lg-2">User ID</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="userId"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">End Dte</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="endDate"></span></div>
												<label class="control-label col-lg-2">Start Dte</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="startDate"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Proj Amt</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="projAmt"></span></div>
												<label class="control-label col-lg-2">Proj Own Name</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="projOwnName"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Cost Center</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="costCenter"></span></div>
												<label class="control-label col-lg-2">Cost Center Name</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="costCenterName"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Contract No</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="contractNo"></span></div>
												<label class="control-label col-lg-2">Contract Date</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="contractDate"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Guarantee Price</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="guaranteePrice"></span></div>
												<label class="control-label col-lg-2">Guarantee Percent</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="guaranteePercent"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Advance Guarantee Price</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="advanceGuaranteePrice"></span></div>
												<label class="control-label col-lg-2">Advance Payment</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="advancePayment"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Works Guarantee Price</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="worksGuaranteePrice"></span></div>
												<label class="control-label col-lg-2">Works Guarantee Percent</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="worksGuaranteePercent"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Collection Phase</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="collectionPhase"></span></div>
												<label class="control-label col-lg-2">Document No</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="documentNo"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Document Date</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="documentDate"></span></div>
												<label class="control-label col-lg-2">Expire Date</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="expireDate"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">Extend Date</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="extendDate"></span></div>
												<label class="control-label col-lg-2">&nbsp;</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">&nbsp;</span></div>
											</div>
										</div>
                                                                                
										<div class="alert alert-warning">
											<div class="form-group">
												<div class="col-lg-12"><b>Review  Log</b></div>
											</div>
                                                                                        
                                                 <div class="form-group" id="reviewLog">
                                                     <div class="form-group">
                                                             <label class="control-label col-lg-1">Status</label>
                                                             <div class="col-lg-1" style="padding-top:8px;"><span class="form-control-static">Approved</span></div>
                                                             <label class="control-label col-lg-1">Account</label>
                                                             <div class="col-lg-2" style="padding-top:8px;"><span class="form-control-static">XXXXXXXXXXXX</span></div>
                                                             <label class="control-label col-lg-1">Reason</label>
                                                             <div class="col-lg-2" style="padding-top:8px;"><span class="form-control-static">&nbsp;</span></div>
                                                             <label class="control-label col-lg-1">Dtm<br/>User</label>
                                                             <div class="col-lg-3" style="padding-top:8px;"><span class="form-control-static">12/05/2015 13:20:01<br/>sXXXXX</span></div>
                                                     </div>
                                                 </div>
										</div>
										
										<div class="alert alert-warning">
											<div class="form-group">
												<div class="col-lg-12"><b>Approval  Log</b></div>
											</div>
                                                                                        
                                                  <div class="form-group" id="approvalLog">
                                                      <div class="form-group">
                                                              <label class="control-label col-lg-1">Status</label>
                                                              <div class="col-lg-1" style="padding-top:8px;"><span class="form-control-static">Approved</span></div>
                                                              <label class="control-label col-lg-1">Account</label>
                                                              <div class="col-lg-2" style="padding-top:8px;"><span class="form-control-static">XXXXXXXXXXXX</span></div>
                                                              <label class="control-label col-lg-1">Reason</label>
                                                              <div class="col-lg-2" style="padding-top:8px;"><span class="form-control-static">&nbsp;</span></div>
                                                              <label class="control-label col-lg-1">Dtm<br/>User</label>
                                                              <div class="col-lg-3" style="padding-top:8px;"><span class="form-control-static">12/05/2015 13:20:01<br/>sXXXXX</span></div>
                                                      </div>
                                                  </div>
										</div>
										
										<!-- //UR58120031 EED Review Log add by Tana L. @12022016 -->
										<div class="alert alert-warning" id="eedReviewBlog">
											<div class="form-group">
												<div class="col-lg-12"><b>Extend Expiry Date Review Log</b></div>
											</div>
                                                                                        
                                                  <div class="form-group" id="eedReviewLog">
                                                      <div class="form-group">
                                                              <label class="control-label col-lg-1">Status<br/>Reason</label>
                                                              <div class="col-lg-1" style="padding-top:8px;"><span class="form-control-static">Approved<br/>&nbsp;</span></div>
                                                              <label class="control-label col-lg-1">Account</label>
                                                              <div class="col-lg-2" style="padding-top:8px;"><span class="form-control-static">XXXXXXXXXXXX</span></div>
                                                              <label class="control-label col-lg-1">Old<br/>New</label>
                                                              <div class="col-lg-2" style="padding-top:8px;"><span class="form-control-static">DD-MM-YYYY<br/>DD-MM-YYYY</span></div>
                                                              <label class="control-label col-lg-1">Dtm<br/>User</label>
                                                              <div class="col-lg-3" style="padding-top:8px;"><span class="form-control-static">12/05/2015 13:20:01<br/>sXXXXX</span></div>
                                                      </div>
                                                      
                                                  </div>
										</div>
										
										<!-- //UR58120031 EED Approval Log add by Tana L. @12022016 -->
										<div class="alert alert-warning" id="eedApprovalBlog">
											<div class="form-group">
												<div class="col-lg-12"><b>Extend Expiry Date Approval Log</b></div>
											</div>
                                                                                        
                                                  <div class="form-group" id="eedApprovalLog">
                                                      <div class="form-group">
                                                               <label class="control-label col-lg-1">Status<br/>Reason</label>
                                                               <div class="col-lg-1" style="padding-top:8px;"><span class="form-control-static">Approved<br/>&nbsp;</span></div>
                                                               <label class="control-label col-lg-1">Account</label>
                                                               <div class="col-lg-2" style="padding-top:8px;"><span class="form-control-static">XXXXXXXXXXXX</span></div>
                                                               <label class="control-label col-lg-1">Old<br/>New</label>
                                                               <div class="col-lg-2" style="padding-top:8px;"><span class="form-control-static">DD-MM-YYYY<br/>DD-MM-YYYY</span></div>
                                                               <label class="control-label col-lg-1">Dtm<br/>User</label>
                                                               <div class="col-lg-3" style="padding-top:8px;"><span class="form-control-static">12/05/2015 13:20:01<br/>sXXXXX</span></div>
                                                      </div>                                                                                                                                     
                                                  </div>
										</div>
										
										<div class="alert alert-warning">
											<div class="form-group">
												<div class="col-lg-12"><b>Log Web Service</b></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">XML Input from WSG</label>
												<div class="col-lg-4" style="padding-top:8px;"><textarea class="form-control" rows="5" id="logXmlInput"></textarea></div>
												<label class="control-label col-lg-2">Input Datetime</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="logInputDatetime"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">XML Response to WSG</label>
												<div class="col-lg-4" style="padding-top:8px;"><textarea class="form-control" rows="5" id="logXmlResponseToEbxml"></textarea></div>
												<label class="control-label col-lg-2">Response Datetime</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="logResponseToEbxmlDatetime"></span></div>
											</div>									
											<div class="form-group">
												<label class="control-label col-lg-2">XML Output to WSG</label>
												<div class="col-lg-4" style="padding-top:8px;"><textarea class="form-control" rows="5" id="logXmlOutput"></textarea></div>
												<label class="control-label col-lg-2">Output Datetime</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="logOutputDatetime"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">XML Response from WSG</label>
												<div class="col-lg-4" style="padding-top:8px;"><textarea class="form-control" rows="5" id="logXmlResponseFromEbxml"></textarea></div>
												<label class="control-label col-lg-2">Response Datetime</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="logResponseFromEbxmlDatetime"></span></div>
											</div>
											<div class="form-group">
												<label class="control-label col-lg-2">ALS Mode</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="logAlsOnline"></span></div>
												<!-- //UR59040034 Add eGP Pending Review & Resend Response function -->
												<label class="control-label col-lg-2">Resend Count</label>
												<div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="resendCount"></div>
											</div>
										</div>
										<!-- add by Malinee T. UR58100048 @20151224  -->
										<div class="alert alert-warning">
											<div class="form-group">
										          <div class="col-lg-12"><b>eGP Acknowledge</b></div>
										    </div>
										    <div class="form-group">
										          <label class="control-label col-lg-2">Ack Status</label>
										          <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="egpAckStatus"></span></div>
										          <label class="control-label col-lg-2">Ack Datetime</label>
										          <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="egpAckDtm"></span></div>
										    </div>
										    <div class="form-group">
										          <label class="control-label col-lg-2">Tranx Id</label>
										          <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="egpAckTranxId"></span></div>
										          <label class="control-label col-lg-2">Ack Code</label>
										          <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="egpAckCode"></span></div>
										    </div>
										    <div class="form-group">
										         <label class="control-label col-lg-2">Ack Message</label>
										         <div class="col-lg-4" style="padding-top:8px;"><textarea class="form-control" rows="5" id="egpAckMsg"></textarea></div>
										         <label class="control-label col-lg-2">&nbsp;</label>
										         <div class="col-lg-4" style="padding-top:8px;">&nbsp;</div>
										    </div>
										</div>
										<!-- end of add by Malinee T. UR58100048 @20151224 -->
										
									</form>
								</div>
								<!-- END REQUEST DETAIL -->
								<div class="modal-footer">
									<!-- //UR59040034 Add eGP Pending Review & Resend Response function -->
									<input type="hidden" id="gpGuaranteeId" name="gpGuaranteeId" value=""/>
									<input id="resendBtn" name="resendBtn" type="button" value="Resend XML Output" class="btn btn-default btn-sm" />
									<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">Close</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- END REQUEST DETAIL= -->
                
                <hr />
                <!-- END INQUIRY FORM -->

<!-- ######################################################################## -->

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <b>Result List</b>
                        </div>
                        <div class="panel-body">
                            <ul class="nav nav-tabs">
                                <li id="dataListTabBtn" class="active"><a href="#dataListTab" data-toggle="tab">Data List</a>
                                </li>
                            </ul>

                            <div class="tab-content">
                                <div class="tab-pane fade in active" id="dataListTab">
                                    
                                    <!-- BEGIN INQUIRY RESULT -->
                                    <div id="inquiryLoading" style="text-align:center;display:none;"><div class="progress progress-striped active"><div class="progress-bar" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;"></div></div></div>
                                    <div class="row" id="inquiryResultPanel">
                                        <div class="col-lg-12">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <b>Inquiry Result</b>
                                                </div>
                                                <!-- //UR58120031 Inquiry Fields add by Tana L. @18022016 -->
                                                <div class="panel-body">
                                                    <div class="table-responsive">
                                                        <table class="table table-striped table-bordered table-hover" id="dataTables-result">
                                                            <thead>
                                                                <tr style="white-space:nowrap">
                                                                    <th style="text-align:center;font-size:small;">Detail</th>
                                                                    <th style="text-align:center;font-size:small;">Parent Detail</th>
                                                                    <th style="text-align:center;font-size:small;">TxRef</th>
                                                                    <th style="text-align:center;font-size:small;">Vendor Name</th>
                                                                    <th style="text-align:center;font-size:small;">Account No.</th>
                                                                    <th style="text-align:center;font-size:small;">Guarantee Amt</th>
                                                                    <th style="text-align:center;font-size:small;">Msg Code</th>
                                                                    <th style="text-align:center;font-size:small;">Expire Date</th>
                                                                    <th style="text-align:center;font-size:small;">Extend Date</th>
                                                                    <th style="text-align:center;font-size:small;">Request Dtm</th>
                                                                    <th style="text-align:center;font-size:small;">Proj No.</th>
                                                                    <th style="text-align:center;font-size:small;">Proj Name</th>
                                                                    <th style="text-align:center;font-size:small;">Start Date</th>
                                                                    <th style="text-align:center;font-size:small;">End Date</th>
                                                                    <th style="text-align:center;font-size:small;">Bond Type</th>
                                                                    <th style="text-align:center;font-size:small;">ALS Msg</th>
                                                                    
                                                                    <th style="text-align:center;font-size:small;">Request Type</th>
                                                                    <th style="text-align:center;font-size:small;">LG No.</th>
                                                                    <th style="text-align:center;font-size:small;">Vendor Tax ID</th>

                                                                    <th style="text-align:center;font-size:small;">Review Status</th>
                                                                    <th style="text-align:center;font-size:small;">Review Reason</th>
                                                                    <th style="text-align:center;font-size:small;">Review By</th>
                                                                    <th style="text-align:center;font-size:small;">Review Dtm</th>
                                                                    <th style="text-align:center;font-size:small;">Approve Status</th>
                                                                    <th style="text-align:center;font-size:small;">Approve Reason</th>
                                                                    <th style="text-align:center;font-size:small;">Approve By</th>
                                                                    <th style="text-align:center;font-size:small;">Approve Dtm</th>                                                                    
                                                                    <th style="text-align:center;font-size:small;">OC Code</th>
                                                                    <th style="text-align:center;font-size:small;">Branch</th>
                                                                    
                                                                    <!-- add by Malinee T. UR58100048 @20151224  -->
                                                                    <th style="text-align:center;font-size:small;">eGP Ack Status</th>
	      															<th style="text-align:center;font-size:small;">eGP Ack Message</th>
	      															
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                            </tbody>
                                                            <tfoot></tfoot>
                                                        </table>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- END INQUIRY RESULT -->
                                    
                                </div>
                                
                            </div>
                        </div>
                    </div>



            </div>
                
                
                
<!-- ######################################################################## -->
                
            </div>

        </div>
        <!--END PAGE CONTENT -->
        
        
    

    <!--END MAIN WRAPPER -->
    
<!-- ######################################################################## -->

    <!-- FOOTER -->
    <div id="footer">
        <p>&copy;  Siam Commercial Bank Pcl. &nbsp;2015 &nbsp;</p>
    </div>
    <!--END FOOTER -->

<!-- ######################################################################## -->
    
     <!-- GLOBAL SCRIPTS -->
    <script src="assets/plugins/jquery-2.0.3.min.js"></script>
     <script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/plugins/modernizr-2.6.2-respond-1.1.0.min.js"></script>
    <!-- END GLOBAL SCRIPTS -->

    <!-- PAGE LEVEL SCRIPTS -->

    <!-- form validation -->
    <script src="assets/plugins/validationengine/js/jquery.validationEngine.js"></script>
    <script src="assets/plugins/validationengine/js/languages/jquery.validationEngine-en.js"></script>
    <script src="assets/plugins/jquery-validation-1.11.1/dist/jquery.validate.min.js"></script>
    <script src="assets/js/validationInit.js"></script>
    
    <!-- data table -->
    <script src="assets/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="assets/plugins/dataTables/dataTables.bootstrap.js"></script>
    
    <script src="assets/plugins/jasny/js/bootstrap-fileupload.js"></script>
    
    <!-- date picker -->
    <script src="assets/plugins/datepicker/js/bootstrap-datepicker.js"></script>
    <link rel="stylesheet" href="assets/plugins/datepicker/css/datepicker.css" />
    <script src="assets/js/jquery.fileDownload.js"></script>
    <script src="assets/js/requestInquiry.js"></script>
     <!--END PAGE LEVEL SCRIPTS -->

<!-- ######################################################################## -->

</body>

    <!-- END BODY -->
</html>

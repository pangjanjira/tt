<!DOCTYPE html>
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

            <!-- HEADER SECTION -->
			<%@ include file ="header.jsp" %>
            <!-- END HEADER SECTION -->

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
                    <div class="row">
                        <div class="col-lg-12">
                            <h3 style="color:#666666;"> <i class="icon-list"></i> Account ALS </h3>
                            <h4>Account ALS Management</h4>
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
                                    <form action="" class="form-horizontal" id="inquiryForm">
                                        
                                        <div class="form-group">
                                            <div class="col-lg-3">&nbsp;</div>
                                            <label class="control-label col-lg-2">Tax ID</label>
                                            <div class="col-lg-3">
                                                <input type="text" id="iTaxID" name="iTaxID" class="form-control" pattern="[0-9]{13}" title="Number 13 Digits Only" maxlength="13"/>
                                            </div>
                                            <div class="col-lg-1">&nbsp;</div>
                                        </div> 
                                        <div class="form-group">
                                            <div class="col-lg-3">&nbsp;</div>
                                            <label class="control-label col-lg-2">Account No.</label>
                                            <div class="col-lg-3">
                                                    <input type="text" id="iAccountNo" name="iAccountNo" class="form-control" pattern="[0-9]{14}" title="Number 14 Digits Only" maxlength="14"/>
                                            </div>
                                            <div class="col-lg-1">&nbsp;</div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-lg-3">&nbsp;</div>
                                            <label class="control-label col-lg-2">OC Code</label>
                                            <div class="col-lg-3">
                                                    <input type="text" id="iOCCode" name="iOCCode" class="form-control" pattern="[0-9]{4}" title="Number 4 Digits Only" maxlength="4"/>
                                            </div>
                                            <div class="col-lg-1">&nbsp;</div>
                                        </div>
                                        
                                        <div class="form-actions no-margin-bottom col-lg-12" style="text-align:center;">
                                            <input id="inquiryBtn" name="btnInquiry" type="submit" value="Search" class="btn btn-primary btn-sm" />
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
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
                                <li id="maintainFormTabBtn"><a href="#maintainFormTab" onclick="resetForm();" data-toggle="tab">Account ALS Form</a>
                                </li>
                            </ul>

                            <div class="tab-content">
                                <div class="tab-pane fade in active" id="dataListTab">

                                    <!-- BEGIN INQUIRY RESULT -->
                                    <div id="inquiryLoading" style="text-align:center;display:none;"><div class="progress progress-striped active"><div class="progress-bar" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;"></div></div></div>
                                    <div class="row" id="inquiryResultPanel" style="">
                                        <div class="col-lg-12">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <b>Inquiry Result</b>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="table-responsive">
                                                        <table class="table table-striped table-bordered table-hover" id="dataTables-result">
                                                            <thead>
                                                                <tr style="white-space:nowrap">
                                                                    <th style="text-align:center;">Detail</th>
                                                                    <th style="text-align:center;">Tax ID</th>
                                                                    <th style="text-align:center;">Account No.</th>
                                                                    <th style="text-align:center;">Account Name</th>
                                                                    <th style="text-align:center;">Line amount</th>
                                                                    <th style="text-align:center;">Credit amount</th>
                                                                    <th style="text-align:center;">OC Code</th>
                                                                    <th style="text-align:center;">Branch</th>
                                                                    <th style="text-align:center;">Action</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
<!--                                                            <tr style="white-space:nowrap">
                                                                    <td style="text-align:center;">xxxxxxxxxx</td>
                                                                    <td style="text-align:center;">01</td>
                                                                    <td style="text-align:left;">xxxxxx xxxxx xxxxxx</td>
                                                                    <td style="text-align:center;"><a href="javascript:;" onclick="updateItem('xxxxxxxxxx');"><i class="icon-edit"></i></a> &nbsp;&nbsp; <a href="javascript:;" onclick="deleteItem('xxxxxxxxxx');"><i class="icon-trash"></i></a></td>
                                                                </tr> -->
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
                                
                                <div class="tab-pane fade" id="maintainFormTab">
                                <div id="inquiryLoadingCreate" style="text-align:center;display:none;"><div class="progress progress-striped active"><div class="progress-bar" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;"></div></div></div>
                                   
                                    <div id="maintainFormTitle"></div>
                                    <form class="form-horizontal" id="createForm">
                                        <input type='hidden' id='mode' name="mode" value=''/>
                                        <div class="form-group">
                                            <div class="row">
                                                
                                            </div>
                                            <div class="row">
                                                <div class="col-xs-6">
                                                        <div class="col-xs-6">
                                                            <label>Account <span class="text-danger" >*</span></label>
                                                            <input type="text" id="sAccountNo" name="sAccountNo" class="form-control " required="" pattern="[0-9]{14}" title="Number 14 Digits Only" maxlength="14" />
                                                        </div>
                                                </div>
                                                <div class="col-xs-6">
                                                    <div class="col-xs-6">
                                                        <label>Confirm Account <span class="text-danger" >*</span></label>
                                                        <input type="text" id="sAccountNoConfirm" name="sAccountNoConfirm" class="form-control " required="" pattern="[0-9]{14}" title="Number 14 Digits Only" maxlength="14"   />
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-xs-6">
                                                    <div class="col-xs-6">
                                                        <label>Tax ID <span class="text-danger" >*</span></label>
                                                        <input type="text" id="sTaxID" name="sTaxID" class="form-control " required=""  pattern="[0-9]{13}" title="Number 13 Digits Only" maxlength="13" />
                                                      </div>      
                                                </div>
                                                <div class="col-xs-6">
                                                    <div class="col-xs-6">
                                                        <label>Confirm Tax ID <span class="text-danger" >*</span></label>
                                                        <input type="text" id="sTaxIDConfirm" name="sTaxIDConfirm" class="form-control " required=""  pattern="[0-9]{13}" title="Number 13 Digits Only" maxlength="13" />
                                                    </div> 
                                                </div>
                                            </div>
                                            <input type="hidden" id="iCreateBy" name="iCreateBy" value=""/>
                                            <input type="hidden" id="iCreateDtm" name="iCreateDtm" value=""/>
                                            <!--<input type="hidden" id="iCreateDatetime" name="iCreateDatetime" value=""/>-->
                                            <input type="hidden" id="iUpdateBy" name="iUpdateBy" value=""/>
                                            <input type="hidden" id="iUpdateDatetime" name="iUpdateDatetime" value=""/>
                                            <input type="hidden" id="sAccountName" name="sAccountName" value=""/>
                                            <input type="hidden" id="sPurpose" name="sPurpose" value=""/>
                                            <input type="hidden" id="sSubPurpose" name="sSubPurpose" value=""/>
                                            <input type="hidden" id="sBank" name="sBank" value=""/>
                                            <input type="hidden" id="sLineAmtinfo" name="sLineAmtinfo" value=""/>
                                            <input type="hidden" id="sAvaliableAmtinfo" name="sAvaliableAmtinfo" value="" />
                                           <!--  <input type="hidden" id="sOCCodeinfo" name="sOCCodeinfo" value="" />
                                            <input type="hidden" id="sBranchinfo" name="sBranchinfo" value="" /> --> 
                                        </div>
                                        <div id="createUpdateInfo">
                                            <div class="form-group">
                                                <div class="col-lg-1">&nbsp;</div>
                                                <label class="control-label col-lg-2">OCCode</label>
                                                <div  class="col-lg-3" style="padding-top: 8px;"><span class="form-control-static" id="sOCCodeinfo"></span></span></div>
                                                <label class="control-label col-lg-2">Branch</label>
                                                <div  class="col-lg-3" style="padding-top: 8px;"><span class="form-control-static" id="sBranchinfo"></span></span></div>
                                                <div class="col-lg-1">&nbsp;</div>
                                                
                                            </div>
                                            <div class="form-group">
                                                <div class="col-lg-1">&nbsp;</div>
                                                <label class="control-label col-lg-2">Create By</label>
                                                <div id="createByinfo" class="col-lg-3" style="padding-top: 8px;"></div>
                                                <label class="control-label col-lg-2">Create Datetime</label>
                                                <div id="createDatetime" class="col-lg-3" style="padding-top: 8px;"><span id="createDatetime"></span></div>
                                                <div class="col-lg-1">&nbsp;</div>
                                                <input type="hidden" id="updateId" value=""/>
                                            </div>
                                            <div class="form-group">
                                                <div class="col-lg-1">&nbsp;</div>
                                                <label class="control-label col-lg-2">Update By</label>
                                                <div id="updateByinfo" class="col-lg-3" style="padding-top: 8px;"></div>
                                                <label class="control-label col-lg-2">Update Datetime</label>
                                                <div id="updateDatetime" class="col-lg-3" style="padding-top: 8px;"></div>
                                                <div class="col-lg-1">&nbsp;</div>
                                            </div>
                                        </div>
                                        
                                        
                                        <div class="form-actions no-margin-bottom col-lg-12" style="text-align:center;">
                                            <input id="submitBtn" name="submitBtn" type="submit" value="Submit" class="btn btn-primary btn-sm" />
                                        </div>
                                    </form>
                                    
                                </div>
                                
                            </div>
                        </div>
                    </div>

                </div>

                <!-- ######################################################################## -->
                 <!-- BEGIN REQUEST DETAIL & APPROVAL FORM -->
                    <div class="col-lg-12">
                        <div class="modal fade" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog" style="width:800px;">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        <h4 class="modal-title" id="H2">Request Detail</h4>
                                    </div>
                                    <div class="modal-body">
                                        <form role="form" class="form-horizontal" id="approveForm">
                                            <input type="hidden" id="iUserName" name="iUserName" value=""/>
                                            <!-- BEGIN REQUEST DETAIL -->
                                            
                                            <div class="alert alert-info" style="background-color: #EFEFEF;border-color: #CDCDCD;color: #666666;">
                                                <div class="form-group">
                                                    <div class="col-lg-12"><b>Request Info</b></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Account</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="accountNo"></span></div>
                                                    <label class="control-label col-lg-2">Tax ID</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="taxID"></span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Account Name</label>
                                                    <div class="col-lg-10" style="padding-top:8px;"><span class="form-control-static" id="accountName"></span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">OC Code</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="ocCode"></span></div>
                                                    <label class="control-label col-lg-2">Branch</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="branch"></span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Purpose</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="purpose"></span></div>
                                                    <label class="control-label col-lg-2">SubPurpose</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="subPurpose"></span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Line amount</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="lineAmount"></span></div>
                                                    <label class="control-label col-lg-2">Credit amount</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="creditAmount"></span></div>
                                                </div>
                                                <br/><br/><br/><br/>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Create By</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="createBy"></span></div>
                                                    <label class="control-label col-lg-2">Create Datetime</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="createDateTime"></span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Update By</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="updateBy"></span></div>
                                                    <label class="control-label col-lg-2">Update Datetime</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static" id="updateDateTime"></span></div>
                                                </div>
                                                
                                            </div>
                                            
                                        </form>
                                    </div>
                                    <!-- END REQUEST DETAIL -->
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default btn-sm" data-dismiss="modal">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- END REQUEST DETAIL & APPROVAL FORM -->
                

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
        <!--<script>-->
         <script src="assets/js/accountALS.js"></script>
        <!--</script>-->
        <!--END PAGE LEVEL SCRIPTS -->

        <!-- ######################################################################## -->

    </body>

    <!-- END BODY -->
</html>

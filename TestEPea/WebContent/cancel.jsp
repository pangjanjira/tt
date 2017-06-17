<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->

    <!-- BEGIN HEAD -->
    <head>
        <meta charset="UTF-8" />
        <title>SCB PEA-Guarantee</title>
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
            div.scroll{
                width: 100%;
                height: 95%;
                overflow: scroll;
            }
            .model{
                display: none;
                position: fixed;
                z-index: 1;
                padding-top: 100px;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgb(0,0,0);
                background-color: rgba(0,0,0,0.4);
            }
            .model-content{
                background-color: #fefefe;
                margin: auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
            }
            .close{
                color: #aaaaaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
            }
            .colse:hover,
            .close:focus{
                color: #000;
                text-decoration: none;
                cursor: pointer;
            }
            .datatable-scroll {
                overflow-x: auto;
                overflow-y: visible;
            }
            --> 
        </style>
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

            <div id="content">

                <div class="inner">

                    <!--   -->

                    <!-- BEGIN PAGE TITLE -->
                    <div class="row">
                        <div class="col-lg-12">
                            <h3 style="color:#666666;">Cancel LG</h3>
                        </div>
                    </div>
                    <hr />
                    <!-- END PAGE TITLE -->
                    <!-- ######################################################################## -->
                    <!-- BEGIN INQUIRY FORM -->


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
                                        <form role="form" class="form-horizontal">
                                            <!-- BEGIN APPROVAL FORM -->
                                            <div class="alert alert-info">
                                                <div class="form-group">
                                                    <label class="control-label col-lg-3">Approve/Reject</label>
                                                    <div class="col-lg-9">
                                                        <select name="iAppvStatus" id="iAppvStatus" class="form-control">
                                                            <option value="RJ">Rejected</option>
                                                            <option value="AP">Approved</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-3">Reason</label>
                                                    <div class="col-lg-9">
                                                        <textarea id="iReason" name="iReason" class="form-control" rows="3"></textarea>
                                                    </div>
                                                </div>
                                                <div class="form-actions no-margin-bottom col-lg-12" style="text-align:center;">
                                                    <input id="appvBtn" name="appvBtn" type="submit" value="Submit" class="btn btn-info btn-sm" />
                                                </div>
                                            </div>
                                            <!-- END APPROVAL FORM -->
                                            <!-- BEGIN REQUEST DETAIL -->
                                            <div class="alert alert-warning">
                                                <div class="form-group">
                                                    <div class="col-lg-12"><b>Transaction Info</b></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Txn Status</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">Rejected</span></div>
                                                    <label class="control-label col-lg-2">Process Datetime</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">12/05/2015 13:21:01</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Msg Code</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">01</span></div>
                                                    <label class="control-label col-lg-2">Msg Desc</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">xxx xxxx</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">ALS Status</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">Rejected</span></div>
                                                    <label class="control-label col-lg-2">ALS Msg</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">AM0001 - XXX XXX XXX</span></div>
                                                </div>
                                            </div>
                                            <div class="alert alert-info" style="background-color: #EFEFEF;border-color: #CDCDCD;color: #666666;">
                                                <div class="form-group">
                                                    <div class="col-lg-12"><b>Request Info</b></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">LG No.</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">20150528000100</span></div>
                                                    <label class="control-label col-lg-2">ALS Account</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">XXXXXXXXXX</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">TxRef</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">1114000000000000001</span></div>
                                                    <label class="control-label col-lg-2">Dtm</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">04/10/2014 00:00:01</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Proj No</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">22200000001</span></div>
                                                    <label class="control-label col-lg-2">Proj Name</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">ประกวดราคาซื้องานสื่อเผยแพร่...(ทดสอบ LG Online)</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Vendor Tax ID</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">7205654000000</span></div>
                                                    <label class="control-label col-lg-2">Vendor Name</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">บริษัท ทดสอบ จำกัด</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Bond Type</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">01 - xxx xxx</span></div>
                                                    <label class="control-label col-lg-2">Dept Code</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">0304</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Seq No</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">1</span></div>
                                                    <label class="control-label col-lg-2">Consider Desc</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">รายการพิจารณาประเภทรวม</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Consider Money</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">0.00</span></div>
                                                    <label class="control-label col-lg-2">Guarantee Amt</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">1,001.00</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Comp ID</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">014</span></div>
                                                    <label class="control-label col-lg-2">User ID</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">014</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">End Dte</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">2015-08-15</span></div>
                                                    <label class="control-label col-lg-2">Start Dte</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">2015-05-15</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Proj Amt</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">2,000,000.00</span></div>
                                                    <label class="control-label col-lg-2">Proj Own Name</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">กรมบัญชีกลาง</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Cost Center</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">0300400000</span></div>
                                                    <label class="control-label col-lg-2">Cost Center Name</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">กรมบัญชีกลาง</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Contract No</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">กค. 0304 234/2558</span></div>
                                                    <label class="control-label col-lg-2">Contract Date</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">2014-09-27</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Guarantee Price</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">2,000,000.00</span></div>
                                                    <label class="control-label col-lg-2">Guarantee Percent</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">5.00</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Advance Guarantee Price</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">0.00</span></div>
                                                    <label class="control-label col-lg-2">Advance Payment</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">0.00</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Works Guarantee Price</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">0.00</span></div>
                                                    <label class="control-label col-lg-2">Works Guarantee Percent</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">0.00</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Collection Phase</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">1,2</span></div>
                                                    <label class="control-label col-lg-2">Document No</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">กค. 0304 23/2557</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Document Date</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">2013-07-30</span></div>
                                                    <label class="control-label col-lg-2">Expire Date</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">2013-10-30</span></div>
                                                </div>
                                            </div>
                                            <div class="alert alert-warning">
                                                <div class="form-group">
                                                    <div class="col-lg-12"><b>Approval  Log</b></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-1">Status</label>
                                                    <div class="col-lg-1" style="padding-top:8px;"><span class="form-control-static">Rejected</span></div>
                                                    <label class="control-label col-lg-1">Reason</label>
                                                    <div class="col-lg-3" style="padding-top:8px;"><span class="form-control-static">เอกสารไม่ครบ</span></div>
                                                    <label class="control-label col-lg-1">Dtm</label>
                                                    <div class="col-lg-3" style="padding-top:8px;"><span class="form-control-static">12/05/2015 10:10:01</span></div>
                                                    <label class="control-label col-lg-1">User</label>
                                                    <div class="col-lg-1" style="padding-top:8px;"><span class="form-control-static">sXXXXX</span></div>
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

                    <hr />
                    <!-- END INQUIRY FORM -->

                    <!-- ######################################################################## -->


                    <div class="panel-body">

                        <div class="tab-content">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <b>Cancel</b>
                                </div>
                                <p> </p>
                                <div class="form-group">
                                    <div class="col-lg-1">&nbsp;</div>
                                    <label class="control-label col-lg-2">Cancel Date<font color="red" size="4"> *</font></label>
                                    <div class="col-lg-3">
                                        <input type="date" id="CancelDate" name="Cancel Date" class="form-control" />
                                    </div>
                                    <font id="blank1">.</font>
                                    <div class="col-lg-1">&nbsp;</div>
                                </div>
                                <div class="form-group">
                                    <div class="col-lg-1">&nbsp;</div>
                                    <label class="control-label col-lg-2">Action<font color="red" size="4"> *</font></label>
                                    <div class="col-lg-3">
                                        <select name="Account No." id="Account" class="form-control">
                                            <option ></option>
                                            <option value="All" >All</option>
                                            <option value="Extend" >Extend</option>
                                            <option value="Cancel" >Cancel</option>
                                            <option value="Claim" >Claim</option>
                                        </select>
                                    </div>
                                    <font id="blank2">.</font>
                                    <div class="col-lg-1">&nbsp;</div>
                                </div>
                                <div class="form-group">
                                    <div class="col-lg-1">&nbsp;</div>
                                    <label class="control-label col-lg-2">LG No.</label>
                                    <div class="col-lg-3">
                                        <input type="text" id="Action" name="Action" class="form-control" />
                                    </div>
                                    <font>.</font>
                                    <div class="col-lg-1">&nbsp;</div>
                                </div>
                                <div class="form-actions no-margin-bottom col-lg-12" style="text-align:center;">
                                    <button onclick="myFunction()">Inquiry</button>
                                </div>
                            </div>
                            <div class="tab-pane fade in active" id="dataListTab">
                                <!-- BEGIN INQUIRY RESULT -->
                                <div id="inquiryLoading" style="text-align:center;display:none;"><div class="progress progress-striped active"><div class="progress-bar" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;"></div></div></div>
                                <div class="row" id="inquiryResultPanel">
                                    <div class="col-lg-12">
                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <div class="scroll">
                                                    <div class="table-responsive">
                                                        <table class="table table-striped table-bordered table-hover">
                                                            <thead>
                                                                <tr style="white-space:nowrap">
                                                                    <th style="text-align:center;font-size:small;">File Name</th>
                                                                    <th style="text-align:center;font-size:small;">LG No.</th>
                                                                    <th style="text-align:center;font-size:small;">จำนวนเงินค้ำประกัน</th>
                                                                    <th style="text-align:center;font-size:small;">หมายเลขผู้ใช้ไฟฟ้า</th>
                                                                    <th style="text-align:center;font-size:small;">เลขที่หลักประกัน</th>
                                                                    <th style="text-align:center;font-size:small;">Tax ID</th>
                                                                    <th style="text-align:center;font-size:small;">หมายเลขคู่ค้า</th>
                                                                    <th style="text-align:center;font-size:small;">ลงวันที่</th>
                                                                    <th style="text-align:center;font-size:small;">วันที่เริ่มต้น</th>
                                                                    <th style="text-align:center;font-size:small;">วันที่สิ้นสุด</th>
                                                                    <th style="text-align:center;font-size:small;">ข้อความเพิ่มเติม</th>
                                                                    <th style="text-align:center;font-size:small;">Action</th>
                                                                    <th style="text-align:center;font-size:small;">Result</th>
                                                                    <th style="text-align:center;font-size:small;">ALS code</th>
                                                                    <th style="text-align:center;font-size:small;">ALS message</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <tr>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:left;font-size:smaller;"></td>
                                                                    <td style="text-align:left;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:right;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- END INQUIRY RESULT -->

                            </div>

                        </div>
                    </div>

                    <div id="MyModel" class="model">
                        <div class="model-content">
                            <p id="Alert"></p>
                        </div>
                    </div>
                    <script>
                        function myFunction() {
                            var model = document.getElementById("MyModel")
                            var action = document.getElementById("Action").value;
                            var cancelDate = document.getElementById("CancelDate").value
                            if (action == "" && cancelDate == "") {
                                var str = "Required";
                                var result = str.fontcolor("red");
                                document.getElementById("blank1").innerHTML = result;
                                document.getElementById("blank2").innerHTML = result;
                            } else if (action != "" || cancelDate != "") {
                                var str = ".";
                                var result = str.fontcolor("white");
                                document.getElementById("blank1").innerHTML = result;
                                document.getElementById("blank2").innerHTML = result;
                            }
                        }
                    </script>
                </div>



                <!-- ######################################################################## -->

            </div>
        </div>
        <!-- ######################################################################## -->

        <!-- FOOTER -->
        <div id="footer">
            <p>&copy;  Siam Commercial Bank Pcl. &nbsp;2017 &nbsp;</p>
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
        <script>
            var datefield=document.createElement("input")
            datefield.setAttribute("type", "date")
            if (datefield.type!="date"){ //if browser doesn't support input type="date", load files for jQuery UI Date Picker
                document.write('<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />\n')
                document.write('<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"><\/script>\n')
                document.write('<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"><\/script>\n') 
            }
        </script>
        <script>
        if (datefield.type!="date"){ //if browser doesn't support input type="date", initialize date picker widget:
            jQuery(function($){ //on document.ready
                $('#CancelDate').datepicker({dateFormat: 'dd/mm/yy'});
            })
        }
        </script>
        <!--END PAGE LEVEL SCRIPTS -->

        <!-- ######################################################################## -->

    </body>

    <!-- END BODY -->
</html>

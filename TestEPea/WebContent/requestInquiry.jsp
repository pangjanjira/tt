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
            .datatable-scroll {
                overflow-x: auto;
                overflow-y: visible;
            }
            --> 
        </style>
        <style type="text/css">   
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
                width: 100%;
                height: 90%;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
            }
            .model-content2{
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

                    <!-- ######################################################################## -->

                    <!-- BEGIN PAGE TITLE -->
                    <div class="row">
                        <div class="col-lg-12">
                            <h3 style="color:#666666;"> <i class=" icon-search"></i> Request Inquiry </h3>
                        </div>
                    </div>
                    <hr />
                    <!-- END PAGE TITLE -->
                    <!-- ######################################################################## -->
                    <!-- BEGIN INQUIRY FORM -->
                    <div class="col-lg-12">
                        <div class="box">
                            <header>
                                <h5>Request Inquiry</h5>
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
                                        <label class="control-label col-lg-2">เลขที่บัญชีแสดงสัญญา (CA No.)</label>
                                        <div class="col-lg-3">
                                            <input type="text" id="CA" name="CA" class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">เลขที่คำขอเงินประกัน (Security No.)</label>
                                        <div class="col-lg-3">
                                            <input type="text" id="Security" name="Security" class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">หมายเลขคู่ค้า (BP No.)</label>
                                        <div class="col-lg-3">
                                            <input type="text" id="Bp" name="Bp" class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">LG No.</label>
                                        <div class="col-lg-3">
                                            <input type="text" id="LG" name="LG" class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">Request status</label>
                                        <div class="col-lg-3">
                                            <select name="iAlsStatus" id="iAlsStatus" class="form-control">
                                                <option>All</option>
                                                <option>Draft</option>
                                                <option>Wait for approval</option>
                                                <option>Approved</option>
                                                <option>Rejected</option>
                                                <option>Sent file to PEA</option>
                                                <option>Received result from PEA</option>
                                                <option>Completed</option>
                                                <option>PEA rejected</option>
                                            </select>
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="form-actions no-margin-bottom col-lg-12" style="text-align:center;">
                            <button id="Mybt">Inquiry</button>
                            <input id="inquiryBtn" name="inquiryBtn" type="button" value="Export to Excel" class="btn btn-primary btn-sm" />
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
                                                            <th style="text-align:center;font-size:small;">เลขที่บัญชี</th>
                                                            <th style="text-align:center;font-size:small;">เลขที่หลักประกัน</th>
                                                            <th style="text-align:center;font-size:small;">LG No.</th>
                                                            <th style="text-align:center;font-size:small;">หมายเลขคู่ค้า</th>
                                                            <th style="text-align:center;font-size:small;">เลขประจำตัวผู้เสียภาษีคู่ค้า</th>
                                                            <td style="text-align:center;font-size:small;">AccountMailingName</td>
                                                            <td style="text-align:center;font-size:small;">StartDate</td>
                                                            <td style="text-align:center;font-size:small;">EndDate</td>
                                                            <td style="text-align:center;font-size:small;">RegisterDate</td>
                                                            <th style="text-align:center;font-size:small;">Request Status</th>
                                                            <td style="text-align:center;font-size:small;">CreatedDate</td>
                                                            <td style="text-align:center;font-size:small;">UpdateDate</td>
                                                            <td style="text-align:center;font-size:small;">PEA-Bank Status</td>
                                                            <td style="text-align:center;font-size:small;">PEA-Massage</td>
                                                            <th style="text-align:center;font-size:small;">Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td style="text-align:center;font-size:smaller;"></td>
                                                            <td style="text-align:left;font-size:smaller;"></td>
                                                            <td style="text-align:center;font-size:smaller;"></td>
                                                            <td style="text-align:right;font-size:smaller;"></td>
                                                            <td style="text-align:center;font-size:smaller;"></td>
                                                            <td style="text-align:center;font-size:smaller;"></td>
                                                            <td style="text-align:center;font-size:smaller;"></td>
                                                            <td style="text-align:center;font-size:smaller;"></td>
                                                            <td style="text-align:left;font-size:smaller;"></td>
                                                            <td style="text-align:center;font-size:smaller;"></td>
                                                            <td style="text-align:right;font-size:smaller;"></td>
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
                    <script>
                        var model = document.getElementById("MyModel");
                        var bt = document.getElementById("Mybt");
                        var fin = document.getElementById("finish");
                        bt.onclick = function () {
                            model.style.display = "block";
                        }
                        window.onclick = function (event) {
                            if (event.target == model) {
                                model.style.display = "none";
                            }
                        }
                    </script>


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
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">1114000000000000001</span></div>
                                                    <label class="control-label col-lg-2">Dtm</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">04/10/2014 00:00:01</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Proj No</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">22200000001</span></div>
                                                    <label class="control-label col-lg-2">Vendor Tax ID</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">7205654000000</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Bond Type</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">01 - xxx xxx</span></div>
                                                    <label class="control-label col-lg-2">Seq No</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">1</span></div>
                                                </div>
                                            </div>
                                            <div class="alert alert-info">
                                                <div class="form-group">
                                                    <div class="col-lg-12"><b>Latest Approval  Status</b></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Approve Status</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">Approved</span></div>
                                                    <label class="control-label col-lg-2">Approved Amount</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">1,001.00</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Reason</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">xxx xxx xxx</span></div>
                                                    <label class="control-label col-lg-2">&nbsp;</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">&nbsp;</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Approved Datetime</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">12/05/2015 13:20:01</span></div>
                                                    <label class="control-label col-lg-2">Approved By</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">sXXXXX</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Bank Code</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">XXX</span></div>
                                                    <label class="control-label col-lg-2">Bank Addr</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">xxx</span></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Branch Code</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">XXX</span></div>
                                                    <label class="control-label col-lg-2">Branch Name</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">xxx</span></div>
                                                </div>
                                            </div>
                                            <!-- BEGIN REQUEST DETAIL -->
                                            <div class="alert alert-warning">
                                                <div class="form-group">
                                                    <div class="col-lg-12"><b>Transaction Info</b></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Txn Status</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">Success</span></div>
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
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">Approved</span></div>
                                                    <label class="control-label col-lg-2">ALS Msg</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">&nbsp;</span></div>
                                                </div>
                                            </div>
                                            <div class="alert alert-info" style="background-color: #EFEFEF;border-color: #CDCDCD;color: #666666;">
                                                <div class="form-group">
                                                    <div class="col-lg-12"><b>Request Info</b></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-lg-2">Request Type</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">Setup</span></div>
                                                    <label class="control-label col-lg-2">&nbsp;</label>
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">&nbsp;</span></div>
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
                                                    <label class="control-label col-lg-2">Request Dtm</label>
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
                                                    <div class="col-lg-4" style="padding-top:8px;"><span class="form-control-static">01</span></div>
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
                                                    <div class="col-lg-1" style="padding-top:8px;"><span class="form-control-static">Approved</span></div>
                                                    <label class="control-label col-lg-1">Reason</label>
                                                    <div class="col-lg-3" style="padding-top:8px;"><span class="form-control-static">&nbsp;</span></div>
                                                    <label class="control-label col-lg-1">Dtm</label>
                                                    <div class="col-lg-3" style="padding-top:8px;"><span class="form-control-static">12/05/2015 13:20:01</span></div>
                                                    <label class="control-label col-lg-1">User</label>
                                                    <div class="col-lg-1" style="padding-top:8px;"><span class="form-control-static">sXXXXX</span></div>
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
                </div>
            </div>
        </div>


        <!-- ######################################################################## -->


        <!-- END REQUEST DETAIL= -->

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
        <!--END PAGE LEVEL SCRIPTS -->

        <!-- ######################################################################## -->

    </body>

    <!-- END BODY -->
</html>

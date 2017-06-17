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

                    <!--   -->

                    <!-- BEGIN PAGE TITLE -->
                    <div class="row">

                        <div class="col-lg-12">
                            <h3 style="color:#666666;"> <i class=" icon-search"></i> Extend LG period </h3>
                        </div>
                    </div>
                    <hr>
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


                    <!-- END INQUIRY FORM -->

                    <!-- ######################################################################## -->


                    <div class="box">
                        <header>
                            <div class="icons"><i class="icon-th-large"></i></div>
                            <h5>Extend LG period</h5>
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
                        <div class="tab-content" id="inquiryFormBox">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <b>Extend LG Request</b>
                                </div>
                                <div class="form-group">
                                    <div class="col-lg-1">&nbsp;</div>
                                    <label class="control-label col-lg-2">CA No.</label>
                                    <div class="col-lg-3">
                                        <input type="text" id="CA" name="CA No." class="form-control" />
                                    </div>
                                    <font color="white">.</font>
                                    <div class="col-lg-1">&nbsp;</div>
                                </div>
                            </div>
                            <div class="form-actions no-margin-bottom col-lg-12" style="text-align:right;">
                                <button id="Mybt">Add Extend Request</button>
                            </div>
                            <div class="tab-pane fade in active">
                                <!-- BEGIN INQUIRY RESULT -->
                                <div id="inquiryLoading" style="text-align:center;display:none;"><div class="progress progress-striped active"><div class="progress-bar" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;"></div></div></div>
                                <div class="row" id="inquiryResultPanel">
                                    <div class="col-lg-12">
                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <b>Extend Request List </b>
                                                <div class="table-responsive">
                                                    <table id="MyTable" class="table table-striped table-bordered table-hover">
                                                        <tr style="white-space:nowrap">
                                                            <th style="text-align:center;font-size:small;">เลขที่หลักประกัน</th>
                                                            <th style="text-align:center;font-size:small;">เลขหนังสือสัญญาค้ำประกัน</th>
                                                            <th style="text-align:center;font-size:small;">จำนวนเงินค้ำประกัน</th>
                                                            <th style="text-align:center;font-size:small;">New Start Date</th>
                                                            <th style="text-align:center;font-size:small;">New End Date</th>
                                                            <th style="text-align:center;font-size:small;">ชื่อผู้ใช้ไฟฟ้า</th>
                                                            <th style="text-align:center;font-size:small;">Action</th>
                                                        </tr>
                                                        <tbody>

                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="panel panel-default">
                                    <div class="accordion-body collapse in body">
                                        <form action="#" class="form-horizontal" id="block-validate">
                                            <h4>Customer Contact Info</h4>
                                            <div class="form-group">
                                                <div class="col-lg-1">&nbsp;</div>
                                                <label class="control-label col-lg-2">E-mail</label>
                                                <div class="col-lg-3">
                                                    <input  type="text" id="E-mail" name="E-mail" class="form-control" />
                                                </div>
                                                <div class="col-lg-1">&nbsp;</div>
                                            </div>
                                            <div class="form-group">
                                                <div class="col-lg-1">&nbsp;</div>
                                                <label class="control-label col-lg-2">SMS</label>
                                                <div class="col-lg-3">
                                                    <input  type="text" id="SMS" name="SMS" class="form-control" />
                                                </div>
                                                <div class="col-lg-1">&nbsp;</div>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                                <div class="form-actions no-margin-bottom col-lg-12" style="text-align:center;">
                                    <button id="finish">Save</button>
                                </div>
                                <!-- END INQUIRY RESULT -->

                            </div>

                        </div>
                    </div>
                    <div id="MyModel" class="model">
                        <div class="model-content">
                            <div class="scroll">
                                <span class="close">&times;</span>
                                <div class="col-lg-12">
                                    <div class="box">
                                        <header>
                                            <h5>Request Detail</h5>
                                        </header>
                                        <div id="inquiryFormBox" class="accordion-body collapse in body">
                                            <form action="#" class="form-horizontal" id="block-validate">
                                                <h4>PEA Request Info</h4>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">เลขที่คำขอเงินค้ำประกัน<font color="red" size="4"> *</font></label>
                                                    <div class="col-lg-3-1">
                                                        <input placeholder="Security No." type="text" id="Security" name="Security" class="form-control" />
                                                    </div>
                                                    <p id="blank1"></p>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                            </form>
                                        </div>
                                        <div id="inquiryFormBox" class="accordion-body collapse in body">
                                            <form action="#" class="form-horizontal" id="block-validate">
                                                <h4>PEA Request Info</h4>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">หมายเลขผู้ใช้ไฟฟ้า<font color="red" size="4"> *</font></label>
                                                    <div class="col-lg-3-1">
                                                        <input placeholder="CA No." type="text" id="CustNo" name="CustNo" class="form-control" />
                                                    </div>
                                                    <p id="blank2"></p>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">สาขาที่ (ภาษีของผชฟ)</label>
                                                    <div class="col-lg-3-1">
                                                        <input placeholder="Tax Branch" type="text" id="Branch" name="Branch" class="form-control" />
                                                    </div>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">หมายเลขของคู่ค้าธุรกิจ<font color="red" size="4"> *</font></label>
                                                    <div class="col-lg-3-1">
                                                        <input placeholder="Bp No." type="text" id="Bp" name="Bp" class="form-control" />
                                                    </div>
                                                    <p id="blank3"></p>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">ชื่อผู้ใช้ไฟ้า</label>
                                                    <div class="col-lg-3-1">
                                                        <input placeholder="Cust Name" type="text" id="CustName" name="CustName" class="form-control" />
                                                    </div>

                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">ที่อยุ่ผู้ใช้ไฟฟ้า</label>
                                                    <div class="col-lg-3-1">
                                                        <input placeholder="Cust Adress" type="text" id="CustAdress" name="CustAdress" class="form-control" />
                                                    </div>
                                                    <label class="control-label col-lg-2">&nbsp;</label>
                                                    <div class="col-lg-3-1">&nbsp;</div>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">เลขที่คำขอเงินค้ำประกัน<font color="red" size="4"> *</font></label>
                                                    <div class="col-lg-3-1">
                                                        <select name="SecurityNo2" id="SecurityNo2" class="form-control">
                                                            <option></option>
                                                            <option value="XXXXX">XXXXXXXX</option>
                                                            <option value="XXXXX">XXXXXXXX</option>
                                                        </select>
                                                    </div>
                                                    <p id="blank4"></p>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">เหตุผลการวาเงินประกัน</label>
                                                    <div class="col-lg-3-1">
                                                        <input placeholder="Reason" type="text" id="Reason" name="Reason" class="form-control" />
                                                    </div>

                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">เลขประจำตัวผู้เสียภาษี<font color="red" size="4"> *</font></label>
                                                    <div class="col-lg-3-1">
                                                        <input placeholder="Tax ID" type="taxt" id="TaxID" name="TaxID" class="form-control" />
                                                    </div>
                                                    <p id="blank5"></p>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">จำนวนเงินค้ำประกัน<font color="red" size="4"> *</font></label>
                                                    <div class="col-lg-3-1">
                                                        <input type="number" id="Amount" name="Amount" class="form-control" />
                                                    </div>
                                                    <p id="blank6"></p>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">รหัสการค้าหาข้อมูล</label>
                                                    <div class="col-lg-3-1">
                                                        <input placeholder="result" type="text" id="result" name="result" class="form-control" />
                                                    </div>

                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">ข้อความแจ้ง</label>
                                                    <div class="col-lg-3-1">
                                                        <input placeholder="Message" type="text" id="Message" name="Message" class="form-control" />
                                                    </div>

                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                            </form>
                                        </div>

                                        <div id="inquiryFormBox" class="accordion-body collapse in body">

                                            <form action="#" class="form-horizontal" id="block-validate">
                                                <h4>LG Request</h4>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">LG No.(BG No.)<font color="red" size="4"> *</font></label>
                                                    <div class="col-lg-3-1">
                                                        <input placeholder="Massege" type="text" id="Massege2" name="Massege2" class="form-control" />
                                                    </div>
                                                    <p id="blank7"></p>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">Tax ID<font color="red" size="4"> *</font></label>
                                                    <div class="col-lg-3-1">
                                                        <input placeholder="Massege" type="text" id="Massege3" name="Massege3" class="form-control" maxlength="13"/>
                                                    </div>
                                                    <p id="blank8"></p>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">Account No.<font color="red" size="4"> *</font></label>
                                                    <div class="col-lg-3-1">
                                                        <input type="text" id="AccountN" name="AccountN" class="form-control" maxlength="13"/>
                                                    </div>
                                                    <p id="blank9"></p>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">Mailing name<font color="red" size="4"> *</font></label>
                                                    <div class="col-lg-3-1">
                                                        <font color="red">Mail format</font>
                                                    </div>
                                                    <div class="col-lg-3-1">
                                                        <input type="text" id="MailingName" name="MailingName" class="form-control" />
                                                    </div>
                                                    <p id="blank10"></p>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">Guarantee Amount<font color="red" size="4"> *</font></label>
                                                    <div class="col-lg-3-1">
                                                        <input placeholder="0.00" type="number" id="guaranteeAmt" name="guaranteeAmt" class="form-control" />
                                                    </div>
                                                    <p id="blank11"></p>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">New Start Date<font color="red" size="4"> *</font></label>
                                                    <div class="col-lg-3-1">
                                                        <input type="date" id="nStartDate" name="nStartDate" class="form-control" />
                                                    </div>
                                                    <p id="blank12"></p>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">New End Date<font color="red" size="4"> *</font></label>
                                                    <div class="col-lg-3-1">
                                                        <input type="date" id="nEndDate" name="nEndDate" class="form-control" />
                                                    </div>
                                                    <p id="blank13"></p>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-actions no-margin-bottom col-lg-12" style="text-align:center;">
                                <button onclick="blankCheck()">OK</button>
                            </div>
                            <div id="alertModel" class="model">
                                <div class="model-content2">
                                    <div class="form-actions no-margin-bottom col-lg-12" style="text-align:center;">
                                        <p id="Alert"></p>
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
        <script>
            var datefield = document.createElement("input")
            datefield.setAttribute("type", "date")
            if (datefield.type != "date") { //if browser doesn't support input type="date", load files for jQuery UI Date Picker
                document.write('<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />\n')
                document.write('<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"><\/script>\n')
                document.write('<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"><\/script>\n')
            }
        </script>
        <script>
            if (datefield.type != "date") { //if browser doesn't support input type="date", initialize date picker widget:
                jQuery(function ($) { //on document.ready
                    $('#nStartDate').datepicker({
                        dateFormat: 'dd/mm/yy',
                        numberOfMonths: 1,
                        onSelect: function (selected) {
                            $("#nEndDate").datepicker("option", "minDate", selected)
                        }
                    });
                    $('#nEndDate').datepicker({
                        dateFormat: 'dd/mm/yy',
                        numberOfMonths: 1,
                        onSelect: function (selected) {
                            $("#nStartDate").datepicker("option", "maxDate", selected)
                        }
                    });
                })
            }
        </script>
        <!--END PAGE LEVEL SCRIPTS -->

        <!-- ######################################################################## -->
        <script>
            //$(function () { formValidation(); });
            function blankCheck() {
                var model2 = document.getElementById("alertModel");
                var custNo = document.getElementById("CustNo").value;
                var securityNo = document.getElementById("Security").value;
                var bp = document.getElementById("Bp").value;
                var securityNo2 = document.getElementById("SecurityNo2").value;
                var taxId = document.getElementById("TaxID").value;
                var amount = document.getElementById("Amount").value;
                var lg = document.getElementById("Massege2").value;
                var taxId2 = document.getElementById("Massege3").value;
                var accountN = document.getElementById("AccountN").value;
                var mailing = document.getElementById("MailingName").value;
                var guaranteeAmt = document.getElementById("guaranteeAmt").value;
                var nEndDate = document.getElementById("nEndDate").value;
                var nStartDate = document.getElementById("nStartDate").value;
                if (custNo == "") {
                    var str = "Required"
                    var result = str.fontcolor("red");
                    document.getElementById("blank2").innerHTML = result;
                } else if (custNo != "") {
                    var str = "Required"
                    var result = str.fontcolor("white");
                    document.getElementById("blank2").innerHTML = result;
                }
                if (securityNo == "") {
                    var str = "Required"
                    var result = str.fontcolor("red");
                    document.getElementById("blank1").innerHTML = result;
                } else if (securityNo != "") {
                    var str = "Required"
                    var result = str.fontcolor("white");
                    document.getElementById("blank1").innerHTML = result;
                }
                if (bp == "") {
                    var str = "Required"
                    var result = str.fontcolor("red");
                    document.getElementById("blank3").innerHTML = result;
                } else if (bp != "") {
                    var str = "Required"
                    var result = str.fontcolor("white");
                    document.getElementById("blank3").innerHTML = result;
                }
                if (securityNo2 == "") {
                    var str = "Required"
                    var result = str.fontcolor("red");
                    document.getElementById("blank4").innerHTML = result;
                } else if (securityNo2 != "") {
                    var str = "Required"
                    var result = str.fontcolor("white");
                    document.getElementById("blank4").innerHTML = result;
                }
                if (taxId == "") {
                    var str = "Required"
                    var result = str.fontcolor("red");
                    document.getElementById("blank5").innerHTML = result;
                } else if (taxId != "") {
                    var str = "Required"
                    var result = str.fontcolor("white");
                    document.getElementById("blank5").innerHTML = result;
                }
                if (amount == "") {
                    var str = "Required"
                    var result = str.fontcolor("red");
                    document.getElementById("blank6").innerHTML = result;
                } else if (taxId != "") {
                    var str = "Required"
                    var result = str.fontcolor("white");
                    document.getElementById("blank6").innerHTML = result;
                }
                if (lg == "") {
                    var str = "Required"
                    var result = str.fontcolor("red");
                    document.getElementById("blank7").innerHTML = result;
                } else if (lg != "") {
                    var str = "Required"
                    var result = str.fontcolor("white");
                    document.getElementById("blank7").innerHTML = result;
                }
                if (taxId2 == "") {
                    var str = "Required"
                    var result = str.fontcolor("red");
                    document.getElementById("blank8").innerHTML = result;
                } else if (taxId2 != "") {
                    var str = "Required"
                    var result = str.fontcolor("white");
                    document.getElementById("blank8").innerHTML = result;
                }
                if (accountN == "") {
                    var str = "Required"
                    var result = str.fontcolor("red");
                    document.getElementById("blank9").innerHTML = result;
                } else if (accountN != "") {
                    var str = "Required"
                    var result = str.fontcolor("white");
                    document.getElementById("blank9").innerHTML = result;
                }
                if (mailing == "") {
                    var str = "Required"
                    var result = str.fontcolor("red");
                    document.getElementById("blank10").innerHTML = result;
                } else if (mailing != "") {
                    var str = "Required"
                    var result = str.fontcolor("white");
                    document.getElementById("blank10").innerHTML = result;
                }
                if (guaranteeAmt == "") {
                    var str = "Required"
                    var result = str.fontcolor("red");
                    document.getElementById("blank11").innerHTML = result;
                } else if (guaranteeAmt != "") {
                    var str = "Required"
                    var result = str.fontcolor("white");
                    document.getElementById("blank11").innerHTML = result;
                }
                if (nStartDate == "") {
                    var str = "Required"
                    var result = str.fontcolor("red");
                    document.getElementById("blank12").innerHTML = result;
                } else if (nStartDate != "") {
                    var str = "Required"
                    var result = str.fontcolor("white");
                    document.getElementById("blank12").innerHTML = result;
                }
                if (nEndDate == "") {
                    var str = "Required"
                    var result = str.fontcolor("red");
                    document.getElementById("blank13").innerHTML = result;
                } else if (nEndDate != "") {
                    var str = "Required"
                    var result = str.fontcolor("white");
                    document.getElementById("blank13").innerHTML = result;
                }
                if (nEndDate != "" && nStartDate != "" && guaranteeAmt != "" && mailing != "" && accountN != "" && taxId2 != ""
                        && lg != "" && amount != "" && taxId != "" && securityNo2 != "" && bp != "" && securityNo != "" && custNo != "") {
                    model.style.display = "none";
                    addRowFuntion();
                }
            }
            function addRowFuntion() {
                var table = document.getElementById("MyTable");
                var row = table.insertRow(1);
                var cell1 = row.insertCell(0);
                var cell2 = row.insertCell(1);
                var cell3 = row.insertCell(2);
                var cell4 = row.insertCell(3);
                var cell5 = row.insertCell(4);
                var cell6 = row.insertCell(5);
                var cell7 = row.insertCell(6);
                cell1.innerHTML = document.getElementById("CustNo").value;
                var num = parseInt(document.getElementById("Amount").value);
                var n = num.toFixed(2);
                cell2.innerHTML = document.getElementById("Security").value;
                cell3.innerHTML = n;
                cell4.innerHTML = document.getElementById("nStartDate").value;
                cell5.innerHTML = document.getElementById("nEndDate").value;
                cell6.innerHTML = document.getElementById("CustName").value;
                cell7.innerHTML = "view";
            }
            var model3 = document.getElementById("alertModel2");
            var ca = document.getElementById("CA").value;
            var model = document.getElementById("MyModel");
            var bt = document.getElementById("Mybt");
            var fin = document.getElementById("finish");
            var close = document.getElementsByClassName("close");
            // Closes the modal on 'X' click
            close[1].onclick = function () {
                model.style.display = "none";
                //console.log('wtf why is this not working')
            }
            // When the user clicks anywhere outside of the modal, close it
            window.onclick = function (event) {
                if (event.target == model) {
                    model.style.display = "none";
                }
            }
            //document.getElementById("Alert2").innerHTML = "Create Request Succesfully";
            bt.onclick = function () {
                model.style.display = "block";
            }
            fin.onclick = function () {
                var ca = document.getElementById("CA").value;
                if (ca != "") {
                    model3.style.display = "block";
                    window.onclick = function (event) {
                        if (event.target == model3) {
                            model3.style.display = "none";
                        }
                    }
                }
            }

            $(document).ready(function () {
                $('#dataTables-result').dataTable({
                    "sDom": '<"datatable-scroll"t><"F"ilp>',
                    "dom": '<"top"i>rt<"bottom"flp><"clear">',
                    "bProcessing": true,
                    "bScrollCollapse": true,
                    "bFilter": false,
                    "bJQueryUI": false
                });

                $("#inquiryBtn").click(function () {
                    $("#inquiryBtn").attr("disabled", true);
                    $("#inquiryResultPanel").show();
                    $("#inquiryBtn").attr("disabled", false);
                    return true;
                });

                $("#detailModal").dialog();
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

            function setForm(account) {
                $("#maintainFormTitle").html("<h4>Update Control Account</h4>");
                $("#mode").val("update");
                $("#account").val("xxxxxxxxxx");
                $("#messageCode").val("xxx");
                $("#messageDesc").val("xxx xxx xxxxx xxxxxx xxx.");
                $("#createBy").html("sXXXXX");
                $("#createDatetime").html("28/05/2015 10:11:05");
                $("#updateBy").html("sYYYYY");
                $("#updateDatetime").html("29/05/2015 12:22:15");
                $("#createUpdateInfo").show();
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
        </script>
    </body>

    <!-- END BODY -->
</html>

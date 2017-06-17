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
                            <h3 style="color:#666666;"> <i class=" icon-search"></i>Request Approval</h3>
                        </div>
                    </div>
                    <hr />
                    <!-- END PAGE TITLE -->
                    <!-- ######################################################################## -->
                    <!-- BEGIN INQUIRY FORM -->
                    <div class="col-lg-12">
                        <div class="box">
                            <header>
                                <h5>Inquiry Request for Approval</h5>
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
                                        <label class="control-label col-lg-2">เลขที่บัญชีแสดงสัญญา</label>
                                        <div class="col-lg-3">
                                            <input value="CA No." type="text" id="iTxRef" name="iTxRef" class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">เลขที่หลักประกัน</label>
                                        <div class="col-lg-3">
                                            <input value="Security No." type="text" id="iTxRef" name="iTxRef" class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">LG No.</label>
                                        <div class="col-lg-3">
                                            <input value="LG No." type="text" id="iLgNo" name="iLgNo" class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">หมายเลขของคู่ค้าธุรกิจ</label>
                                        <div class="col-lg-3">
                                            <input value="BG No." type="text" id="iLgNo" name="iLgNo" class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">วันที่ Request</label>
                                        <div class="col-lg-3">
                                            <select name="iAlsStatus" id="iAlsStatus" class="form-control">
                                                <option value="Security No.">Request Date</option>
                                                <option value="AP">XX-XX-XXXX</option>
                                                <option value="RJ">XX-XX-XXXX</option>
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
                    <div id="MyModel" class="model">
                        <div class="model-content">
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
                                                                    <th style="text-align:center;font-size:small;">LG No.</th>
                                                                    <th style="text-align:center;font-size:small;">เลขประจำตัวผู้เสียภาษีคู่ค้า</th>
                                                                    <th style="text-align:center;font-size:small;">จำนวนเงินค้ำประกัน</th>
                                                                    <th style="text-align:center;font-size:small;">Account No.</th>
                                                                    <th style="text-align:center;font-size:small;">วันที่ request</th>
                                                                    <th style="text-align:center;font-size:small;">เลขที่หลักประกัน</th>
                                                                    <th style="text-align:center;font-size:small;">เลขที่บัยชีแสดงสัญญา</th>
                                                                    <th style="text-align:center;font-size:small;">หมายเลขคู่ค้า</th>
                                                                    <td style="text-align:center;font-size:smaller;">AccountMailingName</td>
                                                                    <td style="text-align:center;font-size:smaller;">StartDate</td>
                                                                    <td style="text-align:center;font-size:smaller;">EndDate</td>
                                                                    <td style="text-align:center;font-size:smaller;">RegisterDate</td>
                                                                    <th style="text-align:center;font-size:small;">Request Status</th>
                                                                    <td style="text-align:center;font-size:smaller;">CreatedDate</td>
                                                                    <td style="text-align:center;font-size:smaller;">UpdateDate</td>
                                                                    <td style="text-align:center;font-size:smaller;">PEA-Bank Status</td>
                                                                    <td style="text-align:center;font-size:smaller;">PEA-Massage</td>
                                                                    <th style="text-align:center;font-size:small;">Request Status</th>
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
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:left;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:right;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"></td>
                                                                    <td style="text-align:center;font-size:smaller;"><p id="Mybt2">view</p></td>
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
                        <div id="MyModel2" class="model">
                            <div class="model-content">
                                <div class="col-lg-12">
                                    <div class="box">
                                        <header>
                                            <h5>Request Approval</h5>
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
                                                    <label class="control-label col-lg-2">เลขที่บัญชีแสดงสัญญา</label>
                                                    <div class="col-lg-3">
                                                        <input value="CA No." type="text" id="iVendorTaxID" name="iVendorTaxID" class="form-control" />
                                                    </div>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">เลขที่หลักประกัน</label>
                                                    <div class="col-lg-3">
                                                        <input value="SEcurity No." type="text" id="iVendorTaxID" name="iVendorTaxID" class="form-control" />
                                                    </div>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">LG No.</label>
                                                    <div class="col-lg-3">
                                                        <input value="LG No." type="text" id="iTxRef" name="iTxRef" class="form-control" />
                                                    </div>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">เลขประจำตัวผู้เสียภาษีคู่ค้า</label>
                                                    <div class="col-lg-3">
                                                        <input value="Tax ID" type="text" id="iLgNo" name="iLgNo" class="form-control" />
                                                    </div>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">Account No.</label>
                                                    <div class="col-lg-3">
                                                        <input value="Account No." type="text" id="iProjNo" name="iProjNo" class="form-control" />
                                                    </div>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">จำนวนเงินค้ำประกัน</label>
                                                    <div class="col-lg-3">
                                                        <input value="Guarntee Amt" type="text" id="iProjNo" name="iProjNo" class="form-control" />
                                                    </div>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">Action</label>
                                                    <div class="col-lg-3">
                                                        <select name="iAlsStatus" id="iAlsStatus" class="form-control">
                                                            <option value="Security No."></option>
                                                            <option value="AP">Approve</option>
                                                            <option value="RJ">Reject</option>
                                                        </select>
                                                    </div>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-lg-1">&nbsp;</div>
                                                    <label class="control-label col-lg-2">Reject Reason</label>
                                                    <div class="col-lg-3">
                                                        <input type="text" id="iProjNo" name="iProjNo" class="form-control" />
                                                    </div>
                                                    <div class="col-lg-1">&nbsp;</div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="form-actions no-margin-bottom col-lg-12" style="text-align:center;">
                                        <button id="finish2">Submit</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <script>
                            var model2 = document.getElementById("MyModel2");
                            var bt2 = document.getElementById("Mybt2");
                            var fin2 = document.getElementById("finish2");
                            bt2.onclick = function () {
                                model2.style.display = "block";
                            }
                            fin2.onclick = function () {
                                model2.style.display = "none";
                            }
                        </script>
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

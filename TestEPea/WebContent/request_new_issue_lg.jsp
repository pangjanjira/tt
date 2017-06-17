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
                            <h3 style="color:#666666;"> <i class=" icon-search"></i> Issue LG Request </h3>
                        </div>
                    </div>
                    <hr />
                    <!-- END PAGE TITLE -->
                    <!-- ######################################################################## -->
                    <!-- BEGIN INQUIRY FORM -->
                    <div class="col-lg-12">
                        <div class="box">
                            <header>
                                <div class="icons"><i class="icon-th-large"></i></div>
                                <h5>Issue LG Request</h5>
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
                                    <h4>PEA Request Info</h4>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label id="Ca" class="control-label col-lg-2">หมายเลขผู้ใช้ไฟฟ้า<font color="red" size="4"> *</font></label>
                                        <div class="col-lg-3">
                                            <input placeholder="CA No." type="text" id="CA" name="CA No." class="form-control" />
                                        </div>
                                        <p id="blank1"></p>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label id="secureNo" class="control-label col-lg-2">เลขที่คำขอเงินค้ำประกัน<font color="red" size="4"> *</font></label>
                                        <div class="col-lg-3">
                                            <input placeholder="Security No." type="text" id="Security" name="Security" class="form-control" />
                                        </div>
                                        <p id="blank2"></p>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label id="bpNo" class="control-label col-lg-2">หมายเลขของคู่ค้าธุรกิจ<font color="red" size="4"> *</font></label>
                                        <div class="col-lg-3">
                                            <input placeholder="Bp No." type="text" id="Bp" name="Bp No." class="form-control" />
                                        </div>
                                        <p id="blank3"></p>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">ชื่อผู้ใช้ไฟ้า</label>
                                        <div class="col-lg-3">
                                            <input placeholder="Cust Name" type="text" id="CustN" name="Cust Name" class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">ที่อยุ่ผู้ใช้ไฟฟ้า</label>
                                        <div class="col-lg-3">
                                            <textarea line="200" cols="31" id="CustAddress"></textarea>
                                        </div>
                                        <label class="control-label col-lg-2">&nbsp;</label>
                                        <div class="col-lg-3">&nbsp;</div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">สาขาที่ (ภาษีของผชฟ)</label>
                                        <div class="col-lg-3">
                                            <input placeholder="Tax Branch" type="text" id="Tex" name="Tax Branch" class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">เลขที่หนังสือค้ำประกัน</label>
                                        <div class="col-lg-3">
                                            <input placeholder="BG No." type="text" id="BG" name="BG No." class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">เหตุผลการวาเงินประกัน</label>
                                        <div class="col-lg-3">
                                            <input placeholder="Reason" type="text" id="Reason" name="Reason" class="form-control" />
                                        </div>
                                        <p id="blank3"></p>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label id="taxId" class="control-label col-lg-2">เลขประจำตัวผู้เสียภาษี<font color="red" size="4"> *</font></label>
                                        <div class="col-lg-3">
                                            <input placeholder="Tax ID" type="text" id="Tax" name="Tax ID" class="form-control" />
                                        </div>
                                        <p id="blank4"></p>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label id="amount" class="control-label col-lg-2">จำนวนเงินค้ำประกัน<font color="red" size="4"> *</font></label>
                                        <div class="col-lg-3">
                                            <input type="number" placeholder="0.00" id="Amount" name="Amount" class="form-control" />
                                        </div>
                                        <p id="blank5"></p>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">รหัสการค้าหาข้อมูล</label>
                                        <div class="col-lg-3">
                                            <input placeholder="result" type="text" id="result" name="result" class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">ข้อความแจ้ง</label>
                                        <div class="col-lg-3">
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
                                        <label id="taxId2" class="control-label col-lg-2">Tax ID<font color="red" size="4"> *</font></label>
                                        <div class="col-lg-3">
                                            <input type="text" id="Tax2" name="Tax ID2" class="form-control" />
                                        </div>
                                        <p id="blank6"></p>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label id="accountNo" class="control-label col-lg-2">Account No.<font color="red" size="4"> *</font></label>
                                        <div class="col-lg-3">
                                            <select name="Account No." id="Account" class="form-control">
                                                <option ></option>
                                                <option >XXXXXXXX</option>
                                                <option>XXXXXXXX</option>
                                            </select>
                                        </div>
                                        <p id="blank7"></p>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">Mailing name</label>
                                        <div class="col-lg-3">
                                            <input type="text" id="Mailing" name="Mailing name" class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label class="control-label col-lg-2">Avaliable Amount</label>
                                        <div class="col-lg-3">
                                            <input type="number" id="Avaliable" name="Avaliable Amount" class="form-control" />
                                        </div>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label id="guarantee" class="control-label col-lg-2">Guarantee Amount<font color="red" size="4"> *</font></label>
                                        <div class="col-lg-3">
                                            <input type="number" id="Guarantee" name="Guarantee Amount" class="form-control" />
                                        </div>
                                        <p id="blank8"></p>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label id="startDate" class="control-label col-lg-2">Start Date<font color="red" size="4"> *</font></label>
                                        <div class="col-lg-3">
                                            <input type="date" id="Start" name="Start Date" class="form-control" />
                                        </div>
                                        <p id="blank9"></p>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label id="endDate" class="control-label col-lg-2">End Date<font color="red" size="4"> *</font></label>
                                        <div class="col-lg-3">
                                            <input type="date" id="End" name="End Date" class="form-control" />
                                        </div>
                                        <p id="blank10"></p>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                </form>
                            </div>

                            <div id="inquiryFormBox" class="accordion-body collapse in body">

                                <form action="#" class="form-horizontal" id="block-validate">
                                    <h4>Customer Contact Info</h4>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label id="email" class="control-label col-lg-2">Email address<font color="red" size="4"> *</font></label>
                                        <div class="col-lg-3">
                                            <input type="email" placeholder="xxx@xxx.xxx" id="Email" name="Email address" class="form-control" />
                                        </div>
                                        <p id="blank11"></p>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-1">&nbsp;</div>
                                        <label id="sms" class="control-label col-lg-2">Mobile number for SMS<font color="red" size="4"> *</font></label>
                                        <div class="col-lg-3">
                                            <input type="text" id="SMS" name="SMS" class="form-control" />
                                        </div>
                                        <p id="blank12"></p>
                                        <div class="col-lg-1">&nbsp;</div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="form-actions no-margin-bottom col-lg-12" style="text-align:center;">
                            <button onclick="myFunction()">Save</button>
                        </div>
                    </div>
                    <div id="MyModel" class="model">
                        <div class="model-content">
                            <p id="Alert"></p>
                        </div>
                    </div>
                </div>
                <script>
                    function myFunction() {
                        var ca = document.getElementById("CA").value;
                        var secureNo = document.getElementById("Security").value
                        var taxId = document.getElementById("Tax").value;
                        var amount = document.getElementById("Amount").value;
                        var bpNo = document.getElementById("Bp").value;
                        var accountNo = document.getElementById("Account").value;
                        var taxId2 = document.getElementById("Tax2").value;
                        var guarantee = document.getElementById("Guarantee").value;
                        var startDate = document.getElementById("Start").value;
                        var endDate = document.getElementById("End").value;
                        var email = document.getElementById("Email").value;
                        var sms = document.getElementById("SMS").value;
                        var model = document.getElementById("MyModel");
                        var bt = document.getElementById("test");
                        if (ca == "") {
                            var str = "Required"
                            var result = str.fontcolor("red");
                            document.getElementById("blank1").innerHTML = result;
                        } else if (ca != "") {
                            var str = "Required"
                            var result = str.fontcolor("white");
                            document.getElementById("blank1").innerHTML = result;
                        }
                        if (secureNo == "") {
                            var str = "Required"
                            var result = str.fontcolor("red");
                            document.getElementById("blank2").innerHTML = result;
                        } else if (secureNo != "") {
                            var str = "Required"
                            var result = str.fontcolor("white");
                            document.getElementById("blank2").innerHTML = result;
                        }
                        if (bpNo == "") {
                            var str = "Required"
                            var result = str.fontcolor("red");
                            document.getElementById("blank3").innerHTML = result;
                        } else if (bpNo != "") {
                            var str = "Required"
                            var result = str.fontcolor("white");
                            document.getElementById("blank3").innerHTML = result;
                        }
                        if (taxId == "") {
                            var str = "Required"
                            var result = str.fontcolor("red");
                            document.getElementById("blank4").innerHTML = result;
                        } else if (taxId != "") {
                            var str = "Required"
                            var result = str.fontcolor("white");
                            document.getElementById("blank4").innerHTML = result;
                        }
                        if (amount == "") {
                            var str = "Required"
                            var result = str.fontcolor("red");
                            document.getElementById("blank5").innerHTML = result;
                        } else if (amount != "") {
                            var str = "Required"
                            var result = str.fontcolor("white");
                            document.getElementById("blank5").innerHTML = result;
                        }
                        if (taxId2 == "") {
                            var str = "Required"
                            var result = str.fontcolor("red");
                            document.getElementById("blank6").innerHTML = result;
                        } else if (taxId2 != "") {
                            var str = "Required"
                            var result = str.fontcolor("white");
                            document.getElementById("blank6").innerHTML = result;
                        }
                        if (accountNo == "") {
                            var str = "Required"
                            var result = str.fontcolor("red");
                            document.getElementById("blank7").innerHTML = result;
                        } else if (accountNo != "") {
                            var str = "Required"
                            var result = str.fontcolor("white");
                            document.getElementById("blank7").innerHTML = result;
                        }
                        if (guarantee == "") {
                            var str = "Required"
                            var result = str.fontcolor("red");
                            document.getElementById("blank8").innerHTML = result;
                        } else if (guarantee != "") {
                            var str = "Required"
                            var result = str.fontcolor("white");
                            document.getElementById("blank8").innerHTML = result;
                        }
                        if (startDate == "") {
                            var str = "Required"
                            var result = str.fontcolor("red");
                            document.getElementById("blank9").innerHTML = result;
                        } else if (startDate != "") {
                            var str = "Required"
                            var result = str.fontcolor("white");
                            document.getElementById("blank9").innerHTML = result;
                        }
                        if (endDate == "") {
                            var str = "Required"
                            var result = str.fontcolor("red");
                            document.getElementById("blank10").innerHTML = result;
                        } else if (endDate != "") {
                            var str = "Required"
                            var result = str.fontcolor("white");
                            document.getElementById("blank10").innerHTML = result;
                        }
                        var mailValid = "0";
                        if (email != "") {
                            var x = email;
                            var atpos = x.indexOf("@");
                            var dotpos = x.indexOf(".");
                            if (atpos < 1 || dotpos < atpos + 2 || dotpos + 2 >= x.length) {
                                mailValid = "0";
                                var str = "Email invalid"
                                var result = str.fontcolor("red");
                                document.getElementById("blank11").innerHTML = result;
                            } else if (!(atpos < 1 || dotpos < atpos + 2 || dotpos + 2 >= x.length)) {
                                mailValid = "1";
                                var str = "."
                                var result = str.fontcolor("white");
                                document.getElementById("blank11").innerHTML = result;
                            }
                        }
                        if (email == "" && sms == "") {
                            var str1 = "Required"
                            var str2 = "Required"
                            var result = str.fontcolor("red");
                            var result2 = str2.fontcolor("red");
                            document.getElementById("blank11").innerHTML = result;
                            document.getElementById("blank12").innerHTML = result2;
                        } else if (ca != "" && secureNo != "" && bpNo != "" && taxId != "" && amount != "" && taxId2 != "" && guarantee != "" && accountNo != "" && startDate != "" && endDate != "" && mailValid == "1") {
                            var amt = parseInt(amount);
                            amt.toFixed(2);
                            document.getElementById("Alert").innerHTML = "Create Request Successfully";
                            model.style.display = "block";
                            window.onclick = function (event) {
                                if (event.target == model) {
                                    model.style.display = "none";
                                }
                            }
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
                <!-- END REQUEST DETAIL= -->

                <hr />
                <!-- END INQUIRY FORM -->
                <!-- END REQUEST DETAIL= -->


                <!-- END INQUIRY FORM -->
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
                    $('#Start').datepicker({
                        dateFormat: 'dd/mm/yy',
                        numberOfMonths: 1,
                        onSelect: function (selected) {
                            $("#End").datepicker("option", "minDate", selected)
                        }
                    });
                    $('#End').datepicker({
                        dateFormat: 'dd/mm/yy',
                        numberOfMonths: 1,
                        onSelect: function (selected) {
                            $("#Start").datepicker("option", "maxDate", selected)
                        }
                    });
                })
            }
        </script> 
        <!--END PAGE LEVEL SCRIPTS -->

        <!-- ######################################################################## -->

    </body>

    <!-- END BODY -->
</html>

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
                            <h3 style="color:#666666;"> <i class=" icon-search"></i> Increase Amount During Year  </h3>
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
                                <h5>Increase Amount During Year</h5>
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
                                        <label class="control-label col-lg-2">ที่อยู่ผู้ใช้ไฟฟ้า</label>
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
                                        <label class="control-label col-lg-2">Available Amount</label>
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

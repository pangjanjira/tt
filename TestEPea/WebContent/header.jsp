        <!-- HEADER SECTION -->
        <script src="assets/js/global.js"></script>
        <script type="text/javascript">
        	var EGUA_VERSION = 'SCB ePea-Guarantee version 1.0 U59040034 - New Interface with Pea';
        	var DIALOG_HEADER = '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title" id="H2">About</h4>';
        	function viewVersion(){
        		alertModal(DIALOG_HEADER, EGUA_VERSION,"");
        		return;
        	}
        </script>
        <div id="top">

            <nav class="navbar navbar-inverse navbar-fixed-top " style="padding-top: 10px;background-color:#4E2981;background-image: url('assets/img/header.png');background-repeat: no-repeat;background-position: right;">
                <a data-original-title="Show/Hide Menu" data-placement="bottom" data-tooltip="tooltip" class="accordion-toggle btn btn-primary btn-sm visible-xs" data-toggle="collapse" href="#menu" id="menu-toggle">
                    <i class="icon-align-justify"></i>
                </a>
                <!-- LOGO SECTION -->
                <header class="navbar-header">
                    <a href="index.jsp" class="navbar-brand"><img src="assets/img/logo_scb.gif" alt="" /></a>
                </header>
                <!-- END LOGO SECTION -->
                <ul class="nav navbar-top-links navbar-right">
                    <li><h4 style="padding: 4px  4px  4px  4px;color:#EFEFEF;">SCB ePea-Guarantee</h4></li>
                    <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
                    <!--ADMIN SETTINGS SECTIONS -->

                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#" style="background-color:#EED9FE;">
                            <i class="icon-user "></i>&nbsp; <i class="icon-chevron-down "></i>
                        </a>
                        <ul class="dropdown-menu dropdown-user">
                            <li><a href="javascript:void(0)" onclick = "logout()"><i class="icon-signout" id = "logout"></i> Logout (<%=request.getSession().getAttribute("user_name")%>) </a>
                            </li>
                            <li><a href="javascript:void(0)" onclick = "viewVersion()"><i class="icon-info" id = "eguaInfo"></i> About </a>
                            </li>
                        </ul>
                    </li>
                    <!--END ADMIN SETTINGS -->
                </ul>

            </nav>

        </div>
        <!-- END HEADER SECTION -->
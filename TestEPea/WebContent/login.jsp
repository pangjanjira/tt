<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->
<% 
if(request.getSession().getAttribute("user_name")!=null){
	response.sendRedirect("index.jsp");
}
%>
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
        <!-- PAGE LEVEL STYLES -->
        <link rel="stylesheet" href="assets/plugins/bootstrap/css/bootstrap.css" />
        <link rel="stylesheet" href="assets/css/login.css" />
        <link rel="stylesheet" href="assets/plugins/magic/magic.css" />
        <!-- END PAGE LEVEL STYLES -->
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
    </head>
    <!-- END HEAD -->

    <!-- BEGIN BODY -->
    <body >

        <!-- PAGE CONTENT --> 
        <div class="container">
            <div class="text-center">
                <img src="assets/img/logo_scb.gif" style="width: 200px;" id="logoimg" alt=" Logo" />
            </div>
            <div class="tab-content">
                <div id="login" class="tab-pane active">
                    <!--            <form action="index.html" class="form-signin" method="post">-->
                    <form action="AuthenAuthorizeRequestControl" class="form-signin" method="post">
                        <p class="text-muted text-center btn-block btn btn-primary btn-rect">
                            Enter your username and password
                        </p>
                        <input type="text" placeholder="Username" required="" class="form-control" name="iUserName" />
                        <input type="password" placeholder="Password" required="" class="form-control" name="iPassword" />
                        <button type="submit"class="btn text-muted text-center btn-warning" name="btnSubmit" type="submit">Sign in</button>
                    </form>
                </div>
            </div>
            <div class="text-center">
                <ul class="list-inline">
                    <!--<li><a class="text-muted"  href="#login" data-toggle="tab" style="display:none;" id="loginEffect">Login</a></li>-->
                    <li style="color: #FFFFFF">
                        <%
                            String error_msg = "";
                            if(request.getSession().getAttribute("errorMsg")!=null){
                                error_msg = request.getSession().getAttribute("errorMsg").toString();
                            }
                        %>
                        <%=error_msg%>
                    </li>
                </ul>
            </div>
        </div>

        <!--END PAGE CONTENT -->     

        <!-- PAGE LEVEL SCRIPTS -->
        <script src="assets/plugins/jquery-2.0.3.min.js"></script>
        <script src="assets/plugins/bootstrap/js/bootstrap.js"></script>
        <script src="assets/js/login.js"></script>
        <!--END PAGE LEVEL SCRIPTS -->

        <script>
//            $(function() {
//                $("#loginEffect").trigger("click");
//            });
        </script>
    </body>
    <!-- END BODY -->
</html>

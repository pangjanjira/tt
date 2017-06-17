<%@ page contentType="text/html; charset=utf-8" language="java"   %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
session.setAttribute("user_name", "s69601");
%>
<html>
<head>
    <title>Upload File</title>
    <script type="text/javascript">
    	function submit_click(){
    		//if(document._form.filename.value == ""){
    			//alert("Enter Filename !!");
    			//document._form.loading.style.display = "none";
    			//return false;
    		//}else{
    			//document._form.loading.style.display = "";
    			//return true;
    		//}
    		//return false;
    	}
    </script>
</head>
<body>
    <form name="_form" action="GPGuaranteeRequestControl" method="post" enctype="multipart/form-data">
        <table>
            <tr>
                <td>Approval Status : </td>
                <td>
                	<select name="iApprovalStatus" id="iApprovalStatus">
                		<option value="RJ">Rejected</option>
                		<option value="AP">Approved</option>
                	</select>
                </td>
            </tr>
            <tr>
                <td>Approval Reason : </td>
                <td><input type="text" name="iApprovalReason" id="iApprovalReason" /></td>
            </tr>
            <tr>
                <td>GP Guarantee ID : </td>
                <td><input type="text" name="iGpGuaranteeId" id="iGpGuaranteeId" /></td>
            </tr>
            <tr>
                <td>Requestor : </td>
                <td><input type="text" name="requestor" id="requestor" value="approval" /></td>
            </tr>
        </table>
        <p/>
        <input type="submit" value="Upload File"/> 
        <input type="reset" value="Reset">
        <img alt="loading" name="loading" src="./images/loading.gif" width="120px" style="display: none">
    </form>
 </body>
</html> 

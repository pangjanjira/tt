<%@ page contentType="text/html; charset=utf-8" language="java"   %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Upload File</title>
    <script type="text/javascript">
    	function submit_click(){
    		if(document._form.filename.value == ""){
    			alert("Enter Filename !!");
    			document._form.loading.style.display = "none";
    			return false;
    		}else{
    			document._form.loading.style.display = "";
    			return true;
    		}
    		return false;
    	}
    </script>
</head>
<body>
    <form name="_form" action="EGuaranteeRequestControl" method="post" enctype="multipart/form-data" onsubmit="return submit_click()">
        <table>
            <tr>
                <td>Select File : </td>
                <td><input  name="file" type="file"/> </td>
            </tr>
            <tr>
                <td>Enter Filename : </td>
                <td><input type="text" name="filename" size="20"/> </td>
            </tr>
        </table>
        <p/>
        <input type="submit" value="Upload File"/> 
        <input type="reset" value="Reset">
        <img alt="loading" name="loading" src="./images/loading.gif" width="120px" style="display: none">
    </form>
 </body>
</html> 

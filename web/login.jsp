<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登陆界面</title>
</head>
<body>
<form class="loginForm">
    <table border="1px" cellspacing="0" cellpadding="10px" align="center">
        <tr>
            <td colspan="2" align="center"><h1>登陆页面</h1></td>
        </tr>
        <tr>
            <td>username</td>
            <td><input type="text" name="username" value="${username}"/></td>
        </tr>
		<tr>
			<td>address</td>
			<td><input type="text" name="address"/></td>
		</tr>
        <tr>
            <td colspan="2" align="center">
                <input type="button" onclick="loginServlet()" value="登陆"/>&nbsp;&nbsp;
                <input type="reset" value="重置"/>
            </td>
        </tr>
    </table>
</form>

</body>
</html>
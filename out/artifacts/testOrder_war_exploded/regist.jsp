<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>注册界面</title>
</head>
<body>
<form class="registForm">
    <table border="1px" cellspacing="0" cellpadding="10px" align="center">
        <tr>
            <td colspan="2" align="center"><h1>注册页面</h1></td>
        </tr>
        <tr>
            <td>username</td>
            <td>
                <input type="text" name="username" class="username" onblur=""/>
                <span class="namemsg"></span>
            </td>
        </tr>
		<tr>
			<td>birthday</td>
			<td>
				<input type="date" name="birthday" class="birthday" />
			</td>
		</tr>
		<tr>
			<td>address</td>
			<td>
				<input type="text" name="address" class="address"/>
			</td>
		</tr>
		<tr>
			<td>gender</td>
			<td>
				<input type="radio" name="sex" class="sex" value="男"/>男
				<input type="radio" name="sex" class="sex" value="女"/>女
			</td>
		</tr>
        <tr>
            <td colspan="2" align="center">
                <input type="button" onclick="" value="注册"/>&nbsp;&nbsp;
                <input type="reset" value="重置"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
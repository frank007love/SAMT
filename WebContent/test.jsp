<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
</head>
<body>
this is a test page.
<%=request.getAttribute("path") %>
<a href="test.action?test=MyRequestTest">test</a>

<input type="button" value="Close2" onClick="javascript:window.close();">
<input type="button" value="Close"
	onClick="javascript:top.SqueezeBox.close();">
</body>
</html>
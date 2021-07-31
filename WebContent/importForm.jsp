<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<p><font color="red">${message}</font><p> 
<s:form action="upLoadAAProfile" method="POST" enctype="multipart/form-data">
	<s:file name="upload" id="upload" size="50" onchange=""/>
	<s:submit value="匯入"/>
</s:form>



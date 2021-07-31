<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=big5">
<title>Cook Tree</title>
<script type="text/javascript" src="SqueezeBox/js/build.js"></script>
<link rel="stylesheet" href="SqueezeBox/css/squeezebox.css"
	type="text/css" media="screen" />
<script type="text/javascript" src="SqueezeBox/js/SqueezeBox.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="js/JSCookTree.js"></SCRIPT>
<LINK REL="stylesheet" HREF="CookTreeTheme/ThemeXP/theme.css"
	TYPE="text/css">
<SCRIPT LANGUAGE="JavaScript" SRC="CookTreeTheme/ThemeXP/theme.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript"><!-- 
var treeHorizMain = [
	[null, 'TABLE class = *Main', "test.jsp", null, null,
		[null, 'TR', null, null, null,
			[null, 'TD class = *MainItem', null, null, null,
				[null, 'SPAN class = *MainItemLeft', null, null, null],
				[null, 'SPAN class = *MainItemText', null, null, null],
				[null, 'SPAN class = *MainItemRight', null, null, null]
			],
			['<img alt="" src="CookTreeTheme/ThemeXP/folder1.gif">', '<i>... next item</i>', null, null, null]
		]
	]
	,
	[null, 'TABLE class = *Main', null, null, null,
		[null, 'TR', null, null, null,
			[null, 'TD class = *MainItem', null, null, null,
				[null, 'SPAN class = *MainItemLeft', null, null, null],
				[null, 'SPAN class = *MainItemText', null, null, null],
				[null, 'SPAN class = *MainItemRight', null, null, null]
			],
			['<img alt="" src="CookTreeTheme/ThemeXP/folder1.gif">', '<i>... next item</i>', null, null, null]
		]
	] 
];


--></SCRIPT>
</head>

<body>
<DIV ID=myMenuID></DIV>
<SCRIPT LANGUAGE="JavaScript">
　　<!--
　　　　ctDraw ('myMenuID', treeHorizMain, ctThemeXP1, 'ThemeXP', 0, 0);
　　-->
</SCRIPT>

<a href="test.action?test=MyRequestTest" onClick="window.alert('test')";>test</a>
<input type="button" value="test" onClick="window.alert('test')" />
</body>
</html>
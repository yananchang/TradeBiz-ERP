<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title>TradeBiz Management Platform</title>
<!-- 比如: 点击"部门管理"应该进入DeptAction_list,但是很多过滤器起作用, 规定:不行就往登录页面跳; 
     和当前地址不相同, 应该要当前的顶层框架集变成要访问的地址; -->
<script type="text/javascript">
	if(self.location != top.location){
		top.location=self.location;
	}
	
</script>

</head>
<frameset rows="125,*" name="topFrameset" border="0">
	<frame name="top_frame" scrolling="no"  target="middleFrameSet" src="homeAction_title">	
	<frameset cols="202,*" height="100%" name="middle" frameborder="no" border="0" framespacing="0">
		<frame name="leftFrame" class="leftFrame" target="main" scrolling="no" src="homeAction_toleft.action?moduleName=home" />
		<frame name="main" class="rightFrame" src="homeAction_tomain.action?moduleName=home" />
	</frameset>
</frameset>

<noframes>
<body>
    <p>此网页使用了框架，但您的浏览器不支持框架。</p>
</body>
</noframes>

</html>
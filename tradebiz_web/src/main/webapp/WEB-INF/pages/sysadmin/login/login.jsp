<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>TradeBiz ERP</title>
	<link rel="stylesheet" rev="stylesheet" type="text/css" href="${ctx}/skin/default/css/login.css" media="all" />
	<script src="${ctx}/components/pngfix/DD_belatedPNG.js"></script>
	<script> DD_belatedPNG.fix('*'); </script>
	
	<script type="text/javascript">
		if(self.location != top.location){
			top.location=self.location;
		}
	</script>
</head>

<body>
<form id="login_main" method="post">
<div id="png">
	<div class="box">
			<div class="inputstyle">
				<div class="inputlable">Username：
					<input type="text" value="${userName}" name="username" id="userName" onFocus="this.select();" title="Pls input username"/>
					<div id="ts" style="z-index:1;">
					</div>
				</div>

			    <div class="inputlable">Password：
					<input type="password" value="${password}" name="password" id="password" onfocus="$('#ts').css('display','none');this.select();"
						onKeyDown="javascript:if(event.keyCode==13){ submitFind(); }" title="Pls input password"/>
				</div>
			</div>
			<div class="btnstyle">
				<input  class="loginImgOut" value="" type="button" onclick="formSubmit('${ctx}/login.action','_self');"
				  onmouseover="this.className='loginImgOver'" 
				  onmouseout="this.className='loginImgOut'"
				/>
				<input class="resetImgOut" value="" type="button"   
				  onmouseover="this.className='resetImgOver'" 
				  onmouseout="this.className='resetImgOut'"
				/>
			</div>
		  	<div class="msgtip">
				<c:if test="${!empty errorInfo}">
					${errorInfo}
				</c:if>
			</div>
	</div>
</div>
</form>

<script type="text/JavaScript">
	document.getElementById('login_main').userName.focus();
</script>

</body>
</html>



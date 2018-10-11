<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
</head>

<body>
<form name="icform" method="post">
      <input type="hidden" name="id" value="${id}"/>
      
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('deptAction_update','_self');this.blur();">Save</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">Return</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   Change Dept
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">Parent Dept:</td>
	            <td class="tableContent">
	                <!--struts2标签默认具有自动回显功能  原理:它默认会去取值root栈的栈顶,此处取到了root栈栈顶的dept对象  -->
	            	<s:select name="parent.id" list="deptList"
	            		listKey="id" listValue="deptName"
	            		headerKey="" headerValue="--请选择--"
	            	></s:select>
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">Dept Name:</td>
	            <td class="tableContent"><input type="text" name="deptName" value="${deptName }"/>
	            </td>
	        </tr>		
		</table>
	</div>
 </form>
</body>
</html>
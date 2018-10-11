<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js"></script>
</head>

<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('userAction_insert','_self');this.blur();">Save</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">Return</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
   Add New User
  </div> 
  </div>
  </div>
  

 
    <div>
		<table class="commonTable" cellspacing="1">
       		<tr>
	            <td class="columnTitle">Dept:</td>
	            <td class="tableContent">
	            	<s:select name="dept.id" list="deptList"
	            		listKey="id" listValue="deptName"
	            		headerKey="" headerValue="--Pls select--"
	            	></s:select>
	            </td>
	        </tr>
        	<tr>
	            <td class="columnTitle">Login Name:</td>
	            <td class="tableContent"><input type="text" name="userName" value=""/></td>
	            <td class="columnTitle">Status:</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="state" value="1" checked class="input"/>Activate
	            	<input type="radio" name="state" value="0" class="input"/>De-activalte
	            </td>
	        </tr>
        	<tr>
	            <td class="columnTitle">Name:</td>
	            <td class="tableContent"><input type="text" name="userinfo.name" value=""/></td>
	            <td class="columnTitle">Manager:</td>
	            <td class="tableContent">
	            	<s:select name="userinfo.manager.id" list="userList"
	            		listKey="id" listValue="userinfo.name"
	            		headerKey="" headerValue="--Pls select--"
	            	></s:select>
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">Hire Date</td>
	            <td class="tableContent">
					<input type="text" style="width:90px;" name="userinfo.joinDate"
	            	 value=""
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
				<td class="columnTitle">Salary:</td>
	            <td class="tableContent"><input type="text" name="userinfo.salary" value=""/></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">Level:</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="userinfo.degree" value="0" class="input"/>Admin
	            	<input type="radio" name="userinfo.degree" value="1" class="input"/>Cross-Dept
	            	<input type="radio" name="userinfo.degree" value="2" class="input"/>ParentDept Manager
	            	<input type="radio" name="userinfo.degree" value="3" class="input"/>Dept Manager
	            	<input type="radio" name="userinfo.degree" value="4" class="input"/>Employee
	            </td>
				<td class="columnTitle">Gender:</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="userinfo.gender" value="1" class="input"/>Male
	            	<input type="radio" name="userinfo.gender" value="0" class="input"/>Female
	            </td>
	        </tr>	
        	<tr>
	            <td class="columnTitle">Position:</td>
	            <td class="tableContent"><input type="text" name="userinfo.station" value=""/></td>
	            <td class="columnTitle">Tel:</td>
	            <td class="tableContent"><input type="text" name="userinfo.telephone" value=""/></td>
	        </tr>	
        	<tr>
        	    <td class="columnTitle">Email:</td>
	            <td class="tableContent"><input type="text" name="userinfo.email" value=""/></td>
	            <td class="columnTitle">DOB:</td>
	            <td class="tableContent">
					<input type="text" style="width:90px;" name="userinfo.birthday"
	            	 value=""
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
	        </tr>	
        	<tr>
        	    <td class="columnTitle">Serial No.:</td>
	            <td class="tableContent"><input type="text" name="userinfo.orderNo" value=""/></td>
	            <td class="columnTitle">Remark:</td>
	            <td class="tableContent">
	            	<textarea name="userinfo.remark" style="height:120px;"></textarea>
	            </td>
	        </tr>	
		</table>
	</div>
 
 
</form>
</body>
</html>


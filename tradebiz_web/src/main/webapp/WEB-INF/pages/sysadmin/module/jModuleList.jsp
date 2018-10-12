<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
</head>

<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="view"><a href="#" onclick="formSubmit('moduleAction_toview','_self');this.blur();">View</a></li>
<li id="new"><a href="#" onclick="formSubmit('moduleAction_tocreate','_self');this.blur();">Add</a></li>
<li id="update"><a href="#" onclick="formSubmit('moduleAction_toupdate','_self');this.blur();">Mod</a></li>
<li id="delete"><a href="#" onclick="formSubmit('moduleAction_delete','_self');this.blur();">Del</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
    Module List
  </div> 
  </div>
  </div>
  
<div>


<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">No</td>
		<td class="tableHeader">Name</td>
		<td class="tableHeader">Layer #</td>
		<td class="tableHeader">Authority</td>
		<td class="tableHeader">Link</td>
		<td class="tableHeader">Type</td>
		<td class="tableHeader">Belongs to</td>
		<td class="tableHeader">Status</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
	${links }
	<c:forEach items="${results}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="checkbox" name="id" value="${o.id}"/></td>
		<td>${status.index+1}</td>
		<td><a href="moduleAction_toview?id=${o.id}">${o.name}</a></td>
		<td>${o.layerNum}</td>
		<td>${o.cpermission}</td>
		<td>${o.curl}</td>
		<td>${o.ctype}</td>
		<td>${o.belong}</td>
		<td>${o.state}</td>
	</tr>
	</c:forEach>
	
	</tbody>
</table>
</div>
 
</div>
 
 
</form>
</body>
</html>


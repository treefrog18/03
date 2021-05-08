<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<%-- EL/JSTL태그 사용으로 주석처리
<%@ page import="com.model2.mvc.service.domain.Purchase" %>    
<%@ page import="com.model2.mvc.service.domain.User" %>  

<%
  Purchase purchase = (Purchase)request.getAttribute("purchase");
  User user = (User)request.getAttribute("user");
  %>	  
 --%>
  
 

<html>
<head>
<title>Insert title here</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=0" method="post">

다음과 같이 구매가 되었습니다.

<table border=1>
	<tr>
		<td>물품번호</td>
		<td>${purchase.prodNo}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자아이디</td>
		<td>${user.userId}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td>
		<%--주석처리
		<%if (purchase.getPaymentOption().equals("1"))  {%>
				현금구매
			<%}else{ %>
				신용구매
			<%} %>	
		 --%>
		<c:if test = "${purchase.paymentOption.equals('1')}">
			현금구매
		</c:if>
		<c:if test = "${purchase.paymentOption.equals('2')}">
			신용구매
		</c:if>
		</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<td>${purchase.receiverName}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<td>${purchase.receiverPhone}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<td>${purchase.divyAddr}</td>
		<td></td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<td>${purchase.divyRequest}</td>
		<td></td>
	</tr>
	<tr>
		<td>배송희망일자</td>
		<td>${purchase.divyDate}</td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>
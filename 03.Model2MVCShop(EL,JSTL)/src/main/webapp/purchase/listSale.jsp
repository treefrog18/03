<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--EL/JSTL 사용으로 주석처리
<%@ page import="java.util.List"  %>
<%@ page import="com.model2.mvc.service.domain.User" %>
<%@ page import="com.model2.mvc.common.util.CommonUtil"%>
<%@ page import="com.model2.mvc.service.domain.Purchase" %>
<%@ page import="com.model2.mvc.common.Search" %>
<%@ page import="com.model2.mvc.common.Page"%>

<%
List<Purchase> list= (List<Purchase>)request.getAttribute("list");
Search search=(Search)request.getAttribute("search");
Page resultPage=(Page)request.getAttribute("resultPage");
//==> null 을 ""(nullString)으로 변경
String searchCondition = CommonUtil.null2str(search.getSearchCondition());
String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
%>
 --%>    

<html>
<head>
<title>구매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listSale.do" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">판매 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">
		<%--EL/JSTL
		전체 <%= resultPage.getTotalCount() %> 건수,	현재 <%= resultPage.getCurrentPage() %> 페이지</td>
		 --%>
		전체 ${resultPage.totalCount } 건수,	현재 ${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">전화번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<%--EL/JSTL 사용 
	<% 	
		for(int i=0; i<list.size(); i++) {
			Purchase purchase = list.get(i);
			<tr class="ct_list_pop">
		<td align="center">
		<a href="/getPurchase.do?tranNo=<%=purchase.getTranNo()%>"><%=i + 1%></a>
		</td>
		<td></td>
		<td align="left">
			<a href="/getUser.do?userId=<%=purchase.getBuyer().getUserId()%>"><%=purchase.getBuyer().getUserId()%></a>
		</td>
		<td></td>
		<td align="left"><%=purchase.getReceiverName()%></td>
		<td></td>
		<td align="left"><%=purchase.getReceiverPhone()%></td>
		<td></td>
		<td align="left">
			현재 
			<%System.out.println("트랜코드값:"+purchase.getTranCode()); 
			%>
			<%String trancode = "1"; 
				if(trancode.equals("1")){
					System.out.println("trancode의 값은="+trancode);
				}
			%>
			<%if((purchase.getTranCode()).equals("1")) {%>
			<%System.out.println("구매완료"); %> 
			구매완료
			<%} %>
			
			<%if((purchase.getTranCode()).equals("2")) {%> 
			배송중
			<%} %>
			
			<%if((purchase.getTranCode()).equals("3")) {%> 
			배송완료
			<%} %>
			상태 입니다.</td>
		<td></td>
		<td align="left">		
			<%if((purchase.getTranCode()).equals("2")) {%>
			<a href="/updateTranCode.do?tranNo=<%=purchase.getTranNo()%>&tranCode=<%=purchase.getTranCode()%>">물건도착</a>
			<%} %>
		</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	<% } %>
</table>
	%>
	--%>
	<c:set var="i" value="0"/>
	<c:forEach var="purchase" items="${list }">
		<c:set var="i" value="${i+1 }" />
		<tr class="ct_list_pop">
		<td align="center">
		<c:if test="${purchase.tranCode.equals('1')}">
		<a href="/getPurchase.do?tranNo=${purchase.tranNo}">${i}</a>
		</c:if>
		<c:if test="${purchase.tranCode.equals('2') || purchase.tranCode.equals('3')}">
		${i}
		</c:if>
		<%--<a href="/getPurchase.do?tranNo=${purchase.tranNo}">${i}</a> --%>
		</td>
		<td></td>
		<td align="left">
			<a href="/getUser.do?userId=${purchase.buyer.userId}">${purchase.buyer.userId}</a>
		</td>
		<td></td>
		<td align="left">${purchase.receiverName}</td>
		<td></td>
		<td align="left">${purchase.receiverPhone}</td>
		<td></td>
		<td align="left">
			현재 
			<c:if test= "${purchase.tranCode.equals('1')}">
			구매완료
			</c:if>
			<c:if test= "${purchase.tranCode.equals('2')}">
			배송중
			</c:if>
			<c:if test= "${purchase.tranCode.equals('3')}">
			배송완료
			</c:if>
			상태 입니다.</td>
		<td></td>
		<td align="left">		
		<c:if test= "${purchase.tranCode.equals('1')}">
		<a href="/updateTranCode.do?tranNo=${purchase.tranNo}&tranCode=${purchase.tranCode}&menu=listSale">배송하기</a>
		</c:if>
		</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	</c:forEach>
	
</table>
<!-- PageNavigation Start... -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value=""/>
			<%--EL/JSTL
			<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					◀ 이전
			<% }else{ %>
					<a href="javascript:fncGetPurchaseList('<%=resultPage.getCurrentPage()-1%>')">◀ 이전</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncGetPurchaseList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					이후 ▶
			<% }else{ %>
					<a href="javascript:fncGetPurchaseList('<%=resultPage.getEndUnitPage()+1%>')">이후 ▶</a>
			<% } %>
			 --%>
			 <jsp:include page="../common/pageNavigator.jsp"/>
    	</td>
	</tr>
</table>
<!-- PageNavigation End... -->
</form>

</div>

</body>
</html>
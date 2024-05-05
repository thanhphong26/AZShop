<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<%@page import="com.azshop.utils.CSRF" %>
<meta http-equiv="Content-Security-Policy" content=" "/> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
#height_reset {
	height: 0;
}
</style>
<!-- Header -->
<header class="header-v4">
	<!-- Header desktop -->
	<div class="container-menu-desktop" id="height_reset">

		<div class="wrap-menu-desktop how-shadow1">
			<nav class="limiter-menu-desktop container">

				<!-- Logo desktop -->
				<a href="<c:url value='/home'/>" class="logo"> <img src="https://storage.googleapis.com/web-budget1/Image/logo.png"
					alt="IMG-LOGO">
				</a>

				<!-- Menu desktop -->
				<div class="menu-desktop">
					<ul class="main-menu">
						<li
							<c:if test="${fn:contains(pageContext.request.requestURI, 'home')}">class="active-menu"</c:if>><a
							href="<c:url value='/home'/>">Trang chủ</a></li>

						<li
							<c:if test="${fn:contains(pageContext.request.requestURI, 'introduction')}">class="active-menu"</c:if>><a
							href="<c:url value='/introduction'/>">Giới thiệu</a></li>

						<li
							<c:if test="${fn:contains(pageContext.request.requestURI, 'products')}">class="active-menu"</c:if>><a
							href="<c:url value='/products'/>">Sản phẩm</a></li>

						

						<li><a href="#" onclick="scrollToBottom()">Liên hệ</a></li>
					</ul>
				</div>

				<!-- Icon header -->
				<div class="wrap-icon-header flex-w flex-r-m">

					<ul class="main-menu">
						<li><a href="<c:url value='/infoUser'/>"
							class="icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11">
								<i class=".zmdi fa-solid fa-user fa-xl"></i>
						</a>
							<ul class="sub-menu">
								<li><a href="<c:url value='/infoUser'/>">Thông tin cá
										nhân</a></li>
								<li><a href="<c:url value='/logout'/>">Đăng xuất</a></li>
							</ul></li>
					</ul>
					<div
						class="icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11 icon-header-noti js-show-cart"
						data-notify="${carts != null ? carts.size() : 0}">
						<i class="zmdi zmdi-shopping-cart"></i>
					</div>
				</div>
			</nav>
		</div>
	</div>
	<!-- Modal Search -->
	<%
	String csrfToken = CSRF.getToken();
	javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie("csrf", csrfToken);
	cookie.setSecure(true);
	cookie.setHttpOnly(true);
	response.addCookie(cookie);
	%>
	<div class="modal-search-header flex-c-m trans-04 js-hide-modal-search">
		<div class="container-search-header">
			<button
				class="flex-c-m btn-hide-modal-search trans-04 js-hide-modal-search">
				<img src="images/icons/icon-close2.png" alt="CLOSE">
			</button>

			<form class="wrap-search-header flex-w p-l-15">
				<input type="hidden" name="csrfToken" value="<%=csrfToken%>" />

				<button class="flex-c-m trans-04">
					<i class="zmdi zmdi-search"></i>
				</button>
				<input class="plh3" type="text" name="search"
					placeholder="Search...">
			</form>
		</div>
	</div>
</header>

<!-- Cart -->
<c:if test="${not empty carts}">
	<div class="wrap-header-cart js-panel-cart">
		<div class="s-full js-hide-cart"></div>
		<div class="header-cart flex-col-l p-l-65 p-r-25">
			<div class="header-cart-title flex-w flex-sb-m p-b-8">
				<span class="mtext-103 cl2"> Giỏ hàng của bạn </span>

				<div
					class="fs-35 lh-10 cl2 p-lr-5 pointer hov-cl1 trans-04 js-hide-cart">
					<i class="zmdi zmdi-close"></i>
				</div>
			</div>

			<div class="header-cart-content flex-w js-pscroll">
				<ul class="header-cart-wrapitem w-full">
					<c:forEach var="i" items="${carts}">
						<li class="header-cart-item flex-w flex-t m-b-12">
							<div class="header-cart-item-img">
								<img src="${i.image}" alt="IMG">
							</div>

							<div class="header-cart-item-txt p-t-8">
								<a href="#"
									class="header-cart-item-name m-b-18 hov-cl1 trans-04">
									${i.productName} </a><span class="header-cart-item-info"
									style="font-size: 0.8rem;">${i.quantity} x <fmt:formatNumber
										type="currency" value="${i.promotionPrice}" currencyCode="VND"
										pattern="#,##0 VND" var="formattedPrice" /> ${formattedPrice}
								</span>
							</div>
						</li>
					</c:forEach>
				</ul>

				<div class="w-full">
					<div class="header-cart-total w-full p-tb-40">
						Tổng tiền:<span> <fmt:formatNumber type="currency"
								value="${subTotal}" currencyCode="VND" pattern="#,##0 VND"
								var="formattedPrice" /> ${formattedPrice}
						</span>
					</div>

					<div class="header-cart-buttons flex-w w-full">
						<a href="<c:url value='/carts'/>"
							class="flex-c-m stext-101 cl0 size-107 bg3 bor2 hov-btn3 p-lr-15 trans-04 m-r-8 m-b-10">
							Đi đến giỏ hàng </a>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>

<script>
	function scrollToBottom() {
		window.scrollTo(0, document.body.scrollHeight);
	}
</script>
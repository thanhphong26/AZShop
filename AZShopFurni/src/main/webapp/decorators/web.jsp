<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!doctype html>
<html lang="en">
<head>
<title><c:if
		test="${fn:contains(pageContext.request.requestURI, 'home')}">Trang chủ</c:if>
	<c:if test="${fn:contains(pageContext.request.requestURI, 'products')}">Sản phẩm</c:if>
	<c:if test="${fn:contains(pageContext.request.requestURI, 'infoUser')}">Tài khoản</c:if>
	<c:if
		test="${fn:contains(pageContext.request.requestURI, 'listVoucher')}">Kho voucher</c:if>
	<c:if test="${fn:contains(pageContext.request.requestURI, 'Order')}">Đơn hàng</c:if>
	<c:if test="${fn:contains(pageContext.request.requestURI, 'carts')}">Giỏ hàng</c:if>
	<c:if test="${fn:contains(pageContext.request.requestURI, 'checkout')}">Thanh toán</c:if>
	<c:if
		test="${fn:contains(pageContext.request.requestURI, 'introduction')}">Giới thiệu</c:if>
	<c:if
		test="${fn:contains(pageContext.request.requestURI, 'updateUser')}">Chỉnh sửa thông tin</c:if>
	<c:if
		test="${fn:contains(pageContext.request.requestURI, 'updateAccount')}">Thay đổi mật khẩu</c:if>
</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="Content-Security-Policy"
	content="
    default-src 'self';
    script-src 'self';
    img-src 'self' https://drive.google.com https://storage.googleapis.com;
    style-src 'self';
    form-action 'self';
">

<!-- <meta http-equiv="Content-Security-Policy" content=" "/> 
    <meta name="robots" content="NONE,NOARCHIVE"> -->

<!--===============================================================================================-->
<link rel="icon" type="image/png"
	href="https://drive.google.com/file/d/1DPn4srTQ9dgsTd5xH2h_5KlgLAZ8qxkT/view?usp=sharing">

<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/vendor/bootstrap/css/bootstrap.min.css"/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/fonts/iconic/css/material-design-iconic-font.min.css"/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/fonts/linearicons-v1.0.0/icon-font.min.css"/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/vendor/animate/animate.css"/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/vendor/css-hamburgers/hamburgers.min.css"/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/css/util.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/css/main.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/css/products/products.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/vendor/animsition/css/animsition.min.css"/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/vendor/perfect-scrollbar/perfect-scrollbar.css"/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/vendor/select2/select2.min.css"/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/vendor/daterangepicker/daterangepicker.css"/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/vendor/slick/slick.css"/>" />
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/vendor/MagnificPopup/magnific-popup.css"/>">
<!--===============================================================================================-->
<link
	href='<c:url value="/templates/web/css/products/tiny-slider.css"/>'
	rel="stylesheet" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
	rel="stylesheet">  
<link rel="stylesheet"
    href="/templates/web/css/infor/all.min.css"/>  

<link
	href='<c:url value="/templates/web/css/products/product-detail.css"/>'
	rel="stylesheet" />

<link href='<c:url value="/templates/web/css/carts/carts.css"/>'
	rel="stylesheet" />

<link href='<c:url value="/templates/web/css/checkout/checkout.css"/>'
	rel="stylesheet" />

<link rel="stylesheet"
	href="<c:url value="/templates/web/css/infor/materialdesignicons.css"/>">
 href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" 
<link
	rel="stylesheet"
	href="<c:url value="/templates/web/css/infor/boxicons.min.css"/>">

<link rel="stylesheet"
	href="<c:url value="/templates/web/css/infor/materialdesignicons.css"/>">



</head> --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="Content-Security-Policy"
	content="
    default-src 'self';
    script-src 'self';
    img-src 'self' https://drive.google.com https://storage.googleapis.com https://theme.hstatic.net/1000280685/ https://file.hstatic.net/1000280685/ ;
    style-src 'self' ;
    form-action 'self';
">

<c:set var="pageTitle">
	<c:choose>
		<c:when test="${fn:contains(pageContext.request.requestURI, 'home')}">Trang chủ</c:when>
		<c:when
			test="${fn:contains(pageContext.request.requestURI, 'products')}">Sản phẩm</c:when>
		<c:when
			test="${fn:contains(pageContext.request.requestURI, 'infoUser')}">Tài khoản</c:when>
		<c:when
			test="${fn:contains(pageContext.request.requestURI, 'listVoucher')}">Kho voucher</c:when>
		<c:when test="${fn:contains(pageContext.request.requestURI, 'Order')}">Đơn hàng</c:when>
		<c:when test="${fn:contains(pageContext.request.requestURI, 'carts')}">Giỏ hàng</c:when>
		<c:when
			test="${fn:contains(pageContext.request.requestURI, 'checkout')}">Thanh toán</c:when>
		<c:when
			test="${fn:contains(pageContext.request.requestURI, 'introduction')}">Giới thiệu</c:when>
		<c:when
			test="${fn:contains(pageContext.request.requestURI, 'updateUser')}">Chỉnh sửa thông tin</c:when>
		<c:when
			test="${fn:contains(pageContext.request.requestURI, 'updateAccount')}">Thay đổi mật khẩu</c:when>
	</c:choose>
</c:set>

<title><c:out value="${pageTitle}" /></title>

<link rel="icon" type="image/png" href="/favicon.ico">

<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/vendor/bootstrap/css/bootstrap.min.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/fonts/iconic/css/material-design-iconic-font.min.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/fonts/linearicons-v1.0.0/icon-font.min.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/vendor/animate/animate.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/vendor/css-hamburgers/hamburgers.min.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/css/util.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/css/main.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/css/products/products.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/vendor/animsition/css/animsition.min.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/vendor/perfect-scrollbar/perfect-scrollbar.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/vendor/select2/select2.min.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/vendor/daterangepicker/daterangepicker.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/vendor/slick/slick.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/vendor/MagnificPopup/magnific-popup.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/css/products/tiny-slider.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/css/products/product-detail.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/css/carts/carts.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/css/checkout/checkout.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/css/infor/materialdesignicons.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/templates/web/css/infor/style.css"/>" />
</head>
<body>
	<%@ include file="/common/web/header.jsp"%>
	<div class="m-t-84">
		<decorator:body></decorator:body>
	</div>

	<%@ include file="/common/web/footer.jsp"%>

	<!--===============================================================================================-->
	<script
		src="<c:url value="/templates/web/vendor/jquery/jquery-3.6.4.min.js"/>"></script>
	<script
		src="<c:url value="/templates/web/vendor/sweetalert/sweetalert2@10.js"/>"></script>

	<script
		src="<c:url value="/templates/web/vendor/jquery/jquery-3.7.1.min.js"/>"></script>
	<!--===============================================================================================-->
	<script
		src="<c:url value="/templates/web/vendor/animsition/js/animsition.min.js"/>"></script>
	<!--===============================================================================================-->
	<script
		src="<c:url value="/templates/web/vendor/bootstrap/js/popper.js"/>"></script>
	<script
		src="<c:url value="/templates/web/vendor/bootstrap/js/bootstrap.min.js"/>"></script>
	<!--===============================================================================================-->
	<script
		src="<c:url value="/templates/web/vendor/select2/select2.min.js"/>"></script>
	<script>
		$(".js-select2").each(function() {
			$(this).select2({
				minimumResultsForSearch : 20,
				dropdownParent : $(this).next(".dropDownSelect2")
			});
		})
	</script>
	<!--===============================================================================================-->
	<script
		src="<c:url value="/templates/web/vendor/MagnificPopup/jquery.magnific-popup.min.js"/>"></script>
	<!--===============================================================================================-->
	<script
		src="<c:url value="/templates/web/vendor/perfect-scrollbar/perfect-scrollbar.min.js"/>"></script>
	<script>
		$(".js-pscroll").each(function() {
			$(this).css("position", "relative");
			$(this).css("overflow", "hidden");
			var ps = new PerfectScrollbar(this, {
				wheelSpeed : 1,
				scrollingThreshold : 1000,
				wheelPropagation : false,
			});

			$(window).on("resize", function() {
				ps.update();
			})
		});
	</script>
	<!--===============================================================================================-->
	<script src="<c:url value="/templates/web/js/main.js"/>"></script>
	<script src="<c:url value="/templates/web/js/product-detail.js"/>"></script>
	<%-- <script src="<c:url value="/templates/web/js/tiny-slider.js"/>"></script> --%>

	<!--===============================================================================================-->
	<script
		src="<c:url value="/templates/web/vendor/jquery/jquery-3.7.1.min.js"/>"></script>
	<!--===============================================================================================-->
	<script
		src="<c:url value="/templates/web/vendor/bootstrap/js/popper.js"/>"></script>
	<script
		src="<c:url value="/templates/web/vendor/bootstrap/js/bootstrap.min.js"/>"></script>
	<!--===============================================================================================-->
	<!--===============================================================================================-->
	<script
		src="<c:url value="/templates/web/vendor/daterangepicker/moment.min.js"/>"></script>
	<script
		src="<c:url value="/templates/web/vendor/daterangepicker/daterangepicker.js"/>"></script>
	<!--===============================================================================================-->
	<script src="<c:url value="/templates/web/vendor/slick/slick.min.js"/>"></script>
	<script src="<c:url value="/templates/web/js/slick-custom.js"/>"></script>
	<!--===============================================================================================-->
	<%-- <script src="<c:url value="vendor/parallax100/parallax100.js"/>"></script> --%>
	<script>
		$('.parallax100').parallax100();
	</script>
	<!--===============================================================================================-->
	<script
		src="<c:url value="/templates/web/vendor/MagnificPopup/jquery.magnific-popup.min.js"/>"></script>
	<script>
		$('.gallery-lb').each(function() { // the containers for all your galleries
			$(this).magnificPopup({
				delegate : 'a', // the selector for gallery item
				type : 'image',
				gallery : {
					enabled : true
				},
				mainClass : 'mfp-fade'
			});
		});
	</script>
	<!--===============================================================================================-->
	<script
		src="<c:url value="/templates/web/vendor/isotope/isotope.pkgd.min.js"/>"></script>
	<!--===============================================================================================-->
	<script
		src="<c:url value="/templates/web/vendor/sweetalert/sweetalert.min.js"/>"></script>
	<script>
		$('.js-addwish-b2').on('click', function(e) {
			e.preventDefault();
		});

		$('.js-addwish-b2').each(
				function() {
					var nameProduct = $(this).parent().parent().find(
							'.js-name-b2').html();
					$(this).on(
							'click',
							function() {
								swal(nameProduct,
										"được thêm vào danh sách yêu thích !",
										"success");

								$(this).addClass('js-addedwish-b2');
								$(this).off('click');
							});
				});

		$('.js-addwish-detail').each(
				function() {
					var nameProduct = $(this).parent().parent().parent().find(
							'.js-name-detail').html();

					$(this).on(
							'click',
							function() {
								swal(nameProduct,
										"được thêm vào danh sách yêu thích !",
										"success");

								$(this).addClass('js-addedwish-detail');
								$(this).off('click');
							});
				});

		/*---------------------------------------------*/

		$('.js-addcart-detail').each(
				function() {
					var nameProduct = $(this).parent().parent().parent()
							.parent().find('.js-name-detail').html();
					$(this).on('click', function() {
						swal(nameProduct, "is added to cart !", "success");
					});
				});
	</script>
	<!--===============================================================================================-->
	<script
		src="<c:url value="/templates/web/vendor/perfect-scrollbar/perfect-scrollbar.min.js"/>"></script>
	<script>
		$('.js-pscroll').each(function() {
			$(this).css('position', 'relative');
			$(this).css('overflow', 'hidden');
			var ps = new PerfectScrollbar(this, {
				wheelSpeed : 1,
				scrollingThreshold : 1000,
				wheelPropagation : false,
			});

			$(window).on('resize', function() {
				ps.update();
			})
		});
	</script>
	<script>
		function setRating(index) {
			// Get all buttons
			var stars = document.getElementsByClassName("star");

			// Remove highlight class from all buttons
			for (var i = 0; i < stars.length; i++) {
				stars[i].classList.remove("highlight");
			}

			// Add highlight class to buttons from 1 to the clicked index
			for (var i = 0; i < index; i++) {
				stars[i].classList.add("highlight");
			}

			// Update the value of the hidden input with the selected index
			document.getElementById("ratingInput").value = index;
		}
		function handleButtonClick(button) {
			// Remove the active class from all buttons
			var buttons = document.querySelectorAll('.btn-group button');
			buttons.forEach(function(btn) {
				btn.style.borderColor = '#bdc3c7';
			});

			// Add the active class to the clicked button
			button.style.borderColor = '#4b4b4b'; // Change to your desired active border color

			// Additional logic if needed
			// For example, you can update some hidden input with the selected value
			var selectedValue = button.value;
			document.getElementById('hiddenInput').value = selectedValue;
		}
	</script>
</body>
</html>
<%@page import="com.azshop.utils.CSRF"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@page import="com.azshop.utils.CSRF"%>
<head>
<link
	href='<c:url value="/templates/web/css/products/product-detail.css"/>'
	rel="stylesheet" />
<link href='<c:url value="/templates/web/css/infor/style.css"/>'
	rel="stylesheet" />
</head>
<body>
	<header>
		<div class="container pt-5">
			<nav class="d-flex">
				<h6 class="mb-0">
					<a href="<c:url value='/home'/>" class="link-dark detail32">Home</a>
					<span class="mx-2"> > </span> <a
						href="<c:url value='/products?cateId=${product.categoryID}'/>"
						class=" detail32">${product.categoryName}</a> <span class="mx-2">
						> </span> <a href="#" class="link-dark detail32">${product.productName}</a>
				</h6>
			</nav>
		</div>
	</header>

	<section class="py-5">
		<div class="container">
			<div class="row gx-5">
				<aside class="detail1 col-lg-6">
					<div class="border rounded-4 mb-3 d-flex justify-content-center">
						<a id="mainImageLink" data-fslightbox="mygalley" class="rounded-4"
							target="_blank" data-type="image" href="#"> <img
							id="mainImage detail2" class=" rounded-4 fit "
							src="${product.listItem[0].image}" />
						</a>
					</div>
					<div class="d-flex justify-content-center mb-3 product-container">
						<c:forEach items="${product.listItem}" var="item" varStatus="loop">
							<div class="detail3">
								<a data-fslightbox="mygalley"
									class="border mx-1 rounded-2 item-thumb" target="_blank"
									data-type="image"
									onmouseover="changeMainImage('${item.image}')"> <img id="detail4"
									width="100" height="100" class=" rounded-2"
									src="${item.image}" />
								</a>
							</div>
						</c:forEach>
					</div>
				</aside>
				<main class="col-lg-6">
					<div class="ps-lg-3">
						<h3 class="title text-dark">${product.productName}</h3>
						<div class="d-flex flex-row my-3">
							<div class="text-warning mb-1 me-2">
								<span class="stext-105 cl3"> <c:choose>
										<c:when test="${product.avgRating >= 1}">
											<i><img alt="sao"
												src="<c:url value="/templates/web/images/star.png"/>"
												width="15" height="15"></i>
										</c:when>
									</c:choose> <c:choose>
										<c:when test="${product.avgRating >= 2}">
											<i><img alt="sao"
												src="<c:url value="/templates/web/images/star.png"/>"
												width="15" height="15"></i>
										</c:when>
									</c:choose> <c:choose>
										<c:when test="${product.avgRating >= 3}">
											<i><img alt="sao"
												src="<c:url value="/templates/web/images/star.png"/>"
												width="15" height="15"></i>
										</c:when>
									</c:choose> <c:choose>
										<c:when test="${product.avgRating >= 4}">
											<i><img alt="sao"
												src="<c:url value="/templates/web/images/star.png"/>"
												width="15" height="15"></i>
										</c:when>
									</c:choose> <c:choose>
										<c:when test="${product.avgRating ==5}">
											<i><img alt="sao"
												src="<c:url value="/templates/web/images/star.png"/>"
												width="15" height="15"></i>
										</c:when>
									</c:choose>
								</span>
							</div>
							<span class="text-muted detail5"> | ${product.description}</span>
						</div>
						<div class="mb-3">
							<c:if test="${product.displayedPromotionPrice ne 0}">
								<span class="h5"> <fmt:formatNumber type="currency"
										value="${product.displayedPromotionPrice}" currencyCode="VND"
										pattern="#,##0 VND" var="formattedPrice" />${formattedPrice}
								</span>
								<span class="detail33"> <fmt:formatNumber type="currency"
										value="${product.displayedOriginalPrice}" currencyCode="VND"
										pattern="#,##0 VND" var="formattedOriginalPrice" />${formattedOriginalPrice}
								</span>
							</c:if>
							<c:if test="${product.displayedPromotionPrice eq 0}">
								<span class="h5"> <fmt:formatNumber type="currency"
										value="${product.displayedOriginalPrice}" currencyCode="VND"
										pattern="#,##0 VND" var="formattedPrice" />${formattedPrice}
								</span>
							</c:if>
						</div>

						<div class="row">
							<div class="col-3">Chất liệu</div>
							<div class="col-9">${product.material}</div>
						</div>
						<div class="row">
							<div class="col-3">Nhà cung cấp</div>
							<div class="col-9">${product.supplierName}</div>
						</div>
						<div class="row">
							<div class="col-3">Xuất xứ</div>
							<div class="col-9">${product.origin}</div>
						</div>
						<div class="row">
							<div class="col-3">Mặt hàng</div>
							<div class="col-9">
								<div class="btn-group detail6" role="group"
									aria-label="Basic outlined example">
									<c:forEach items="${product.listItem}" var="item"
										varStatus="loop">
										<button type="button" id="${item.itemID}"
											onmouseover="changeMainImage('${item.image}')"
											onclick="handleButtonClick(this)" value="${item.size}"
											class="detail7">
											<span class="detail8"
												style="background-color: ${item.colorCode};"></span> <span>${item.size}</span>
										</button>
									</c:forEach>
									<div id="errorContainer" class="detail9">
										<c:if test="${message != null}">${message}</c:if>
									</div>
								</div>
							</div>
						</div>
						<hr />
						<div class="row mb-3 input-group">

							<label class="mb-2 detail10">Số lượng </label>
							<button class="px-3" type="button" id="button-addon1"
								data-mdb-ripple-color="dark" onclick="updateQuantity(-1)">
								<i><img alt="sao"
									src="<c:url value="/templates/web/images/minus.png"/>"
									width="15" height="15"></i>
							</button>
							<input type="text"
								class="form-control text-center border border-secondary detail11"
								id="quantityInput" placeholder="1" aria-label="example"
								aria-describedby="button-addon1" />
							<button class="px-3" type="button" id="button-addon2"
								data-mdb-ripple-color="dark" onclick="updateQuantity(1)">
								<i><img alt="plus"
									src="<c:url value="/templates/web/images/plus.png"/>"
									width="15" height="15"></i>
							</button>
						</div>
						<%
						String csrfToken = CSRF.getToken();

						javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie("csrf", csrfToken);
						cookie.setSecure(true);
						cookie.setHttpOnly(true);
						response.addCookie(cookie);
						%>
						<div class="mt-5 row">
							<dd class="col-5">
								<form id="addToCartForm" action="#" method="post">
									<input type="hidden" name="csrfToken" id="csrfToken"
										value="<%=csrfToken%>" />
									<div>
										<input type="hidden" name="selectedItemID" id="selectedItemID"
											value=""> <input type="hidden"
											name="selectedQuantity" id="selectedQuantity" value="1">
									</div>
									<div class="text-center">
										<input onclick="addToCart()"
											class="detail12 btn btn-primary shadow-0 flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04 js-addcart-detail"
											value="Thêm vào giỏ hàng" readonly
											onmouseover="this.style.cursor='pointer';"
											onmouseout="this.style.cursor='default';" />
									</div>
								</form>
							</dd>
						</div>
					</div>
				</main>
			</div>
		</div>
	</section>
	<c:if test="${not empty ratingList}">
		<section class="content-item detail13" id="comments">
			<div class="container">
				<div class="row">
					<div class="col-lg-9">
						<div class="container">
							<div class="detail14">
								<h3 class="detail15">Đánh giá về sản phẩm</h3>
								<div id="reviews" class="review-section detail16">

									<div class="col-md-6">
										<table class="stars-counters detail35">
											<tbody>
												<c:forEach items="${ratingList}" var="item" varStatus="loop">
													<tr>
														<td>
															<button
																class="fit-button fit-button-color-blue fit-button-fill-ghost fit-button-size-medium stars-filter">${item.numOfStar}
																Stars</button>
														</td>
														<td>
															<div class="text-warning mb-1 me-2 detail35">
																<span class="stext-105 cl3"> <c:choose>
																		<c:when test="${product.avgRating > 1}">
																			<i><img alt="sao"
																				src="<c:url value="/templates/web/images/star.png"/>"
																				width="15" height="15"></i>
																		</c:when>
																	</c:choose> <c:choose>
																		<c:when test="${product.avgRating > 2}">
																			<i><img alt="sao"
																				src="<c:url value="/templates/web/images/star.png"/>"
																				width="15" height="15"></i>
																		</c:when>
																	</c:choose> <c:choose>
																		<c:when test="${product.avgRating > 3}">
																			<i><img alt="sao"
																				src="<c:url value="/templates/web/images/star.png"/>"
																				width="15" height="15"></i>
																		</c:when>
																	</c:choose> <c:choose>
																		<c:when test="${product.avgRating >= 4}">
																			<i><img alt="sao"
																				src="<c:url value="/templates/web/images/star.png"/>"
																				width="15" height="15"></i>
																		</c:when>
																	</c:choose> <c:choose>
																		<c:when test="${product.avgRating ==5}">
																			<i><img alt="sao"
																				src="<c:url value="/templates/web/images/star.png"/>"
																				width="15" height="15"></i>
																		</c:when>
																	</c:choose>
																</span><span class="ms-1 star-num detail17">
																	(${item.numOfRating}) </span>
															</div>

														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
								<c:forEach items="${detailList}" var="item" varStatus="loop">
									<div class="media detail18">
										<a class="pull-left" href="#"><img
											class="media-object detail19" src="${item.avatar}" alt=""></a>
										<div class="media-body">
											<h4 class="media-heading detail20">${item.name}</h4>
											<p>${item.content}</p>
											<ul class="list-unstyled list-inline media-detail pull-left">
												<li><i class="fa fa-calendar"></i>${item.evaluationDate}</li>
											</ul>
											<ul class="list-unstyled list-inline media-detail pull-right">
												<li class=""><a href="#" class="detail21">Reply</a></li>
											</ul>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
					<div class="col-lg-3">
						<h5 class="detail21">CÙNG NHÀ CUNG CẤP</h5>
						<c:forEach items="${supProList}" var="item" varStatus="loop">
							<div class="product-card detail23">

								<div class="product-image detail36">
									<a href="<c:url value='/products?id=${item.productID}' />"
										class="product-image"> <img src="${item.displayedImage}"
										class="product-thumb detail37" alt="">
									</a>
								</div>

								<div class="product-info detail38">
									<h2 class="product-brand detail24">${item.productName}</h2>
									<p class="product-short-description detail25">${item.description}</p>
									<c:if test="${item.displayedPromotionPrice ne 0}">
										<span class="price detai27"> <fmt:formatNumber
												type="currency" value="${item.displayedPromotionPrice}"
												currencyCode="VND" pattern="#,##0 VND" var="formattedPrice" />${formattedPrice}
										</span>
										<span class="actual-price detail28"> <fmt:formatNumber
												type="currency" value="${item.displayedOriginalPrice}"
												currencyCode="VND" pattern="#,##0 VND" var="formattedPrice" />${formattedPrice}
										</span>
									</c:if>
									<c:if test="${item.displayedPromotionPrice eq 0}">
										<span class="price detail29"> <fmt:formatNumber
												type="currency" value="${item.displayedOriginalPrice}"
												currencyCode="VND" pattern="#,##0 VND" var="formattedPrice" />${formattedPrice}
										</span>
									</c:if>
								</div>

							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</section>
	</c:if>
	<c:if test="${empty ratingList}">
		<section class="product detail30">
			<h3 class="product-category detail31">Không có đánh giá về sản
				phẩm!</h3>
		</section>
		<section class="product">
			<h2 class="product-category">sản phẩm cùng nhà cung cấp</h2>
			<button class="pre-btn">
				<img src="images/arrow.png" alt="">
			</button>
			<button class="nxt-btn">
				<img src="images/arrow.png" alt="">
			</button>
			<div class="product-container">
				<c:forEach items="${supProList}" var="item" varStatus="loop">
					<div class="product-card">
						<div class="product-image">
							<a href="<c:url value='/products?id=${item.productID}' />"
								class="product-image"><img src="${item.displayedImage}"
								class="product-thumb" alt=""> </a>
						</div>
						<div class="product-info">
							<h2 class="product-brand">${item.productName}</h2>
							<p class="product-short-description">${item.description}</p>
							<c:if test="${item.displayedPromotionPrice ne 0}">
								<span class="price"><fmt:formatNumber type="currency"
										value="${item.displayedPromotionPrice}" currencyCode="VND"
										pattern="#,##0 VND" var="formattedPrice" />${formattedPrice}</span>
								<span class="actual-price"><fmt:formatNumber
										type="currency" value="${item.displayedOriginalPrice}"
										currencyCode="VND" pattern="#,##0 VND" var="formattedPrice" />${formattedPrice}</span>
							</c:if>
							<c:if test="${item.displayedPromotionPrice eq 0}">
								<span class="price"><fmt:formatNumber type="currency"
										value="${item.displayedOriginalPrice}" currencyCode="VND"
										pattern="#,##0 VND" var="formattedPrice" />${formattedPrice}</span>
							</c:if>
						</div>
					</div>
				</c:forEach>
			</div>
		</section>
	</c:if>
	<section class="product">
		<h2 class="product-category">sản phẩm cùng loại</h2>
		<button class="pre-btn">
			<img src="images/arrow.png" alt="">
		</button>
		<button class="nxt-btn">
			<img src="images/arrow.png" alt="">
		</button>
		<div class="product-container">
			<c:forEach items="${cateProList}" var="item" varStatus="loop">
				<div class="product-card">
					<div class="product-image">
						<a href="<c:url value='/products?id=${item.productID}' />"
							class="product-image"><img src="${item.displayedImage}"
							class="product-thumb" alt=""> </a>
					</div>
					<div class="product-info">
						<h2 class="product-brand">${item.productName}</h2>
						<p class="product-short-description">${item.description}</p>
						<c:if test="${item.displayedPromotionPrice ne 0}">
							<span class="price"><fmt:formatNumber type="currency"
									value="${item.displayedPromotionPrice}" currencyCode="VND"
									pattern="#,##0 VND" var="formattedPrice" />${formattedPrice}</span>
							<span class="actual-price"><fmt:formatNumber
									type="currency" value="${item.displayedOriginalPrice}"
									currencyCode="VND" pattern="#,##0 VND" var="formattedPrice" />${formattedPrice}</span>
						</c:if>
						<c:if test="${item.displayedPromotionPrice eq 0}">
							<span class="price"><fmt:formatNumber type="currency"
									value="${item.displayedOriginalPrice}" currencyCode="VND"
									pattern="#,##0 VND" var="formattedPrice" />${formattedPrice}</span>
						</c:if>
					</div>
				</div>
			</c:forEach>
		</div>
	</section>
</body>
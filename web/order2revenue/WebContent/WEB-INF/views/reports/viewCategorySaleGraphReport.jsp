<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

<jsp:include page="../globalcsslinks.jsp"></jsp:include>

<!-- orris -->
<link href="/O2R/seller/css/plugins/morris/morris-0.4.3.min.css"
	rel="stylesheet">

<!-- Data Tables -->
<link href="/O2R/seller/css/plugins/dataTables/dataTables.bootstrap.css"
	rel="stylesheet">
<link
	href="/O2R/seller/css/plugins/dataTables/dataTables.responsive.css"
	rel="stylesheet">
<link
	href="/O2R/seller/css/plugins/dataTables/dataTables.tableTools.min.css"
	rel="stylesheet">

</head>

<body>

	<div id="wrapper">
		<jsp:include page="../sidenavigation.jsp"></jsp:include>
		<div id="page-wrapper" class="gray-bg">
			<jsp:include page="../globalheader.jsp"></jsp:include>
			<div class="wrapper wrapper-content animated fadeInRight"
				id="centerpane">
				<div class="row">
					<div class="col-lg-12">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>Reports</h5>
							</div>
							<div class="ibox-content overflow-h">

								<div class="panel-options">

									<ul class="nav nav-tabs">
										<li class="active"><a data-toggle="tab" href="#tab-1">View
												Graph</a></li>
										<li class=""><a data-toggle="tab" href="#tab-2">View
												Report</a></li>
									</ul>
								</div>
								<div class="tab-content">
									<div id="tab-1" class="tab-pane active col-sm-12 chart-even">
										<div class="row">
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Category</th>
																	<th>Net Sale SP</th>
																	<th>Net Sale N/R</th>
																	<th>Net Sale A/R</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty categoryByNetSaleSP}">
																	<c:forEach items="${categoryByNetSaleSP}"
																		var="categoryDto" varStatus="loop">
																		<tr>
																			<td>${categoryDto.category}</td>
																			<td>${categoryDto.netSpAmount}</td>
																			<td>${categoryDto.netNrAmount}</td>
																			<td>${categoryDto.netAr}</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<div class="flot-chart">
															<div class="flot-chart-content"
																id="line-chart-category-saleSP"></div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Category</th>
																	<th>Net A/R</th>
																	<th>Net Due to be Received</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty categoryByNetAR}">
																	<c:forEach items="${categoryByNetAR}" var="categoryDto"
																		varStatus="loop">
																		<tr>
																			<td>${categoryDto.category}</td>
																			<td>${categoryDto.netAr}</td>
																			<td>${categoryDto.netToBeReceived}</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<div class="flot-chart">
															<div class="flot-chart-content"
																id="line-chart-category-ar"></div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Category</th>
																	<th>Gross Sale Qty</th>
																	<th>Return Qty</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty categoryByGSvSR}">
																	<c:forEach items="${categoryByGSvSR}" var="categoryDto"
																		varStatus="loop">
																		<tr>
																			<td>${categoryDto.category}</td>
																			<td>${categoryDto.grossQty}</td>
																			<td>${categoryDto.saleRetQty}</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<div class="flot-chart">
															<div class="flot-chart-content"
																id="bar-chart-category-gross-qty"></div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Category</th>
																	<th>Gross Sale Amount</th>
																	<th>Return Amount</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty categoryByGSAvRA}">
																	<c:forEach items="${categoryByGSAvRA}" var="categoryDto"
																		varStatus="loop">
																		<tr>
																			<td>${categoryDto.category}</td>
																			<td>${categoryDto.grossSpAmount}</td>
																			<td>${categoryDto.saleRetSpAmount}</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<div class="flot-chart">
															<div class="flot-chart-content"
																id="bar-chart-category-gross-amount"></div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div id="tab-2" class="tab-pane col-sm-12">		
										<div class="row">
											<div class="col-lg-12">
												<div class="float-e-margins graph-brd">
													<div>
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th rowspan="2">Category</th>
																	<th colspan="3" style="text-align: center;">Gross</th>
																	<th colspan="3" style="text-align: center;">Sale Return</th>
																	<th rowspan="2">Sale Return vs Gross Sale</th>
																	<th colspan="3" style="text-align: center;">Net Sale</th>
																	<th rowspan="2">Tax Category</th>
																	<th rowspan="2">Net Tax Liability</th>
																	<th colspan="3" style="text-align: center;">Net Pure Sale</th>
																	<th rowspan="2">Net A/R</th>
																	<th rowspan="2">Net Due to be Received</th>
																</tr>
																<tr>
																	<th>N/R Amount</th>
																	<th>SP Amount</th>
																	<th>Qty</th>
																	<th>N/R Amount</th>
																	<th>SP Amount</th>
																	<th>Qty</th>
																	<th>N/R Amount</th>
																	<th>SP Amount</th>
																	<th>Qty</th>
																	<th>N/R Amount</th>
																	<th>SP Amount</th>
																	<th>Qty</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty shortTableCategory}">
																	<c:forEach items="${shortTableCategory}" var="category"
																		varStatus="loop">
																		<tr>
																			<td>${category.category}</td>
																			<td>${category.grossNrAmount}</td>
																			<td>${category.grossSpAmount}</td>
																			<td>${category.grossQty}</td>
																			<td>${category.saleRetNrAmount}</td>
																			<td>${category.saleRetSpAmount}</td>
																			<td>${category.saleRetQty}</td>
																			<td>${category.saleRetVsGrossSale}</td>
																			<td>${category.netNrAmount}</td>
																			<td>${category.netSpAmount}</td>
																			<td>${category.netQty}</td>
																			<td>${category.taxCategory}</td>
																			<td>${category.netTaxLiability}</td>
																			<td>${category.netPureSaleNrAmount}</td>
																			<td>${category.netPureSaleSpAmount}</td>
																			<td>${category.netPureSaleQty}</td>
																			<td>${category.netAr}</td>
																			<td>${category.netToBeReceived}</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>

													<div class="ibox-content">
														<div id="morris-line-chart"></div>
													</div>
												</div>
											</div>
										</div>

										<div class="col-sm-12">
											<div class="hr-line-dashed"></div>
											<button class="btn btn-primary pull-right" type="submit">Print</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<jsp:include page="../globalfooter.jsp"></jsp:include>

			</div>
		</div>
	</div>

	<jsp:include page="../globaljslinks.jsp"></jsp:include>

	<!-- Flot -->
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.js"></script>
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.resize.js"></script>
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.pie.js"></script>
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.time.js"></script>
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.axislabels.js"></script>
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.symbol.js"></script>
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.spline.js"></script>
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.resize.js"></script>

	<!-- Morris -->
	<script src="/O2R/seller/js/plugins/morris/raphael-2.1.0.min.js"></script>
	<script src="/O2R/seller/js/plugins/morris/morris.js"></script>

	<!-- Morris demo data-->
	<script src="/O2R/seller/js/demo/morris-demo.js"></script>

	<!-- ChartJS-->
	<script src="/O2R/seller/js/plugins/chartJs/Chart.min.js"></script>
	<script src="/O2R/seller/js/demo/flot-demo-1.js"></script>


	<!-- Data Tables -->
	<script src="/O2R/seller/js/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="/O2R/seller/js/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script
		src="/O2R/seller/js/plugins/dataTables/dataTables.responsive.js"></script>
	<script
		src="/O2R/seller/js/plugins/dataTables/dataTables.tableTools.min.js"></script>
	<script>
		//Script for Bar Chart

		var temp1 = [];
		var categoryByNetSaleSP = [];
		var i = 1;
		<c:forEach items="${categoryByNetSaleSP}" var="categoryDto" varStatus="loop">
		var arr1 = [ i, '${categoryDto.netSpAmount}' ];
		var arr2 = [ i++, '${categoryDto.category}' ];
		temp1.push(arr1);
		categoryByNetSaleSP.push(arr2);
		</c:forEach>

		var temp2 = [];
		var categoryByNetAR = [];
		var i = 1;
		<c:forEach items="${categoryByNetAR}" var="categoryDto" varStatus="loop">
		var arr1 = [ i, '${categoryDto.netAr}' ];
		var arr2 = [ i++, '${categoryDto.category}' ];
		temp2.push(arr1);
		categoryByNetAR.push(arr2);
		</c:forEach>

		var temp3 = [];
		var categoryByGSvSR = [];
		var i = 1;
		<c:forEach items="${categoryByGSvSR}" var="categoryDto" varStatus="loop">
		var arr1 = [ i, '${categoryDto.grossQty}' ];
		var arr2 = [ i++, '${categoryDto.category}' ];
		temp3.push(arr1);
		categoryByGSvSR.push(arr2);
		</c:forEach>

		var temp4 = [];
		var categoryByGSAvRA = [];
		var i = 1;
		<c:forEach items="${categoryByGSAvRA}" var="categoryDto" varStatus="loop">
		var arr1 = [ i, '${categoryDto.grossSpAmount}' ];
		var arr2 = [ i++, '${categoryDto.category}' ];
		temp4.push(arr1);
		categoryByGSAvRA.push(arr2);
		</c:forEach>
		
		$(window)
				.load(
						function() {

							flotline(temp1, categoryByNetSaleSP,
								"#line-chart-category-saleSP");
							flotbar(temp2, categoryByNetAR,
								"#line-chart-category-ar");
							flotbar(temp3, categoryByGSvSR,
								"#bar-chart-category-gross-qty");
							flotbar(temp4, categoryByGSAvRA,
								"#bar-chart-category-gross-amount");
							
							$('.dataTables-example')
									.dataTable(
											{
												responsive : true,
												"dom" : 'T<"clear">lfrtip',
												"tableTools" : {
													"sSwfPath" : "js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
												}
											});
						});
	</script>
	<style>
body.DTTT_Print {
	background: #fff;
}

.DTTT_Print #page-wrapper {
	margin: 0;
	background: #fff;
}

button.DTTT_button,div.DTTT_button,a.DTTT_button {
	border: 1px solid #e7eaec;
	background: #fff;
	color: #676a6c;
	box-shadow: none;
	padding: 6px 8px;
}

button.DTTT_button:hover,div.DTTT_button:hover,a.DTTT_button:hover {
	border: 1px solid #d2d2d2;
	background: #fff;
	color: #676a6c;
	box-shadow: none;
	padding: 6px 8px;
}

.dataTables_filter label {
	margin-right: 5px;
}

div.dataTables_length select {
	padding: 0 10px;
}
</style>
</body>

</html>

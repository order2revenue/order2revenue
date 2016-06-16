<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
span .#error {
	color: red;
	font-weight: bold;
}
</style>
</head>
<body>
	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>Add Manual Charge</h5>
				</div>
				<div class="ibox-content overflow-h">
					<form:form method="POST" action="saveManualCharge.html" role="form" class="form-horizontal" id="addManualChargeForm">
						<c:if test="${!empty manualCharge.mcId}">
							<input type="hidden" name="mcId" id="mcId"
								value="${manualCharge.mcId}" />
						</c:if>
							<div id="tab-1" class="tab-pane active col-sm-12">
								<div class="col-sm-6">
									<div class="mar-btm-20-oh">
										<label class="col-sm-5 control-label">Particular</label>
										<div class="col-sm-7">
											<form:input path="particular"
												value="${manualCharge.particular}" class="form-control"
												id="test" onblur="varify();" />
											<span id="res"></span>
										</div>
									</div>
									<div class="mar-btm-20-oh">
										<label class="col-sm-5 control-label">Payment Date</label>
										<div class="col-sm-7" id="data_1">
											<div class="input-group date">
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
												<form:input path="dateOfPayment" value="${manualCharge.dateOfPayment}"
													class="form-control" />
											</div>
										</div>
									</div>
									<div class="mar-btm-20-oh">
										<label class="col-sm-5 control-label">Payment Cycle</label>
										<div class="col-sm-7">
											<form:input path="paymentCycle" value="${manualCharge.paymentCycle}"
												class="form-control" />
										</div>
									</div>
								</div>
								<div class="col-sm-6 ">
									<div class="mar-btm-20-oh">
										<label class="col-sm-4 control-label">Partner (Optional)</label>

										<div class="col-sm-8">
											<form:select path="partner" items="${partnermap}"
												class="form-control"></form:select>
										</div>
									</div>
									<div class="mar-btm-20-oh">
										<label class="col-sm-4 control-label">Paid Amount</label>										
										<div class="col-sm-8">
											<form:input path="paidAmount" class="form-control"
												value=" " />
										</div>										
									</div>
									<div class="mar-btm-20-oh">
										<label class="col-sm-4 control-label">Payment ID</label>
										<div class="col-sm-8">
											<form:select path="chargesDesc"
												items="${paymentIDMap}" class="form-control"></form:select>
										</div>
									</div>
								</div>
							</div>
							
						<div class="col-sm-12">
							<div class="hr-line-dashed"></div>
							<button class="btn btn-primary pull-right" type="submit">Save</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function() {
			$('#data_1 .input-group.date').datepicker({
				todayBtn : "linked",
				keyboardNavigation : false,
				forceParse : false,
				calendarWeeks : true,
				autoclose : true
			});
		});
	</script>
</body>
</html>
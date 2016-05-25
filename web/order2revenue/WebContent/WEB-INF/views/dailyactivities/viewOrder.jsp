<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>

	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>Order : ${order.channelOrderID}</h5>
				</div>
				<div class="ibox-content view-order">

					<div class="time-line-wrp">
						<ul>
							<c:if test="${!empty order.orderTimeline}">
								<c:forEach items="${order.orderTimeline}" var="timeline"
									varStatus="loop">
									<li class="active"><i class="fa fa-check"></i> <span><fmt:formatDate
												value="${timeline.eventDate}" pattern="MMM dd,YY" /></span>
										<p>${timeline.event}</p></li>

								</c:forEach>
							</c:if>
						</ul>
					</div>

				</div>
			</div>
			<div class="ibox float-e-margins">
				<div class="ibox-title ">
					<h5>Order Info</h5>
				</div>

				<div class="ibox-content add-company view-order">
					<table class="table table-bordered custom-table">
						<thead>
							<tr>
								<th>Partner</th>
								<th>Invoice Id</th>
								<th>PI Reference No</th>
								<th>Sub Order Id</th>
								<th>Order Date</th>
								<th>Status</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${order.pcName}</td>
								<td>${order.invoiceID}</td>
								<td>${order.PIreferenceNo}</td>
								<td>${order.subOrderID}</td>
								<td><fmt:formatDate value="${order.orderDate}"
										pattern="MMM-dd-YYYY" /></td>
								<td>${order.status}</td>
								<td>${order.finalStatus}</td>
							</tr>
						</tbody>
					</table>

				</div>


			</div>
			<div class="col-lg-12 order-info-block">
                    <div class="float-e-margins col-lg-4">
                        <div class="panel panel-default">
                          <div class="panel-heading">
                            <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapsecust">Sale Info <i class="glyphicon glyphicon-minus" style="float: right;"></i> </a></h4>
                          </div>
                          <div id="collapsecust" class="panel-collapse collapse in">
                            <div class="panel-body">
                              <div class="ibox-content add-company view-order">                                
                                <table class="table table table-striped">
                                    <tbody>
                                    <tr>
                                        <td>Gross Sale Quantity</td>
                                        <td>${order.quantity}</td>
                                        
                                    </tr>
                                    <tr>
                                        <td>Order MRP</td>
                                        <td>${order.orderMRP}</td>
                                       
                                    </tr>
                                    <tr>
                                        <td>Customer Discount</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.discount}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Order SP</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.orderSP}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Gross Commission</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.partnerCommission * order.quantity}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>TDS</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.orderTax.tdsToDeduct}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Gross N/R</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.grossNetRate}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Tax</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.orderTax.tax}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Product Cost</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.quantity * productCost}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>P/R</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.pr}" /></td> 
                                    </tr>
                                    <tr>
                                        <td>Gross Profit</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${((order.grossProfit / order.quantity)- order.orderReturnOrRTO.returnorrtoQty) * order.quantity}" /></td>  
                                    </tr>
                                    </tbody>
                                </table>
                              </div>
                            </div>
                        </div>
                    </div>
                    </div>
                    <div class="float-e-margins col-lg-4">
                        <div class="panel panel-default">
                          <div class="panel-heading">
                            <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapsevents">Return Info <i class="glyphicon glyphicon-minus" style="float: right;"></i> </a></h4>
                          </div>
                          <div id="collapsevents" class="panel-collapse collapse in">
                            <div class="panel-body" style="height: 472px;">
                              <div class="ibox-content add-company view-order">        
                                <table class="table table table-striped">
                                    <tbody>
                                    <tr>
                                    	<td>Return Id</td>
                                        <td>${order.orderReturnOrRTO.returnOrRTOId}</td>                                      
                                    </tr>
                                    <tr>
                                        <td>Return Quantity</td>
                                        <td>${order.orderReturnOrRTO.returnorrtoQty}</td>                                       
                                    </tr>
                                    <tr>
                                        <td>Return Date</td>
                                        <td><fmt:formatDate	value="${order.orderReturnOrRTO.returnDate}" pattern="MMM dd,YY" /></td>  
                                    </tr>
                                    <tr>
                                        <td>RLC Status</td>
                                        <td>${order.orderReturnOrRTO.returnOrRTOstatus}</td>  
                                    </tr>
                                    <tr>
                                        <td>Sale Return Type</td>
                                        <td>${order.orderReturnOrRTO.cancelType}</td>  
                                    </tr>
                                    <tr>
                                        <td>Return Charges</td>
                                        <td>${order.orderReturnOrRTO.returnOrRTOChargestoBeDeducted}</td>  
                                    </tr>
                                    <tr>
                                        <td>Return Reason</td>
                                        <td>${order.orderReturnOrRTO.returnOrRTOreason}</td>  
                                    </tr>
                                    </tbody>
                                </table>
                              </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="float-e-margins col-lg-4">
                      <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapsenotes">Net Transaction Info<i class="glyphicon glyphicon-minus" style="float: right;"></i> </a>
                          </h4>
                        </div>
                        <div id="collapsenotes" class="panel-collapse collapse in">
                          <div class="panel-body">
                            <div class="ibox-content add-company view-order">      
                              <table class="table table table-striped">
                                    <tbody>
                                    <tr>
                                        <td>Net Sale Qty</td>
                                        <td>${order.quantity - order.orderReturnOrRTO.returnorrtoQty}</td>
                                        
                                    </tr>
                                    <tr>
                                        <td>Net Order MRP</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${(order.orderMRP / order.quantity) * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>
                                       
                                    </tr>
                                    <tr>
                                        <td>Net Customer Discount</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${(order.discount / order.quantity) * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Net Order SP</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${(order.orderSP / order.quantity) * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Net Commission</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.partnerCommission * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Net TDS</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${(order.orderTax.tdsToDeduct / order.quantity) * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Net N/R</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.grossNetRate * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Net Tax</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${(order.orderTax.tax / order.quantity) * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Net P/R</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${(order.pr / order.quantity) * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Net Product Cost</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${productCost * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Gross Profit</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.grossProfit}" /></td>  
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-lg-12 order-info-block">
                    <div class="float-e-margins col-lg-4">
                        <div class="panel panel-default">
                          <div class="panel-heading">
                            <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapsecustomer">Event Info <i class="glyphicon glyphicon-minus" style="float: right;"></i> </a></h4>
                          </div>
                          <div id="collapsecustomer" class="panel-collapse collapse in">
                            <div class="panel-body" style="height: 331px;">
                              <div class="ibox-content add-company view-order">                                
                                <table class="table table table-striped">
                                    <tbody>
                                    <tr>
                                        <td>Name</td>
                                        <td>${event.eventName}</td>
                                        
                                    </tr>
                                    <tr>
                                        <td>Start Date</td>
                                        <td><fmt:formatDate	value="${event.startDate}" pattern="MMM dd,YY" /></td>
                                       
                                    </tr>
                                    <tr>
                                        <td>End Date</td>
                                        <td><fmt:formatDate	value="${event.endDate}" pattern="MMM dd,YY" /></td>  
                                    </tr>
                                    </tbody>
                                </table>

                                </div>
                              </div>
                            </div>
                        </div>
                    
                    </div>
                    <div class="float-e-margins col-lg-4">
                        <div class="panel panel-default">
                          <div class="panel-heading">
                            <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapsepayment">Payment Info <i class="glyphicon glyphicon-minus" style="float: right;"></i> </a></h4>
                          </div>
                          <div id="collapsepayment" class="panel-collapse collapse in">
                            <div class="panel-body">
                              <div class="ibox-content add-company view-order">        
                                <table class="table table table-striped">
                                    <tbody>
                                    <tr>
                                        <td>Payment Type</td>
                                        <td>${order.paymentType}</td>
                                        
                                    </tr>
                                    <tr>
                                        <td>Payment Due Date</td>
                                        <td><fmt:formatDate	value="${order.paymentDueDate}" pattern="MMM dd,YY" /></td>
                                       
                                    </tr>
                                    <tr>
                                        <td>Last Payment Date</td>
                                        <td><fmt:formatDate	value="${order.orderPayment.dateofPayment}" pattern="MMM dd,YY" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Total Negative Amount</td>
                                        <td>
                                        	<c:if test="${order.orderPayment.negativeAmount gt 0}">
												-${order.orderPayment.negativeAmount}
											</c:if>
										</td>  
                                    </tr>
                                    <tr>
                                        <td>Total Positive Amount</td>
                                        <td>${order.orderPayment.positiveAmount}</td>  
                                    </tr>
                                    <tr>
                                        <td>Net Payment Result</td>
                                        <td>${order.orderPayment.netPaymentResult}</td>  
                                    </tr>
                                    <tr>
                                        <td>Payment Difference</td>
                                        <td>6</td>  
                                    </tr>

                                    </tbody>
                                </table>
                              </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="float-e-margins col-lg-4">
                      <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseshipping">Shipping Info <i class="glyphicon glyphicon-minus" style="float: right;"></i> </a></span> </h4>
                        </div>
                        <div id="collapseshipping" class="panel-collapse collapse in">
                          <div class="panel-body" style="height: 331px;">
                            <div class="ibox-content add-company view-order">      
                              <table class="table table table-striped">
                                    <tbody>
                                    <tr>
                                        <td>AWB Number</td>
                                        <td>${order.awbNum}</td>
                                        
                                    </tr>
                                    <tr>
                                        <td>SKU CODE</td>
                                        <td>${order.productSkuCode}</td>
                                       
                                    </tr>
                                    <tr>
                                        <td>Logistic Partner</td>
                                        <td>${order.logisticPartner}</td>  
                                    </tr>
                                    <tr>
                                        <td>Shipped Date</td>
                                        <td><fmt:formatDate	value="${order.shippedDate}" pattern="MMM dd,YY" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Delivery Date</td>
                                        <td><fmt:formatDate	value="${order.deliveryDate}" pattern="MMM dd,YY" /></td>  
                                    </tr> 
                                    <tr>
                                        <td>Shipping Charges</td>
                                        <td>${order.shippingCharges}</td>  
                                    </tr>  
                                   </tbody>
                                </table>
                            </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-lg-12 order-info-block">
                        <div class="float-e-margins col-lg-6">
                            <div class="panel panel-default">
                              <div class="panel-heading">
                                <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseeventsinfo">Customer Info <i class="glyphicon glyphicon-minus" style="float: right;"></i> </a></span></h4>
                              </div>
                              <div id="collapseeventsinfo" class="panel-collapse collapse in">
                                <div class="panel-body" style="height: 331px;">
                                  <div class="ibox-content add-company view-order">                                
                                    <table class="table table table-striped">
                                        <tbody>
                                        <tr>
                                            <td>Name</td>
                                            <td>${order.customer.customerName}</td>        
                                        </tr>
                                        <tr>
                                            <td>Contact</td>
                                            <td>${order.customer.customerPhnNo}</td>
                                           
                                        </tr>
                                        <tr>
                                            <td>Email</td>
                                            <td>${order.customer.customerEmail}</td>  
                                        </tr>
                                        <tr>
                                            <td>City</td>
                                            <td>${order.customer.customerCity}</td>  
                                        </tr>
                                        <tr>
                                            <td>Address</td>
                                            <td>${order.customer.customerAddress}</td>  
                                        </tr>
                                        </tbody>
                                    </table>
                                    </div>
                                  </div>
                                </div>
                            </div>
                        </div>
                        <div class="float-e-margins col-lg-6">
                            <div class="panel panel-default">
                              <div class="panel-heading">
                                <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapsesellernotes">Seller Notes <i class="glyphicon glyphicon-minus" style="float: right;"></i> </a></h4>
                              </div>
                              <div id="collapsesellernotes" class="panel-collapse collapse in">
                                <div class="panel-body">
                                  <div class="ibox-content add-company view-order">        
                                    <table class="table table table-striped">
                                        <tbody>
                                        <tr>
                                            <td>Payment Type</td>
                                            <td>type</td>  
                                        </tr>
                                        <tr>
                                            <td>Payment Due Date</td>
                                            <td>09-20-2016</td>
                                           
                                        </tr>
                                        <tr>
                                            <td>Last Payment Date</td>
                                            <td>10-21-2016</td>  
                                        </tr>
                                        <tr>
                                            <td>Total Negative Amount</td>
                                            <td>2</td>  
                                        </tr>
                                        <tr>
                                            <td>Total Positive Amount</td>
                                            <td>2</td>  
                                        </tr>
                                        <tr>
                                            <td>Net Payment Result</td>
                                            <td>8</td>  
                                        </tr>
                                        <tr>
                                            <td>Payment Difference</td>
                                            <td>6</td>  
                                        </tr>

                                        </tbody>
                                    </table>
                                  </div>
                              </div>
                            </div>
                          </div>
                        </div>
                    </div>

			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>Comments</h5>
				</div>
				<div class="ibox-content add-company view-order">

					<blockquote>
						<p>${order.sellerNote}</p>
						<%--  <small><strong>Author name</strong> in <cite title="" data-original-title="">June 26, 2015</cite></small> --%>
					</blockquote>
					<!--  <textarea class="form-control" placeholder="Type your comments here..."></textarea> -->

				</div>
			</div>
		
		</div>
		<jsp:include page="../globalfooter.jsp"></jsp:include>
	</div>
	<script>
		$(document).ready(function() {
			$("#form").validate({
				rules : {
					number : {
						required : true,
						number : true
					}
				}
			});
		});
	</script>
	<script type="text/javascript">


var selectIds = $('#collapsecust');
$(function ($) {
    selectIds.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});

var selectIds1 = $('#collapsevents');
$(function ($) {
    selectIds1.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});
var selectIds2 = $('#collapsenotes');
$(function ($) {
    selectIds2.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});
var selectIds3 = $('#collapsecustomer');
$(function ($) {
    selectIds3.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});
var selectIds4 = $('#collapsepayment');
$(function ($) {
    selectIds4.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});
var selectIds5 = $('#collapseshipping');
$(function ($) {
    selectIds5.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});
var selectIds6 = $('#collapseeventsinfo');
$(function ($) {
    selectIds6.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});
var selectIds7 = $('#collapsesellernotes');
$(function ($) {
    selectIds7.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});








</script>
</body>
</html>
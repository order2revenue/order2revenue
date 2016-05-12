<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <jsp:include page="../globalcsslinks.jsp"></jsp:include>
  <script type="text/javascript"
    src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
    function onclickviewExpCat(id) {
       $.ajax({
            url : 'viewExpenseGroup.html?expcategoryId='+id,
            success : function(data) {
                $('#centerpane').html(data);
            }
        });
    }
    function onclickAddTaxCategory() {
    	  $.ajax({
            url : 'addTaxCategory.html',
            success : function(data) {
                $('#centerpane').html(data);
            }
        });
    }
	function updateFields(obj){
		if($(obj).val() && "notSelected" != $(obj).val()){
			var minAmount = parseInt($(obj).find(':selected').data('minamount'));
			var planPrice = $(obj).find(':selected').data('planprice');
			var orderCount = minAmount/planPrice;
			var taxAmount = minAmount*14.5/100;
			var totalAmount = minAmount + taxAmount;
			$(".currPlanPriceTxt").html(planPrice);
			$(".currAmount").val(minAmount);
			$(".currTotalAmount").val(totalAmount);
			$(".currOrderCount").val(orderCount);
			$(".currAmountText").html(minAmount);
			$(".currTaxAmountText").html(taxAmount);
			$(".currTotalAmountText").html(totalAmount);
			$(".checkoutButton").removeAttr('disabled');
		}else{
			$(".currPlanPriceTxt").html(0);
			$(".currAmount").val(0);
			$(".currTotalAmount").val(0);
			$(".currOrderCount").val(0);
			$(".currAmountText").html(0);
			$(".currTaxAmountText").html(0);
			$(".currTotalAmountText").html(0);
			$(".checkoutButton").prop('disabled', true);
		}
	} 
	function updatePrices(obj){
		var currObjVal = $(obj).val();
		if(currObjVal && "" != currObjVal){
			var minAmount = parseInt($(".selectPlanField").find(':selected').data('minamount'));
			var currAmount = parseInt(currObjVal);
			if(currAmount >= minAmount){
				$(".checkoutButton").removeAttr('disabled');
				var planPrice = $(".selectPlanField").find(':selected').data('planprice');
				var orderCount = currAmount/planPrice;
				var taxAmount = currAmount*14.5/100;
				var totalAmount = currAmount + taxAmount;
				$(".currTotalAmount").val(totalAmount);
				$(".currOrderCount").val(orderCount);
				$(".currAmountText").html(currAmount);
				$(".currTaxAmountText").html(taxAmount);
				$(".currTotalAmountText").html(totalAmount);
			} else{
				$(".currTotalAmount").val(0);
				$(".currOrderCount").val(0);
				$(".currAmountText").html(0);
				$(".currTaxAmountText").html(0);
				$(".currTotalAmountText").html(0);
				$(".checkoutButton").prop('disabled', true);
			}
		} else{
			$(".currTotalAmount").val(0);
			$(".currOrderCount").val(0);
			$(".currAmountText").html(0);
			$(".currTaxAmountText").html(0);
			$(".currTotalAmountText").html(0);
			$(".checkoutButton").prop('disabled', true);
		}			
	}
</script>
 </head>
 <body>
  <div id="wrapper">
<jsp:include page="../sidenavigation.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
     <jsp:include page="../globalheader.jsp"></jsp:include>  
		<div class="wrapper wrapper-content animated fadeInRight" id="centerpane"> 
			<div class="row">
                <div class="col-lg-12 text-center">
					<div class ="navy-line" style="border:1px solid #1ab395;width: 6%;margin-left: 47%;">
					</div>
					<h1>My Account</h1>
				</div>
            </div>
			<div class="row">
                <div class="col-lg-12">
                	<div class="col-lg-6 text-center" style="background: #f5f5f5;">
                        <h1 class="heading">Order Bucket</h1>
                        <div class="col-md-6">                           
                            <img src="/O2R/seller/img/bucket.png" alt="bucket" width="50%" onmouseover="show_sidebar();" onmouseout="hide_sidebar();">
                            <p><b>Orders</b></p>
                            <p class="para"><c:out value="${myAccount.sellerAccount.orderBucket}"/></p>

                            <p id="box" style="visibility: hidden;">
                                All balance credited into O2R account is non refundable & can only be used towards processing of orders & purchase of products/services from Uniware.
                            </p> 
                        </div>
                        <div class="col-md-6">
							<div>
								<p style="position: relative;left: -34%;"><b>Complete life</b></p>
							</div>
                            <div class="progress" onmouseover="show_sidebar1();" onmouseout="hide_sidebar1();">
                                <div class="progress-bar progress-bar-success" role="progressbar" style="width:80%;background-color:#b4d733;">
                                  12654
                                </div>
                                <div class="progress-bar progress-bar-warning" role="progressbar" style="width:20%;background-color:#000;">
                                456
                                </div>
                            </div>
							<div class="progress" onmouseover="show_sidebar1();" onmouseout="hide_sidebar1();">
                                <div class="progress-bar progress-bar-success" role="progressbar" style="width:60%;">
                                  746
                                </div>
                                <div class="progress-bar progress-bar-warning" role="progressbar" style="width:40%;background-color:#000;">
                                254
                                </div>
                            </div>
							<div>
								<p style="position: relative;left: -31%;top:-5px;"><b>Complete Cycle</b></p>
							</div>
                            <p id="box1" style="visibility: hidden;">
                                These bar represent the number of order purchased,consumed,remaining during the current cycle and the whole life period
                            </p>	
                        </div>
						<div class="col-md-12">
							<h1 class="heading">Account Summary</h1>
							<div class="col-md-6 text-center">
								<p> <b>Current Plan</b></p>
								<h1><c:out value="${myAccount.plan.planName}"/></h1>
                                <div class="stars">
                                    <form action="">
                                      <input class="star star-5" id="star-5" type="radio" name="star"/>
                                      <label class="star star-5" for="star-5"></label>
                                      <input class="star star-4" id="star-4" type="radio" name="star"/>
                                      <label class="star star-4" for="star-4"></label>
                                      <input class="star star-3" id="star-3" type="radio" name="star"/>
                                      <label class="star star-3" for="star-3"></label>
                                      <input class="star star-2" id="star-2" type="radio" name="star"/>
                                      <label class="star star-2" for="star-2"></label>
                                      <input class="star star-1" id="star-1" type="radio" name="star"/>
                                      <label class="star star-1" for="star-1"></label>
                                    </form>
                                  </div>
								<br>
								<h3>Activation Date</h3>
								<p><b><fmt:formatDate type="date" value="${myAccount.sellerAccount.ativationDate}" /></b></p>
								
							</div>
							<div class="col-md-6 text-center">
								<p><b>Price per order</b></p>
								<h1>&#8377; <c:out value="${myAccount.plan.planPrice}"/></h1>
								 <button class="btn" style="background-color:#00a1f1;color:#fff;">Upgrade</button>
                                 <br><br><br>
								<h3 style="margin-top: 16px;">Last Plan Upgrade Date</h3>
								<p><b><fmt:formatDate type="date" value="${myAccount.sellerAccount.lastTransaction}" /></b></p>
							</div>
						</div>								
					</div>
					
                    <div class="col-lg-6 text-center">
						<div class="col-lg-12" style="background: #f5f5f5;">
						   <h1 class="heading">Payment History</h1>
						   <table class="table">
							<thead style="background-color:#e5e7e6;">
								<tr>
									<th>
										Invoice ID
									</th>
									<th>
										Date of Transaction
									</th>
									<th>
										Transaction ID
									</th>
									<th>
										Status
									</th>
									<th>
										Amount
									</th>
									<th>
										<img src="img/download.png" alt="download">
									</th>
								</tr>
							</thead>
							<tbody style="background-color: #fff;">
								<tr>
									<td>
										OD00039219880
									</td>
									<td>
										2 Aug 2015
									</td>
									<td>
										UTOOOOO8815
									</td>
									<td>
										Successfull
									</td>
									<td>
										2280.00
									</td>
									<td>
									</td>
								</tr>
								<tr>
									<td>
										OD00039219880
									</td>
									<td>
										2 Aug 2015
									</td>
									<td>
										UTOOOOO8815
									</td>
									<td>
										Successfull
									</td>
									<td>
										2280.00
									</td>
									<td>
									</td>
								</tr>
							</tbody>
						   </table>
						</div>
						<div class="col-lg-12" style="background: #f5f5f5;margin-top: 20px;">
						   <h1 class="heading">Usage History</h1>
						   <table class="table">
							<thead style="background-color:#e5e7e6;">
								<tr>
									<th>
										Date
									</th>
									<th>
										Amount Spent
									</th>
									<th>
										Price / Order
									</th>
									<th>
										Orders Bought
									</th>
									<th>
										Orders Consumed
									</th>
									<th>
										Orders Remaining
									</th>
								</tr>
							</thead>
							<tbody style="background-color: #fff;">
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>								
							</tbody>
						   </table>
						</div>
					</div>
                </div>
            </div>
		</div>
 <jsp:include page="../globalfooter.jsp"></jsp:include>

    </div>
</div>

<jsp:include page="../globaljslinks.jsp"></jsp:include>
</html>
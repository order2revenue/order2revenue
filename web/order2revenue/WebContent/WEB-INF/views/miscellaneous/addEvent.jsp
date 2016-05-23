<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../globalcsslinks.jsp"></jsp:include>

<style>
    body.DTTT_Print {
        background: #fff;

    }
    .DTTT_Print #page-wrapper {
        margin: 0;
        background:#fff;
    }

    button.DTTT_button, div.DTTT_button, a.DTTT_button {
        border: 1px solid #e7eaec;
        background: #fff;
        color: #676a6c;
        box-shadow: none;
        padding: 6px 8px;
    }
    button.DTTT_button:hover, div.DTTT_button:hover, a.DTTT_button:hover {
        border: 1px solid #d2d2d2;
        background: #fff;
        color: #676a6c;
        box-shadow: none;
        padding: 6px 8px;
    }

    .dataTables_filter label {
        margin-right: 5px;

    }
</style>

<style>
.error {
	color: red;
	font-weight: bold;
}
</style>
<style type="text/css">
span .#error {
	color: red;
	font-weight: bold;
}

.pickListButtons {
	padding: 10px;
	text-align: center;
}

.pickListSelect {
	height: 200px !important;
}

.pickList1Select {
	height: 200px !important;
}
</style>
<link href="/O2R/seller/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<link href="/O2R/seller/css/plugins/switchery/switchery.css"
	rel="stylesheet">
<link href="/O2R/seller/css/plugins/iCheck/custom.css" rel="stylesheet">

<script type="text/javascript">
var nameAvailability=true;

function checkOnBlur()
{
	var name=document.getElementById("eventName").value;
	
	$.ajax({
        url: "checkEvent.html?name="+name,
       success : function(res) {
        	   if(res=="false")
                	{
                	nameAvailability=false;
                	 $("#eventNameMessage").html("Inventory Group already exist");
                	}
                else
                	{
                	nameAvailability=true;
                	$("#eventNameMessage").html("Inventory Group name available");
                	}
            
       
 	   }
	 });
	}
    function submitInventoryGroup(){
    	 	 var validator = $("#eventName").validate({
	    		  rules: {
	    			  catName: {
	    			        required: true,
	    			            }
	    			  },
	    	     messages: {
	    	    	 catName: "Inventory group name required"
	    	    	
	    	     }
	    	 });
	    	 if(validator.form()&&nameAvailability){ // validation perform
	    		     $.ajax({
                    url: $("#eventName").attr("action"),
                    context: document.body,
                    type: 'post',
                    data:$("#eventName").serialize(),
                    success : function(res) {
                                  
                        $("#centerpane").html(res);
                   
             	   }
            	 });
	    	 }
 	   };



</script>


</head>
<body>
	<div id="wrapper">
		<jsp:include page="../sidenavigation.jsp"></jsp:include>
		<div id="page-wrapper" class="gray-bg">
			<jsp:include page="../globalheader.jsp"></jsp:include>
			<div class="wrapper wrapper-content animated fadeInRight" id="centerpane">
				<div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Events</h5>
                            <!-- <div class="ibox-tools">
                                <a href="" class="btn btn-primary btn-xs" >Create New Events</a>
                            </div> -->
                        </div>
                        
             	<form:form method="POST" action="saveEvent.html" role="form" class="form-horizontal" id="addEvent">
                        <div class="ibox-content add-company"> 
                     
                        <div class="col-sm-6">
                          <div class="form-group">
                            <label class="col-sm-4 control-label">Event Name</label>
                            <div class="col-sm-8">
                              <form:input path="eventName" value="${eventsBean.eventName}"	class="form-control" id="eventName" onblur="checkOnBlur()"/>
                              <span id="eventNameMessage"  style="color:red;font-weight:bold"></span>
                            </div>
                          </div>
                        </div>
                        <div class="col-sm-6">
                          <div class="form-group">
                            <label class="col-sm-4 control-label">Select Channel</label>
                            <div class="col-sm-8">
                              <form:select path="channelName" items="${partnerMap}" class="form-control"></form:select>
                            </div>
                          </div>
                        </div>
                        <div class="col-lg-12">
                            <h4>Event Period</h4>
                        </div>
                        
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Start Date</label>
                                <div class="col-sm-8" id="data_1">                                
                                    <div class="input-group date"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    	<fmt:formatDate value="${eventsBean.startDate}" var="startDate" type="date" pattern="MM/dd/yyyy"/>
                                        <form:input path="startDate"  value="${startDate}" type="text" class="form-control"></form:input>
                                    </div>
                                </div>
                            </div> 
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-4 control-label">End Date</label>
                                <div class="col-sm-8" id="data_1">
                                    <div class="input-group date"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    	<fmt:formatDate value="${eventsBean.endDate}" var="endDate" type="date" pattern="MM/dd/yyyy"/>
                                        <form:input path="endDate"   value="${endDate}" type="text" class="form-control"></form:input>
                                    </div>
                                </div>
                            </div>  
                        </div>
                        <div class="col-sm-12">
                          <h4>NR Calculator</h4>
                        </div>
                        <div class="col-sm-12">
							<div class="col-sm-4">
                            <div class="radio">
                              <label>
                                <form:radiobutton  value="original" id="nrCalculatorEvent_original" path="nrnReturnConfig.nrCalculatorEvent" class="nrCalculatorEvent" />
                                Original </label>
                            </div>
                          </div>
                          <div class="col-sm-4">
                            <div class="radio">
                              <label>
                                <form:radiobutton  value="variable" id="nrCalculatorEvent_variable" path="nrnReturnConfig.nrCalculatorEvent"  class="nrCalculatorEvent"  />
                                Variable NR</label>
                            </div>
                          </div>
                          <div class="col-sm-4">
                            <div class="radio">
                              <label>
                                <form:radiobutton  value="fixed" id="nrCalculatorEvent_fixed" path="nrnReturnConfig.nrCalculatorEvent" class="nrCalculatorEvent"  />
                                Fixed TP </label>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-sm-12 radio2 m-b" id="blk-nrCalculatorEvent_variable">
                            <div class="col-sm-12">
								<div class="col-sm-12 radio5" id="nr-switch-sec" style="display: block;">
												<div class="panel-body">
												<div class="panel-group" id="accordion">
													<div class="panel panel-default">
														<div class="panel-heading">
															<h5 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion"
																	href="#collapseOne1">Commission</a>
															</h5>
														</div>
														<div id="collapseOne1" class="panel-collapse collapse">
															<div class="panel-body">
																<div class="col-sm-12">
																	<div class="col-sm-6">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.commissionType" value="fixed"
																					id="commisionType-fixed" name="commissionType"
																					class="commissionType" /> <!--       <input type="radio" value="4" id="optionsRadios1" name="toggler"> -->
																				Fixed
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-6">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.commissionType"
																					value="categoryWise"
																					id="commisionType-categoryWise"
																					name="commissionType" class="commissionType" /> <!--  <input type="radio" value="5" id="optionsRadios1" name="toggler"> -->
																				Category Wise
																			</label>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12 radio1"
																	id="blk-commisionType-fixed">
																	<div class="col-sm-12">
																		<div class="form-group">
																			<div class="input-group m-b col-md-4">
																				<input type="text" class="form-control"
																					name="nr-fixedCommissionPercent"
																					value="${chargeMap.fixedCommissionPercent}">
																				<span class="input-group-addon">%</span>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12 radio1"
																	id="blk-commisionType-categoryWise">
																	<c:choose>
																		<c:when test="${!empty categoryMap}">
																			<c:forEach items="${categoryMap}" var="cat"
																				varStatus="loop">
																				<div class="form-group col-md-12">
																					<label class="col-md-4 control-label">${cat.key}</label>
																					<div class="input-group m-b col-md-4">
																						<input type="text" class="form-control"
																							name='nr-${cat.key}' value='${cat.value}'>
																						<!--   <span class="input-group-addon">%</span> -->
																					</div>
																				</div>
																			</c:forEach>
																		</c:when>
																		
																	</c:choose>

																</div>
															</div>
														</div>
													</div>

													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion"
																	href="#collapseTwo1">Fixed Fee</a>
															</h4>
														</div>
														<div id="collapseTwo1" class="panel-collapse collapse">
															<div class="panel-body">
																<div class="col-sm-4"
																	style="border-right: 1px dotted #ccc;">
																	<div class="form-group col-md-12">
																		<label class="col-md-4 control-label content-rgt">&lt;250</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeelt250"
																				value="${chargeMap.fixedfeelt250}">
																		</div>
																	</div>
																	<div class="form-group col-md-12">
																		<label class="col-md-12 control-label">&gt;250&&&lt;500</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeegt250lt500"
																				value="${chargeMap.fixedfeegt250lt500}">
																		</div>
																	</div>
																	<div class="form-group col-md-12">
																		<label class="col-md-4 control-label">&gt;500</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeegt500"
																				value="${chargeMap.fixedfeegt500}">
																		</div>
																	</div>
																</div>
																<div class="col-sm-4"
																	style="border-right: 1px dotted #ccc;">
																	<div class="form-group col-md-12">
																		<label class="col-md-4 control-label content-rgt">&lt;500</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeelt500Big"
																				value="${chargeMap.fixedfeelt500Big}">
																		</div>
																	</div>
																	<div class="form-group col-md-12">
																		<label class="col-md-12 control-label">&gt;500&&&lt;1000</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeegt500lt1000"
																				value="${chargeMap.fixedfeegt500lt1000}">
																		</div>
																	</div>
																	<div class="form-group col-md-12">
																		<label class="col-md-4 control-label">&gt;1000&&&lt;10000</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeegt1000lt10000"
																				value="${chargeMap.fixedfeegt1000lt10000}">
																		</div>
																	</div>
																	<div class="form-group col-md-12">
																		<label class="col-md-4 control-label">&gt;10000</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeegt10000"
																				value="${chargeMap.fixedfeegt10000}">
																		</div>
																	</div>
																</div>
																<div class="col-sm-4">
																	<div class="form-group col-md-12">
																		<label class="col-md-4 control-label content-rgt">&gt;500</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeelt500"
																				value="${chargeMap.fixedfeelt500}">
																		</div>
																	</div>
																	<div class="form-group col-md-12">
																		<label class="col-md-12 control-label">&gt;500</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeegt500Big"
																				value="${chargeMap.fixedfeegt500Big}">
																		</div>
																	</div>
																</div>
															</div>
														</div>
														<div class="panel panel-default">
															<div class="panel-heading">
																<h4 class="panel-title">
																	<a data-toggle="collapse" data-parent="#accordion"
																		href="#collapseThree1">Payment Collection Charges</a>
																</h4>
															</div>
															<div id="collapseThree1" class="panel-collapse collapse">
																<div class="panel-body">
																	<div class="col-sm-12">
																		<div class="col-sm-12">
																			<div class="col-sm-6">
																				<div class="checkbox i-checks">
																					<label> <form:checkbox
																							path="nrnReturnConfig.whicheverGreaterPCC" 	id="whicheverGreaterPCC" /> <i></i> Which Ever
																						Is Greater
																					</label>
																				</div>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="col-sm-6">
																				<div class="checkbox i-checks">
																					<label> <input type="checkbox" value=""
																						name="ispercentSPPCC"> <i></i> Percentage
																						of SP
																					</label>
																				</div>
																			</div>
																			<div class="col-sm-6">
																				<div class="input-group m-b">
																					<input type="text" class="form-control"
																						name="nr-percentSPPCC"
																						value="${chargeMap.percentSPPCC}"> <span
																						class="input-group-addon">%</span>
																				</div>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="col-sm-6">
																				<div class="checkbox i-checks">
																					<label> <input type="checkbox"
																						name="isfixedAmountPCC"> <i></i> Fixed
																						Amount
																					</label>
																				</div>
																			</div>
																			<div class="col-sm-6">
																				<div class="input-group m-b">
																					<input type="text" class="form-control"
																						name="nr-fixedAmtPCC"
																						value="${chargeMap.fixedAmtPCC}"> <span
																						class="input-group-addon">%</span>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
														<div class="panel panel-default">
															<div class="panel-heading">
																<h4 class="panel-title">
																	<a data-toggle="collapse" data-parent="#accordion"
																		href="#collapsefour1">Shipping Fee</a>
																</h4>
															</div>
															<div id="collapsefour1" class="panel-collapse collapse">
																<div class="panel-body">
																	<div class="col-sm-12">
																		<div class="col-sm-6">
																			<div class="radio">
																				<label> <!-- <input type="radio" value="6" id="optionsRadios1" name="toggler"> -->
																					<form:radiobutton
																						path="nrnReturnConfig.shippingFeeType"
																						value="variable" id="shippingfee-variable"
																						name="toggler" class="shippingFeeType" />
																					Variable Shipping Charges
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-6">
																			<div class="radio">
																				<label> <!--    <input type="radio" value="7" id="optionsRadios1" name="toggler"> -->
																					<form:radiobutton
																						path="nrnReturnConfig.shippingFeeType"
																						value="fixed" id="shippingfee-fixed"
																						class="shippingFeeType" /> Fixed Shipping Charges
																				</label>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1"
																		id="blk-shippingfee-variable">
																		<div
																			class="col-sm-12 center-align text-center font-bold">
																			<h4 class="text-info">Which Ever is Higher</h4>
																		</div>
																		<div class="col-sm-6">
																			<h4>Volume calculation= (lxbxh)(cm)/5</h4>
																			<table class="table table-bordered">
																				<thead>
																					<tr>
																						<th>Volume Weight Slab(gms)</th>
																						<th>Local</th>
																						<th>Zonal</th>
																						<th>National</th>
																						<th>Metro</th>
																					</tr>
																				</thead>
																				<tbody>
																					<tr>
																						<td><label>&lt; 500</label></td>
																						<td><div class="form-group ">
																								<div class=" content-rgt">
																									<input type="text" placeholder=""
																										class="form-control" name="nr-localvwlt500"
																										value="${chargeMap.localvwlt500}">
																								</div>
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-zonalvwlt500"
																									value="${chargeMap.zonalvwlt500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-nationalvwlt500"
																									value="${chargeMap.nationalvwlt500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-metrovwlt500"
																									value="${chargeMap.metrovwlt500}">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>500 &gt; 1000</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-localvwgt500lt1000"
																									value="${chargeMap.localvwgt500lt1000}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-zonalvwgt500lt1000"
																									value="${chargeMap.zonalvwgt500lt1000}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-nationalvwgt500lt1000"
																									value="${chargeMap.nationalvwgt500lt1000}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-metrovwgt500lt1000"
																									value="${chargeMap.metrovwgt500lt1000}">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>1000 &gt; 1500</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-localvwgt1000lt1500"
																									value="${chargeMap.localvwgt1000lt1500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-zonalvwgt1000lt1500"
																									value="${chargeMap.zonalvwgt1000lt1500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-nationalvwgt1000lt1500"
																									value="${chargeMap.nationalvwgt1000lt1500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-metrovwgt1000lt1500"
																									value="${chargeMap.metrovwgt1000lt1500}">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>1500 &gt; 5000</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-localvwgt1500lt5000"
																									value="${chargeMap.localvwgt1000lt1500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-zonalvwgt1500lt5000"
																									value="${chargeMap.zonalvwgt1500lt5000}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-nationalvwgt1500lt5000"
																									value="${chargeMap.nationalvwgt1500lt5000}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-metrovwgt1500lt5000"
																									value="${chargeMap.metrovwgt1500lt5000}">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>add 1 kg</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-localvwgt5000"
																									value="${chargeMap.metrovwgt1500lt5000}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-zonalvwgt5000"
																									value="${chargeMap.zonalvwgt5000}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-nationalvwgt5000"
																									value="${chargeMap.nationalvwgt5000}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-metrovwgt5000"
																									value="${chargeMap.metrovwgt5000}">
																							</div></td>
																					</tr>
																				</tbody>
																			</table>
																		</div>
																		<div class="col-sm-6">
																			<h4>Weight calculation</h4>
																			<table class="table table-bordered">
																				<thead>
																					<tr>
																						<th>Dead Weight Slab(gms)</th>
																						<th>Local</th>
																						<th>Zonal</th>
																						<th>National</th>
																						<th>Metro</th>
																					</tr>
																				</thead>
																				<tbody>
																					<tr>
																						<td><label>&lt; 500</label></td>
																						<td><div class="form-group ">
																								<div class=" content-rgt">
																									<input type="text" placeholder=""
																										class="form-control" name="nr-localdwlt500"
																										value="${chargeMap.metrovwgt5000}">
																								</div>
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-zonaldwlt500"
																									value="${chargeMap.zonaldwlt500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-nationaldwlt500"
																									value="${chargeMap.nationaldwlt500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-metrodwlt500"
																									value="${chargeMap.metrodwlt500}">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>additional &gt; 500</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-localdwgt500"
																									value="${chargeMap.localdwgt500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-zonaldwgt500"
																									value="${chargeMap.zonaldwgt500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-nationaldwgt500"
																									value="${chargeMap.nationaldwgt500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-metrodwgt500"
																									value="${chargeMap.metrodwgt500}">
																							</div></td>
																					</tr>
																				</tbody>
																			</table>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1"
																		id="blk-shippingfee-fixed">
																		<div
																			class="col-sm-12 center-align text-center font-bold">
																			<h4 class="text-info">Which Ever is Higher</h4>
																		</div>
																		<div class="col-sm-6">
																			<h4>Weight calculation</h4>
																			<table class="table table-bordered">
																				<thead>
																					<tr>
																						<th>Dead Weight Slab(gms)</th>
																						<th>Price</th>
																					</tr>
																				</thead>
																				<tbody>
																					<tr>
																						<td><label>&lt; 500</label></td>
																						<td><div class="form-group ">
																								<div class=" content-rgt">
																									<input type="text" placeholder=""
																										class="form-control" name="nr-fixeddwlt500"
																										value="${chargeMap.fixeddwlt500}">
																								</div>
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>additional &gt; 500</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-fixeddwgt500"
																									value="${chargeMap.fixeddwgt500}">
																							</div></td>
																					</tr>
																				</tbody>
																			</table>
																		</div>
																		<div class="col-sm-6">
																			<h4>Volume calculation= (lxbxh)(cm)/5000</h4>
																			<table class="table table-bordered">
																				<thead>
																					<tr>
																						<th>Volume Weight Slab(gms)</th>
																						<th>Price</th>
																					</tr>
																				</thead>
																				<tbody>
																					<tr>
																						<td><label>&lt; 500</label></td>
																						<td><div class="form-group ">
																								<div class=" content-rgt">
																									<input type="text" placeholder=""
																										class="form-control" name="nr-fixedvwlt500"
																										value="${chargeMap.fixedvwlt500}">
																								</div>
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>500 &gt; 1000</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-fixedvwgt500lt1000"
																									value="${chargeMap.fixedvwgt500lt1000}">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>1000 &gt; 1500</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-fixedvwgt1000lt1500"
																									value="${chargeMap.fixedvwgt1000lt1500}">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>1500 &gt; 5000</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-fixedvwgt1500lt5000"
																									value="${chargeMap.fixedvwgt1000lt1500}">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>add 1 kg</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-fixedvwgt5000"
																									value="${chargeMap.fixedvwgt5000}">
																							</div></td>
																					</tr>
																				</tbody>
																			</table>
																		</div>
																	</div>
																</div>
															</div>
														</div>
														<div class="panel panel-default">
															<div class="panel-heading">
																<h4 class="panel-title">
																	<a data-toggle="collapse" data-parent="#accordion"
																		href="#collapsefive1">Service Tax</a>
																</h4>
															</div>
															<div id="collapsefive1" class="panel-collapse collapse">
																<div class="panel-body">
																	<div class="form-group col-md-12">
																		<div class="col-md-4 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" name="nr-serviceTax"
																				value="${chargeMap.serviceTax}">
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
		</div><!-- -----------------------------id-40----------------------------- -->
                            </div>
                          </div>                          
                        </div>
                 <div class="ibox-content add-company">
              			<div class="col-sm-12 ">
                  			<h4>Return Calculator/Charges</h4>
                        
                        <div class="col-sm-12">
                          <div class="col-sm-6">
                            <div class="radio">
                              <label>
                                <form:radiobutton  value="original" id="returnCalculatorEvent_original" path="nrnReturnConfig.returnCalculatorEvent" 
                                class="returnCalculatorEvent"/>
                                Original Terms</label>
                            </div>
                          </div>
                          <div class="col-sm-6">
                            <div class="radio">
                              <label>
                                <form:radiobutton  value="newTerms" id="returnCalculatorEvent_newTerms" path="nrnReturnConfig.returnCalculatorEvent"
                                class="returnCalculatorEvent" />
                                New Terms </label>
                            </div>
                          </div>
                        </div>
                        </div>
                        <div class="row">
                          <div class="col-sm-12 radio3 m-b" id="blk-returnCalculatorEvent_original">
                            <div class="col-sm-12">
                                
                            </div>
                          </div>
                          <div class="col-sm-12 radio3" id="blk-returnCalculatorEvent_newTerms">
                                <div class="ibox-content add-company">
											<div class="panel-body">
												<div class="panel-group" id="accordion">
													<div class="panel panel-default">
														<div class="panel-heading">
															<h5 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion"
																	href="#collapseOne">Return Charges</a>
															</h5>
														</div>
														<div id="collapseOne" class="panel-collapse collapse">
															<div class="panel-body">
																<h4>Seller Fault</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.retCharSFType" value="fixed"
																					id="retrun-sf-fix" name="toggler"
																					class="retCharSFType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.retCharSFType"
																					value="variable" id="retrun-sf-variable"
																					name="toggler" class="retCharSFType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.retCharSFType"
																					value="noCharges" id="retrun-sf-nocharges"
																					name="toggler" class="retCharSFType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b"
																		id="blk-retrun-sf-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-retCharSFFixedAmt"
																						value="${chargeMap.retCharSFFixedAmt}">
																				</div>
																			</div>
																		</div>

																	</div>
																	<div class="col-sm-12 radio1"
																		id="blk-retrun-sf-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-retCharSFVarFixedAmt"
																					value="${chargeMap.retCharSFVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-retCharSFPercentSP"
																					value="${chargeMap.retCharSFPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-retCharSFPercentPCC"
																					value="${chargeMap.retCharSFPercentPCC}">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.retCharSFFF" id="retCharSFFF"/> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label><form:checkbox
																						path="nrnReturnConfig.retCharSFShipFee" id="retCharSFShipFee"/> <i></i>
																					Shipping Fee </label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.retCharSFRevShipFee" id="retCharSFRevShipFee"/> <i></i>
																					Reverse Shipping Fee
																				</label>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<h4>Buyer Return</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.retCharBRType" value="fixed"
																					id="retrun-br-fix" name="toggler"
																					class="retCharBRType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.retCharBRType"
																					value="variable" id="retrun-br-variable"
																					name="toggler" class="retCharBRType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.retCharBRType"
																					value="noCharges" id="retrun-br-nocharges"
																					name="toggler" class="retCharBRType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b"
																		id="blk-retrun-br-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-retCharBRFixedAmt"
																						value="${chargeMap.retCharBRFixedAmt}">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1"
																		id="blk-retrun-br-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-retCharBRVarFixedAmt"
																					value="${chargeMap.retCharBRVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-retCharBRPercentSP"
																					value="${chargeMap.retCharBRPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-retCharBRPercentPCC"
																					value="${chargeMap.retCharBRPercentPCC}">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.retCharBRFF" id="retCharBRFF"/> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label><form:checkbox
																						path="nrnReturnConfig.retCharBRShipFee" id="retCharBRShipFee"/> <i></i>
																					Shipping Fee </label>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion"
																	href="#collapseTwo">RTO Charges</a>
															</h4>
														</div>
														<div id="collapseTwo" class="panel-collapse collapse">
															<div class="panel-body">
																<h4>Seller Fault</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.RTOCharSFType" value="fixed"
																					id="RTO-sf-fix" name="toggler"
																					class="RTOCharSFType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.RTOCharSFType"
																					value="variable" id="RTO-sf-variable"
																					name="toggler" class="RTOCharSFType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.RTOCharSFType"
																					value="noCharges" id="RTO-sf-nocharges"
																					name="toggler" class="RTOCharSFType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-RTO-sf-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-RTOCharSFFixedAmt"
																						value="${chargeMap.RTOCharSFFixedAmt}">
																				</div>
																			</div>
																		</div>

																	</div>
																	<div class="col-sm-12 radio1" id="blk-RTO-sf-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-RTOCharSFVarFixedAmt"
																					value="${chargeMap.RTOCharSFFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-RTOCharSFPercentSP"
																					value="${chargeMap.RTOCharSFPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-RTOCharSFPercentPCC"
																					value="${chargeMap.RTOCharSFPercentPCC}">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.RTOCharSFFF" id="RTOCharSFFF"/> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.RTOCharSFShipFee" id="RTOCharSFShipFee"/> <i></i>
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.RTOCharSFRevShipFee" id="RTOCharSFRevShipFee"/> <i></i>
																					Reverse Shipping Fee
																				</label>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<h4>Buyer Return</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.RTOCharBRType" value="fixed"
																					id="RTO-br-fix" name="toggler"
																					class="RTOCharBRType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.RTOCharBRType"
																					value="variable" id="RTO-br-variable"
																					name="toggler" class="RTOCharBRType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.RTOCharBRType"
																					value="noCharges" id="RTO-br-nocharges"
																					name="toggler" class="RTOCharBRType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-RTO-br-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-RTOCharBRFixedAmt"
																						value="${chargeMap.RTOCharBRFixedAmt}">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1" id="blk-RTO-br-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-RTOCharBRVarFixedAmt"
																					value="${chargeMap.RTOCharBRVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-RTOCharBRPercentSP"
																					value="${chargeMap.RTOCharBRPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-RTOCharBRPercentPCC"
																					value="${chargeMap.RTOCharBRPercentPCC}">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.RTOCharBRFF" id="RTOCharBRFF"/> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.RTOCharBRShipFee" id='RTOCharBRShipFee'/> <i></i>
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion"
																	href="#collapseThree">Replacement Charges</a>
															</h4>
														</div>
														<div id="collapseThree" class="panel-collapse collapse">
															<div class="panel-body">
																<h4>Seller Fault</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.repCharSFType" value="fixed"
																					id="rep-sf-fix" name="toggler"
																					class="repCharSFType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.repCharSFType"
																					value="variable" id="rep-sf-variable"
																					name="toggler" class="repCharSFType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.repCharSFType"
																					value="noCharges" id="rep-sf-nocharges"
																					name="toggler" class="repCharSFType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-rep-sf-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-repCharSFFixedAmt"
																						value="${chargeMap.repCharSFFixedAmt}">
																				</div>
																			</div>
																		</div>

																	</div>
																	<div class="col-sm-12 radio1" id="blk-rep-sf-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-repCharSFVarFixedAmt"
																					value="${chargeMap.repCharSFVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-repCharSFPercentSP"
																					value="${chargeMap.repCharSFPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-repCharSFPercentPCC"
																					value="${chargeMap.repCharSFPercentPCC}">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.repCharSFFF" id="repCharSFFF"/> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.repCharSFShipFee" id="repCharSFShipFee"/> <i></i>
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.repCharSFRevShipFee" id="repCharSFRevShipFee"/> <i></i>
																					Reverse Shipping Fee
																				</label>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<h4>Buyer Return</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.repCharBRType" value="fixed"
																					id="rep-br-fix" name="toggler"
																					class="repCharBRType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.repCharBRType"
																					value="variable" id="rep-br-variable"
																					name="toggler" class="repCharBRType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.repCharBRType"
																					value="noCharges" id="rep-br-nocharges"
																					name="toggler" class="repCharBRType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-rep-br-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-repCharBRFixedAmt"
																						value="${chargeMap.repCharBRFixedAmt}">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1" id="blk-rep-br-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-repCharBRVarFixedAmt"
																					value="${chargeMap.repCharBRFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-repCharBRPercentSP"
																					value="${chargeMap.repCharBRPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-repCharBRPercentPCC"
																					value="${chargeMap.repCharBRPercentPCC}">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.repCharBRFF" id="repCharBRFF"/> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.repCharBRShipFee" id="repCharBRShipFee"/> <i></i>
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion"
																	href="#collapsefive">Partial Delivery Charges</a>
															</h4>
														</div>
														<div id="collapsefive" class="panel-collapse collapse">
															<div class="panel-body">
																<h4>Seller Fault</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.PDCharSFType" value="fixed"
																					id="PD-sf-fix" name="toggler" class="PDCharSFType" />
																				Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.PDCharSFType"
																					value="variable" id="PD-sf-variable" name="toggler"
																					class="PDCharSFType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.PDCharSFType"
																					value="noCharges" id="PD-sf-nocharges"
																					name="toggler" class="PDCharSFType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-PD-sf-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-PDCharSFFixedAmt"
																						value="${chargeMap.PDCharSFFixedAmt}">
																				</div>
																			</div>
																		</div>

																	</div>
																	<div class="col-sm-12 radio1" id="blk-PD-sf-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-PDCharSFVarFixedAmt"
																					value="${chargeMap.PDCharSFVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-PDCharSFPercentSP"
																					value="${chargeMap.PDCharSFPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-PDCharSFPercentPCC"
																					value="${chargeMap.PDCharSFPercentPCC}">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.PDCharSFFF" id="PDCharSFFF"/> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.PDCharSFShipFee" id="PDCharSFShipFee"/> <i></i>
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.PDCharSFRevShipFee" id="PDCharSFRevShipFee"/> <i></i>
																					Reverse Shipping Fee
																				</label>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<h4>Buyer Return</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.PDCharBRType" value="fixed"
																					id="PD-br-fix" name="toggler" class="PDCharBRType" />
																				Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.PDCharBRType"
																					value="variable" id="PD-br-variable" name="toggler"
																					class="PDCharBRType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.PDCharBRType"
																					value="noCharges" id="PD-br-nocharges"
																					name="toggler" class="PDCharBRType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-PD-br-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-PDCharBRFixedAmt"
																						value="${chargeMap.PDCharBRFixedAmt}">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1" id="blk-PD-br-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-PDCharBRVarFixedAmt"
																					value="${chargeMap.PDCharBRVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-PDCharBRPercentSP"
																					value="${chargeMap.PDCharBRPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-PDCharBRPercentPCC"
																					value="${chargeMap.PDCharBRPercentPCC}">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.PDCharBRFF" id="PDCharBRFF"/> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.PDCharBRShipFee" id="PDCharBRShipFee"/> <i></i>
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion"
																	href="#collapsesix">Cancellation Charges</a>
															</h4>
														</div>
														<div id="collapsesix" class="panel-collapse collapse">
															<div class="panel-body">
																<h4>Seller Fault</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<h5>Before RTD</h5>
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.canCharSFBFRTDType"
																					value="fixed" id="can-sfbfrtd-fix" name="toggler"
																					class="canCharSFBFRTDType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.canCharSFBFRTDType"
																					value="variable" id="can-sfbfrtd-variable"
																					name="toggler" class="canCharSFBFRTDType" />
																				Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.canCharSFBFRTDType"
																					value="noCharges" id="can-sfbfrtd-nocharges"
																					name="toggler" class="canCharSFBFRTDType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b"
																		id="blk-can-sfbfrtd-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-canCharSFBFRTDFixedAmt"
																						value="${chargeMap.canCharSFBFRTDFixedAmt}">
																				</div>
																			</div>
																		</div>

																	</div>
																	<div class="col-sm-12 radio1"
																		id="blk-can-sfbfrtd-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control"
																					name="nr-canCharSFBFRTDVarFixedAmt"
																					value="${chargeMap.canCharSFBFRTDVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control"
																					name="nr-canCharSFBFRTDPercentSP"
																					value="${chargeMap.canCharSFBFRTDPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control"
																					name="nr-canCharSFBFRTDPercentPCC"
																					value="${chargeMap.canCharSFBFRTDPercentPCC}">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharSFBRTDFF" id="canCharSFBRTDFF"/> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharSFBRTDShipFee" id="canCharSFBRTDShipFee"/> <i></i>
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharSFBRTDRevShipFee" id="canCharSFBRTDRevShipFee" />
																					<i></i> Reverse Shipping Fee
																				</label>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<h5>After RTD</h5>
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.canCharSFARTDType"
																					value="fixed" id="can-sfartd-fix" name="toggler"
																					class="canCharSFARTDType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.canCharSFARTDType"
																					value="variable" id="can-sfartd-variable"
																					name="toggler" class="canCharSFARTDType" />
																				Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.canCharSFARTDType"
																					value="noCharges" id="can-sfartd-nocharges"
																					name="toggler" class="canCharSFARTDType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b"
																		id="blk-can-sfartd-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-canCharSFFixedAmt"
																						value="${chargeMap.canCharSFFixedAmt}">
																				</div>
																			</div>
																		</div>

																	</div>
																	<div class="col-sm-12 radio1"
																		id="blk-can-sfartd-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharSFVarFixedAmt"
																					value="${chargeMap.canCharSFVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharSFPercentSP"
																					value="${chargeMap.canCharSFPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharSFPercentPCC"
																					value="${chargeMap.canCharSFPercentPCC}">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharSFFF" id="canCharSFFF"/> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharSFShipFee" id="canCharSFShipFee" /> <i></i>
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharSFARTDRevShipFee" id="canCharSFARTDRevShipFee"/>
																					<i></i> Reverse Shipping Fee
																				</label>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<h4>Buyer Cancellation</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.canCharBRType" value="fixed"
																					id="can-br-fix" name="toggler"
																					class="canCharBRType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.canCharBRType"
																					value="variable" id="can-br-variable"
																					name="toggler" class="canCharBRType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.canCharBRType"
																					value="noCharges" id="can-br-nocharges"
																					name="toggler" class="canCharBRType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-can-br-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-canCharBRFixedAmt"
																						value="${chargeMap.canCharSFPercentPCC}">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1" id="blk-can-br-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharBRVarFixedAmt"
																					value="${chargeMap.canCharBRVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharBRPercentSP"
																					value="${chargeMap.canCharBRPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharBRPercentPCC"
																					value="${chargeMap.canCharBRPercentPCC}">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharBRFF" id="canCharBRFF"/> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharBRShipFee" id="canCharBRShipFee"/> <i></i>
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharBRRevShipFee" id="canCharBRRevShipFee"/> <i></i>
																					Reverse Shipping Fee
																				</label>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>

													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion"
																	href="#collapseseven">Reverse Shipping Fee</a>
															</h4>
														</div>
														<div id="collapseseven" class="panel-collapse collapse">
															<div class="panel-body">
																<div class="col-sm-12">
																	<div class="col-sm-6">
																		<div class="checkbox i-checks">
																			<label> <form:checkbox
																					path="nrnReturnConfig.revShippingFeeType" id="revShippingFeeType_revShipFeePCC"
																					value="revShipFeePCC" /> <i></i>( % of Shipping
																				Fee )
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-6">
																		<div class="input-group m-b">
																			<input type="text" placeholder=""
																				class="form-control" name="nr-revShipFeePCC"
																				value="${chargeMap.revShipFeePCC}"> <span
																				class="input-group-addon">%</span>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-6">
																		<div class="checkbox i-checks">
																			<label> <form:checkbox
																					path="nrnReturnConfig.revShippingFeeType" id="revShippingFeeType_revShipFeeGRT"
																					value="revShipFeeGRT" /> <i></i> Which Ever Is
																				Greater
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-12">
																		<div class="col-sm-6">
																			<div class="checkbox i-checks">
																				<label> Flat Amount </label>
																				<div id="sub">
																					<img src="/O2R/seller/img/about.png" alt="about"
																						style="float: right; position: relative; top: -31px; left: 18px;">
																				</div>
																				<div id="welcome" style="display: none;">
																					<p>Lorem ipsum dolor sit amet, consectetur quis</p>
																				</div>
																			</div>
																		</div>
																		<div class="col-sm-6">
																			<div class="input-group m-b">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-revShipFeeFlatAmt"
																					value="${chargeMap.revShipFeeFlatAmt}">
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12">
																		<div class="col-sm-6">
																			<div class="checkbox i-checks">
																				<label> <i></i> % of Market Fee(Payment
																					Collection Charges)
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-6">
																			<div class="input-group m-b">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-revShipFeePCCMF"
																					value="${chargeMap.revShipFeePCCMF}"> <span
																					class="input-group-addon">%</span>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-6">
																		<div class="checkbox i-checks">
																			<label> <form:checkbox
																					path="nrnReturnConfig.revShippingFeeType" id="revShippingFeeType_revShipFeeFF"
																					value="revShipFeeFF" /> <i></i>Fix Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-6">
																		<div class="input-group m-b" style="width: 96%;">
																			<input type="text" placeholder=""
																				class="form-control" name="nr-revShipFeeFF"
																				value="${chargeMap.revShipFeeFF}">
																		</div>
																	</div>
																</div>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-6">
																		<div class="checkbox i-checks">
																			<label> <form:checkbox
																					path="nrnReturnConfig.revShippingFeeType" id="revShippingFeeType_revShipFeeShipFee"
																					value="revShipFeeShipFee" /> <i></i>Same as
																				Shipping Fee
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-6"></div>
																</div>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-6">
																		<div class="checkbox i-checks">
																			<label> <form:checkbox
																					path="nrnReturnConfig.revShippingFeeType" id="revShippingFeeType_revShipFeeVar"
																					value="revShipFeeVar" /> <i></i>Variable Shipping
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-6"></div>
																</div>
																<br> <br>
																<div class="col-sm-12">
																	<div class="col-sm-3 control-label">
																		<label> Dead Weight </label>
																	</div>
																	<div class="col-sm-3">
																		<label> </label>
																	</div>
																	<div class="col-sm-3 control-label">
																		<label> Volume Weight </label>
																	</div>
																	<div class="col-sm-3">
																		<label> </label>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12">
																		<div class="form-group">
																			<div class="col-md-2 control-label">
																				<label>Amount</label>
																			</div>
																			<div class="col-md-2 content-rgt">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-revShipFeeDWAmt"
																					value="${chargeMap.revShipFeeDWAmt}">
																			</div>
																			<div class="col-md-2">
																				<label> </label>
																			</div>
																			<div class="col-md-2 control-label">
																				<label>Amount</label>
																			</div>
																			<div class="col-md-2 content-rgt">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-revShipFeeVWAmt"
																					value="${chargeMap.revShipFeeVWAmt}">
																			</div>
																			<div class="col-md-2">
																				<label> </label>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12">
																		<div class="form-group">
																			<div class="col-md-2 control-label">
																				<label>Per Weight</label>
																			</div>
																			<div class="col-md-2 content-rgt">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-revShipFeeDWPW"
																					value="${chargeMap.revShipFeeDWPW}">
																			</div>
																			<div class="col-md-2">
																				<label> </label>
																			</div>
																			<div class="col-md-2 control-label">
																				<label>Per Weight</label>
																			</div>
																			<div class="col-md-2 content-rgt">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-revShipFeeVWPW"
																					value="${chargeMap.revShipFeeVWPW}">
																			</div>
																			<div class="col-md-2">
																				<label> </label>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12">
																		<div class="form-group">
																			<div class="col-md-2 control-label">
																				<label>Min Weight</label>
																			</div>
																			<div class="col-md-2 content-rgt">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-revShipFeeDWMW"
																					value="${chargeMap.revShipFeeDWMW}">
																			</div>
																			<div class="col-md-2">
																				<label> </label>
																			</div>
																			<div class="col-md-2 control-label">
																				<label>Min Weight</label>
																			</div>
																			<div class="col-md-2 content-rgt">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-revShipFeeVWMW"
																					value="${chargeMap.revShipFeeVWMW}">
																			</div>
																			<div class="col-md-2">
																				<label> </label>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
                          </div>
                        </div>
                    </div>
                
				<div class="ibox-content add-company">
					<button class="btn btn-primary pull-right" type="submit"
								onclick="submitOrder();">Save</button>
				</div>
				
				
              </div>
                
             </form:form>
			</div>	
            </div>
			
            </div>
				
			</div>
			<jsp:include page="../globalfooter.jsp"></jsp:include>
		</div>
		
	</div>

	<jsp:include page="../globaljslinks.jsp"></jsp:include>
	
	<!-- Switchery -->
	<script src="/O2R/seller/js/plugins/switchery/switchery.js"></script>
	<script src="/O2R/seller/js/pickList.js"></script>
	
<!-- Mainly scripts -->


<script src="/O2R/seller/js/plugins/dataTables/jquery.dataTables.js"></script>
<script src="/O2R/seller/js/plugins/dataTables/dataTables.bootstrap.js"></script>
<script src="/O2R/seller/js/plugins/dataTables/dataTables.responsive.js"></script>
<script src="/O2R/seller/js/plugins/dataTables/dataTables.tableTools.min.js"></script>




<!-- <script src="/O2R/seller/js/jquery-2.1.1.js"></script> 
<script src="/O2R/seller/js/bootstrap.min.js"></script> 
<script src="/O2R/seller/js/plugins/metisMenu/jquery.metisMenu.js"></script> 
<script src="/O2R/seller/js/plugins/slimscroll/jquery.slimscroll.min.js"></script> 
Data picker 
<script src="/O2R/seller/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
Custom and plugin javascript 
<script src="/O2R/seller/js/inspinia.js"></script> 
<script src="/O2R/seller/js/plugins/pace/pace.min.js"></script>  -->
<!-- iCheck --> 
<script src="/O2R/seller/js/plugins/iCheck/icheck.min.js"></script> 
<!-- Switchery --> 



<script type="text/javascript">
  $(document).ready(function () {
            	
        /* Retrive Radio Buttons Starts */
        	$(".commissionType").click(function() {
        				$('.radio1').hide();
						$("#blk-" + $(this).attr('id')).slideDown();
					});
					$(".shippingFeeType").click(function() {
						$('.radio1').hide();
						$("#blk-" + $(this).attr('id')).slideDown();
					});
					$(".retCharSFType").click(function() {
						
						$('.radio1').hide();
						$("#blk-" + $(this).attr('id')).slideDown();
					});
					$(".retCharBRType").click(function() {
						$('.radio1').hide();
						$("#blk-" + $(this).attr('id')).slideDown();
					});
					$(".RTOCharSFType").click(function() {
						$('.radio1').hide();
						$("#blk-" + $(this).attr('id')).slideDown();
					});
					$(".RTOCharBRType").click(function() {
						$('.radio1').hide();
						$("#blk-" + $(this).attr('id')).slideDown();
					});
					$(".repCharSFType").click(function() {
						$('.radio1').hide();
						$("#blk-" + $(this).attr('id')).slideDown();
					});
					$(".repCharBRType").click(function() {
						$('.radio1').hide();
						$("#blk-" + $(this).attr('id')).slideDown();
					});
					$(".PDCharSFType").click(function() {
						$('.radio1').hide();
						$("#blk-" + $(this).attr('id')).slideDown();
					});
					$(".PDCharBRType").click(function() {
						$('.radio1').hide();
						$("#blk-" + $(this).attr('id')).slideDown();
					});
					$(".canCharSFBFRTDType").click(function() {
						$('.radio1').hide();
						$("#blk-" + $(this).attr('id')).slideDown();
					});
					$(".canCharSFARTDType").click(function() {
						$('.radio1').hide();
						$("#blk-" + $(this).attr('id')).slideDown();
					});
					$(".canCharBRType").click(function() {
						$('.radio1').hide();
						$("#blk-" + $(this).attr('id')).slideDown();
					});
					$(".weight").click(function() {
						$('.radio1').hide();
						$("#blk-" + $(this).attr('id')).slideDown();
					});
					$(".returnCalculatorEvent").click(function() {
						$('.radio3').hide();
						$("#blk-" + $(this).attr('id')).slideDown();
					});
					$(".nrCalculatorEvent").click(function() {
						$('.radio2').hide();
						$("#blk-" + $(this).attr('id')).slideDown();
					});

					
					$('#data_1 .input-group.date').datepicker({
						todayBtn : "linked",
						keyboardNavigation : false,
						forceParse : false,
						calendarWeeks : true,
						autoclose : true
					});
        
					
					if ('${eventsBean.nrnReturnConfig.nrCalculatorEvent}' == 'original')
						$("#nrCalculatorEvent_original").prop("checked", true)
								.trigger("click");
					else if ('${eventsBean.nrnReturnConfig.nrCalculatorEvent}' == 'variable')
						$("#nrCalculatorEvent_variable").prop("checked", true)
						.trigger("click");
					else if ('${eventsBean.nrnReturnConfig.nrCalculatorEvent}' == 'fixed')
						$("#nrCalculatorEvent_fixed").prop("checked", true)
						.trigger("click");
					
					if ('${eventsBean.nrnReturnConfig.returnCalculatorEvent}' == 'original')
						$("#returnCalculatorEvent_original").prop("checked", true)
								.trigger("click");
					else if ('${eventsBean.nrnReturnConfig.returnCalculatorEvent}' == 'newTerms')
						$("#returnCalculatorEvent_newTerms").prop("checked", true)
						.trigger("click");
					
					
					
					if ('${eventsBean.nrnReturnConfig.commissionType}' == 'fixed')
						$("#commisionType-fixed").prop("checked", true)
								.trigger("click");
					else if ('${eventsBean.nrnReturnConfig.commissionType}' == 'categoryWise')
						$("#commisionType-categoryWise").prop(
								"checked", true).trigger("click");

					if ('${eventsBean.nrnReturnConfig.shippingFeeType}' == 'fixed')
						$("#shippingfee-fixed").prop("checked", true)
								.trigger("click");
					else if ('${eventsBean.nrnReturnConfig.shippingFeeType}' == 'variable')
						$("#shippingfee-variable")
								.prop("checked", true).trigger("click");
					if ('${eventsBean.nrnReturnConfig.retCharSFType}' == 'fixed') {
						$("#retrun-sf-fix").prop("checked", true)
								.trigger("click");
					} else if ('${eventsBean.nrnReturnConfig.retCharSFType}' == 'variable') {
						$("#retrun-sf-variable").prop("checked", true)
								.trigger("click");
					} else if ('${eventsBean.nrnReturnConfig.retCharSFType}' == 'noCharges')
						$("#retrun-sf-nocharges").prop("checked", true)
								.trigger("click");

					if ('${eventsBean.nrnReturnConfig.retCharBRType}' == 'fixed')
						$("#retrun-br-fix").prop("checked", true)
								.trigger("click");
					else if ('${eventsBean.nrnReturnConfig.retCharBRType}' == 'variable')
						$("#retrun-br-variable").prop("checked", true)
								.trigger("click");
					else if ('${eventsBean.nrnReturnConfig.retCharBRType}' == 'noCharges')
						$("#retrun-br-nocharges").prop("checked", true)
								.trigger("click");

					if ('${eventsBean.nrnReturnConfig.RTOCharSFType}' == 'fixed')
						$("#RTO-sf-fix").prop("checked", true).trigger(
								"click");
					else if ('${eventsBean.nrnReturnConfig.RTOCharSFType}' == 'variable')
						$("#RTO-sf-variable").prop("checked", true)
								.trigger("click");
					else if ('${eventsBean.nrnReturnConfig.RTOCharSFType}' == 'noCharges')
						$("#RTO-sf-nocharges").prop("checked", true)
								.trigger("click");

					if ('${eventsBean.nrnReturnConfig.RTOCharBRType}' == 'fixed')
						$("#RTO-br-fix").prop("checked", true).trigger(
								"click");
					else if ('${eventsBean.nrnReturnConfig.RTOCharBRType}' == 'variable')
						$("#RTO-br-variable").prop("checked", true)
								.trigger("click");
					else if ('${eventsBean.nrnReturnConfig.RTOCharBRType}' == 'noCharges')
						$("#RTO-br-nocharges").prop("checked", true)
								.trigger("click");

					if ('${eventsBean.nrnReturnConfig.repCharSFType}' == 'fixed')
						$("#rep-sf-fix").prop("checked", true).trigger(
								"click");
					else if ('${eventsBean.nrnReturnConfig.repCharSFType}' == 'variable')
						$("#rep-sf-variable").prop("checked", true)
								.trigger("click");
					else if ('${eventsBean.nrnReturnConfig.repCharSFType}' == 'noCharges')
						$("#rep-sf-nocharges").prop("checked", true)
								.trigger("click");

					if ('${eventsBean.nrnReturnConfig.repCharBRType}' == 'fixed')
						$("#rep-br-fix").prop("checked", true).trigger(
								"click");
					else if ('${eventsBean.nrnReturnConfig.repCharBRType}' == 'variable')
						$("#rep-br-variable").prop("checked", true)
								.trigger("click");
					else if ('${eventsBean.nrnReturnConfig.repCharBRType}' == 'noCharges')
						$("#rep-br-nocharges").prop("checked", true)
								.trigger("click");

					if ('${eventsBean.nrnReturnConfig.PDCharSFType}' == 'fixed')
						$("#PD-sf-fix").prop("checked", true).trigger(
								"click");
					else if ('${eventsBean.nrnReturnConfig.PDCharSFType}' == 'variable')
						$("#PD-sf-variable").prop("checked", true)
								.trigger("click");
					else if ('${eventsBean.nrnReturnConfig.PDCharSFType}' == 'noCharges')
						$("#PD-sf-nocharges").prop("checked", true)
								.trigger("click");

					if ('${eventsBean.nrnReturnConfig.PDCharBRType}' == 'fixed')
						$("#PD-br-fix").prop("checked", true).trigger(
								"click");
					else if ('${eventsBean.nrnReturnConfig.PDCharBRType}' == 'variable')
						$("#PD-br-variable").prop("checked", true)
								.trigger("click");
					else if ('${eventsBean.nrnReturnConfig.PDCharBRType}' == 'noCharges')
						$("#PD-br-nocharges").prop("checked", true)
								.trigger("click");

					if ('${eventsBean.nrnReturnConfig.canCharSFBFRTDType}' == 'fixed')
						$("#can-sfbfrtd-fix").prop("checked", true)
								.trigger("click");
					else if ('${eventsBean.nrnReturnConfig.canCharSFBFRTDType}' == 'variable')
						$("#can-sfbfrtd-variable")
								.prop("checked", true).trigger("click");
					else if ('${eventsBean.nrnReturnConfig.canCharSFBFRTDType}' == 'noCharges')
						$("#can-sfbfrtd-nocharges").prop("checked",
								true).trigger("click");

					if ('${eventsBean.nrnReturnConfig.canCharSFARTDType}' == 'fixed')
						$("#can-sfartd-fix").prop("checked", true)
								.trigger("click");
					else if ('${eventsBean.nrnReturnConfig.canCharSFARTDType}' == 'variable')
						$("#can-sfartd-variable").prop("checked", true)
								.trigger("click");
					else if ('${eventsBean.nrnReturnConfig.canCharSFARTDType}' == 'noCharges')
						$("#can-sfartd-nocharges")
								.prop("checked", true).trigger("click");

					if ('${eventsBean.nrnReturnConfig.canCharBRType}' == 'fixed')
						$("#can-br-fix").prop("checked", true).trigger(
								"click");
					else if ('${eventsBean.nrnReturnConfig.canCharBRType}' == 'variable')
						$("#can-br-variable").prop("checked", true)
								.trigger("click");
					else if ('${eventsBean.nrnReturnConfig.canCharBRType}' == 'noCharges')
						$("#can-br-nocharges").prop("checked", true)
								.trigger("click");
	
	 /* Retrive Radio Buttons Ends */
	
	 
	 /* Retrive CheckBoxes Starts */
	
	 if ('${eventsBean.nrnReturnConfig.whicheverGreaterPCC}' == 'true')
		 {		 
		 $('#whicheverGreaterPCC').iCheck('check');		
		 }
	 if ('${eventsBean.nrnReturnConfig.retCharSFRevShipFee}' == 'true')
		 	$('#retCharSFRevShipFee').iCheck('check');			
	 if ('${eventsBean.nrnReturnConfig.retCharSFShipFee}' == 'true')
		 $('#retCharSFShipFee').iCheck('check');
	 if ('${eventsBean.nrnReturnConfig.retCharBRFF}' == 'true')
		 $('#retCharBRFF').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.retCharBRShipFee}' == 'true')
		 $('#retCharBRShipFee').iCheck('check');	
		 
	 if ('${eventsBean.nrnReturnConfig.RTOCharSFRevShipFee}' == 'true')
		 $('#RTOCharSFRevShipFee').iCheck('check');	
		 	 
	 if ('${eventsBean.nrnReturnConfig.RTOCharSFFF}' == 'true')
		 $('#RTOCharSFFF').iCheck('check');	
			
	 if ('${eventsBean.nrnReturnConfig.RTOCharSFShipFee}' == 'true')
		 $('#RTOCharSFShipFee').iCheck('check');	
			
	 if ('${eventsBean.nrnReturnConfig.RTOCharSFRevShipFee}' == 'true')
		 $('#RTOCharSFRevShipFee').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.RTOCharBRFF}' == 'true')
		 $('#RTOCharBRFF').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.RTOCharBRShipFee}' == 'true')
		 $('#RTOCharBRShipFee').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.repCharSFRevShipFee}' == 'true')
		 $('#repCharSFRevShipFee').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.repCharSFFF}' == 'true')
		 $('#repCharSFFF').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.repCharSFShipFee}' == 'true')
		 $('#repCharSFShipFee').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.repCharSFRevShipFee}' == 'true')
		 $('#repCharSFRevShipFee').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.repCharBRFF}' == 'true')
		 $('#repCharBRFF').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.repCharBRShipFee}' == 'true')
		 $('#repCharBRShipFee').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.PDCharSFRevShipFee}' == 'true')
		 $('#PDCharSFRevShipFee').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.PDCharSFFF}' == 'true')
		 $('#PDCharSFFF').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.PDCharSFShipFee}' == 'true')
		 $('#PDCharSFShipFee').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.PDCharSFRevShipFee}' == 'true')
		 $('#PDCharSFRevShipFee').iCheck('check');
			
	 
	 if ('${eventsBean.nrnReturnConfig.PDCharBRFF}' == 'true')
		 $('#PDCharBRFF').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.PDCharBRShipFee}' == 'true')
		 $('#PDCharBRShipFee').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.canCharSFBRTDRevShipFee}' == 'true')
		 $('#canCharSFBRTDRevShipFee').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.canCharSFBRTDFF}' == 'true')
		 $('#canCharSFBRTDFF').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.canCharSFBRTDShipFee}' == 'true')
		 $('#canCharSFBRTDShipFee').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.canCharSFBRTDRevShipFee}' == 'true')
		 $('#canCharSFBRTDRevShipFee').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.canCharSFARTDRevShipFee}' == 'true')
		 $('#canCharSFARTDRevShipFee').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.canCharSFFF}' == 'true')
		 $('#canCharSFFF').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.canCharSFShipFee}' == 'true')
		 $('#canCharSFShipFee').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.canCharSFARTDRevShipFee}' == 'true')
		 $('#canCharSFARTDRevShipFee').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.canCharBRFF}' == 'true')
		 $('#canCharBRFF').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.canCharBRFF}' == 'true')
		 $('#canCharBRFF').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.canCharBRShipFee}' == 'true')
		 $('#canCharBRShipFee').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.canCharBRRevShipFee}' == 'true')
		 $('#canCharBRRevShipFee').iCheck('check');
			
	 if ('${eventsBean.nrnReturnConfig.revShippingFeeType}' == 'revShipFeeVar')
		 $('#revShippingFeeType_revShipFeeVar').iCheck('check');
	 if ('${eventsBean.nrnReturnConfig.retCharSFFF}' == 'true')
         $('#retCharSFFF').iCheck('check');
	 else if ('${eventsBean.nrnReturnConfig.revShippingFeeType}' == 'revShipFeeShipFee')
		 $('#revShippingFeeType_revShipFeeShipFee').iCheck('check');			
	 else if ('${eventsBean.nrnReturnConfig.revShippingFeeType}' == 'revShipFeeFF')
		 $('#revShippingFeeType_revShipFeeFF').iCheck('check');			
	 else if ('${eventsBean.nrnReturnConfig.revShippingFeeType}' == 'revShipFeeGRT')
		 $('#revShippingFeeType_revShipFeeGRT').iCheck('check');			
	 else if ('${eventsBean.nrnReturnConfig.revShippingFeeType}' == 'revShipFeePCC')
		 $('#revShippingFeeType_revShipFeePCC').iCheck('check');
			
	 
	 
	/* Retrive CheckBoxes Ends */
	
            
            

 
 
 /* 		$('.dataTables-example').dataTable({
                responsive: true,
                "dom": 'T<"clear">lfrtip',
                "tableTools": {
                    "sSwfPath": "js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
                }
        }); */
            	
            	
            	
            	
                $('.i-checks').iCheck({
                    checkboxClass: 'icheckbox_square-green',
                    radioClass: 'iradio_square-green',
                });
				
                      	        $('#data_1 .input-group.date').datepicker({
            	                todayBtn: "linked",
            	                keyboardNavigation: false,
            	                forceParse: false,
            	                calendarWeeks: true,
            	                autoclose: true
            	            });
            

            });
      

		function submitOrder() {
			var validator = $("#addEvent").validate({
				rules : {
					eventName : {
						required : true,
					},
					channelName : {
						required : true,
					},
					
					startDate : {
						required : true,
					},
					endDate : {
						required : true,
					},
					
				},
				messages : {
					eventName : "Event Name Should Be Entered !",
					channelName : "Channel Name Should be Selected !",
					startDate : "Select a Starting Date !",
					endDate : "select an Ending Date !"
				}
			});
		}

	div = {
		show: function(elem) {
			document.getElementById(elem).style.visibility = 'visible';
		},
		hide: function(elem) {
			document.getElementById(elem).style.visibility = 'hidden';
		}
	}	
</script>



	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"

    pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<body>

  <div class="row">

                <div class="col-lg-12">

                    <div class="ibox float-e-margins">

                        <div class="ibox-title">

                            <h5>${reportNameStr}</h5>

                          <!--  <h5>Report</h5> -->

                        </div>

                        <div class="ibox-content report-links">

                          <form method="POST" action="getReport.html" id="selectReportForm"

                        class="form-horizontal" name="selectReportForm">

                        <input type="hidden" name="reportName" id="reportName" value="${reportName}"/>

                        <div class="col-sm-12 mar-btm-20-oh">

                             <div class="form-group">

                             <label class="col-sm-4 control-label label-text-mrg">Select Period</label>

                                    <div class="col-sm-8">

                                        <div class="row">

                                            <div class="col-md-4" id="data_1">

                                            <div class="input-group date">

                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input type="text" name="startdate" class="form-control" value="Start Date">

                                            </div>

                                        </div>

                                        <div class="col-md-4" id="data_2">

                                            <div class="input-group date">

                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input  type="text" name="enddate" class="form-control" value="End Date">

                                            </div>

                                        </div>

                                        </div>

                                    </div>

                                </div>

                        </div>

          

                        <div class="col-sm-12">

                        <div class="hr-line-dashed"></div>

                            <h2><small>Select Headers</small></h2>

                            <div class="col-md-3">
                             <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="SelectAll"  id="selectall" name="headers">Select All</label></div>

                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="orderId" name="headers"> Order Id</label></div>

                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="invoiceId" name="headers"> Invoice Id </label></div>
                                
                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="returnCharges" name="headers"> Return Charges </label></div>
                                

                            </div>

                            <div class="col-md-3">

                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="Partner" name="headers"> Partner </label></div>

                                <div class="checkbox"><label> <input type="checkbox"  class="checkbox1" value="recievedDate" name="headers"> Received Date </label></div>

                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="status" name="headers"> Status</label></div>
                                  
                                 <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="netRate" name="headers"> Net Receivable </label></div>
                             

                            </div>

                            <div class="col-md-3">

                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="shippedDate" name="headers"> Shipped Date </label></div>

                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="payCycle" name="headers"> Payment Cycle </label></div>

                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="finalStatus" name="headers"> Final Status </label></div>

                            </div>

                            <div class="col-md-3">

                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="payDate" name="headers"> Payment Date </label></div>

                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="netPayment" name="headers"> Net Payment </label></div>

                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="payDiff" name="headers"> Payment Difference </label></div>

                            </div>

                        </div>

                                         <input type="hidden" name="requestType" id="requestType" value=""/>

                        <div class="col-sm-12">

                        <div class="hr-line-dashed"></div>

                        <button class="btn btn-primary pull-right mar-left-20" type="button" onclick="submitReport('download')" >Download Report</button>

                        <button class="btn btn-success pull-right" type="button" onclick="submitReport('view')">View Complete Report</button>

                        </div>

                          </form>     

                        </div>

                    </div>

                </div>

            </div>

<script>

    $(document).ready(function(){

        $('#data_1 .input-group.date').datepicker({

                todayBtn: "linked",

                keyboardNavigation: false,

                forceParse: false,

                calendarWeeks: true,

                autoclose: true

            });

        $('#data_2 .input-group.date').datepicker({

                todayBtn: "linked",

                keyboardNavigation: false,

                forceParse: false,

                calendarWeeks: true,

                autoclose: true

            });

 

        $("[name=toggler]").click(function(){

            $('.radio2').hide();

            $("#blk-"+$(this).val()).slideDown();

        });
        
    
            $('#selectall').click(function(event) {  //on click 
            	alert(" Select all ");
                if(this.checked) { // check select status
                    $('.checkbox1').each(function() { //loop through each checkbox
                        this.checked = true;  //select all checkboxes with class "checkbox1"               
                    });
                }else{
                    $('.checkbox1').each(function() { //loop through each checkbox
                        this.checked = false; //deselect all checkboxes with class "checkbox1"                       
                    });         
                }
            });
            
        

    });

    function submitReport(value){
    	
    	var startDate = new Date($("input[name='startdate']").val());
    	var endDate = new Date($("input[name='enddate']").val());
    	if(startDate == "Invalid Date" || endDate == "Invalid Date"){
    		alert("Please select both the dates to proceed!");
    		return;
    	}
    	
    	if(startDate > endDate){
    		alert("'Start Date' cannot be after 'End Date'! Kindly correct the dates to proceed!");
    		return;
    	}

       	if(value=='download'){
    	   	var timeDiff = Math.abs(new Date(startDate).getTime() - new Date(endDate).getTime());
    	   	var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
    	   	if(diffDays > 90){
        	   	alert("Download Report cannnot be generated for more than 90 days! Kindly change your date selection!");
        	   	return;
    	   	}
    	   
    	   	var isCheckboxSelected = false;
    	   	$('.checkbox1').each(function() {
    	   		if(this.checked && this.value!="SelectAll"){
    	   			isCheckboxSelected = true;
    	   		}               
           	});
    	   
    	   	if(!isCheckboxSelected){
    		   	alert("Please select atleast one checkbox to proceed");
    		   	return;
    	   	}
			document.getElementById('selectReportForm').action = "downloadreport.html";
		} else {
    	   	document.getElementById('selectReportForm').action = "getReport.html";
   	   	}
       	$("#selectReportForm").submit();
       	
       	$.ajax({
			url:$("#selectReportForm").attr("action"),
            context: document.body,
            type: 'post',
			data:$("#selectReportForm").serialize(),
			success : function(res) {
				$("#centerpane").html(res);
           	}
		});
	}

</script>

</body>

</html>
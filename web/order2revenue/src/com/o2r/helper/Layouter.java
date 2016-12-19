package com.o2r.helper;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

/**
 * Builds the report layout, the template, the design, the pattern
 *
 * @author Deep Mehrotra
 */

public class Layouter {

	private static Logger logger = Logger.getLogger("service");

	/**
	 * Builds the report layout.
	 * <p>
	 * This doesn't have any data yet. This is your template.
	 */

	public static void buildReport(HSSFSheet worksheet, int startRowIndex,
			int startColIndex, String sheetName) {
		// Set column widths
		worksheet.setColumnWidth(0, 5000);
		worksheet.setColumnWidth(1, 5000);
		worksheet.setColumnWidth(2, 5000);
		worksheet.setColumnWidth(3, 5000);
		worksheet.setColumnWidth(4, 5000);
		worksheet.setColumnWidth(5, 5000);

		// Build the title and date headers
		buildTitle(worksheet, startRowIndex, startColIndex, sheetName);
		// Build the column headers
		if (sheetName.equalsIgnoreCase("MP_Order_Upload"))
			buildHeaders(worksheet, startRowIndex, startColIndex);
		else if (sheetName.equalsIgnoreCase("Create_Parent_Product")) {
			buildProductHeaders(worksheet, startRowIndex, startColIndex);
		} else if (sheetName.equalsIgnoreCase("Product_Edit")) {
			buildEditProductHeaders(worksheet, startRowIndex, startColIndex);
		} else if (sheetName.equalsIgnoreCase("PO_Product_Config")) {
			buildProductConfigHeaders(worksheet, startRowIndex, startColIndex);
		} else if (sheetName.equalsIgnoreCase("MP_Payment_Upload")) {
			buildPaymentHeaders(worksheet, startRowIndex, startColIndex);
		} else if (sheetName.equalsIgnoreCase("InventoryReport")) {
			buildInventoryHeaders(worksheet, startRowIndex, startColIndex);
		} else if (sheetName.equalsIgnoreCase("MP_Return_Upload")) {
			buildReturnHeaders(worksheet, startRowIndex, startColIndex);
		} else if (sheetName.equalsIgnoreCase("PO_Gatepass_Uplooad")) {
			buildGatePassHeaders(worksheet, startRowIndex, startColIndex);
		} else if (sheetName.equalsIgnoreCase("PO_Order_Upload")) {
			buildOrderPOHeaders(worksheet, startRowIndex, startColIndex);
		} else if (sheetName.equalsIgnoreCase("DebitNoteSheet")) {
			buildDebitNoteHeaders(worksheet, startRowIndex, startColIndex);
		} else if (sheetName.equalsIgnoreCase("Po_Payment_Upload")) {
			buildPOPaymentHeaders(worksheet, startRowIndex, startColIndex);
		} else if (sheetName.equalsIgnoreCase("Expense_Upload")) {
			buildPExpenseHeaders(worksheet, startRowIndex, startColIndex);
		} else if (sheetName.equalsIgnoreCase("MP_SKU_Mapping")) {
			buildSKUMappingHeaders(worksheet, startRowIndex, startColIndex);
		} else if (sheetName.equalsIgnoreCase("MP_Vendor_SKU_Mapping")) {
			buildVendorSKUMappingHeaders(worksheet, startRowIndex, startColIndex);
		} else if (sheetName.equalsIgnoreCase("Dlink_SKU_Mapping")) {
			buildDlinkSKUMappingHeaders(worksheet, startRowIndex, startColIndex);
		} else if (sheetName.equalsIgnoreCase("ProdCat_Comm_Event_Mapping")) {
			buildProductCommEventHeaders(worksheet, startRowIndex, startColIndex);
		}
	}

	public static void buildOrderReport(HSSFSheet worksheet, int startRowIndex,
			int startColIndex, String sheetName, String[] headers) {
		// Set column widths
		int index = 0;
		for(String header: headers){
			worksheet.setColumnWidth(index++, 5000);
		}
		// Build the title and date headers
		buildTitle(worksheet, startRowIndex, startColIndex, sheetName);
		// Build the column headers
		buildReportHeaders(worksheet, headers, startRowIndex, startColIndex);

	}

	public static void buildReportHeaders(HSSFSheet worksheet,
			String[] headers, int startRowIndex, int startColIndex) {
		// Create font style for the headers
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create cell style for the headers
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		// Create the column headers
		HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
		rowHeader.setHeight((short) 500);
		int i = 0;
		for (String headervalue : headers) {

			if (!headervalue.equals("SelectAll")) {
				HSSFCell cell = rowHeader.createCell(startColIndex + i++);
				String headerStr = GlobalConstant.headerMap.get(headervalue);
				if(headerStr != null)
					cell.setCellValue(headerStr);
				else
					cell.setCellValue(headervalue);
				cell.setCellStyle(headerCellStyle);
			}

		}
	}

	/**
	 * Builds the report title and the date header
	 *
	 * @param worksheet
	 * @param startRowIndex
	 *            starting row offset
	 * @param startColIndex
	 *            starting column offset
	 */
	@SuppressWarnings("deprecation")
	public static void buildTitle(HSSFSheet worksheet, int startRowIndex,
			int startColIndex, String sheetName) {
		// Create font style for the report title
		Font fontTitle = worksheet.getWorkbook().createFont();
		fontTitle.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fontTitle.setFontHeight((short) 280);

		// Create cell style for the report title
		HSSFCellStyle cellStyleTitle = worksheet.getWorkbook()
				.createCellStyle();
		cellStyleTitle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyleTitle.setWrapText(true);
		cellStyleTitle.setFont(fontTitle);

		// Create report title
		HSSFRow rowTitle = worksheet.createRow((short) startRowIndex);
		rowTitle.setHeight((short) 500);
		HSSFCell cellTitle = rowTitle.createCell(startColIndex);
		cellTitle.setCellValue(sheetName);
		cellTitle.setCellStyle(cellStyleTitle);

		// Create merged region for the report title
		worksheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

		// Create date header
		HSSFRow dateTitle = worksheet.createRow((short) startRowIndex + 1);
		HSSFCell cellDate = dateTitle.createCell(startColIndex);
		if(sheetName.equalsIgnoreCase("OrderReturnReport")){
			cellDate.setCellValue("Note : Valid values For Criteria : channelOrderId, AWB, PIreference, suborderId, invoiceId & For Inventry Type : goodInventory, badInventory");
		}else if(sheetName.equalsIgnoreCase("PaymentReport")){
			cellDate.setCellValue("Note : Valid values For Criteria : 'manual charges' or 'payment'");
		}else{
			cellDate.setCellValue("This report was generated at " + new Date());
		}
	}

	/**
	 * Builds the column headers
	 *
	 * @param worksheet
	 * @param startRowIndex
	 *            starting row offset
	 * @param startColIndex
	 *            starting column offset
	 */
	public static void buildHeaders(HSSFSheet worksheet, int startRowIndex,
			int startColIndex) {
		// Create font style for the headers
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create cell style for the headers
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		// Create the column headers
		/*
		 * HSSFRow rowHeaderq = worksheet.createRow((short) startRowIndex +2);
		 * rowHeaderq.setHeight((short) 500);
		 */
		HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
		rowHeader.setHeight((short) 500);

		HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
		cell1.setCellValue("ChannelOrderID");
		cell1.setCellStyle(headerCellStyle);
		cell1.setCellType(HSSFCell.CELL_TYPE_STRING);

		HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
		cell2.setCellValue("OrderRecievedDate");
		cell2.setCellStyle(headerCellStyle);

		HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);
		cell3.setCellValue("SkUCode");
		cell3.setCellStyle(headerCellStyle);

		HSSFCell cell4 = rowHeader.createCell(startColIndex + 3);
		cell4.setCellValue("Partner");
		cell4.setCellStyle(headerCellStyle);

		HSSFCell cell5 = rowHeader.createCell(startColIndex + 4);
		cell5.setCellValue("Customer Name");
		cell5.setCellStyle(headerCellStyle);

		HSSFCell cell6 = rowHeader.createCell(startColIndex + 5);
		cell6.setCellValue("Payment Type");
		cell6.setCellStyle(headerCellStyle);

		HSSFCell cell7 = rowHeader.createCell(startColIndex + 6);
		cell7.setCellValue("AWB No.");
		cell7.setCellStyle(headerCellStyle);

		HSSFCell cell8 = rowHeader.createCell(startColIndex + 7);
		cell8.setCellValue("InvoiceID");
		cell8.setCellStyle(headerCellStyle);

		HSSFCell cell9 = rowHeader.createCell(startColIndex + 8);
		cell9.setCellValue("subOrderID");
		cell9.setCellStyle(headerCellStyle);

		HSSFCell cell10 = rowHeader.createCell(startColIndex + 9);
		cell10.setCellValue("PIreferenceNo");
		cell10.setCellStyle(headerCellStyle);

		HSSFCell cell11 = rowHeader.createCell(startColIndex + 10);
		cell11.setCellValue("Logistic Partner");
		cell11.setCellStyle(headerCellStyle);

		HSSFCell cell12 = rowHeader.createCell(startColIndex + 11);
		cell12.setCellValue("Order MRP");
		cell12.setCellStyle(headerCellStyle);

		HSSFCell cell13 = rowHeader.createCell(startColIndex + 12);
		cell13.setCellValue("Order SP");
		cell13.setCellStyle(headerCellStyle);

		/*HSSFCell cell14 = rowHeader.createCell(startColIndex + 13);
		cell14.setCellValue("Tax Category");
		cell14.setCellStyle(headerCellStyle);*/

		HSSFCell cell14 = rowHeader.createCell(startColIndex + 13);
		cell14.setCellValue("Shipped Date");
		cell14.setCellStyle(headerCellStyle);

		HSSFCell cell15 = rowHeader.createCell(startColIndex + 14);
		cell15.setCellValue("Quantity");
		cell15.setCellStyle(headerCellStyle);

		HSSFCell cell16 = rowHeader.createCell(startColIndex + 15);
		cell16.setCellValue("Net Rate");
		cell16.setCellStyle(headerCellStyle);

		HSSFCell cell17 = rowHeader.createCell(startColIndex + 16);
		cell17.setCellValue("Customer Email");
		cell17.setCellStyle(headerCellStyle);

		HSSFCell cell18 = rowHeader.createCell(startColIndex + 17);
		cell18.setCellValue("Customer Phone No");
		cell18.setCellStyle(headerCellStyle);

		HSSFCell cell19 = rowHeader.createCell(startColIndex + 18);
		cell19.setCellValue("Customer City");
		cell19.setCellStyle(headerCellStyle);

		HSSFCell cell20 = rowHeader.createCell(startColIndex + 19);
		cell20.setCellValue("Customer Address");
		cell20.setCellStyle(headerCellStyle);

		HSSFCell cell21 = rowHeader.createCell(startColIndex + 20);
		cell21.setCellValue("PinCode");
		cell21.setCellStyle(headerCellStyle);

		HSSFCell cell22 = rowHeader.createCell(startColIndex + 21);
		cell22.setCellValue("Seller Notes");
		cell22.setCellStyle(headerCellStyle);

	}

	/**
	 * Builds the column headers
	 *
	 * @param worksheet
	 * @param startRowIndex
	 *            starting row offset
	 * @param startColIndex
	 *            starting column offset
	 */
	public static void buildProductHeaders(HSSFSheet worksheet,
			int startRowIndex, int startColIndex) {
		// Create font style for the headers
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create cell style for the headers
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		// Create the column headers
		HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
		rowHeader.setHeight((short) 500);

		HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
		cell1.setCellValue("ProductName");
		cell1.setCellStyle(headerCellStyle);

		HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
		cell2.setCellValue("SkUCode");
		cell2.setCellStyle(headerCellStyle);

		HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);
		cell3.setCellValue("Category");
		cell3.setCellStyle(headerCellStyle);

		HSSFCell cell4 = rowHeader.createCell(startColIndex + 3);
		cell4.setCellValue("ProductPrice");
		cell4.setCellStyle(headerCellStyle);

		HSSFCell cell5 = rowHeader.createCell(startColIndex + 4);
		cell5.setCellValue("Quantity");
		cell5.setCellStyle(headerCellStyle);

		HSSFCell cell6 = rowHeader.createCell(startColIndex + 5);
		cell6.setCellValue("Threshold Limit");
		cell6.setCellStyle(headerCellStyle);

		/*HSSFCell cell7 = rowHeader.createCell(startColIndex + 6);
		cell7.setCellValue("ChanelSKU(Separated by ;)");
		cell7.setCellStyle(headerCellStyle);*/

		HSSFCell cell7 = rowHeader.createCell(startColIndex + 6);
		cell7.setCellValue("Length");
		cell7.setCellStyle(headerCellStyle);

		HSSFCell cell8 = rowHeader.createCell(startColIndex + 7);
		cell8.setCellValue("Breadth");
		cell8.setCellStyle(headerCellStyle);

		HSSFCell cell9 = rowHeader.createCell(startColIndex + 8);
		cell9.setCellValue("Height");
		cell9.setCellStyle(headerCellStyle);

		HSSFCell cell10 = rowHeader.createCell(startColIndex + 9);
		cell10.setCellValue("Dead Weight");
		cell10.setCellStyle(headerCellStyle);

	}
	
	public static void buildEditProductHeaders(HSSFSheet worksheet,
			int startRowIndex, int startColIndex) {
		// Create font style for the headers
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create cell style for the headers
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		// Create the column headers
		HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
		rowHeader.setHeight((short) 500);

		HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
		cell1.setCellValue("Title");
		cell1.setCellStyle(headerCellStyle);

		HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
		cell2.setCellValue("productSkuCode");
		cell2.setCellStyle(headerCellStyle);
		
		HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);
		cell3.setCellValue("ProductPrice");
		cell3.setCellStyle(headerCellStyle);		

		HSSFCell cell4 = rowHeader.createCell(startColIndex + 3);
		cell4.setCellValue("Threshold Limit");
		cell4.setCellStyle(headerCellStyle);

		HSSFCell cell5 = rowHeader.createCell(startColIndex + 4);
		cell5.setCellValue("ChanelSKU(Separated by ;)");
		cell5.setCellStyle(headerCellStyle);

		HSSFCell cell6 = rowHeader.createCell(startColIndex + 5);
		cell6.setCellValue("Length");
		cell6.setCellStyle(headerCellStyle);

		HSSFCell cell7 = rowHeader.createCell(startColIndex + 6);
		cell7.setCellValue("Breadth");
		cell7.setCellStyle(headerCellStyle);

		HSSFCell cell8 = rowHeader.createCell(startColIndex + 7);
		cell8.setCellValue("Height");
		cell8.setCellStyle(headerCellStyle);

		HSSFCell cell9 = rowHeader.createCell(startColIndex + 8);
		cell9.setCellValue("Dead Weight");
		cell9.setCellStyle(headerCellStyle);

	}
	
	

	public static void buildProductConfigHeaders(HSSFSheet worksheet,
			int startRowIndex, int startColIndex) {
		// Create font style for the headers
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create cell style for the headers
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		// Create the column headers
		HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
		rowHeader.setHeight((short) 500);

		HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
		cell1.setCellValue("ProductSKUCode");
		cell1.setCellStyle(headerCellStyle);

		HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
		cell2.setCellValue("ChannelName");
		cell2.setCellStyle(headerCellStyle);

		HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);
		cell3.setCellValue("ChannelSKUReference");
		cell3.setCellStyle(headerCellStyle);

		HSSFCell cell4 = rowHeader.createCell(startColIndex + 3);
		cell4.setCellValue("MRP");
		cell4.setCellStyle(headerCellStyle);

		HSSFCell cell5 = rowHeader.createCell(startColIndex + 4);
		cell5.setCellValue("ProductPrice");
		cell5.setCellStyle(headerCellStyle);

		HSSFCell cell6 = rowHeader.createCell(startColIndex + 5);
		cell6.setCellValue("Discount");
		cell6.setCellStyle(headerCellStyle);
	}

	/**
	 * Builds the column headers
	 *
	 * @param worksheet
	 * @param startRowIndex
	 *            starting row offset
	 * @param startColIndex
	 *            starting column offset
	 */
	public static void buildPaymentHeaders(HSSFSheet worksheet,
			int startRowIndex, int startColIndex) {
		// Create font style for the headers
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create cell style for the headers
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		// Create the column headers
		HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
		rowHeader.setHeight((short) 500);

		HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
		cell1.setCellValue("Criteria");
		cell1.setCellStyle(headerCellStyle);		
		
		HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
		cell2.setCellValue("ChannelOrderId");
		cell2.setCellStyle(headerCellStyle);

		HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);
		cell3.setCellValue("SKU");
		cell3.setCellStyle(headerCellStyle);		

		HSSFCell cell4 = rowHeader.createCell(startColIndex + 3);
		cell4.setCellValue("Secondary Order ID");
		cell4.setCellStyle(headerCellStyle);

		HSSFCell cell5 = rowHeader.createCell(startColIndex + 4);
		cell5.setCellValue("Recieved Amount");
		cell5.setCellStyle(headerCellStyle);

		HSSFCell cell6 = rowHeader.createCell(startColIndex + 5);
		cell6.setCellValue("Payment Date");
		cell6.setCellStyle(headerCellStyle);
		
		HSSFCell cell7 = rowHeader.createCell(startColIndex + 6);
		cell7.setCellValue("Channel");
		cell7.setCellStyle(headerCellStyle);
		
		HSSFCell cell8 = rowHeader.createCell(startColIndex + 7);
		cell8.setCellValue("Particular");
		cell8.setCellStyle(headerCellStyle);
		
		HSSFCell cell9 = rowHeader.createCell(startColIndex + 8);
		cell9.setCellValue("Recieved Date");
		cell9.setCellStyle(headerCellStyle);

	}

	public static void buildInventoryHeaders(HSSFSheet worksheet,
			int startRowIndex, int startColIndex) {
		// Create font style for the headers
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create cell style for the headers
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		// Create the column headers
		HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
		rowHeader.setHeight((short) 500);

		HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
		cell1.setCellValue("SkUCode");
		cell1.setCellStyle(headerCellStyle);

		HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
		cell2.setCellValue("CurrentQuantity");
		cell2.setCellStyle(headerCellStyle);

		HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);
		cell3.setCellValue("Quantity to Add");
		cell3.setCellStyle(headerCellStyle);

		HSSFCell cell4 = rowHeader.createCell(startColIndex + 3);
		cell4.setCellValue("Quantity to Substract");
		cell4.setCellStyle(headerCellStyle);

	}

	public static void buildReturnHeaders(HSSFSheet worksheet,
			int startRowIndex, int startColIndex) {
		// Create font style for the headers
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create cell style for the headers
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		// Create the column headers
		HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
		rowHeader.setHeight((short) 500);

		HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
		cell1.setCellValue("Criteria");
		cell1.setCellStyle(headerCellStyle);

		HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
		cell2.setCellValue("Value");
		cell2.setCellStyle(headerCellStyle);

		HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);
		cell3.setCellValue("SKU Code");
		cell3.setCellStyle(headerCellStyle);

		HSSFCell cell4 = rowHeader.createCell(startColIndex + 3);
		cell4.setCellValue("Return/RTO Id");
		cell4.setCellStyle(headerCellStyle);

		HSSFCell cell5 = rowHeader.createCell(startColIndex + 4);
		cell5.setCellValue("Return Reason");
		cell5.setCellStyle(headerCellStyle);

		HSSFCell cell6 = rowHeader.createCell(startColIndex + 5);
		cell6.setCellValue("Date");
		cell6.setCellStyle(headerCellStyle);

		HSSFCell cell7 = rowHeader.createCell(startColIndex + 6);
		cell7.setCellValue("Quantity");
		cell7.setCellStyle(headerCellStyle);

		HSSFCell cell8 = rowHeader.createCell(startColIndex + 7);
		cell8.setCellValue("Return Type");
		cell8.setCellStyle(headerCellStyle);

		HSSFCell cell9 = rowHeader.createCell(startColIndex + 8);
		cell9.setCellValue("Fault Type");
		cell9.setCellStyle(headerCellStyle);

		HSSFCell cell10 = rowHeader.createCell(startColIndex + 9);
		cell10.setCellValue("Stage");
		cell10.setCellStyle(headerCellStyle);
		
		HSSFCell cell11 = rowHeader.createCell(startColIndex + 10);
		cell11.setCellValue("Inventory Type");
		cell11.setCellStyle(headerCellStyle);
		
		HSSFCell cell12 = rowHeader.createCell(startColIndex + 11);
		cell12.setCellValue("Bad Inventory Quantity");
		cell12.setCellStyle(headerCellStyle);
		
		HSSFCell cell13 = rowHeader.createCell(startColIndex + 12);
		cell13.setCellValue("O2R Channel");
		cell13.setCellStyle(headerCellStyle);
	}
	
	public static void buildGatePassHeaders(HSSFSheet worksheet,
			int startRowIndex, int startColIndex) {
		// Create font style for the headers
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create cell style for the headers
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		// Create the column headers
		HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
		rowHeader.setHeight((short) 500);

		HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
		cell1.setCellValue("GatePass ID");
		cell1.setCellStyle(headerCellStyle);

		HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
		cell2.setCellValue("PO ID");
		cell2.setCellStyle(headerCellStyle);

		HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);
		cell3.setCellValue("Vendor Invoice ID");
		cell3.setCellStyle(headerCellStyle);

		HSSFCell cell4 = rowHeader.createCell(startColIndex + 3);
		cell4.setCellValue("SKU");
		cell4.setCellStyle(headerCellStyle);

		HSSFCell cell5 = rowHeader.createCell(startColIndex + 4);
		cell5.setCellValue("Return QTY");
		cell5.setCellStyle(headerCellStyle);

		HSSFCell cell6 = rowHeader.createCell(startColIndex + 5);
		cell6.setCellValue("PO Price/unit (with tax)");
		cell6.setCellStyle(headerCellStyle);

		HSSFCell cell7 = rowHeader.createCell(startColIndex + 6);
		cell7.setCellValue("Total PO Price (with tax)");
		cell7.setCellStyle(headerCellStyle);

		HSSFCell cell8 = rowHeader.createCell(startColIndex + 7);
		cell8.setCellValue("Sales Channel");
		cell8.setCellStyle(headerCellStyle);

		HSSFCell cell9 = rowHeader.createCell(startColIndex + 8);
		cell9.setCellValue("Return Reason");
		cell9.setCellStyle(headerCellStyle);

		HSSFCell cell10 = rowHeader.createCell(startColIndex + 9);
		cell10.setCellValue("Return Date");
		cell10.setCellStyle(headerCellStyle);		
	}

	public static void buildOrderPOHeaders(HSSFSheet worksheet,
			int startRowIndex, int startColIndex) {
		// Create font style for the headers
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create cell style for the headers
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		// Create the column headers
		HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
		rowHeader.setHeight((short) 500);

		HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
		cell1.setCellValue("Sales Channel");
		cell1.setCellStyle(headerCellStyle);

		HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
		cell2.setCellValue("PO ID");
		cell2.setCellStyle(headerCellStyle);

		HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);
		cell3.setCellValue("SkU");
		cell3.setCellStyle(headerCellStyle);

		HSSFCell cell4 = rowHeader.createCell(startColIndex + 3);
		cell4.setCellValue("Sale Qty");
		cell4.setCellStyle(headerCellStyle);

		HSSFCell cell5 = rowHeader.createCell(startColIndex + 4);
		cell5.setCellValue("PO Price/unit (with tax)");
		cell5.setCellStyle(headerCellStyle);

		HSSFCell cell6 = rowHeader.createCell(startColIndex + 5);
		cell6.setCellValue("Total PO Price (with tax)");
		cell6.setCellStyle(headerCellStyle);

		HSSFCell cell7 = rowHeader.createCell(startColIndex + 6);
		cell7.setCellValue("Invoice ID");
		cell7.setCellStyle(headerCellStyle);

		HSSFCell cell8 = rowHeader.createCell(startColIndex + 7);
		cell8.setCellValue("Seal No(AWB)");
		cell8.setCellStyle(headerCellStyle);

		HSSFCell cell9 = rowHeader.createCell(startColIndex + 8);
		cell9.setCellValue("PO Ship Date");
		cell9.setCellStyle(headerCellStyle);

		HSSFCell cell10 = rowHeader.createCell(startColIndex + 9);
		cell10.setCellValue("Seller Note");
		cell10.setCellStyle(headerCellStyle);
	}

	public static void buildDebitNoteHeaders(HSSFSheet worksheet,
			int startRowIndex, int startColIndex) {
		// Create font style for the headers
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create cell style for the headers
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		// Create the column headers
		HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
		rowHeader.setHeight((short) 500);

		HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
		cell1.setCellValue("POOrderID");
		cell1.setCellStyle(headerCellStyle);

		HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
		cell2.setCellValue("Gate Pass Id");
		cell2.setCellStyle(headerCellStyle);

		HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);
		cell3.setCellValue("SkUCode");
		cell3.setCellStyle(headerCellStyle);

		HSSFCell cell4 = rowHeader.createCell(startColIndex + 3);
		cell4.setCellValue("Partner");
		cell4.setCellStyle(headerCellStyle);

		HSSFCell cell5 = rowHeader.createCell(startColIndex + 4);
		cell5.setCellValue("Invoice Id");
		cell5.setCellStyle(headerCellStyle);

		HSSFCell cell6 = rowHeader.createCell(startColIndex + 5);
		cell6.setCellValue("Order Amount");
		cell6.setCellStyle(headerCellStyle);

		HSSFCell cell7 = rowHeader.createCell(startColIndex + 6);
		cell7.setCellValue("Quantity");
		cell7.setCellStyle(headerCellStyle);

		HSSFCell cell8 = rowHeader.createCell(startColIndex + 7);
		cell8.setCellValue("Return Date");
		cell8.setCellStyle(headerCellStyle);

		HSSFCell cell9 = rowHeader.createCell(startColIndex + 8);
		cell9.setCellValue("Return Reason");
		cell9.setCellStyle(headerCellStyle);

	}

	public static void buildPOPaymentHeaders(HSSFSheet worksheet,
			int startRowIndex, int startColIndex) {
		// Create font style for the headers
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create cell style for the headers
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		// Create the column headers
		HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
		rowHeader.setHeight((short) 500);

		HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
		cell1.setCellValue("Channel");
		cell1.setCellStyle(headerCellStyle);

		HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
		cell2.setCellValue("InvoiceID");
		cell2.setCellStyle(headerCellStyle);

		HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);
		cell3.setCellValue("Date of Payment");
		cell3.setCellStyle(headerCellStyle);

		HSSFCell cell4 = rowHeader.createCell(startColIndex + 3);
		cell4.setCellValue("Positive Amount");
		cell4.setCellStyle(headerCellStyle);

		HSSFCell cell5 = rowHeader.createCell(startColIndex + 4);
		cell5.setCellValue("Negative Amount");
		cell5.setCellStyle(headerCellStyle);

		HSSFCell cell6 = rowHeader.createCell(startColIndex + 5);
		cell6.setCellValue("Seller Notes");
		cell6.setCellStyle(headerCellStyle);

	}

	public static void buildPExpenseHeaders(HSSFSheet worksheet,
			int startRowIndex, int startColIndex) {
		// Create font style for the headers
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create cell style for the headers
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		// Create the column headers
		HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
		rowHeader.setHeight((short) 500);

		HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
		cell1.setCellValue("Name");
		cell1.setCellStyle(headerCellStyle);

		HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
		cell2.setCellValue("Description");
		cell2.setCellStyle(headerCellStyle);

		HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);
		cell3.setCellValue("Expense Category");
		cell3.setCellStyle(headerCellStyle);

		HSSFCell cell4 = rowHeader.createCell(startColIndex + 3);
		cell4.setCellValue("Expense Amount");
		cell4.setCellStyle(headerCellStyle);

		HSSFCell cell5 = rowHeader.createCell(startColIndex + 4);
		cell5.setCellValue("Expenditure By");
		cell5.setCellStyle(headerCellStyle);

		HSSFCell cell6 = rowHeader.createCell(startColIndex + 5);
		cell6.setCellValue("Paid To");
		cell6.setCellStyle(headerCellStyle);

		HSSFCell cell7 = rowHeader.createCell(startColIndex + 6);
		cell7.setCellValue("Expense Date");
		cell7.setCellStyle(headerCellStyle);

	}
	
	public static void buildSKUMappingHeaders(HSSFSheet worksheet,
			int startRowIndex, int startColIndex) {
		// Create font style for the headers
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create cell style for the headers
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		// Create the column headers
		HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
		rowHeader.setHeight((short) 500);

		HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
		cell1.setCellValue("Parent SKU");
		cell1.setCellStyle(headerCellStyle);

		HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
		cell2.setCellValue("Child SKU");
		cell2.setCellStyle(headerCellStyle);

		HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);
		cell3.setCellValue("Channel Name");
		cell3.setCellStyle(headerCellStyle);
	}
	
	public static void buildVendorSKUMappingHeaders(HSSFSheet worksheet,
			int startRowIndex, int startColIndex) {
		// Create font style for the headers
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create cell style for the headers
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		// Create the column headers
		HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
		rowHeader.setHeight((short) 500);

		HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
		cell1.setCellValue("Parent SKU");
		cell1.setCellStyle(headerCellStyle);

		HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
		cell2.setCellValue("Child SKU");
		cell2.setCellStyle(headerCellStyle);
		
		HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);
		cell3.setCellValue("Vendor SKU");
		cell3.setCellStyle(headerCellStyle);

		HSSFCell cell4 = rowHeader.createCell(startColIndex + 3);
		cell4.setCellValue("Channel Name");
		cell4.setCellStyle(headerCellStyle);
		
		HSSFCell cell5 = rowHeader.createCell(startColIndex + 4);
		cell5.setCellValue("Commision Percent");
		cell5.setCellStyle(headerCellStyle);
		
		HSSFCell cell6 = rowHeader.createCell(startColIndex + 5);
		cell6.setCellValue("Commision Percent");
		cell6.setCellStyle(headerCellStyle);
	}
	
	
	public static void buildDlinkSKUMappingHeaders(HSSFSheet worksheet,
			int startRowIndex, int startColIndex) {
		// Create font style for the headers
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create cell style for the headers
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		// Create the column headers
		HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
		rowHeader.setHeight((short) 500);

		HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
		cell1.setCellValue("Product SKU_Code");
		cell1.setCellStyle(headerCellStyle);

		HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
		cell2.setCellValue("Channel SKU_Ref");
		cell2.setCellStyle(headerCellStyle);
		
		HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);
		cell3.setCellValue("Vendor SKU_Ref");
		cell3.setCellStyle(headerCellStyle);

		HSSFCell cell4 = rowHeader.createCell(startColIndex + 3);
		cell4.setCellValue("Channel Name");
		cell4.setCellStyle(headerCellStyle);
	}
	
	public static void buildProductCommEventHeaders(HSSFSheet worksheet,
			int startRowIndex, int startColIndex) {
		// Create font style for the headers
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create cell style for the headers
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		// Create the column headers
		HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
		rowHeader.setHeight((short) 500);

		HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
		cell1.setCellValue("Channel Name");
		cell1.setCellStyle(headerCellStyle);

		HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
		cell2.setCellValue("Product Category");
		cell2.setCellStyle(headerCellStyle);
		
		HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);
		cell3.setCellValue("Commission Percent");
		cell3.setCellStyle(headerCellStyle);

		HSSFCell cell4 = rowHeader.createCell(startColIndex + 3);
		cell4.setCellValue("Event Name");
		cell4.setCellStyle(headerCellStyle);
	}
	
	
	

	public static void buildChannelOrderReport(HSSFSheet worksheet,
			int startRowIndex, int startColIndex, String reportName,
			String[] reportheaders) {

		// worksheet.setColumnWidth(0, 5000);
		// worksheet.setColumnWidth(1, 5000);
		// worksheet.setColumnWidth(2, 5000);
		// worksheet.setColumnWidth(3, 5000);
		// worksheet.setColumnWidth(4, 5000);
		// worksheet.setColumnWidth(5, 5000);

		// Build the title and date headers
		buildTitleCO(worksheet, startRowIndex, startColIndex, reportName);
		// Build the column headers
		buildReportHeadersCO(worksheet, reportheaders, startRowIndex,
				startColIndex);
	}

	private static void buildReportHeadersCO(HSSFSheet worksheet,
			String[] headers, int startRowIndex, int startColIndex) {
		Font font = worksheet.getWorkbook().createFont();
		// worksheet.addMergedRegion(new CellRangeAddress(2,2,6,8));
		// worksheet.addMergedRegion(new CellRangeAddress(2,2,9,11));
		// worksheet.addMergedRegion(new CellRangeAddress(2,2,13,15));
		// worksheet.addMergedRegion(new CellRangeAddress(2,2,18,20));

		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		startColIndex = 0; // Create cell style for the headers
		font.setColor((short) 125);
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		// headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		CellStyle style = worksheet.getWorkbook().createCellStyle();
		// style.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
		// style.setFillPattern(CellStyle.);
		Font font1 = worksheet.getWorkbook().createFont();
		font1.setColor(HSSFColor.LIGHT_BLUE.index);
		font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font1);

		// Create the column headers
		HSSFRow rowHeadera = worksheet.createRow(2);
		rowHeadera.setHeight((short) 250);

		int j = 0;
		HSSFCell cellz = null;
		// (int j=0;j<21;j++){
		// }

		worksheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));
		worksheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 8));
		worksheet.addMergedRegion(new CellRangeAddress(2, 2, 9, 12));
		worksheet.addMergedRegion(new CellRangeAddress(2, 2, 14, 17));
		worksheet.addMergedRegion(new CellRangeAddress(2, 2, 17, 19));
		HSSFRow rowHeader = worksheet.createRow(3);
		rowHeader.setHeight((short) 500);
		int i = 0;

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("Selected Period");
		cellz.setCellStyle(style);
		HSSFCell cell = rowHeader.createCell(i++);
		cell.setCellValue("Start Date");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(i++);
		cell.setCellValue("End Date");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("OrderId");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("InvoiceId");
		cell.setCellStyle(headerCellStyle);
		/*
		 * cell = rowHeader.createCell(startColIndex+ i++);
		 * cell.setCellValue("Product Category");
		 * cell.setCellStyle(headerCellStyle);
		 * 
		 * cell = rowHeader.createCell(startColIndex+ i++);
		 * cell.setCellValue("Inventory Group");
		 * cell.setCellStyle(headerCellStyle);
		 */

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("SKU");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("Gross Sale");
		cellz.setCellStyle(style);
		cellz.getCellStyle().setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("N/R Amount");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("SP Amount");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("Quantity");
		cell.setCellStyle(headerCellStyle);
		
		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("Tax");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("Sale Return");
		cellz.setCellStyle(style);
		cellz.getCellStyle().setFillBackgroundColor(HSSFColor.LIGHT_GREEN.index);
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("N/R Amount");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("SP Amount");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("Quantity");
		cell.setCellStyle(headerCellStyle);
		
		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("Tax");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("Sale Rtn Vs Gross Sale %");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("Net Sale");
		cellz.setCellStyle(style);
		cellz.getCellStyle().setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("N/R Amount");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("SP Amount");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("Quantity");
		cell.setCellStyle(headerCellStyle);
		
		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("Tax");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("Tax Category");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("Net Tax Liability On SP");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("Net Pure Sale");
		cellz.setCellStyle(style);
		cellz.getCellStyle().setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("N/R Amount");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("SP Amount");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("Quantity");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("Net A/R");
		cell.setCellStyle(headerCellStyle);

		cellz = rowHeadera.createCell(j++);
		cellz.setCellStyle(headerCellStyle);
		cellz.setCellValue("");
		cell = rowHeader.createCell(startColIndex + i++);
		cell.setCellValue("Net Due TBR");
		cell.setCellStyle(headerCellStyle);

	}

	private static void buildTitleCO(HSSFSheet worksheet, int startRowIndex,
			int startColIndex, String reportName) {
		buildTitle(worksheet, startRowIndex, startColIndex, reportName);
	}

}

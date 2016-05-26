package com.o2r.dao;

import java.util.Date;
import java.util.List;

import com.o2r.bean.ChannelReportDetails;
import com.o2r.bean.PartnerReportDetails;
import com.o2r.bean.TotalShippedOrder;
import com.o2r.helper.CustomException;
import com.o2r.model.UploadReport;

/**
 * @author Deep Mehrotra
 *
 */
public interface ReportsGeneratorDao {

	public TotalShippedOrder getPartnerTSOdetails(String pcName,
			Date startDate, Date endDate, int sellerId) throws CustomException;

	public List<TotalShippedOrder> getAllPartnerTSOdetails(Date startDate,
			Date endDate, int sellerId) throws CustomException;

	public List<PartnerReportDetails> getPartnerReportDetails(Date startDate, Date endDate,
			int sellerId) throws CustomException;

	List<ChannelReportDetails> getChannelReportDetails(Date startDate,
			Date endDate, int sellerId) throws CustomException;

	//public void addUploadReport(UploadReport uploadReport) throws CustomException;

	/*public List<UploadReport> listUploadReport() throws CustomException;

	public UploadReport addUploadReport(UploadReport uploadReport, int sellerId)
			throws CustomException;*/

}
package com.o2r.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.o2r.model.NRnReturnConfig;
import com.o2r.model.Partner;

public class EventsBean {
	
	private int sellerId;	
	private int eventId;
	private String eventName;
	private String channelName;
	private Date startDate;
	private Date endDate;
	private String productCategories;
	private String nrType;
	private String returnCharges;
	private NRnReturnConfig nrnReturnConfig;
	private Partner partner;
	private Date createdDate;
	private long netSalesQuantity;
	private double netSalesAmount;
	private String skuList;
	private String status;	
	
	private List<ChargesBean> fixedfeeList = new ArrayList<ChargesBean>();
	private List<ChargesBean> shippingfeeVolumeList = new ArrayList<ChargesBean>();
	private List<ChargesBean> shippingfeeWeightList = new ArrayList<ChargesBean>();
	
	
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getProductCategories() {
		return productCategories;
	}
	public void setProductCategories(String productCategories) {
		this.productCategories = productCategories;
	}
	public String getNrType() {
		return nrType;
	}
	public void setNrType(String nrType) {
		this.nrType = nrType;
	}
	public String getReturnCharges() {
		return returnCharges;
	}
	public void setReturnCharges(String returnCharges) {
		this.returnCharges = returnCharges;
	}
	public NRnReturnConfig getNrnReturnConfig() {
		return nrnReturnConfig;
	}
	public void setNrnReturnConfig(NRnReturnConfig nrnReturnConfig) {
		this.nrnReturnConfig = nrnReturnConfig;
	}
	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public long getNetSalesQuantity() {
		return netSalesQuantity;
	}
	public void setNetSalesQuantity(long netSalesQuantity) {
		this.netSalesQuantity = netSalesQuantity;
	}
	public double getNetSalesAmount() {
		return netSalesAmount;
	}
	public void setNetSalesAmount(double netSalesAmount) {
		this.netSalesAmount = netSalesAmount;
	}
	public List<ChargesBean> getFixedfeeList() {
		return fixedfeeList;
	}
	public void setFixedfeeList(List<ChargesBean> fixedfeeList) {
		this.fixedfeeList = fixedfeeList;
	}
	public List<ChargesBean> getShippingfeeVolumeList() {
		return shippingfeeVolumeList;
	}
	public void setShippingfeeVolumeList(List<ChargesBean> shippingfeeVolumeList) {
		this.shippingfeeVolumeList = shippingfeeVolumeList;
	}
	public List<ChargesBean> getShippingfeeWeightList() {
		return shippingfeeWeightList;
	}
	public void setShippingfeeWeightList(List<ChargesBean> shippingfeeWeightList) {
		this.shippingfeeWeightList = shippingfeeWeightList;
	}
	
	public ChargesBean getChargesBean(String type, String criteria, long criteriaRange) {
		ChargesBean returnBean = null;
		if (type.equalsIgnoreCase("fixedfee")) {
			for (ChargesBean bean : this.fixedfeeList) {
				if (bean.getCriteria().equalsIgnoreCase(criteria)
						&& bean.getRange() == criteriaRange) {
					returnBean = bean;
				}
			}
		} else if (type.equalsIgnoreCase("shippingfeeVolume")) {
			for (ChargesBean bean : this.shippingfeeVolumeList) {
				if (bean.getCriteria().equalsIgnoreCase(criteria)
						&& bean.getRange() == criteriaRange) {
					returnBean = bean;
				}
			}
		} else if (type.equalsIgnoreCase("shippingfeeWeight")) {
			for (ChargesBean bean : this.shippingfeeWeightList) {
				if (bean.getCriteria().equalsIgnoreCase(criteria)
						&& bean.getRange() == criteriaRange) {
					returnBean = bean;
				}
			}
		}
		return returnBean;
	}
	public String getSkuList() {
		return skuList;
	}
	public void setSkuList(String skuList) {
		this.skuList = skuList;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}

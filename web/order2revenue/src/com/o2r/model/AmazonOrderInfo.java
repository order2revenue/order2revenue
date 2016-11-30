package com.o2r.model;
// Generated Oct 23, 2016 4:48:17 PM by Hibernate Tools 3.4.0.CR1


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;

/**
 * AmazonOrderInfo generated by hbm2java
 */

@Entity
@Table(name="amazon_order_info",catalog="o2rschema")

public class AmazonOrderInfo  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int orderId;
     private String amazonorderid;
     private String sellerorderid;
     private Date purchasedate;
     private Date lastupdatedate;
     private String orderstatus;
     private String fulfillmentchannel;
     private String saleschannel;
     private String orderchannel;
     private String shipservicelevel;
     private Integer shippingAddId;
     private String ordertotalcurrencycode;
     private String ordertotalamount;
     private Integer numberofitemsshipped;
     private Integer numberofitemsunshipped;
     private String paymentexecutiondetailcurrencycode;
     private String paymentexecutiondetailamount;
     private String paymentexecutiondetailpaymentmethod;
     private String paymentmethod;
     private String marketplaceid;
     private String buyeremail;
     private String buyername;
     private String shipmentservicelevelcategory;
     private String shippedbyamazontfm;
     private String tfmshipmentstatus;
     private String cbadisplayableshippinglabel;
     private String ordertype;
     private Date earliestshipdate;
     private Date latestshipdate;
     private Date earliestdeliverydate;
     private Date latestdeliverydate;
     private String isbusinessorder;
     private String purchaseordernumber;
     private String isprime;
     private String ispremiumorder;
     private String requestid;    
     private int sellerId;

	private String o2rStatus;
     private String errorMessage;
     
     
    
 	
  private Set<AmazonOrderItemInfo> amazonOrderItemInfo = new HashSet<AmazonOrderItemInfo>(0);
  
  private Set<ShippingAddress> shippingAddressinfo = new HashSet<ShippingAddress>(0);
   


	private Seller seller;

    public AmazonOrderInfo() {
    }

	
    public AmazonOrderInfo(int orderId) {
        this.orderId = orderId;
    }
    public AmazonOrderInfo(int orderId, String amazonorderid, String sellerorderid, Date purchasedate, Date lastupdatedate, String orderstatus, String fulfillmentchannel, String saleschannel, String orderchannel, String shipservicelevel, Integer shippingAddId, String ordertotalcurrencycode, String ordertotalamount, Integer numberofitemsshipped, Integer numberofitemsunshipped, String paymentexecutiondetailcurrencycode, String paymentexecutiondetailamount, String paymentexecutiondetailpaymentmethod, String paymentmethod, String marketplaceid, String buyeremail, String buyername, String shipmentservicelevelcategory, String shippedbyamazontfm, String tfmshipmentstatus, String cbadisplayableshippinglabel, String ordertype, Date earliestshipdate, Date latestshipdate, Date earliestdeliverydate, Date latestdeliverydate, String isbusinessorder, String purchaseordernumber, String isprime, String ispremiumorder, String requestid, int sellerId) {
       this.orderId = orderId;
       this.amazonorderid = amazonorderid;
       this.sellerorderid = sellerorderid;
       this.purchasedate = purchasedate;
       this.lastupdatedate = lastupdatedate;
       this.orderstatus = orderstatus;
       this.fulfillmentchannel = fulfillmentchannel;
       this.saleschannel = saleschannel;
       this.orderchannel = orderchannel;
       this.shipservicelevel = shipservicelevel;
       this.shippingAddId = shippingAddId;
       this.ordertotalcurrencycode = ordertotalcurrencycode;
       this.ordertotalamount = ordertotalamount;
       this.numberofitemsshipped = numberofitemsshipped;
       this.numberofitemsunshipped = numberofitemsunshipped;
       this.paymentexecutiondetailcurrencycode = paymentexecutiondetailcurrencycode;
       this.paymentexecutiondetailamount = paymentexecutiondetailamount;
       this.paymentexecutiondetailpaymentmethod = paymentexecutiondetailpaymentmethod;
       this.paymentmethod = paymentmethod;
       this.marketplaceid = marketplaceid;
       this.buyeremail = buyeremail;
       this.buyername = buyername;
       this.shipmentservicelevelcategory = shipmentservicelevelcategory;
       this.shippedbyamazontfm = shippedbyamazontfm;
       this.tfmshipmentstatus = tfmshipmentstatus;
       this.ordertype = ordertype;
       this.earliestshipdate = earliestshipdate;
       this.latestshipdate = latestshipdate;
       this.earliestdeliverydate = earliestdeliverydate;
       this.latestdeliverydate = latestdeliverydate;
       this.isbusinessorder = isbusinessorder;
       this.purchaseordernumber = purchaseordernumber;
       this.isprime = isprime;
       this.ispremiumorder = ispremiumorder;
       this.requestid = requestid;
       //this.sellerId = sellerId;
    }
    

    @Id
 	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ORDER_ID", unique=true, nullable=false)
    public int getOrderId() {
        return this.orderId;
    }
    
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    
    
    
    
    @Column(name="AMAZONORDERID", length=1000)
    public String getAmazonorderid() {
        return this.amazonorderid;
    }
    
    public void setAmazonorderid(String amazonorderid) {
        this.amazonorderid = amazonorderid;
    }

    
    @Column(name="SELLERORDERID")
    public String getSellerorderid() {
        return this.sellerorderid;
    }
    
    public void setSellerorderid(String sellerorderid) {
        this.sellerorderid = sellerorderid;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="PURCHASEDATE", length=19)
    public Date getPurchasedate() {
        return this.purchasedate;
    }
    
    public void setPurchasedate(Date purchasedate) {
        this.purchasedate = purchasedate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LASTUPDATEDATE", length=19)
    public Date getLastupdatedate() {
        return this.lastupdatedate;
    }
    
    public void setLastupdatedate(Date lastupdatedate) {
        this.lastupdatedate = lastupdatedate;
    }

    
    @Column(name="ORDERSTATUS")
    public String getOrderstatus() {
        return this.orderstatus;
    }
    
    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    
    @Column(name="FULFILLMENTCHANNEL")
    public String getFulfillmentchannel() {
        return this.fulfillmentchannel;
    }
    
    public void setFulfillmentchannel(String fulfillmentchannel) {
        this.fulfillmentchannel = fulfillmentchannel;
    }

    
    @Column(name="SALESCHANNEL")
    public String getSaleschannel() {
        return this.saleschannel;
    }
    
    public void setSaleschannel(String saleschannel) {
        this.saleschannel = saleschannel;
    }

    
    @Column(name="ORDERCHANNEL")
    public String getOrderchannel() {
        return this.orderchannel;
    }
    
    public void setOrderchannel(String orderchannel) {
        this.orderchannel = orderchannel;
    }

    
    @Column(name="SHIPSERVICELEVEL")
    public String getShipservicelevel() {
        return this.shipservicelevel;
    }
    
    public void setShipservicelevel(String shipservicelevel) {
        this.shipservicelevel = shipservicelevel;
    }

    
    @Column(name="SHIPPING_ADD_ID")
    public Integer getShippingAddId() {
        return this.shippingAddId;
    }
    
    public void setShippingAddId(Integer shippingAddId) {
        this.shippingAddId = shippingAddId;
    }

    
    @Column(name="ORDERTOTALCURRENCYCODE")
    public String getOrdertotalcurrencycode() {
        return this.ordertotalcurrencycode;
    }
    
    public void setOrdertotalcurrencycode(String ordertotalcurrencycode) {
        this.ordertotalcurrencycode = ordertotalcurrencycode;
    }

    
    @Column(name="ORDERTOTALAMOUNT")
    public String getOrdertotalamount() {
        return this.ordertotalamount;
    }
    
    public void setOrdertotalamount(String ordertotalamount) {
        this.ordertotalamount = ordertotalamount;
    }

    
    @Column(name="NUMBEROFITEMSSHIPPED")
    public Integer getNumberofitemsshipped() {
        return this.numberofitemsshipped;
    }
    
    public void setNumberofitemsshipped(Integer numberofitemsshipped) {
        this.numberofitemsshipped = numberofitemsshipped;
    }

    
    @Column(name="NUMBEROFITEMSUNSHIPPED")
    public Integer getNumberofitemsunshipped() {
        return this.numberofitemsunshipped;
    }
    
    public void setNumberofitemsunshipped(Integer numberofitemsunshipped) {
        this.numberofitemsunshipped = numberofitemsunshipped;
    }

    
    @Column(name="PAYMENTEXECUTIONDETAILCURRENCYCODE")
    public String getPaymentexecutiondetailcurrencycode() {
        return this.paymentexecutiondetailcurrencycode;
    }
    
    public void setPaymentexecutiondetailcurrencycode(String paymentexecutiondetailcurrencycode) {
        this.paymentexecutiondetailcurrencycode = paymentexecutiondetailcurrencycode;
    }

    
    @Column(name="PAYMENTEXECUTIONDETAILAMOUNT")
    public String getPaymentexecutiondetailamount() {
        return this.paymentexecutiondetailamount;
    }
    
    public void setPaymentexecutiondetailamount(String paymentexecutiondetailamount) {
        this.paymentexecutiondetailamount = paymentexecutiondetailamount;
    }

    
    @Column(name="PAYMENTEXECUTIONDETAILPAYMENTMETHOD")
    public String getPaymentexecutiondetailpaymentmethod() {
        return this.paymentexecutiondetailpaymentmethod;
    }
    
    public void setPaymentexecutiondetailpaymentmethod(String paymentexecutiondetailpaymentmethod) {
        this.paymentexecutiondetailpaymentmethod = paymentexecutiondetailpaymentmethod;
    }

    
    @Column(name="PAYMENTMETHOD")
    public String getPaymentmethod() {
        return this.paymentmethod;
    }
    
    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    
    @Column(name="MARKETPLACEID")
    public String getMarketplaceid() {
        return this.marketplaceid;
    }
    
    public void setMarketplaceid(String marketplaceid) {
        this.marketplaceid = marketplaceid;
    }

    
    @Column(name="BUYEREMAIL")
    public String getBuyeremail() {
        return this.buyeremail;
    }
    
    public void setBuyeremail(String buyeremail) {
        this.buyeremail = buyeremail;
    }

    
    @Column(name="BUYERNAME")
    public String getBuyername() {
        return this.buyername;
    }
    
    public void setBuyername(String buyername) {
        this.buyername = buyername;
    }

    
    @Column(name="SHIPMENTSERVICELEVELCATEGORY")
    public String getShipmentservicelevelcategory() {
        return this.shipmentservicelevelcategory;
    }
    
    public void setShipmentservicelevelcategory(String shipmentservicelevelcategory) {
        this.shipmentservicelevelcategory = shipmentservicelevelcategory;
    }

    
    @Column(name="SHIPPEDBYAMAZONTFM")
    public String getShippedbyamazontfm() {
        return this.shippedbyamazontfm;
    }
    
    public void setShippedbyamazontfm(String shippedbyamazontfm) {
        this.shippedbyamazontfm = shippedbyamazontfm;
    }

    
    @Column(name="TFMSHIPMENTSTATUS")
    public String getTfmshipmentstatus() {
        return this.tfmshipmentstatus;
    }
    
    public void setTfmshipmentstatus(String tfmshipmentstatus) {
        this.tfmshipmentstatus = tfmshipmentstatus;
    }

    
    @Column(name="CBADISPLAYABLESHIPPINGLABEL", length=1000)
    public String getCbadisplayableshippinglabel() {
        return this.cbadisplayableshippinglabel;
    }
    
    public void setCbadisplayableshippinglabel(String cbadisplayableshippinglabel) {
        this.cbadisplayableshippinglabel = cbadisplayableshippinglabel;
    }

    
    @Column(name="ORDERTYPE")
    public String getOrdertype() {
        return this.ordertype;
    }
    
    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EARLIESTSHIPDATE", length=19)
    public Date getEarliestshipdate() {
        return this.earliestshipdate;
    }
    
    public void setEarliestshipdate(Date earliestshipdate) {
        this.earliestshipdate = earliestshipdate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LATESTSHIPDATE", length=19)
    public Date getLatestshipdate() {
        return this.latestshipdate;
    }
    
    public void setLatestshipdate(Date latestshipdate) {
        this.latestshipdate = latestshipdate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EARLIESTDELIVERYDATE", length=19)
    public Date getEarliestdeliverydate() {
        return this.earliestdeliverydate;
    }
    
    public void setEarliestdeliverydate(Date earliestdeliverydate) {
        this.earliestdeliverydate = earliestdeliverydate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LATESTDELIVERYDATE", length=19)
    public Date getLatestdeliverydate() {
        return this.latestdeliverydate;
    }
    
    public void setLatestdeliverydate(Date latestdeliverydate) {
        this.latestdeliverydate = latestdeliverydate;
    }

    
    @Column(name="ISBUSINESSORDER")
    public String getIsbusinessorder() {
        return this.isbusinessorder;
    }
    
    public void setIsbusinessorder(String isbusinessorder) {
        this.isbusinessorder = isbusinessorder;
    }

    
    @Column(name="PURCHASEORDERNUMBER")
    public String getPurchaseordernumber() {
        return this.purchaseordernumber;
    }
    
    public void setPurchaseordernumber(String purchaseordernumber) {
        this.purchaseordernumber = purchaseordernumber;
    }

    
    @Column(name="ISPRIME")
    public String getIsprime() {
        return this.isprime;
    }
    
    public void setIsprime(String isprime) {
        this.isprime = isprime;
    }

    
    @Column(name="ISPREMIUMORDER")
    public String getIspremiumorder() {
        return this.ispremiumorder;
    }
    
    public void setIspremiumorder(String ispremiumorder) {
        this.ispremiumorder = ispremiumorder;
    }

    
    @Column(name="REQUESTID")
    public String getRequestid() {
        return this.requestid;
    }
    
    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    
    @Column(name="SELLER_ID")
    public int getSellerId() {
        return this.sellerId;
    }
    
    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", nullable = false, referencedColumnName = "id")
	public Seller getSeller() {
		return seller;
	}


	public void setSeller(Seller seller) {
		this.seller = seller;
	}	
	
	
	@Column(name="O2R_STATUS")
    public String getO2rStatus() {
		return o2rStatus;
	}


	public void setO2rStatus(String o2rStatus) {
		this.o2rStatus = o2rStatus;
	}

	 @Column(name="ERROR_MESSAGE")
	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	


	@OneToMany(mappedBy="amazonOrderInfo", cascade=CascadeType.ALL, fetch = FetchType.EAGER )
	public Set<AmazonOrderItemInfo> getAmazonOrderItemInfo() {
		return amazonOrderItemInfo;
	}

	
	public void setAmazonOrderItemInfo(Set<AmazonOrderItemInfo> amazonOrderItemInfo) {
		this.amazonOrderItemInfo = amazonOrderItemInfo;
	}

	@OneToMany(mappedBy="amazonOrderInfo", cascade=CascadeType.ALL, fetch = FetchType.EAGER )
	 public Set<ShippingAddress> getShippingAddressinfo() {
			return shippingAddressinfo;
		}


		public void setShippingAddressinfo(Set<ShippingAddress> shippingAddressinfo) {
			this.shippingAddressinfo = shippingAddressinfo;
		}
		

}



package com.o2r.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class ShopcluesOrderAPI {	

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int shopcluesOrderId;
	@Column
	private String shopcluesUniqueId;
	@Column
	private int order_id;	
	@Column
	private String is_parent_order;
	@Column
	private int exempt_from_billing;
	@Column
	private int parent_order_id;
	@Column
	private int company_id;
	@Column
	private Date timestamp;
	@Column
	private String status;
	@Column
	private double total;
	@Column
	private double subtotal;
	@Column
	private String details;
	@Column
	private int payment_id;
	@Column
	private String s_city;
	@Column
	private String s_state;
	@Column
	private int s_zipcode;
	@Column
	private String label_printed;
	@Column
	private String gift_it;
	@Column
	private String firstname;
	@Column
	private String lastname;
	@Column
	private double credit_used;	
	@Column
	private String product_name;
	@Column
	private int product_id;
	@Column
	private int quantity;
	@Column
	private double selling_price;
	@Column
	private String image_path;
	@Column
	private String payment_type;
	@Column
	private String errorMsg;
	@Column
	private String orderStatus;
	@Column
	private int sellerId;	
	
	
	public int getShopcluesOrderId() {
		return shopcluesOrderId;
	}
	public void setShopcluesOrderId(int shopcluesOrderId) {
		this.shopcluesOrderId = shopcluesOrderId;
	}
	public String getShopcluesUniqueId() {
		return shopcluesUniqueId;
	}
	public void setShopcluesUniqueId(String shopcluesUniqueId) {
		this.shopcluesUniqueId = shopcluesUniqueId;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getIs_parent_order() {
		return is_parent_order;
	}
	public void setIs_parent_order(String is_parent_order) {
		this.is_parent_order = is_parent_order;
	}
	public int getExempt_from_billing() {
		return exempt_from_billing;
	}
	public void setExempt_from_billing(int exempt_from_billing) {
		this.exempt_from_billing = exempt_from_billing;
	}
	public int getParent_order_id() {
		return parent_order_id;
	}
	public void setParent_order_id(int parent_order_id) {
		this.parent_order_id = parent_order_id;
	}
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public int getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
	}
	public String getS_city() {
		return s_city;
	}
	public void setS_city(String s_city) {
		this.s_city = s_city;
	}
	public String getS_state() {
		return s_state;
	}
	public void setS_state(String s_state) {
		this.s_state = s_state;
	}
	public int getS_zipcode() {
		return s_zipcode;
	}
	public void setS_zipcode(int s_zipcode) {
		this.s_zipcode = s_zipcode;
	}
	public String getLabel_printed() {
		return label_printed;
	}
	public void setLabel_printed(String label_printed) {
		this.label_printed = label_printed;
	}
	public String getGift_it() {
		return gift_it;
	}
	public void setGift_it(String gift_it) {
		this.gift_it = gift_it;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public double getCredit_used() {
		return credit_used;
	}
	public void setCredit_used(double credit_used) {
		this.credit_used = credit_used;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getSelling_price() {
		return selling_price;
	}
	public void setSelling_price(double selling_price) {
		this.selling_price = selling_price;
	}
	public String getImage_path() {
		return image_path;
	}
	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	
	
	
	
	
	
	
}
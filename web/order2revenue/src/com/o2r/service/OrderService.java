package com.o2r.service;

import java.util.Date;
import java.util.List;

import com.o2r.bean.DebitNoteBean;
import com.o2r.bean.PoPaymentBean;
import com.o2r.model.Order;
import com.o2r.model.OrderPayment;
import com.o2r.model.OrderRTOorReturn;

/**
 * @author Deep Mehrotra
 *
 */
public interface OrderService {

 public void addOrder(Order order , int sellerId);

 public List<Order> listOrders(int sellerId);

 public Order getOrder(int orderId);

 public Order getOrder(int orderId,int sellerId);

 public void deleteOrder(Order order,int sellerId);

 public void addReturnOrder(String channelOrderId ,OrderRTOorReturn orderReturn,int sellerId);

 public void deleteReturnInfo(String orderId);

 public List<Order> findOrders(String column , String value ,int sellerId);

 public List<Order> findOrdersbyDate(String column ,Date startDate , Date endDate ,int sellerId);

 public List<Order> findOrdersbyReturnDate(String column ,Date startDate , Date endDate ,int sellerId);

 public List<Order> findOrdersbyPaymentDate(String column ,Date startDate , Date endDate ,int sellerId);

 public List<Order> findOrdersbyCustomerDetails(String column , String value ,int sellerId);

 public Order addOrderPayment(String skucode ,String channelOrderId , OrderPayment orderPayment,int sellerId);

 public Order addOrderPayment(int orderid, OrderPayment orderPayment,int sellerId);

 public void addDebitNote(DebitNoteBean dnBean,int sellerId);

 public void addPOPayment(PoPaymentBean popaBean,int sellerId);

 public List<Order> listOrders(int sellerId, int pageNo);


}
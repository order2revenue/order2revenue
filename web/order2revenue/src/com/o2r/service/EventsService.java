package com.o2r.service;

import java.util.Date;
import java.util.List;

import com.o2r.helper.CustomException;
import com.o2r.model.Events;
import com.o2r.model.Partner;

public interface EventsService {
	
	public void addEvent(Events event,int sellerId)throws CustomException;
	public Events getEvent(int eventId)throws CustomException;
	public List<Events> getEvents(Partner partner, int sellerId)throws CustomException;
	public Events getEvent(int partnerId, int sellerId)throws CustomException;
	public List<Events> listEvents(int sellerId)throws CustomException;
	public List<Events> listEvents(Date sDate, Date eDate, int sellerID)throws CustomException;
	public Events isEventActiive(Date orderDate,String channelName, String sku, int sellerId)throws CustomException;
	public Events getEvent(String eventName, int sellerID)throws CustomException;
	public boolean isDatesAllowForEvent(Date startDate, Date endDate, String channelName, int sellerId);
	public void changeStatus(int eventId,int sellerId);
}

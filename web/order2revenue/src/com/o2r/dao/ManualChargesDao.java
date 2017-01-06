package com.o2r.dao;

import java.util.Date;
import java.util.List;

import com.o2r.helper.CustomException;
import com.o2r.model.ManualCharges;

/**
 * @author Deep Mehrotra
 *
 */
public interface ManualChargesDao {
 
 public void addManualCharges(ManualCharges manualCharges , int sellerId)throws CustomException;
 
 public void addListManualCharges(List<ManualCharges> manualCharges , int sellerId)throws CustomException;
 
 public List<ManualCharges> listManualCharges(int sellerId)throws CustomException;

 public ManualCharges getManualCharges(int mcId)throws CustomException;
 
 public void deleteManualCharges(ManualCharges manualCharges,int sellerId)throws CustomException;

public Double getMCforPaymentID(String paymentId, int sellerId)throws CustomException;

	public List<ManualCharges> listManualCharges(int sellerId, Date startDate, Date endDate);
 
}
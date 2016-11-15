package com.o2r.service;

import java.util.List;
import java.util.Map;

import com.o2r.helper.CustomException;
import com.o2r.model.MetaPartner;
import com.o2r.model.Partner;

/**
 * @author Deep Mehrotra
 *
 */
public interface PartnerService {

	public void addPartner(Partner partner, int sellerId)
			throws CustomException;

	public List<Partner> listPartners(int sellerId) throws CustomException;

	public Partner getPartner(long partnerid) throws CustomException;

	public Partner getPartner(String name, int sellerId) throws CustomException;

	public void deletePartner(Partner partner, int sellerId)
			throws CustomException;

	public void addMetaPartner(MetaPartner partner) throws CustomException;

	public MetaPartner getMetaPartner(String partnerName)
			throws CustomException;

	public void editPartner(Partner partner, int sellerId)
			throws CustomException;

	public Map<String, Boolean> getPartnerMap(int sellerId)
			throws CustomException;
}
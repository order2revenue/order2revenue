package com.o2r.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.ManualCharges;
import com.o2r.model.Seller;

/**
 * @author Deep Mehrotra
 *
 */
@Repository("manualChargesDao")
public class ManualChargesDaoImpl implements ManualChargesDao {

	@Autowired
	private SessionFactory sessionFactory;	

	static Logger log = Logger.getLogger(ManualChargesDaoImpl.class.getName());

	@Override
	public void addManualCharges(ManualCharges manualCharges, int sellerId)	throws CustomException {
		log.info("*** addManualChargees start");
		Seller seller = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			if (manualCharges.getMcId() == 0) {
				Criteria criteria = session.createCriteria(Seller.class).add(
						Restrictions.eq("id", sellerId));
				seller = (Seller) criteria.list().get(0);
				manualCharges.setUploadDate(new Date());				
				seller.getManualCharges().add(manualCharges);
				session.saveOrUpdate(seller);
			} else {
				manualCharges.setUploadDate(new Date());
				session.saveOrUpdate(manualCharges);
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.error("Failed!",e);
			e.printStackTrace();
			throw new CustomException(GlobalConstant.addManualChargesError,
					new Date(), 1, GlobalConstant.addManualChargesErrorCode, e);			
		}
		log.info("***addManualCharges exit***");
	}
	
	
	@Override
	public void addListManualCharges(List<ManualCharges> manualCharges,
			int sellerId) throws CustomException {
		
		log.info("***** addListManualCharges starts ****");
		Seller seller=null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			if(manualCharges != null && manualCharges.size() != 0 && sellerId != 0 ){
				Criteria criteria = session.createCriteria(Seller.class).add(
						Restrictions.eq("id", sellerId));
				seller = (Seller) criteria.list().get(0);
				seller.getManualCharges().addAll(manualCharges);
				session.saveOrUpdate(seller);
			}
			session.getTransaction().commit();
			session.close();
			
		} catch (Exception e) {			
			log.error("Failed!",e);
			e.printStackTrace();
			throw new CustomException(GlobalConstant.addManualChargesError,
					new Date(), 1, GlobalConstant.addManualChargesErrorCode, e);
		}
		log.info("***** addListManualCharges Exits ****");
	}
	

	public List<ManualCharges> listManualCharges(int sellerId)
			throws CustomException {
		log.info("*** listManualCharges start ***");
		List<ManualCharges> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Seller seller = (Seller) session.get(Seller.class, sellerId);
			if (seller.getManualCharges() != null
					&& seller.getManualCharges().size() != 0)
				returnlist = seller.getManualCharges();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.error("Failed!",e);
			e.printStackTrace();
			throw new CustomException(GlobalConstant.listManualChargesError,
					new Date(), 3, GlobalConstant.listManualChargesErrorCode, e);
		}
		log.info("*** listManualCharges exit ***");
		return returnlist;
	}

	@Override
	public List<ManualCharges> listManualCharges(int sellerId, Date startDate, Date endDate)
			throws CustomException {
		log.info("*** ManualCharges between selected date range");
		List<ManualCharges> manualCharges = listManualCharges(sellerId);
		List<ManualCharges> filteredList = new ArrayList<ManualCharges>();

		if(manualCharges!=null&&manualCharges.size()!=0){
			for(ManualCharges manualCharge: manualCharges){
				if(manualCharge.getDateOfPayment().after(startDate) && manualCharge.getDateOfPayment().before(endDate))
					filteredList.add(manualCharge);
			}
		}
		log.info("*** ManualCharges between selected date range");
		return filteredList;
	}
	
	@Override
	public Double getMCforPaymentID(String paymentId, int sellerId)
			throws CustomException {
		log.info("*** getMCforPaymentID start ***");
		Double returnValue = 0.0;
		Seller seller = null;
		List<ManualCharges> mc = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("manualCharges", "manualCharges",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions
							.eq("manualCharges.chargesDesc", paymentId))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			if (criteria.list() != null && criteria.list().size() != 0) {
				seller = (Seller) criteria.list().get(0);
				mc = seller.getManualCharges();
			}
			if (mc != null) {
				for (ManualCharges manchar : mc) {
					returnValue = returnValue + manchar.getPaidAmount();
				}
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
			throw new CustomException(GlobalConstant.getMCforPaymentIDError,
					new Date(), 3, GlobalConstant.getMCforPaymentIDErrorCode, e);			
		}
		log.info("*** getMCforPaymentID exit ***");
		return returnValue;
	}

	public ManualCharges getManualCharges(int mcId) throws CustomException {
		log.info("*** getmanualCharges start ***");
		ManualCharges manualCharges = new ManualCharges();
		try {
			manualCharges = (ManualCharges) sessionFactory.getCurrentSession()
					.get(ManualCharges.class, mcId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
			throw new CustomException(GlobalConstant.getManualChargesError,
					new Date(), 3, GlobalConstant.getManualChargesErrorCode, e);
		}
		log.info("*** getManualCharges exit ***");
		return manualCharges;
	}

	public void deleteManualCharges(ManualCharges manualCharges, int sellerId)
			throws CustomException {
		log.info("*** deleteManualCharges start ***");
		try {

			Session session = sessionFactory.openSession();
			session.beginTransaction();

			Query deleteQuery = session
					.createSQLQuery("delete from Seller_ManualCharges where seller_Id=? and ManualCharges_mcId=?");
			deleteQuery.setInteger(0, sellerId);
			deleteQuery.setInteger(1, manualCharges.getMcId());

			int updated = deleteQuery.executeUpdate();
			int catdelete = session.createQuery(
					"DELETE FROM ManualCharges WHERE mcId = "
							+ manualCharges.getMcId()).executeUpdate();

			log.debug("Deleteing manual charges updated:" + updated + " catdelete :" + catdelete);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			log.error("Failed!",e);
			e.printStackTrace();
			throw new CustomException(GlobalConstant.deleteManualChargesError,
					new Date(), 3, GlobalConstant.deleteManualChargesErrorCode,
					e);
		}
		log.info("*** deleteManualCharges exit ***");
	}

}
package com.o2r.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.bean.ChannelCatNPR;
import com.o2r.bean.ChannelGP;
import com.o2r.bean.ChannelMC;
import com.o2r.bean.ChannelMCNPR;
import com.o2r.bean.ChannelNPR;
import com.o2r.bean.ChannelNR;
import com.o2r.bean.ChannelNetQty;
import com.o2r.bean.ChannelReportDetails;
import com.o2r.bean.CommissionDetails;
import com.o2r.bean.DataConfig;
import com.o2r.bean.PartnerReportDetails;
import com.o2r.bean.TotalShippedOrder;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.Customer;
import com.o2r.model.Order;
import com.o2r.model.OrderPayment;
import com.o2r.model.OrderRTOorReturn;
import com.o2r.model.OrderTax;
import com.o2r.model.Product;
import com.o2r.model.Seller;
import com.o2r.model.TaxCategory;
import com.o2r.model.UploadReport;
import com.o2r.service.TaxDetailService;

/**
 * @author Deep Mehrotra
 *
 */

@Repository("reportGeneratorDao")
public class ReportsGeneratorDaoImpl implements ReportsGeneratorDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private DataConfig dataConfig;
	
	@Resource(name="taxDetailService")
	private TaxDetailService taxDetailService;

	static Logger log = Logger.getLogger(ReportsGeneratorDaoImpl.class.getName());

	@SuppressWarnings("unchecked")
	@Override
	public TotalShippedOrder getPartnerTSOdetails(String pcName,
			Date startDate, Date endDate, int sellerId) throws CustomException {
		
		log.info("*** getPartnerTSOdetails Starts : ReportsGeneratorDaoImpl ****");
		log.debug(" getPartnerTSOdetails   : pcName :" + pcName
				+ " >>startDate" + startDate + ">>endDate :" + endDate);
		TotalShippedOrder ttso = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("pcName", pcName))
					.add(Restrictions.between("orderDate", startDate, endDate));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.sum("quantity"));
			projList.add(Projections.sum("netRate"));
			criteria.setProjection(projList);
			List<Object[]> results = criteria.list();
			Iterator iterator1 = results.iterator();
			if (results != null) {
				while (iterator1.hasNext()) {
					System.out.println("\n");
					Object[] recordsRow = (Object[]) iterator1.next();
					log.debug(" record length:" + recordsRow.length);
					for (int i = 0; i < recordsRow.length; i++) {
						System.out.print("\t" + recordsRow[i]);

					}
				}
			}

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
			throw new CustomException(GlobalConstant.getPartnerTSOdetailsError,
					new Date(), 3,
					GlobalConstant.getPartnerTSOdetailsErrorCode, e);			
		}
		log.info("*** getPartnerTSOdetails ends : ReportsGeneratorDaoImpl ****");
		return ttso;
	}

	@Override
	public List<TotalShippedOrder> getAllPartnerTSOdetails(Date startDate,
			Date endDate, int sellerId) throws CustomException {

		log.info("*** getAllPartnerTSOdetails Starts : ReportsGeneratorDaoImpl ****");
		TotalShippedOrder[] ttso = null;

		int totalQuantity = 0;
		int returnQuantity = 0;
		double totalNR = 0;
		double returnAmount = 0;
		double netSaleAmoount = 0;
		int cityTotalQuantity = 0;
		int totalNoofDO = 0;
		int totalNoofRO = 0;
		int totalNoofSO = 0;
		int totalNoofAO = 0;
		int totalNoofRTOCross = 0;
		int totalNoofreturnCross = 0;
		TotalShippedOrder ttsoTemp = null;
		Map<String, Double> cityMap = new HashMap<>();
		Map<String, Double> cityPercentMap = new HashMap<>();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("customer", "customer",
					CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("orderPayment", "orderPayment",
					CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.between("orderDate", startDate, endDate));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.groupProperty("pcName"));
			projList.add(Projections.sum("quantity"));
			projList.add(Projections.sum("netRate"));
			projList.add(Projections.sum("orderReturnOrRTO.returnorrtoQty"));
			projList.add(Projections.sum("orderPayment.negativeAmount"));
			projList.add(Projections.sum("orderSP"));
			projList.add(Projections.sum("orderPayment.positiveAmount"));
			criteria.setProjection(projList);
			criteria.addOrder(org.hibernate.criterion.Order.asc("pcName"));
			List<Object[]> results = criteria.list();
			log.debug("results.size()  " + results.size());
			ttso = new TotalShippedOrder[results.size()];
			log.debug(" Array size :" + ttso.length);
			log.debug("ttso  Object " + ttso[0]);
			Iterator iterator1 = results.iterator();
			int iteratorCount = 0;
			if (results != null) {
				while (iterator1.hasNext()) {
					System.out.println("\n");
					ttsoTemp = new TotalShippedOrder();
					Object[] recordsRow = (Object[]) iterator1.next();
					log.debug("recordsRow.length  "+ recordsRow.length);
					log.debug(" Quantity  NR ReturnQty  Return Amount SaleAmount  PositiveAmount");
					for (int i = 0; i < recordsRow.length; i++) {
						if (i == 0) {
							log.debug(" Setting pc name :"
									+ recordsRow[i].toString());
							
							ttsoTemp.setPcName(recordsRow[i].toString());
							log.debug(" After setting pc name = "
									+ ttsoTemp.getPcName());
						}
						if (i == 1) {
							ttsoTemp.setSaleQuantity(Integer
									.parseInt(recordsRow[i].toString()));
							totalQuantity = totalQuantity
									+ Integer
											.parseInt(recordsRow[i].toString());
						} else if (i == 2) {
							ttsoTemp.setNr(Double.parseDouble(recordsRow[i]
									.toString()));
							totalNR = totalNR
									+ Double.parseDouble(recordsRow[i]
											.toString());
						} else if (i == 3) {
							ttsoTemp.setReturnQuantity(Integer
									.parseInt(recordsRow[i].toString()));
							returnQuantity = returnQuantity
									+ Integer
											.parseInt(recordsRow[i].toString());
						} else if (i == 4) {
							ttsoTemp.setReturnAmount(Double
									.parseDouble(recordsRow[i].toString()));
							returnAmount = returnAmount
									+ Double.parseDouble(recordsRow[i]
											.toString());
						} else if (i == 5) {
							ttsoTemp.setNetSaleAmount(Double
									.parseDouble(recordsRow[i].toString()));
							netSaleAmoount = netSaleAmoount
									+ Double.parseDouble(recordsRow[i]
											.toString());
						}

						log.debug("\t" + recordsRow[i]);

					}
					ttso[iteratorCount] = ttsoTemp;
					iteratorCount++;
				}
			}

			Criteria criteriaforDeliveredOrder = session.createCriteria(Order.class);
			criteriaforDeliveredOrder
					.createAlias("seller", "seller",
							CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.between("orderDate", startDate, endDate))
					.add(Restrictions.le("deliveryDate", new Date()));
			criteriaforDeliveredOrder
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList DOprojList = Projections.projectionList();
			DOprojList.add(Projections.groupProperty("pcName"));
			DOprojList.add(Projections.rowCount());
			criteriaforDeliveredOrder.setProjection(DOprojList);
			// Object deliveredOrderCount =
			// criteriaforDeliveredOrder.list().get(0);
			List<Object[]> deliveredOrderCount = criteriaforDeliveredOrder.list();
			Iterator DOiterator = deliveredOrderCount.iterator();
			if (deliveredOrderCount != null) {
				iteratorCount = 0;
				while (DOiterator.hasNext()) {
					System.out.println("\n");
					Object[] recordsRow = (Object[]) DOiterator.next();
					log.debug(" Delivered order partner :"
							+ recordsRow[0].toString() + " coumt :"
							+ recordsRow[1].toString());
					totalNoofDO = totalNoofDO
							+ Integer.parseInt(recordsRow[1].toString());
					ttso[iteratorCount++].setNoOfDeliveredOrder(Integer
							.parseInt(recordsRow[1].toString()));
				}
			}

			Criteria criteriaforReturnOrder = session
					.createCriteria(Order.class);
			criteriaforReturnOrder.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			criteriaforReturnOrder
					.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
							CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.between("orderDate", startDate, endDate))
					.add(Restrictions
							.isNotNull("orderReturnOrRTO.returnOrRTOId"));
			criteriaforReturnOrder
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList ROprojList = Projections.projectionList();
			ROprojList.add(Projections.groupProperty("pcName"));
			ROprojList.add(Projections.rowCount());
			criteriaforReturnOrder.setProjection(ROprojList);

			List<Object[]> returnOrderCount = criteriaforReturnOrder.list();
			Iterator ROiterator = returnOrderCount.iterator();
			if (returnOrderCount != null) {
				iteratorCount = 0;
				while (ROiterator.hasNext()) {
					Object[] recordsRow = (Object[]) ROiterator.next();
					log.debug(" Return order partner :"
							+ recordsRow[0].toString() + " coumt :"
							+ recordsRow[1].toString());

					totalNoofRO = totalNoofRO
							+ Integer.parseInt(recordsRow[1].toString());
					ttso[iteratorCount++].setNoOfReturnOrder(Integer
							.parseInt(recordsRow[1].toString()));

				}
			}

			/*
			 * Code for caluclating no of actionalble orders
			 */
			Criteria criteriaforActionalbleOrder = session
					.createCriteria(Order.class);
			criteriaforActionalbleOrder
					.createAlias("seller", "seller",
							CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.between("orderDate", startDate, endDate))
					.add(Restrictions.eq("finalStatus", "Actionable"));
			criteriaforActionalbleOrder
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList AOprojList = Projections.projectionList();
			AOprojList.add(Projections.groupProperty("pcName"));
			AOprojList.add(Projections.rowCount());
			criteriaforActionalbleOrder.setProjection(AOprojList);

			List<Object[]> actionableOrderCount = criteriaforActionalbleOrder.list();
			Iterator AOiterator = actionableOrderCount.iterator();
			if (actionableOrderCount != null) {
				iteratorCount = 0;
				while (AOiterator.hasNext()) {
					Object[] recordsRow = (Object[]) AOiterator.next();
					log.debug(" Actionable order partner :"
							+ recordsRow[0].toString() + " coumt :"
							+ recordsRow[1].toString());

					totalNoofAO = totalNoofAO
							+ Integer.parseInt(recordsRow[1].toString());
					ttso[iteratorCount++].setNoOfActionableOrders(Integer
							.parseInt(recordsRow[1].toString()));

				}
			}

			/*
			 * Code for caluclating no of settled orders
			 */
			Criteria criteriaforSettledOrder = session
					.createCriteria(Order.class);
			criteriaforSettledOrder
					.createAlias("seller", "seller",
							CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.between("orderDate", startDate, endDate))
					.add(Restrictions.eq("finalStatus", "Settled"));
			criteriaforSettledOrder
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList SOprojList = Projections.projectionList();
			SOprojList.add(Projections.groupProperty("pcName"));
			SOprojList.add(Projections.rowCount());
			criteriaforSettledOrder.setProjection(SOprojList);

			List<Object[]> settledOrderCount = criteriaforSettledOrder.list();
			Iterator SOiterator = settledOrderCount.iterator();
			if (settledOrderCount != null) {
				iteratorCount = 0;
				while (SOiterator.hasNext()) {
					Object[] recordsRow = (Object[]) SOiterator.next();
					log.debug(" Settled order partner :"
							+ recordsRow[0].toString() + " coumt :"
							+ recordsRow[1].toString());
					totalNoofSO = totalNoofSO
							+ Integer.parseInt(recordsRow[1].toString());
					ttso[iteratorCount++].setNoOfSettledOrders(Integer
							.parseInt(recordsRow[1].toString()));

				}
			}

			/*
			 * Code for caluclating no of RTOlimit crossed orders
			 */
			Criteria criteriaRTOlimitCross = session
					.createCriteria(Order.class);
			criteriaRTOlimitCross.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			criteriaRTOlimitCross
					.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
							CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.geProperty("orderReturnOrRTO.returnDate",
							"rTOLimitCrossed"));
			criteriaRTOlimitCross
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList RTOprojList = Projections.projectionList();
			RTOprojList.add(Projections.groupProperty("pcName"));
			RTOprojList.add(Projections.rowCount());
			criteriaRTOlimitCross.setProjection(RTOprojList);

			List<Object[]> RTOlimitCrossOrderCount = criteriaRTOlimitCross.list();
			Iterator rtoOiterator = RTOlimitCrossOrderCount.iterator();
			if (RTOlimitCrossOrderCount != null) {
				iteratorCount = 0;
				while (rtoOiterator.hasNext()) {
					Object[] recordsRow = (Object[]) rtoOiterator.next();
					log.debug(" Rto limit partner :"
							+ recordsRow[0].toString() + " coumt :"
							+ recordsRow[1].toString());

					totalNoofRTOCross = totalNoofRTOCross
							+ Integer.parseInt(recordsRow[1].toString());
					ttso[iteratorCount++].setNoOfRTOLimitCrossed(Integer
							.parseInt(recordsRow[1].toString()));

				}
			}

			/*
			 * Code for caluclating no of retunrLimit crossed orders
			 */
			Criteria criteriaReturnlimitCross = session
					.createCriteria(Order.class);
			criteriaReturnlimitCross.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			criteriaReturnlimitCross
					.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
							CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.geProperty("orderReturnOrRTO.returnDate",
							"returnLimitCrossed"));
			criteriaReturnlimitCross
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList returnprojList = Projections.projectionList();
			returnprojList.add(Projections.groupProperty("pcName"));
			returnprojList.add(Projections.rowCount());
			criteriaReturnlimitCross.setProjection(returnprojList);
			List<Object[]> returnlimitCrossOrderCount = criteriaReturnlimitCross.list();
			Iterator returniterator = returnlimitCrossOrderCount.iterator();
			if (returnlimitCrossOrderCount != null) {
				while (returniterator.hasNext()) {
					iteratorCount = 0;
					Object[] recordsRow = (Object[]) returniterator.next();
					log.debug(" Return limit partner :"
							+ recordsRow[0].toString() + " coumt :"
							+ recordsRow[1].toString());

					totalNoofreturnCross = totalNoofreturnCross
							+ Integer.parseInt(recordsRow[1].toString());
					ttso[iteratorCount++].setNoOfReturnLimitCrossed(Integer
							.parseInt(recordsRow[1].toString()));

				}
			}
			/*
			 * Code for caluclating cities wise distribution of orders
			 */
			Criteria criteriaforCitiesOrder = session
					.createCriteria(Order.class);
			criteriaforCitiesOrder.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			criteriaforCitiesOrder
					.createAlias("customer", "customer",
							CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.between("orderDate", startDate, endDate))
					.add(Restrictions.isNotNull("customer.customerCity"))
					.add(Restrictions.ne("customer.customerCity", ""));
			criteriaforCitiesOrder
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList ccprojList = Projections.projectionList();
			ccprojList.add(Projections.groupProperty("customer.customerCity"));
			ccprojList.add(Projections.rowCount());
			criteriaforCitiesOrder.setProjection(ccprojList);
			List<Object[]> cityresults = criteriaforCitiesOrder.list();
			Iterator cityIterator = cityresults.iterator();
			if (cityresults != null) {
				while (cityIterator.hasNext()) {
					
					Object[] recordsRow = (Object[]) cityIterator.next();
					log.debug(" Cities row length "+ recordsRow.length);
					if (recordsRow.length > 0) {
						if (!cityMap.containsKey(recordsRow[0].toString())) {
							cityMap.put(recordsRow[0].toString(), Double
									.parseDouble(recordsRow[1].toString()));
						}
						log.debug("city " + recordsRow[0]+ " count : " + recordsRow[1]);
						cityTotalQuantity = cityTotalQuantity
								+ Integer.parseInt(recordsRow[1].toString());
					}

				}
			}
			log.debug(" ***cityTotalQuantity " + cityTotalQuantity);
			ttso[0].setCityQuantity(cityMap);
			for (Map.Entry<String, Double> entry : cityMap.entrySet()) {
				log.debug("for city : " + entry.getKey()
						+ " count is :" + entry.getValue() + " percent is :"
						+ (entry.getValue()) / cityTotalQuantity * 100);
				cityPercentMap.put(entry.getKey(), (entry.getValue())
						/ cityTotalQuantity * 100);
			}

			ttso[0].setCityQuantity(cityMap);
			ttso[0].setCityPercentage(cityPercentMap);

			for (int i = 0; i < ttso.length; i++) {
				ttso[i].setSaleQuantityPercent(ttso[i].getSaleQuantity() * 100
						/ totalQuantity);
				if ((int) returnQuantity != 0)
					ttso[i].setReturnQuantityPercent(ttso[i]
							.getReturnQuantity() * 100 / returnQuantity);
				ttso[i].setNrPercent(ttso[i].getNr() * 100 / totalNR);
				if ((int) returnAmount != 0)
					ttso[i].setReturnAmountPercent(ttso[i].getReturnAmount()
							* 100 / returnAmount);
				if ((int) netSaleAmoount != 0)
					ttso[i].setNetSaleAmountPercent(ttso[i].getNetSaleAmount()
							* 100 / netSaleAmoount);
				if ((int) totalNoofDO != 0)
					ttso[i].setDeliveredOrderPercent(ttso[i]
							.getNoOfDeliveredOrder() * 100 / totalNoofDO);
				if ((int) totalNoofAO != 0)
					ttso[i].setActionableOrdersPercent(ttso[i]
							.getNoOfActionableOrders() * 100 / totalNoofAO);
				if ((int) totalNoofRO != 0)
					ttso[i].setReturnOrderPercent(ttso[i].getNoOfReturnOrder()
							* 100 / totalNoofRO);
				ttso[i].setRTOOrderPercent(ttso[i].getNoOfRTOOrder());

				if ((int) totalNoofRTOCross != 0)
					ttso[i].setRTOLimitCrossedPercent(ttso[i]
							.getNoOfRTOLimitCrossed() * 100 / totalNoofRTOCross);
				if ((int) totalNoofreturnCross != 0)
					ttso[i].setReturnLimitCrossedPercent(ttso[i]
							.getNoOfReturnLimitCrossed()
							* 100
							/ totalNoofreturnCross);
				if ((int) totalNoofSO != 0)
					ttso[i].setSettledOrdersPercent(ttso[i]
							.getNoOfSettledOrders() * 100 / totalNoofSO);

			}

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
			throw new CustomException(
					GlobalConstant.getAllPartnerTSOdetailsError, new Date(), 3,
					GlobalConstant.getAllPartnerTSOdetailsErrorCode, e);
			
		}
		
		log.info("*** getAllPartnerTSOdetails Starts : ReportsGeneratorDaoImpl ****");
		return Arrays.asList(ttso);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerReportDetails> getDebtorsReportDetails(Date startDate,
			Date endDate, int sellerId) throws CustomException {
		List<PartnerReportDetails> partnerBusinessList = new ArrayList<PartnerReportDetails>();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();			
			List<Order> results = fetchDebtorsOrders(session, sellerId, startDate, endDate);
			Map<String, PartnerReportDetails> poOrderMap = new HashMap<String, PartnerReportDetails>();
			for (Order currOrder : results) {
				PartnerReportDetails partnerBusiness = transformPartnerDetail(currOrder, session, startDate, endDate);
					partnerBusinessList.add(partnerBusiness);
			}
			log.info("Total Orders" + partnerBusinessList.size());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
			throw new CustomException(
					GlobalConstant.getAllPartnerTSOdetailsError, new Date(), 3,
					GlobalConstant.getAllPartnerTSOdetailsErrorCode, e);

		}
		return partnerBusinessList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerReportDetails> getPartnerReportDetails(Date startDate,
			Date endDate, int sellerId) throws CustomException {
		List<PartnerReportDetails> partnerBusinessList = new ArrayList<PartnerReportDetails>();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			log.info("Service Tax: " + dataConfig.getServiceTax());
			List<Order> results = fetchOrders(session, sellerId, startDate, endDate);
			Map<String, PartnerReportDetails> poOrderMap = new HashMap<String, PartnerReportDetails>();
			for (Order currOrder : results) {
				PartnerReportDetails partnerBusiness = transformPartnerDetail(currOrder, session, startDate, endDate);
				partnerBusinessList.add(partnerBusiness);
			}
			log.info("Total Orders" + partnerBusinessList.size());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
			throw new CustomException(
					GlobalConstant.getAllPartnerTSOdetailsError, new Date(), 3,
					GlobalConstant.getAllPartnerTSOdetailsErrorCode, e);

		}
		return partnerBusinessList;
	}

	private PartnerReportDetails transformPartnerDetail(Order currOrder, Session session, Date startDate,
			Date endDate) {
		PartnerReportDetails partnerBusiness = new PartnerReportDetails();
		boolean isPoOrder = currOrder.isPoOrder();
		Order consolidatedOrder = currOrder.getConsolidatedOrder();
		Customer currCustomer = currOrder.getCustomer();
		OrderRTOorReturn currOrderReturnOrRTO = currOrder
				.getOrderReturnOrRTO();
		OrderPayment currOrderPayment = currOrder.getOrderPayment();
		OrderTax currOrderTax = currOrder.getOrderTax();
		int quantity = currOrder.getQuantity();
		partnerBusiness.setDeliveryDate(currOrder.getDeliveryDate());
		partnerBusiness.setAwb(currOrder.getAwbNum());
		partnerBusiness.setPiRefNumber(currOrder.getPIreferenceNo());
		partnerBusiness.setSubOrderId(currOrder.getSubOrderID());
		partnerBusiness.setLogisticPartner(currOrder.getLogisticPartner());
		partnerBusiness.setPaymentType(currOrder.getPaymentType());
		if(isPoOrder && consolidatedOrder==null)
			partnerBusiness.setPoOrder(true);
		else
			partnerBusiness.setPoOrder(false);
		if(currCustomer != null){
			partnerBusiness.setCustomerName(currCustomer.getCustomerName());
			partnerBusiness.setCustomerEmail(currCustomer.getCustomerEmail());
			partnerBusiness.setCustomerZip(currCustomer.getZipcode());
			partnerBusiness.setCustomerPhone(currCustomer.getCustomerPhnNo());
			partnerBusiness.setCustomerCity(currCustomer.getCustomerCity());
		}
		Criteria prodcriteria = session.createCriteria(Product.class);
		prodcriteria.add(Restrictions.eq("productSkuCode",
				currOrder.getProductSkuCode()));
		List<Product> productList = prodcriteria.list();
		double productCost = 0;
		if (productList.size() > 0) {
			Product currProduct = productList.get(0);
			partnerBusiness.setProductCategory(currProduct.getCategoryName());
			productCost = currProduct.getProductPrice();
		}
		
		double tdsToBeDeposited = 0;
		double taxToBePaid = 0;
		double taxSP = 0;
		double taxPOPrice = 0;		
		double grossTds = 0;
		double returnTds = 0;
		if(currOrderTax != null){
			grossTds = currOrderTax.getTdsToDeduct();
			taxToBePaid = currOrderTax.getTax();
			if(currOrderReturnOrRTO != null){
				returnTds = currOrderTax.getTdsToReturn();
				tdsToBeDeposited = grossTds - returnTds;
				taxToBePaid -= currOrderTax.getTaxToReturn();
			}
			// Only for PO
			if(partnerBusiness.isPoOrder()){
				taxSP = currOrderTax.getTaxToReturn();
				taxPOPrice = currOrderTax.getTax();
			}
		}
		partnerBusiness.setTaxSP(taxSP);
		partnerBusiness.setTaxPOPrice(taxPOPrice);
		partnerBusiness.setGrossTds(grossTds);
		partnerBusiness.setReturnTds(returnTds);
		partnerBusiness.setTdsToBeDeposited(tdsToBeDeposited);
		
		int returnQty = 0;
		double netReturnCharges = 0;
		double grossNetRate = currOrder.getGrossNetRate();
		double returnSP = 0;
		double grossSP = currOrder.getOrderSP();
		double orderSP =  grossSP * quantity;
		double netRate = currOrder.getNetRate();
		double grossReturnChargesReversed = 0; 
		double additionalReturnCharges = 0; 
		if (currOrderReturnOrRTO != null) {
			Date returnDate = currOrderReturnOrRTO.getReturnDate();
			boolean dateCriteria = returnDate!=null;
			if(dateCriteria){
				partnerBusiness.setReturnDate(currOrderReturnOrRTO.getReturnDate());
				returnQty = currOrderReturnOrRTO.getReturnorrtoQty();
				returnSP = grossSP*returnQty;
				partnerBusiness.setReturnQuantity(returnQty);
				netReturnCharges = grossNetRate * returnQty;
				additionalReturnCharges = currOrderReturnOrRTO.getReturnOrRTOChargestoBeDeducted();
				partnerBusiness.setNetReturnCharges(netReturnCharges);
				partnerBusiness.setReturnId(currOrderReturnOrRTO.getReturnOrRTOId());
				
				String type = currOrderReturnOrRTO.getType();
				String returnCategory = currOrderReturnOrRTO.getReturnCategory();
				String cancelType = currOrderReturnOrRTO.getCancelType();
				StringBuilder builder = new StringBuilder();
				if(StringUtils.isNotBlank(type)){
					builder.append(type);
					builder.append(" >> ");
				}
				if(StringUtils.isNotBlank(returnCategory)){
					builder.append(returnCategory);
					builder.append(" >> ");
				}
				if(StringUtils.isNotBlank(cancelType)){
					builder.append(type);
				}
				partnerBusiness.setReturnChargesDesciption(builder.toString());
			}
		}
		if(quantity > 0)
			grossReturnChargesReversed = netRate/quantity*returnQty;
		partnerBusiness.setProductPrice(productCost*(quantity - returnQty));
		partnerBusiness.setGrossReturnChargesReversed(grossReturnChargesReversed);
		partnerBusiness.setTotalReturnCharges(additionalReturnCharges + netReturnCharges);
		double netActualSale = currOrder.getNetRate() - netReturnCharges - additionalReturnCharges;
		partnerBusiness.setNetActualSale(netActualSale);
		partnerBusiness.setOrderSP(orderSP);
		partnerBusiness.setReturnSP(returnSP);
		partnerBusiness.setNetSP(grossSP/quantity*(quantity-returnQty));
		if (currOrderPayment != null) {
			partnerBusiness.setDateofPayment(currOrderPayment
					.getDateofPayment());
			double netPaymentResult = currOrderPayment
					.getNetPaymentResult();
			partnerBusiness.setNetPaymentResult(netPaymentResult);
			double paymentDifference = currOrderPayment
					.getPaymentDifference();
			partnerBusiness.setPaymentDifference(paymentDifference);
			partnerBusiness.setPaymentCycle(currOrderPayment.getPaymentCycle());
			partnerBusiness.setNegativeAmount(currOrderPayment.getNegativeAmount());
			partnerBusiness.setPositiveAmount(currOrderPayment.getPositiveAmount());
			partnerBusiness.setPaymentId(currOrderPayment.getPaymentId());			
			partnerBusiness.setPaymentUploadedDate(currOrderPayment.getUploadDate());
		}
		partnerBusiness.setOrderId(currOrder.getOrderId());
		partnerBusiness.setInvoiceID(currOrder.getInvoiceID());
		partnerBusiness
				.setChannelOrderID(currOrder.getChannelOrderID());
		partnerBusiness.setPcName(currOrder.getPcName());
		partnerBusiness.setOrderDate(currOrder.getOrderDate());
		partnerBusiness.setShippedDate(currOrder.getShippedDate());
		partnerBusiness
				.setPaymentDueDate(currOrder.getPaymentDueDate());
		partnerBusiness.setGrossSaleQuantity(quantity);
		int netSaleQty = quantity - returnQty;
		partnerBusiness.setNetSaleQuantity(netSaleQty);
		double grossCommission = 0;
		double grossCommissionNoQty = 0;
		double grossCommissionQty = 0;
		// MP & PO Conditions
		if(partnerBusiness.isPoOrder()){
			grossCommission = currOrder.getPartnerCommission();
			grossCommissionNoQty = grossCommission;
			grossCommissionQty = grossCommission;
		} else{
			grossCommission = currOrder.getPartnerCommission() * netSaleQty;
			grossCommissionNoQty = currOrder.getPartnerCommission() * returnQty;
			grossCommissionQty = currOrder.getPartnerCommission() * quantity;
		}
		partnerBusiness.setGrossPartnerCommission(grossCommission);
		double pccAmount = 0;
		double fixedFee = 0;
		double shippingCharges = 0;
		double pccAmountNoQty = 0;
		double fixedFeeNoQty = 0;
		double shippingChargesNoQty = 0;
		double pccAmountQty = 0;
		double fixedFeeQty = 0;
		double shippingChargesQty = 0;
		// Only for MP
		if(!partnerBusiness.isPoOrder()){
			pccAmount = currOrder.getPccAmount() * netSaleQty;
			fixedFee = currOrder.getFixedfee() * netSaleQty; 
			shippingCharges = currOrder.getShippingCharges() * netSaleQty;
			pccAmountNoQty = currOrder.getPccAmount() * returnQty;
			fixedFeeNoQty = currOrder.getFixedfee() * returnQty; 
			shippingChargesNoQty = currOrder.getShippingCharges() * returnQty;
			pccAmountQty = currOrder.getPccAmount() * quantity;
			fixedFeeQty = currOrder.getFixedfee() * quantity; 
			shippingChargesQty = currOrder.getShippingCharges() * quantity;
		}
		partnerBusiness.setPccAmount(pccAmount);				
		partnerBusiness.setFixedfee(fixedFee);
		partnerBusiness.setShippingCharges(shippingCharges);
		double totalAmount = grossCommission + pccAmount + fixedFee + shippingCharges;
		double serviceTax = (totalAmount)*dataConfig.getServiceTax()/100;
		double serviceTaxNoQty = (grossCommissionNoQty + pccAmountNoQty + fixedFeeNoQty + shippingChargesNoQty)*dataConfig.getServiceTax()/100;
		double serviceTaxQty = (grossCommissionQty + pccAmountQty + fixedFeeQty + shippingChargesQty)*dataConfig.getServiceTax()/100;
		partnerBusiness.setServiceTax(serviceTax);
		double grossCommissionToBePaid = 0;
		if(partnerBusiness.isPoOrder())
			grossCommissionToBePaid = grossCommission + taxSP - taxPOPrice;
		else
			grossCommissionToBePaid = totalAmount + serviceTax;
		double grossCommissionToBePaidNoQty = grossCommissionNoQty + pccAmountNoQty + fixedFeeNoQty + shippingChargesNoQty + serviceTaxNoQty;
		double grossCommissionToBePaidQty = grossCommissionQty + pccAmountQty + fixedFeeQty + shippingChargesQty + serviceTaxQty;
		partnerBusiness.setGrossCommissionQty(grossCommissionToBePaidQty);
		partnerBusiness.setGrossCommission(grossCommissionToBePaid);
		double returnCommision = 0;
		// MP & PO Conditions
		if(partnerBusiness.isPoOrder()){
			returnCommision = grossCommissionToBePaid;
		} else{
			returnCommision = grossCommissionToBePaidNoQty;
		}
		partnerBusiness.setReturnCommision(returnCommision);
		partnerBusiness.setAdditionalReturnCharges(additionalReturnCharges);
		double netPartnerCommissionPaid = grossCommissionToBePaidQty - returnCommision + additionalReturnCharges;
		partnerBusiness.setNetPartnerCommissionPaid(netPartnerCommissionPaid);
		partnerBusiness.setTdsToBeDeducted10(0.1 * grossCommission);
		partnerBusiness.setTdsToBeDeducted2(0.02 * (pccAmount + fixedFee + shippingCharges));
		partnerBusiness.setNetTaxToBePaid(taxToBePaid);
		double netEossValue = 0; 
		// Only for Consolidated PO
		if(isPoOrder && consolidatedOrder!=null)
			netEossValue = currOrder.getEossValue();
		partnerBusiness.setNetEossValue(netEossValue);
		partnerBusiness.setNetPr(currOrder.getPr()/quantity*(quantity-returnQty));
		partnerBusiness.setGrossNetRate(grossNetRate*quantity);
		partnerBusiness.setNetRate(currOrder.getNetRate());
		partnerBusiness.setFinalStatus(currOrder.getFinalStatus());
		partnerBusiness.setGrossProfit(currOrder.getGrossProfit());
		return partnerBusiness;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelReportDetails> getChannelReportDetails(Date startDate,
			Date endDate, int sellerId) throws CustomException {
		
		log.info("*** getChannelReportDetails Starts : ReportsGeneratorDaoImpl ****");
		List<ChannelReportDetails> channelReportDetailsList = new ArrayList<ChannelReportDetails>();
		 
		 try
		 {
			 Session session=sessionFactory.openSession();
			 session.getTransaction().begin();
			 
			 List<Order> orders = fetchOrders(session, sellerId, startDate, endDate);
			 Map<String, ChannelReportDetails> poOrderMap = new HashMap<String, ChannelReportDetails>();
			 for (Order currOrder : orders) {
				 ChannelReportDetails channelReport = transformChannelReport(currOrder, session, sellerId);
				 channelReportDetailsList.add(channelReport);
			 }
		 } catch (Exception e) {
			 log.error("Failed!",e);
			throw new CustomException(
					GlobalConstant.getAllPartnerTSOdetailsError, new Date(), 3,
					GlobalConstant.getAllPartnerTSOdetailsErrorCode, e);

		}
			 
		return channelReportDetailsList;	 
	}

	private ChannelReportDetails transformChannelReport(Order currOrder, Session session, int sellerId) {
		boolean isPoOrder = currOrder.isPoOrder();
		Order consolidateOrder = currOrder.getConsolidatedOrder();
		ChannelReportDetails channelReport = new ChannelReportDetails();
		channelReport.setPartner(currOrder.getPcName());
		channelReport.setOrderId(currOrder.getChannelOrderID());
		channelReport.setInvoiceId(currOrder.getInvoiceID());
		channelReport.setShippedDate(currOrder.getShippedDate());
		channelReport.setReceivedDate(currOrder.getOrderDate());
		channelReport.setDeliveryDate(currOrder.getDeliveryDate());
		channelReport.setPaymentDate(currOrder.getPaymentDueDate());
		channelReport.setProductSku(currOrder.getProductSkuCode());
		channelReport.setPaymentType(currOrder.getPaymentType());
		channelReport.setAwb(currOrder.getAwbNum());
		channelReport.setPiRefNo(currOrder.getPIreferenceNo());
		channelReport.setSubOrderId(currOrder.getSubOrderID());
		channelReport.setLogisticPartner(currOrder.getLogisticPartner());
		if(isPoOrder && consolidateOrder==null)
			channelReport.setPoOrder(true);
		else
			channelReport.setPoOrder(false);
		double orderPr = currOrder.getPr();
		double grossProfit = currOrder.getGrossProfit();
		double grossSaleQty = currOrder.getQuantity();
		double grossNrAmount = currOrder.getGrossNetRate();
		double grossSpAmount = currOrder.getOrderSP();
		double saleRetQty = 0;
		double saleRetNrAmount = 0;
		double saleRetSpAmount = 0;
		channelReport.setGrossProfit(grossProfit);
		channelReport.setGrossQty(grossSaleQty);
		channelReport.setGrossNrAmount(grossNrAmount*grossSaleQty);
		channelReport.setGrossSpAmount(grossSpAmount);

		OrderRTOorReturn currOrderReturn = currOrder.getOrderReturnOrRTO();
		double additionalCharges = 0; 
		if(currOrderReturn != null){
			additionalCharges = currOrderReturn.getReturnOrRTOChargestoBeDeducted();
			channelReport.setNetReturnCharges(additionalCharges);
			saleRetQty = currOrderReturn.getReturnorrtoQty();
			channelReport.setReturnDate(currOrderReturn.getReturnDate());
			channelReport.setReturnId(currOrderReturn.getReturnOrRTOId());
			// Only for PO Order
			if(channelReport.isPoOrder()){
				saleRetNrAmount = currOrderReturn.getNetNR();
				channelReport.setReturnId(currOrderReturn.getReturnOrRTOId());
			}
		}
		double netPr = currOrder.getPr()/grossSaleQty*(grossSaleQty-saleRetQty);
		channelReport.setNetPr(netPr);	
		// MP/PO Order conditions
		if(channelReport.isPoOrder()){
			saleRetSpAmount = grossSpAmount;
			if(grossSaleQty != 0)
				channelReport.setPr(orderPr * saleRetQty/grossSaleQty);
		} else {
			saleRetNrAmount = grossNrAmount*saleRetQty;
			if(grossSaleQty != 0)
				saleRetSpAmount = grossSpAmount*(saleRetQty/grossSaleQty);
			channelReport.setPr(orderPr);
		}
		channelReport.setSaleRetQty(saleRetQty);
		channelReport.setSaleRetNrAmount(saleRetNrAmount + additionalCharges);
		channelReport.setRetAmountToBeReversed(saleRetNrAmount);
		channelReport.setSaleRetSpAmount(saleRetSpAmount);
		
		double saleRetVsGrossSale = 0;
		if(grossSaleQty != 0)
			saleRetVsGrossSale = saleRetQty/grossSaleQty*100;
		double netQty = grossSaleQty - saleRetQty;
		double netNrAmount = grossNrAmount*grossSaleQty - saleRetNrAmount - additionalCharges;
		double netSpAmount = grossSpAmount - saleRetSpAmount;
		channelReport.setSaleRetVsGrossSale(saleRetVsGrossSale);
		channelReport.setNetQty(netQty);
		channelReport.setNetNrAmount(netNrAmount);
		channelReport.setNetSpAmount(netSpAmount);
		
		double taxCatPercent = 0;
		OrderTax currOrderTax = currOrder.getOrderTax();
		if(currOrderTax != null){
			String taxCategory = currOrderTax.getTaxCategtory();
			channelReport.setTaxCategory(taxCategory);
			if(StringUtils.isNotBlank(taxCategory)){
				try {
					TaxCategory	taxCategoryDetails = taxDetailService.getTaxCategory(taxCategory, sellerId);
					taxCatPercent = taxCategoryDetails.getTaxPercent();
				} catch (CustomException e) {
					log.error("Tax Category Not found for " + taxCategory);
				}
			}
		}
		double netTaxLiability = 0;
		if(taxCatPercent != 0)
			netTaxLiability = netSpAmount - netSpAmount*(100/(100 + taxCatPercent));
		channelReport.setNetTaxLiability(netTaxLiability);
		
		OrderPayment currOrderPayment = currOrder.getOrderPayment();
		if(currOrderPayment != null){
			channelReport.setNetToBeReceived(currOrderPayment.getPaymentDifference());
			double netPaymentResult = currOrderPayment.getNetPaymentResult();
			double paymentDifference = currOrderPayment.getPaymentDifference();
			channelReport.setNetPaymentResult(netPaymentResult);
			channelReport.setPaymentDifference(paymentDifference);
			channelReport.setNetAr(netPaymentResult);
			channelReport.setPaymentId(currOrderPayment.getPaymentId() + "");						
		}
		
		double productCost = 0;
		double gpVsProductCost = 0;
		Criteria prodcriteria = session.createCriteria(Product.class);
		prodcriteria.add(Restrictions.eq("productSkuCode",
				currOrder.getProductSkuCode()));
		List<Product> productList = prodcriteria.list();
		if (productList.size() > 0) {
			Product currProduct = productList.get(0);
			if(!channelReport.isPoOrder()){
				productCost = currProduct.getProductPrice();
			}
			channelReport.setCategory(currProduct.getCategoryName());
		}
		if(productCost != 0){
			double grossProductCost = productCost*grossSaleQty;
			double returnProductCost = productCost*saleRetQty;
			double netProductCost = grossProductCost - returnProductCost;
			channelReport.setProductCost(grossProductCost);
			channelReport.setReturnProductCost(returnProductCost);
			channelReport.setNetProductCost(netProductCost);
			if(netProductCost != 0)
				gpVsProductCost = grossProfit/netProductCost*100;
		}
		channelReport.setGpVsProductCost(gpVsProductCost);
		channelReport.setFinalStatus(currOrder.getFinalStatus());
		
		return channelReport;
	}

	/**
	 * Generic method to fetch list of Order objects (MP + PO)
	 * 
	 * @param session
	 * @param sellerId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Order> fetchOrders(Session session, int sellerId, Date startDate, Date endDate) {
		List<Order> orderList = new ArrayList<>();
		Criteria criteria = session.createCriteria(Order.class);
		criteria.add(Restrictions.eq("poOrder", false));
		criteria.createAlias("seller", "seller",
				CriteriaSpecification.LEFT_JOIN)
				.add(Restrictions.eq("seller.id", sellerId));
		criteria.createAlias("orderPayment", "orderPayment",
				CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
				CriteriaSpecification.LEFT_JOIN)
				.add(Restrictions.or(
						Restrictions.between("shippedDate", startDate, endDate),
						Restrictions.between("orderReturnOrRTO.returnDate", startDate, endDate)));
		criteria.createAlias("orderTax", "orderTax",
				CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(org.hibernate.criterion.Order.asc("shippedDate"));
		orderList.addAll(criteria.list());

		criteria = session.createCriteria(Order.class);
		criteria.add(Restrictions.eq("poOrder", true));
		criteria.add(Restrictions.isNull("consolidatedOrder"));
		criteria.createAlias("seller", "seller",
				CriteriaSpecification.LEFT_JOIN)
				.add(Restrictions.eq("seller.id", sellerId));
		criteria.createAlias("orderPayment", "orderPayment",
				CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
				CriteriaSpecification.LEFT_JOIN)
				.add(Restrictions.or(
						Restrictions.between("shippedDate", startDate, endDate),
						Restrictions.between("orderReturnOrRTO.returnDate", startDate, endDate)));
		criteria.createAlias("orderTax", "orderTax",
				CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(org.hibernate.criterion.Order.asc("shippedDate"));
		orderList.addAll(criteria.list());

		return orderList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CommissionDetails> fetchPC(int sellerId, Date startDate, Date endDate, String criteria) {
		Map<String, CommissionDetails> commDetailsMap = new HashMap<String, CommissionDetails>(); 
		Session session=sessionFactory.openSession();
		session.getTransaction().begin();
		String mpOrderQueryStr = "select ot.pcName, sum((ot.partnerCommission+ot.pccAmount+ot.fixedFee+ot.shippingCharges) * ot.quantity * 1.145) " +
				"as grossComm, sum(ot.quantity) as netSaleQty, sum(otx.tdsToDeduct) as tdsToDeduct from order_table ot, ordertax otx " +
				"where ot.orderTax_taxId = otx.taxId and ot.poOrder = 0  and ot.seller_Id=:sellerId and ot.shippedDate " +
				"between :startDate AND :endDate group by ot.pcName";
		if("category".equalsIgnoreCase(criteria))
			mpOrderQueryStr = "select pr.categoryName, sum((ot.partnerCommission+ot.pccAmount+ot.fixedFee+ot.shippingCharges) * ot.quantity * 1.145) " +
				"as grossComm, sum(ot.quantity) as netSaleQty, sum(otx.tdsToDeduct) as tdsToDeduct from order_table ot, ordertax otx " +
				", product pr where ot.productSkuCode = pr.productSkuCode and ot.orderTax_taxId = otx.taxId and ot.poOrder = 0  and " +
				"ot.seller_Id=:sellerId and ot.shippedDate between :startDate AND :endDate group by pr.categoryName";
		Query mpOrderQuery = session.createSQLQuery(mpOrderQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("sellerId", sellerId);
		List<Object[]> orderList2 = mpOrderQuery.list();
		for(Object[] order: orderList2){
			CommissionDetails commDetails = new CommissionDetails();
			String key = order[0].toString();
			if("category".equalsIgnoreCase(criteria))
				commDetails.setCategoryName(key);
			else
				commDetails.setPartner(key);
			double grossCommissionPaid = Double.parseDouble(order[1].toString());
			int netSaleQty = Integer.parseInt(order[2].toString());
			double tdsToDeduct = Double.parseDouble(order[3].toString());
			commDetails.setGrossPartnerCommissionPaid(grossCommissionPaid);
			commDetails.setNetSaleQty(netSaleQty);
			commDetails.setNetTDSToBeDeposited(tdsToDeduct);
			commDetailsMap.put(key, commDetails);
		}
		
		String poOrderQueryStr = "select ot.pcName, sum( ot.partnerCommission+otx.taxToReturn-otx.tax ) as grossComm, " +
				"sum(ot.quantity) as saleQty, sum(otx.tdsToDeduct) as tdsToDeduct from order_table ot, ordertax otx " +
				"where ot.orderTax_taxId = otx.taxId and ot.poOrder = 1 and ot.consolidatedOrder_orderId is null " +
				"and ot.seller_Id=:sellerId and ot.shippedDate between :startDate AND :endDate group by ot.pcName";
		if("category".equalsIgnoreCase(criteria))
			poOrderQueryStr = "select pr.categoryName, sum( ot.partnerCommission+otx.taxToReturn-otx.tax ) as grossComm, " +
					"sum(ot.quantity) as saleQty, sum(otx.tdsToDeduct) as tdsToDeduct from order_table ot, ordertax otx, product pr " +
					"where ot.productSkuCode = pr.productSkuCode and ot.orderTax_taxId = otx.taxId and ot.poOrder = 1 and " +
					"ot.consolidatedOrder_orderId is null and ot.seller_Id=:sellerId and " +
					"ot.shippedDate between :startDate AND :endDate group by pr.categoryName";
		Query poOrderQuery = session.createSQLQuery(poOrderQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("sellerId", sellerId);
		orderList2 = poOrderQuery.list();
		for(Object[] order: orderList2){
			String key = order[0].toString();
			CommissionDetails commDetails = commDetailsMap.get(key);
			double existGC = 0;
			int existSQ = 0;
			double existTDS = 0;
			if(commDetails!=null){
				existGC = commDetails.getGrossPartnerCommissionPaid();
				existSQ = commDetails.getNetSaleQty();
				existTDS = commDetails.getNetTDSToBeDeposited();
			}
			else
				commDetails = new CommissionDetails();
			if("category".equalsIgnoreCase(criteria))
				commDetails.setCategoryName(key);
			else
				commDetails.setPartner(key);
			int netSaleQty = Integer.parseInt(order[2].toString());
			double grossCommissionPaid = Double.parseDouble(order[1].toString());
			double tdsToDeduct = Double.parseDouble(order[3].toString());
			commDetails.setNetSaleQty(existSQ + netSaleQty);
			commDetails.setGrossPartnerCommissionPaid(existGC + grossCommissionPaid);
			commDetails.setNetTDSToBeDeposited(existTDS + tdsToDeduct);
			commDetailsMap.put(key, commDetails);
		}
		String mpReturnQueryStr = "select ot.pcName, sum((ot.partnerCommission+ot.pccAmount+ot.fixedFee+ot.shippingCharges) * orr.returnorrtoQty * 1.145) as returnComm, " +
				"sum(estimateddeduction) as addRetCharges, sum(orr.returnorrtoQty) as returnQty, sum(otx.tdsToReturn) as tdsToReturn from order_table ot, " +
				"orderreturn orr, ordertax otx where ot.orderTax_taxId = otx.taxId and ot.orderReturnOrRTO_returnId = orr.returnId and poOrder = 0 " +
				"and ot.seller_Id=:sellerId and orr.returnDate between :startDate AND :endDate group by ot.pcName";
		if("category".equalsIgnoreCase(criteria))
			mpReturnQueryStr = "select pr.categoryName, sum((ot.partnerCommission+ot.pccAmount+ot.fixedFee+ot.shippingCharges) * orr.returnorrtoQty * 1.145) as returnComm, " +
					"sum(estimateddeduction) as addRetCharges, sum(orr.returnorrtoQty) as returnQty, sum(otx.tdsToReturn) as tdsToReturn from order_table ot, " +
					"orderreturn orr, ordertax otx, product pr where ot.productSkuCode = pr.productSkuCode and ot.orderTax_taxId = otx.taxId and " +
					"ot.orderReturnOrRTO_returnId = orr.returnId and poOrder = 0 and ot.seller_Id=:sellerId and " +
					"orr.returnDate between :startDate AND :endDate group by pr.categoryName";
		Query mpReturnQuery = session.createSQLQuery(mpReturnQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("sellerId", sellerId);
		orderList2 = mpReturnQuery.list();
		for(Object[] order: orderList2){
			String key = order[0].toString();
			CommissionDetails commDetails = commDetailsMap.get(key);
			double existTDS = 0;
			int existRQ = 0;
			if(commDetails != null){
				existTDS = commDetails.getNetTDSToBeDeposited(); 
				existRQ = commDetails.getNetSaleQty();
			}
			else
				commDetails = new CommissionDetails();
			if("category".equalsIgnoreCase(criteria))
				commDetails.setCategoryName(key);
			else
				commDetails.setPartner(key);
			double returnCommission = Double.parseDouble(order[1].toString());
			double additionalRetCharges = Double.parseDouble(order[2].toString());
			int returnQty = Integer.parseInt(order[3].toString());
			double tdsToReturn = Double.parseDouble(order[4].toString());
			commDetails.setNetReturnCommission(returnCommission);
			commDetails.setAdditionalReturnCharges(additionalRetCharges);
			commDetails.setNetSaleQty(existRQ - returnQty);
			commDetails.setNetTDSToBeDeposited(existTDS - tdsToReturn);
			commDetailsMap.put(key, commDetails);
		}

		String poReturnQueryStr = "select ot.pcName, sum( (ot.partnerCommission+otx.taxToReturn-otx.tax) * orr.returnorrtoQty ) as returnComm, " +
				"sum(estimateddeduction) as addRetCharges, sum(orr.returnorrtoQty) as returnQty, sum(otx.tdsToReturn) as tdsToReturn  " +
				"from order_table ot, ordertax otx, orderreturn orr where ot.orderReturnOrRTO_returnId = orr.returnId and " +
				"ot.orderTax_taxId = otx.taxId and ot.poOrder = 1 and ot.consolidatedOrder_orderId is null and ot.seller_Id=:sellerId " +
				"and orr.returnDate between :startDate AND :endDate group by ot.pcName";
		if("category".equalsIgnoreCase(criteria))
			poReturnQueryStr = "select pr.categoryName, sum( (ot.partnerCommission+otx.taxToReturn-otx.tax) * orr.returnorrtoQty ), " +
					"sum(estimateddeduction) as returnComm, sum(orr.returnorrtoQty) as returnQty, sum(otx.tdsToReturn) as tdsToReturn  " +
					"from order_table ot, ordertax otx, orderreturn orr, product pr where ot.productSkuCode = pr.productSkuCode and " +
					"ot.orderReturnOrRTO_returnId = orr.returnId and ot.orderTax_taxId = otx.taxId and ot.poOrder = 1 and " +
					"ot.consolidatedOrder_orderId is null and ot.seller_Id=:sellerId " +
					"and orr.returnDate between :startDate AND :endDate group by pr.categoryName";
		Query poReturnQuery = session.createSQLQuery(poReturnQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("sellerId", sellerId);
		orderList2 = poReturnQuery.list();
		for(Object[] order: orderList2){
			String key = order[0].toString();
			CommissionDetails commDetails = commDetailsMap.get(key);
			double existRC = 0;
			double existARC = 0;
			int existRQ = 0;
			double existTDS = 0;
			if(commDetails!=null){
				existRC = commDetails.getNetReturnCommission();
				existARC = commDetails.getAdditionalReturnCharges();
				existRQ = commDetails.getNetSaleQty();
				existTDS = commDetails.getNetTDSToBeDeposited(); 
			}				
			else
				commDetails = new CommissionDetails();
			if("category".equalsIgnoreCase(criteria))
				commDetails.setCategoryName(key);
			else
				commDetails.setPartner(key);
			double returnCommission = Double.parseDouble(order[1].toString());
			double additionalRetCharges = Double.parseDouble(order[2].toString());
			int returnQty = Integer.parseInt(order[3].toString());
			double tdsToReturn = Double.parseDouble(order[4].toString());
			commDetails.setNetSaleQty(existRQ - returnQty);
			commDetails.setNetReturnCommission(existRC + returnCommission);
			commDetails.setAdditionalReturnCharges(existARC + additionalRetCharges);
			commDetails.setNetTDSToBeDeposited(existTDS - tdsToReturn);
			commDetailsMap.put(key, commDetails);
		}
		List<CommissionDetails> commDetailsList = new ArrayList<CommissionDetails>();
		Iterator entries = commDetailsMap.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, CommissionDetails> thisEntry = (Entry<String, CommissionDetails>) entries
					.next();
			CommissionDetails commDetails = thisEntry.getValue();
			double gc = commDetails.getGrossPartnerCommissionPaid();
			double rc = commDetails.getNetReturnCommission();
			double arc = commDetails.getAdditionalReturnCharges();
			commDetails.setNetSrCommisison(rc - arc);
			commDetails.setNetChannelCommissionToBePaid(gc - rc + arc);
			commDetails.setNetPartnerCommissionPaid(gc - rc + arc);
			commDetailsList.add(commDetails);
		}
		return commDetailsList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelNR> fetchChannelNR(int sellerId, Date startDate, Date endDate, String criteria) {
		List<ChannelNR> channelNRList = new ArrayList<ChannelNR>();
		Map<String, ChannelNR> channelNRMap = new HashMap<String, ChannelNR>();
		Session session=sessionFactory.openSession();
		session.getTransaction().begin();
		String orderQueryStr = "select pcName, sum(netRate) as NetRate from order_table where finalStatus = 'Actionable' and " +
				"seller_Id=:sellerId and shippedDate between :startDate AND :endDate group by pcName order by NetRate";
		if("category".equals(criteria))
			orderQueryStr = "select pr.categoryName, sum(ot.netRate) as NetRate from order_table ot, product pr where ot.finalStatus = 'Actionable' " +
					"and ot.productSkuCode = pr.productSkuCode and ot.seller_Id=:sellerId and " +
					"ot.shippedDate between :startDate AND :endDate group by pcName order by NetRate";
		Query orderQuery = session.createSQLQuery(orderQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("sellerId", sellerId);
		List<Object[]> orderList = orderQuery.list();
		for(Object[] order: orderList){
			ChannelNR channelNR = new ChannelNR();
			String key = order[0].toString();
			channelNR.setKey(key);
			channelNR.setActionableNR(new Double(order[1].toString()));
			channelNRMap.put(key,channelNR);
		}
		
		orderQueryStr = "select pcName, sum(netRate) as NetRate from order_table where finalStatus = 'Settled' and " +
				"seller_Id=:sellerId and shippedDate between :startDate AND :endDate group by pcName order by NetRate";
		if("category".equals(criteria))
			orderQueryStr = "select pr.categoryName, sum(ot.netRate) as NetRate from order_table ot, product pr where ot.finalStatus = 'Settled' " +
					"and ot.productSkuCode = pr.productSkuCode and ot.seller_Id=:sellerId and " +
					"ot.shippedDate between :startDate AND :endDate group by pcName order by NetRate";
		orderQuery = session.createSQLQuery(orderQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("sellerId", sellerId);
		orderList = orderQuery.list();
		for(Object[] order: orderList){
			String key = order[0].toString();
			ChannelNR channelNR = channelNRMap.get(key);
			if(channelNR == null){
				channelNR = new ChannelNR();
				channelNR.setKey(key);
			}
			channelNR.setSettledNR(new Double(order[1].toString()));
			channelNRMap.put(key,channelNR);
		}
		
		orderQueryStr = "select pcName, sum(netRate) as NetRate from order_table where finalStatus = 'In Process' and " +
				"seller_Id=:sellerId and shippedDate between :startDate AND :endDate group by pcName order by NetRate";
		if("category".equals(criteria))
			orderQueryStr = "select pr.categoryName, sum(ot.netRate) as NetRate from order_table ot, product pr where ot.finalStatus = 'In Process' " +
					"and ot.productSkuCode = pr.productSkuCode and ot.seller_Id=:sellerId and " +
					"ot.shippedDate between :startDate AND :endDate group by pcName order by NetRate";
		orderQuery = session.createSQLQuery(orderQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("sellerId", sellerId);
		orderList = orderQuery.list();
		for(Object[] order: orderList){
			String key = order[0].toString();
			ChannelNR channelNR = channelNRMap.get(key);
			if(channelNR == null){
				channelNR = new ChannelNR();
				channelNR.setKey(key);
			}
			channelNR.setInProcessNR(new Double(order[1].toString()));
			channelNRMap.put(key,channelNR);
		}
		Iterator entries = channelNRMap.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, ChannelNR> thisEntry = (Entry<String, ChannelNR>) entries.next();
			channelNRList.add(thisEntry.getValue());
		}
		return channelNRList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelNetQty> fetchChannelNetQty(int sellerId, Date startDate, Date endDate, String criteria) {
		List<ChannelNetQty> channelNRList = new ArrayList<ChannelNetQty>();
		Map<String, ChannelNetQty> channelNRMap = new HashMap<String, ChannelNetQty>();
		Session session=sessionFactory.openSession();
		session.getTransaction().begin();
		String orderQueryStr = "select pcName, sum(netSaleQuantity) as NetSaleQuantity from order_table where finalStatus = 'Actionable' and " +
				"seller_Id=:sellerId and shippedDate between :startDate AND :endDate group by pcName order by NetSaleQuantity";
		if("category".equals(criteria))
			orderQueryStr = "select pr.categoryName, sum(ot.netSaleQuantity) as NetSaleQuantity from order_table ot, product pr where ot.finalStatus = 'Actionable' " +
					"and ot.productSkuCode = pr.productSkuCode and ot.seller_Id=:sellerId and " +
					"ot.shippedDate between :startDate AND :endDate group by pcName order by NetSaleQuantity";
		Query orderQuery = session.createSQLQuery(orderQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("sellerId", sellerId);
		List<Object[]> orderList = orderQuery.list();
		for(Object[] order: orderList){
			ChannelNetQty channelNR = new ChannelNetQty();
			String key = order[0].toString();
			channelNR.setKey(key);
			channelNR.setActionableQty(new Double(order[1].toString()));
			channelNRMap.put(key,channelNR);
		}
		
		orderQueryStr = "select pcName, sum(netSaleQuantity) as NetSaleQuantity from order_table where finalStatus = 'Settled' and " +
				"seller_Id=:sellerId and shippedDate between :startDate AND :endDate group by pcName order by NetSaleQuantity";
		if("category".equals(criteria))
			orderQueryStr = "select pr.categoryName, sum(ot.netSaleQuantity) as NetSaleQuantity from order_table ot, product pr where ot.finalStatus = 'Settled' " +
					"and ot.productSkuCode = pr.productSkuCode and ot.seller_Id=:sellerId and " +
					"ot.shippedDate between :startDate AND :endDate group by pcName order by NetSaleQuantity";
		orderQuery = session.createSQLQuery(orderQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("sellerId", sellerId);
		orderList = orderQuery.list();
		for(Object[] order: orderList){
			String key = order[0].toString();
			ChannelNetQty channelNR = channelNRMap.get(key);
			if(channelNR == null){
				channelNR = new ChannelNetQty();
				channelNR.setKey(key);
			}
			channelNR.setSettledQty(new Double(order[1].toString()));
			channelNRMap.put(key,channelNR);
		}
		
		orderQueryStr = "select pcName, sum(netSaleQuantity) as NetSaleQuantity from order_table where finalStatus = 'In Process' and " +
				"seller_Id=:sellerId and shippedDate between :startDate AND :endDate group by pcName order by NetSaleQuantity";
		if("category".equals(criteria))
			orderQueryStr = "select pr.categoryName, sum(ot.netSaleQuantity) as NetSaleQuantity from order_table ot, product pr where ot.finalStatus = 'In Process' " +
					"and ot.productSkuCode = pr.productSkuCode and ot.seller_Id=:sellerId and " +
					"ot.shippedDate between :startDate AND :endDate group by pcName order by NetSaleQuantity";
		orderQuery = session.createSQLQuery(orderQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("sellerId", sellerId);
		orderList = orderQuery.list();
		for(Object[] order: orderList){
			String key = order[0].toString();
			ChannelNetQty channelNR = channelNRMap.get(key);
			if(channelNR == null){
				channelNR = new ChannelNetQty();
				channelNR.setKey(key);
			}
			channelNR.setInProcessQty(new Double(order[1].toString()));
			channelNRMap.put(key,channelNR);
		}
		Iterator entries = channelNRMap.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, ChannelNetQty> thisEntry = (Entry<String, ChannelNetQty>) entries.next();
			channelNRList.add(thisEntry.getValue());
		}
		return channelNRList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelGP> fetchChannelGP(int sellerId, Date startDate, Date endDate, String criteria) {
		List<ChannelGP> channelNRList = new ArrayList<ChannelGP>();
		Map<String, ChannelGP> channelNRMap = new HashMap<String, ChannelGP>();
		Session session=sessionFactory.openSession();
		session.getTransaction().begin();
		String orderQueryStr = "select pcName, sum(grossProfit) as grossProfit from order_table where finalStatus = 'Actionable' and " +
				"seller_Id=:sellerId and shippedDate between :startDate AND :endDate group by pcName order by NetSaleQuantity";
		if("category".equals(criteria))
			orderQueryStr = "select pr.categoryName, sum(ot.grossProfit) as grossProfit from order_table ot, product pr where ot.finalStatus = 'Actionable' " +
					"and ot.productSkuCode = pr.productSkuCode and ot.seller_Id=:sellerId and " +
					"ot.shippedDate between :startDate AND :endDate group by pcName order by grossProfit";
		Query orderQuery = session.createSQLQuery(orderQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("sellerId", sellerId);
		List<Object[]> orderList = orderQuery.list();
		for(Object[] order: orderList){
			ChannelGP channelNR = new ChannelGP();
			String key = order[0].toString();
			channelNR.setKey(key);
			channelNR.setActionableGP(new Double(order[1].toString()));
			channelNRMap.put(key,channelNR);
		}
		
		orderQueryStr = "select pcName, sum(grossProfit) as grossProfit from order_table where finalStatus = 'Settled' and " +
				"seller_Id=:sellerId and shippedDate between :startDate AND :endDate group by pcName order by grossProfit";
		if("category".equals(criteria))
			orderQueryStr = "select pr.categoryName, sum(ot.grossProfit) as grossProfit from order_table ot, product pr where ot.finalStatus = 'Settled' " +
					"and ot.productSkuCode = pr.productSkuCode and ot.seller_Id=:sellerId and " +
					"ot.shippedDate between :startDate AND :endDate group by pcName order by grossProfit";
		orderQuery = session.createSQLQuery(orderQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("sellerId", sellerId);
		orderList = orderQuery.list();
		for(Object[] order: orderList){
			String key = order[0].toString();
			ChannelGP channelNR = channelNRMap.get(key);
			if(channelNR == null){
				channelNR = new ChannelGP();
				channelNR.setKey(key);
			}
			channelNR.setSettledGP(new Double(order[1].toString()));
			channelNRMap.put(key,channelNR);
		}
		
		orderQueryStr = "select pcName, sum(grossProfit) as grossProfit from order_table where finalStatus = 'In Process' and " +
				"seller_Id=:sellerId and shippedDate between :startDate AND :endDate group by pcName order by grossProfit";
		if("category".equals(criteria))
			orderQueryStr = "select pr.categoryName, sum(ot.grossProfit) as grossProfit from order_table ot, product pr where ot.finalStatus = 'In Process' " +
					"and ot.productSkuCode = pr.productSkuCode and ot.seller_Id=:sellerId and " +
					"ot.shippedDate between :startDate AND :endDate group by pcName order by grossProfit";
		orderQuery = session.createSQLQuery(orderQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("sellerId", sellerId);
		orderList = orderQuery.list();
		for(Object[] order: orderList){
			String key = order[0].toString();
			ChannelGP channelNR = channelNRMap.get(key);
			if(channelNR == null){
				channelNR = new ChannelGP();
				channelNR.setKey(key);
			}
			channelNR.setInProcessGP(new Double(order[1].toString()));
			channelNRMap.put(key,channelNR);
		}
		Iterator entries = channelNRMap.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, ChannelGP> thisEntry = (Entry<String, ChannelGP>) entries.next();
			channelNRList.add(thisEntry.getValue());
		}
		return channelNRList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelMC> fetchChannelMC(int sellerId, Date startDate, Date endDate, String criteria) {
		List<ChannelMC> channelMCList = new ArrayList<ChannelMC>();
		Session session=sessionFactory.openSession();
		session.getTransaction().begin();
		String orderQueryStr = "SELECT mc.partner, sum(mc.paidAmount) as ManualCharges FROM paymentupload pu, manualcharges mc " +
				"where pu.uploadId = mc.chargesDesc and pu.uploadDate between :startDate AND :endDate group by mc.partner order by ManualCharges desc";
		Query orderQuery = session.createSQLQuery(orderQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate);
		List<Object[]> orderList = orderQuery.list();
		for(Object[] order: orderList){
			ChannelMC channelMC = new ChannelMC();
			channelMC.setPartner(order[0].toString());
			channelMC.setManualCharges(new Double(order[1].toString()));
			channelMCList.add(channelMC);
		}
		return channelMCList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelCatNPR> fetchChannelCatNPR(int sellerId, Date startDate, Date endDate, String criteria) {
		List<ChannelCatNPR> channelNPRList = new ArrayList<ChannelCatNPR>();
		Session session=sessionFactory.openSession();
		session.getTransaction().begin();
		String mpOrderQueryStr = "select ot.pcName, pr.categoryName, sum(op.netPaymentResult) as 'NPR' from order_table ot, orderpay op " +
				", product pr where ot.productSkuCode = pr.productSkuCode and ot.orderPayment_paymentId = op.paymentId and ot.poOrder = 0 " +
				"and ot.seller_Id=:sellerId and ot.shippedDate between :startDate AND :endDate group by ot.pcName, pr.categoryName order by ot.pcName";
		Query mpOrderQuery = session.createSQLQuery(mpOrderQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("sellerId", sellerId);
		List<Object[]> orderList = mpOrderQuery.list();
		Set<String> categorySet = new HashSet<String>();
		for(Object[] order: orderList){
			categorySet.add(order[1].toString());
		}
		List<String> catList = new ArrayList<>();
		catList.addAll(categorySet);
		Map<String, ChannelCatNPR> channelCatMap = new HashMap<String, ChannelCatNPR>();
		for(Object[] order: orderList){
			String currPartner = order[0].toString();
			ChannelCatNPR channelCatNPR = channelCatMap.get(currPartner);
			List<Double> netNPRList;
			if(channelCatNPR == null){
				channelCatNPR = new ChannelCatNPR();
				netNPRList = new ArrayList<Double>();
				int counter = 0;
				while(counter++ < catList.size()){
					netNPRList.add(new Double(0));
				}
				channelCatNPR.setPartner(currPartner);
				channelCatNPR.setNetNPR(netNPRList);
			}
			netNPRList = channelCatNPR.getNetNPR();
			for(int index =0; index<catList.size(); index++){
				String category = catList.get(index);
				if(category.equals(order[1].toString())){
					netNPRList.set(index, new Double(order[2].toString()));
					break;
				}
			}
			channelCatNPR.setNetNPR(netNPRList);
			channelCatMap.put(currPartner, channelCatNPR);
		}
		
		Iterator entries = channelCatMap.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, ChannelCatNPR> thisEntry = (Entry<String, ChannelCatNPR>) entries
					.next();
			ChannelCatNPR commDetails = thisEntry.getValue();
			channelNPRList.add(commDetails);
		}
		
		return channelNPRList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelNPR> fetchChannelNPR(int sellerId, Date startDate, Date endDate, String criteria) {
		List<ChannelNPR> channelNPRList = new ArrayList<ChannelNPR>();
		Map<String, ChannelNPR> channelNprMap = new HashMap<String, ChannelNPR>(); 
		Session session=sessionFactory.openSession();
		session.getTransaction().begin();
		String mpOrderQueryStr = "SELECT ot.pcName, sum(op.netPaymentResult) as 'Prepaid NPR' FROM order_table ot, orderpay op where " +
				"ot.orderPayment_paymentId = op.paymentId and ot.paymentType = 'Prepaid' and ot.poOrder = 0 " +
				"and ot.seller_Id=:sellerId and ot.shippedDate between :startDate AND :endDate group by ot.pcName";
		if("category".equalsIgnoreCase(criteria))
			mpOrderQueryStr = "select pr.categoryName, sum(op.netPaymentResult) as 'Prepaid NPR' from order_table ot, orderpay op " +
				", product pr where ot.productSkuCode = pr.productSkuCode and ot.orderPayment_paymentId = op.paymentId and ot.paymentType = 'Prepaid' " +
				"and ot.poOrder = 0 and ot.seller_Id=:sellerId and ot.shippedDate between :startDate AND :endDate group by pr.categoryName";
		Query mpOrderQuery = session.createSQLQuery(mpOrderQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("sellerId", sellerId);
		List<Object[]> orderList = mpOrderQuery.list();
		for(Object[] order: orderList){
			String key = order[0].toString();
			ChannelNPR channelNPR = new ChannelNPR();
			channelNPR.setCategory(key);
			channelNPR.setPartner(key);
			channelNPR.setPrepaidNPR(Double.parseDouble(order[1].toString()));
			channelNprMap.put(key, channelNPR);
		}
		mpOrderQueryStr = "SELECT ot.pcName, sum(op.netPaymentResult) as 'COD NPR' FROM order_table ot, orderpay op where " +
				"ot.orderPayment_paymentId = op.paymentId and ot.paymentType = 'COD' and ot.poOrder = 0 " +
				"and ot.seller_Id=:sellerId and ot.shippedDate between :startDate AND :endDate group by ot.pcName";
		if("category".equalsIgnoreCase(criteria))
			mpOrderQueryStr = "select pr.categoryName, sum(op.netPaymentResult) as 'COD NPR' from order_table ot, orderpay op " +
				", product pr where ot.productSkuCode = pr.productSkuCode and ot.orderPayment_paymentId = op.paymentId and ot.paymentType = 'COD' " +
				"and ot.poOrder = 0 and ot.seller_Id=:sellerId and ot.shippedDate between :startDate AND :endDate group by pr.categoryName";
		mpOrderQuery = session.createSQLQuery(mpOrderQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("sellerId", sellerId);
		orderList = mpOrderQuery.list();
		for(Object[] order: orderList){
			String key = order[0].toString();
			ChannelNPR channelNPR = channelNprMap.get(key);
			if(channelNPR == null){
				channelNPR = new ChannelNPR();
			}
			channelNPR.setCategory(key);
			channelNPR.setPartner(key);
			channelNPR.setCodNPR(Double.parseDouble(order[1].toString()));
			channelNprMap.put(key, channelNPR);
		}
		mpOrderQueryStr = "SELECT ot.pcName, sum(op.netPaymentResult) as 'Net NPR' FROM order_table ot, orderpay op where " +
				"ot.orderPayment_paymentId = op.paymentId and ot.poOrder = 0 " +
				"and ot.seller_Id=:sellerId and ot.shippedDate between :startDate AND :endDate group by ot.pcName";
		if("category".equalsIgnoreCase(criteria))
			mpOrderQueryStr = "select pr.categoryName, sum(op.netPaymentResult) as 'Net NPR' from order_table ot, orderpay op " +
				", product pr where ot.productSkuCode = pr.productSkuCode and ot.orderPayment_paymentId = op.paymentId " +
				"and ot.poOrder = 0 and ot.seller_Id=:sellerId and ot.shippedDate between :startDate AND :endDate group by pr.categoryName";
		mpOrderQuery = session.createSQLQuery(mpOrderQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("sellerId", sellerId);
		orderList = mpOrderQuery.list();
		for(Object[] order: orderList){
			String key = order[0].toString();
			ChannelNPR channelNPR = channelNprMap.get(key);
			if(channelNPR == null){
				channelNPR = new ChannelNPR();
			}
			channelNPR.setCategory(key);
			channelNPR.setPartner(key);
			channelNPR.setNetNPR(Double.parseDouble(order[1].toString()));
			channelNprMap.put(key, channelNPR);
		}
		
		Iterator entries = channelNprMap.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, ChannelNPR> thisEntry = (Entry<String, ChannelNPR>) entries
					.next();
			ChannelNPR channelNPR = thisEntry.getValue();
			channelNPRList.add(channelNPR);
		}
		return channelNPRList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelMCNPR> fetchChannelMCNPR(int sellerId, Date startDate, Date endDate, String criteria) {
		List<ChannelMCNPR> channelNPRList = new ArrayList<ChannelMCNPR>();
		Map<String, ChannelMCNPR> channelNprMap = new HashMap<String, ChannelMCNPR>(); 
		Session session=sessionFactory.openSession();
		session.getTransaction().begin();
		String mpOrderQueryStr = "SELECT ot.pcName, ot.paymentType, sum(op.netPaymentResult) as 'NPR' FROM order_table ot, orderpay op where " +
				"ot.orderPayment_paymentId = op.paymentId and ot.poOrder = 0 " +
				"and ot.seller_Id=:sellerId and ot.shippedDate between :startDate AND :endDate group by ot.pcName, ot.paymentType";
		Query mpOrderQuery = session.createSQLQuery(mpOrderQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("sellerId", sellerId);
		List<Object[]> orderList = mpOrderQuery.list();
		for(Object[] order: orderList){
			String key1 = order[0].toString();
			String key2 = order[1].toString();
			ChannelMCNPR channelNPR = new ChannelMCNPR();
			channelNPR.setPartner(key1);
			channelNPR.setPaymentType(key2);
			channelNPR.setBaseNPR(Double.parseDouble(order[2].toString()));
			channelNprMap.put(key1 + key2, channelNPR);
		}
		mpOrderQueryStr = "SELECT mc.partner, sum(mc.paidAmount) as ManualCharges FROM paymentupload pu, manualcharges mc " +
				"where pu.uploadId = mc.chargesDesc and pu.uploadDate between :startDate AND :endDate group by mc.partner";
		mpOrderQuery = session.createSQLQuery(mpOrderQueryStr)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate);
		orderList = mpOrderQuery.list();
		for(Object[] order: orderList){
			String key1 = order[0].toString();
			String key2 = "B2B";
			ChannelMCNPR channelNPR = channelNprMap.get(key1 + key2);
			if(channelNPR == null){
				channelNPR = new ChannelMCNPR();
			}
			channelNPR.setPartner(key1);
			channelNPR.setPaymentType(key2);
			channelNPR.setManualCharges(Double.parseDouble(order[1].toString()));
			channelNprMap.put(key1 + key2, channelNPR);
		}
		
		Iterator entries = channelNprMap.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, ChannelMCNPR> thisEntry = (Entry<String, ChannelMCNPR>) entries
					.next();
			ChannelMCNPR channelNPR = thisEntry.getValue();
			channelNPRList.add(channelNPR);
		}
		return channelNPRList;
	}
	
	/**
	 * Generic method to fetch list of Order objects (MP + PO)
	 * 
	 * @param session
	 * @param sellerId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Order> fetchDebtorsOrders(Session session, int sellerId, Date startDate, Date endDate) {
		List<Order> orderList = new ArrayList<>();
		Criterion lhs = Restrictions.or(Restrictions.ge("orderPayment.paymentDifference", 5.0), 
				Restrictions.le("orderPayment.paymentDifference", -5.0));
		Criterion rhs = Restrictions.ge("paymentDueDate", new Date());

		Criteria criteria = session.createCriteria(Order.class);
		criteria.add(Restrictions.eq("poOrder", false));
		criteria.add(Restrictions.le("shippedDate", new Date()));
		criteria.createAlias("seller", "seller",
				CriteriaSpecification.LEFT_JOIN)
				.add(Restrictions.eq("seller.id", sellerId));
		criteria.createAlias("orderPayment", "orderPayment",
				CriteriaSpecification.LEFT_JOIN)
				.add(Restrictions.or(lhs, rhs));
		criteria.createAlias("orderTax", "orderTax",
				CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(org.hibernate.criterion.Order.asc("shippedDate"));
		orderList.addAll(criteria.list());
		

		criteria = session.createCriteria(Order.class);
		criteria.add(Restrictions.eq("poOrder", true));
		criteria.add(Restrictions.isNotNull("consolidatedOrder"));
		criteria.add(Restrictions.le("shippedDate", new Date()));
		criteria.createAlias("seller", "seller",
				CriteriaSpecification.LEFT_JOIN)
				.add(Restrictions.eq("seller.id", sellerId));
		criteria.createAlias("orderPayment", "orderPayment",
				CriteriaSpecification.LEFT_JOIN)
				.add(Restrictions.or(lhs, rhs));
		criteria.createAlias("orderTax", "orderTax",
				CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(org.hibernate.criterion.Order.asc("shippedDate"));
		orderList.addAll(criteria.list());
		return orderList;
	}

	@Override
	public UploadReport addUploadReport(UploadReport uploadReport, int sellerId)
			throws CustomException {
		log.info("***addUploadReport Start****");
		Seller seller = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			seller = (Seller) criteria.list().get(0);
			uploadReport.setSeller(seller);
			if (seller.getUploadReportList() != null) {
				seller.getUploadReportList().add(uploadReport);
			}
			session.saveOrUpdate(seller);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.addReportErrorCode));
			log.error("Failed!",e);
			throw new CustomException(GlobalConstant.addReportError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.addReportErrorCode, e);
		}
		log.info("***addUploadReport Exit****");
		return uploadReport;
	}
	
	@Override
	public List<UploadReport> listUploadReport(int sellerId) throws CustomException {
		List<UploadReport> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Seller seller = (Seller) session.get(Seller.class, sellerId);
			if (seller.getUploadReportList() != null && seller.getUploadReportList().size() != 0) {
				returnlist = seller.getUploadReportList();
			}
			session.getTransaction().commit();
			session.close();
			return returnlist;
		} catch (Exception e) {
			log.error("Failed!",e);
			throw new CustomException(GlobalConstant.listReportError,
					new Date(), 3, GlobalConstant.listReportsErrorCode, e);
		}
	}

	@Override
	public UploadReport getUploadLog(int id, int sellerId) throws CustomException {
		List<UploadReport> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("uploadReportList", "uploadReport",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("uploadReport.id",
							id))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			if (criteria.list().size() != 0) {
				Seller seller = (Seller) criteria.list().get(0);
				if (seller == null)
					System.out.println("Null seller");
				returnlist = seller.getUploadReportList();		
			}
			session.getTransaction().commit();
			session.close();
			return returnlist.get(0);
		} catch (Exception e) {
			log.error("Failed!",e);
			throw new CustomException(GlobalConstant.listReportError,
					new Date(), 3, GlobalConstant.listReportsErrorCode, e);
		}
	}
}

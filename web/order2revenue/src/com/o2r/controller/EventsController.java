package com.o2r.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.bean.EventsBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.HelperClass;
import com.o2r.model.Category;
import com.o2r.model.Events;
import com.o2r.model.NRnReturnCharges;
import com.o2r.model.Partner;
import com.o2r.service.CategoryService;
import com.o2r.service.EventsService;
import com.o2r.service.PartnerService;

@Controller
public class EventsController {
	
	@Autowired
	private EventsService eventsService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private HelperClass helperClass;
	static Logger log = Logger.getLogger(EventsController.class.getName());
	
	
	@RequestMapping(value = "/seller/saveEvent", method = RequestMethod.POST)
	public ModelAndView saveEvents(HttpServletRequest request,@ModelAttribute("command") EventsBean eventsBean, BindingResult result){
		
		log.info("$$$ saveEvents Starts : EventsController $$$");
		Map<String, Object> model = new HashMap<String, Object>();		
		List<NRnReturnCharges> chargeList=new ArrayList<NRnReturnCharges>();
		Map<String, String[]> parameters = request.getParameterMap();		
			
		try {
			if(eventsBean.getEventId() != 0){
				eventsBean.setEventId(0);
			}
			
			for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
				
				if (entry.getValue()[0] != null	&& !entry.getValue()[0].isEmpty()) {
					if (entry.getKey().contains("nrType"))
						eventsBean.setNrType(entry.getValue()[0]);
					else if (entry.getKey().contains("pCategories"))
						eventsBean.setProductCategories(entry.getValue()[0]);
					else if (entry.getKey().contains("nr-")) {
						
						String temp = entry.getKey().substring(3);
						NRnReturnCharges nrnReturncharge = new NRnReturnCharges();
						nrnReturncharge.setChargeAmount(Float.parseFloat(entry.getValue()[0]));
						nrnReturncharge.setChargeName(temp);
						nrnReturncharge.setConfig(eventsBean.getNrnReturnConfig());
						chargeList.add(nrnReturncharge);
						
					} else if (entry.getKey().contains("local")) {
						eventsBean.getNrnReturnConfig().setLocalList(Arrays.toString(entry.getValue()));
					} else if (entry.getKey().contains("zonal")) {
						eventsBean.getNrnReturnConfig().setZonalList(Arrays.toString(entry.getValue()));
					} else if (entry.getKey().contains("national")) {
						eventsBean.getNrnReturnConfig().setNationalList(Arrays.toString(entry.getValue()));
					} else if (entry.getKey().contains("metro")) {
						eventsBean.getNrnReturnConfig().setMetroList(Arrays.toString(entry.getValue()));
					}
				}
			}
			
			Partner partner=partnerService.getPartner(eventsBean.getChannelName(), helperClass.getSellerIdfromSession(request));
			if(partner != null){
				eventsBean.getNrnReturnConfig().setLocalList(partner.getNrnReturnConfig().getLocalList());
				eventsBean.getNrnReturnConfig().setMetroList(partner.getNrnReturnConfig().getMetroList());
				eventsBean.getNrnReturnConfig().setZonalList(partner.getNrnReturnConfig().getZonalList());
				eventsBean.getNrnReturnConfig().setNationalList(partner.getNrnReturnConfig().getNationalList());
			}
			eventsBean.getNrnReturnConfig().setCharges(chargeList);
			
			try{
				eventsBean.setSellerId(helperClass.getSellerIdfromSession(request));
				eventsBean.setCreatedDate(new Date());
				Events events=ConverterClass.prepareEventsModel(eventsBean);
				eventsService.addEvent(events, helperClass.getSellerIdfromSession(request));
			}catch(CustomException ce){				
				model.put("errorMessage", "You can not create Events during these Dates..... !!!");
				model.put("errorTime", new Date());
				return new ModelAndView("globalErorPage",model);
			}catch(Exception e){
				log.debug("*** Exception In EventsDaoImpl ***");
				e.printStackTrace();
				log.error("Failed !",e);
			}

		} catch(Exception e){
			e.printStackTrace();
			log.error("Failed !",e);
		}
		
		log.info("$$$ saveEvents Ends : EventsController $$$");
		return new ModelAndView("redirect:/seller/eventsList.html");
		
	}
	
	@RequestMapping(value = "/seller/addEvent", method = RequestMethod.GET)
	public ModelAndView addEvent(HttpServletRequest request, @ModelAttribute("command") EventsBean eventsBean, BindingResult result) {

		log.info("$$$ addEvents Starts : EventsController $$$");
		Map<String,Float> chargeMap=new HashMap<String, Float>();
		Map<String,Float> categoryMap=new HashMap<String, Float>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<Partner> partnerList = partnerService.listPartners(helperClass
					.getSellerIdfromSession(request));
			Map<String, String> partnerMap = new HashMap<String, String>();
			if (partnerList != null && !partnerList.isEmpty()) {
				for (Partner bean : partnerList) {
					partnerMap.put(bean.getPcName(), bean.getPcName());
				}
			}			
			List<Category> categoryList = categoryService
					.listCategories(helperClass.getSellerIdfromSession(request));
			for (Category cat : categoryList) {
				categoryMap.put(cat.getCatName(), chargeMap.get(cat.getCatName()));
			}
			model.put("partnerMap", partnerMap);
			model.put("categoryMap", categoryMap);
		} catch (Exception e) {
			log.error("Failed !",e);
			e.printStackTrace();
		}		
		log.info("$$$ addEvents Ends : EventsController $$$");
		return new ModelAndView("miscellaneous/addEvent", model);
	}
	
	@RequestMapping(value = "/seller/addDuplicateEvent", method = RequestMethod.GET)
	public ModelAndView addDuplicateEvent(HttpServletRequest request, @ModelAttribute("command") EventsBean eventsBean, BindingResult result) {

		log.info("$$$ addDuplicateEvent Starts : EventsController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String,Float> chargeMap=new HashMap<String, Float>();
		Events event=null;
		Map<String,Float> categoryMap=new HashMap<String, Float>();
		eventsBean.setEventId(Integer.parseInt(request.getParameter("eventId")));
		log.debug("***** Check : "+eventsBean.getEventId());
		try {
			if (eventsBean.getEventId() != 0) {
				event = eventsService.getEvent(eventsBean.getEventId());
				eventsBean=ConverterClass.prepareEventsBean(event);
				model.put("eventsBean", eventsBean);
				log.debug("************ "+(eventsBean.getNrnReturnConfig().getNrCalculatorEvent()).toString());
				for(NRnReturnCharges charge:eventsBean.getNrnReturnConfig().getCharges())
				{
					chargeMap.put(charge.getChargeName(), charge.getChargeAmount());
				}
				model.put("chargeMap", chargeMap);				
			}
			List<Category> categoryList = categoryService
					.listCategories(helperClass.getSellerIdfromSession(request));
			for (Category cat : categoryList) {
				categoryMap.put(cat.getCatName(), chargeMap.get(cat.getCatName()));
			}
			List<Partner> partnerList = partnerService.listPartners(helperClass.getSellerIdfromSession(request));
			Map<String, String> partnerMap = new HashMap<String, String>();
			if (partnerList != null && !partnerList.isEmpty()) {
				for (Partner bean : partnerList) {
					partnerMap.put(bean.getPcName(), bean.getPcName());
				}
			}
			model.put("categoryMap", categoryMap);
			model.put("partnerMap", partnerMap);			
		} catch (Exception e) {
			log.error("Failed !",e);
			e.printStackTrace();
		}
		log.info("$$$ addDuplicateEvent Ends : EventsController $$$");
		return new ModelAndView("miscellaneous/addEvent", model);
	}
	
		
	@RequestMapping(value = "/seller/eventsList", method = RequestMethod.GET)
	public ModelAndView eventsList(HttpServletRequest request, @ModelAttribute("command") EventsBean eventsBean, BindingResult result) {
		
		log.info("$$$ eventsList Starts : EventsController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		try { 
			model.put("eventsList", ConverterClass
					.prepareListOfEventsBean(eventsService
							.listEvents(helperClass
									.getSellerIdfromSession(request))));			
		} /*catch (CustomException ce) {
			log.error("productList exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}*/ catch (Exception e) {
			log.error("Failed !",e);
			e.printStackTrace();
		}
		log.info("$$$ eventsList Ends : EventsController $$$");
		return new ModelAndView("miscellaneous/eventsList", model);
	}
	
	@RequestMapping(value = "/seller/checkEvent", method = RequestMethod.GET)
	public @ResponseBody String checkEvent(HttpServletRequest request,
			@ModelAttribute("command") EventsBean eventsBean,
			BindingResult result) {
		
		log.info("$$$ checkEvent Starts : EventsController $$$");
		String eventName = request.getParameter("name");
		try {
			if (eventName != null && eventName.length() != 0) {
				Events event = eventsService.getEvent(eventName, helperClass.getSellerIdfromSession(request));
				if (event != null)
					return "false";
				else
					return "true";
			}
		} catch (CustomException ce) {			
			log.error("CheckEventsException: " + ce.toString());
			return "false";
		} catch (Throwable e) {
			log.error("Failed! ",e);
			e.printStackTrace();
			return "false";
		}
		log.info("$$$ checkEvent Ends : EventsController $$$");
		return "false";
	}

}
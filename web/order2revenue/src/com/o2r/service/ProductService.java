package com.o2r.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.o2r.helper.CustomException;
import com.o2r.model.PartnerCategoryMap;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;

/**
 * @author Deep Mehrotra
 *
 */
public interface ProductService {

	public void addProduct(Product product, int sellerId)
			throws CustomException;

	public void addProductConfig(ProductConfig productConfig, int sellerId)
			throws CustomException;

	public List<Product> listProducts(int sellerId, int pageNo)
			throws CustomException;

	public int editProduct(int sellerId, List<Product> products)
			throws CustomException;

	public List<ProductConfig> listProductConfig(int sellerIs, int pageNo,
			String condition) throws CustomException;

	public List<ProductConfig> searchProductConfig(String field, String value,
			int sellerId, String condition) throws CustomException;

	public List<Product> listProducts(int sellerId) throws CustomException;

	public Product getProduct(int productId) throws CustomException;

	public Product getProductEdit(String sku, int sellerId)
			throws CustomException;

	public void deleteProduct(Product product, int sellerId)
			throws CustomException;

	public void updateInventory(String sku, int currentInventory,
			int quantoAdd, int quantoSub, boolean status, int sellerId,
			Date orderDate) throws CustomException;

	public Product getProduct(String skuCode, int sellerId)
			throws CustomException;

	public List<Product> getProductwithCreatedDate(Date startDate,
			Date endDate, int sellerId) throws CustomException;

	public List<ProductConfig> getProductConfig(String channelSKUCode,
			String channel, int sellerId) throws CustomException;

	// public void addSKUMapping(ProductConfig productConfig, int sellerId);

	public boolean getProductwithProductConfig(int sellerId)
			throws CustomException;

	public String deleteProduct(int productId, int sellerId) throws Exception;

	public void removeSKUMapping(ProductConfig productConfig, int sellerId)
			throws CustomException;

	public ProductConfig getProductConfig(int productConfigId)
			throws CustomException;

	public void addProduct(List<Product> productList, int sellerId)
			throws CustomException;

	public void addSKUMapping(List<ProductConfig> productConfigList,
			int sellerId) throws CustomException;

	public List<String> listProductSKU(int sellerId);

	public int productCount(int sellerId);

	public long productMappingCount(int sellerId);

	public ProductConfig getProductConfigByAnySKU(String childSKUCode,
			String channel, int sellerId) throws CustomException;

	public Map<String, String> getSKUCategoryMap(int sellerId)
			throws CustomException;

	public void addPartnerCatMapping(Map<String,  List<PartnerCategoryMap>> saveProductMap, int sellerId)
			throws CustomException;
}
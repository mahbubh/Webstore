package com.bangla.store.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bangla.store.domain.Product;
/**
 *
 * @author Mahabub 
 */
public interface ProductService {
	List<Product> getAllProducts();

	Product getProductById(int productID);

	List<Product> getProductsByCategory(String category);
	
	Set<Product> getProductsByFilter(Map<String, List<String>> filterParams);
	
	List <Product> getProductsByManufacturer(String manufacturer);
	
	void addProduct(Product product);
}

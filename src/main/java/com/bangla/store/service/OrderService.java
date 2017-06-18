package com.bangla.store.service;

import com.bangla.store.domain.Order;
/**
 *
 * @author Mahabub 
 */
public interface OrderService {
	void processOrder(int productId, int count);
	Long saveOrder(Order order);
}

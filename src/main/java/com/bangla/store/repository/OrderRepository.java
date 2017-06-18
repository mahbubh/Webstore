package com.bangla.store.repository;

import com.bangla.store.domain.Order;
/**
 *
 * @author Mahabub 
 */
public interface OrderRepository {
	Long saveOrder(Order order);
}

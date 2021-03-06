package com.bangla.store.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.ResponseStatus;
import com.bangla.store.domain.Cart;
import com.bangla.store.domain.CartItem;
import com.bangla.store.domain.Product;
import com.bangla.store.exception.ProductNotFoundException;
import com.bangla.store.service.CartService;
import com.bangla.store.service.ProductService;
/**
 *
 * @author Mahabub 
 */
@Controller
@RequestMapping(value = "rest/cart")
public class CartRestController {
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Cart create(@RequestBody Cart cart) {
		return cartService.create(cart);
	}

	@RequestMapping(value = "/{cartId}", method = RequestMethod.GET)
	public @ResponseBody Cart read(@PathVariable(value = "cartId") String cartId) {
		return cartService.read(cartId);
	}

	@RequestMapping(value = "/{cartId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void update(@PathVariable(value = "cartId") String cartId,
			@RequestBody Cart cart) {
		cartService.update(cartId, cart);
	}

	@RequestMapping(value = "/{cartId}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(value = "cartId") String cartId) {
		cartService.delete(cartId);
	}

	@RequestMapping(value = "/add/{id}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void addItem(@PathVariable int id,
			HttpServletRequest request) {
		String sessionId = request.getSession(true).getId();
		Cart cart = cartService.read(sessionId);
		if (cart == null) {
			cart = cartService.create(new Cart(sessionId));
		}
		Product product = productService.getProductById(id);
		if (product == null) {
//			throw new IllegalArgumentException(new ProductNotFoundException(id));
		}
		cart.addCartItem(new CartItem(product));
		cartService.update(sessionId, cart);
	}

	@RequestMapping(value = "/remove/{id}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeItem(@PathVariable int id,
			HttpServletRequest request) {
		String sessionId = request.getSession(true).getId();
		Cart cart = cartService.read(sessionId);
		if (cart == null) {
			cart = cartService.create(new Cart(sessionId));
		}
		Product product = productService.getProductById(id);
		if (product == null) {
//			throw new IllegalArgumentException(new ProductNotFoundException(id));
		}
		cart.removeCartItem(new CartItem(product));

		cartService.update(sessionId, cart);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal request, please verify your payload")
	public void handleClientErrors(Exception ex) {
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal server error")
	public void handleServerErrors(Exception ex) {
	}
}
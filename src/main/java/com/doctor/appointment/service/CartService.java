package com.doctor.appointment.service;

import com.doctor.appointment.dto.CartDto;
import com.doctor.appointment.dto.ProductDto;
import com.doctor.appointment.exception.CustomEntityNotFoundException;
import com.doctor.appointment.model.Cart;
import com.doctor.appointment.model.Product;
import com.doctor.appointment.repository.CartRepository;
import com.doctor.appointment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart getCartById(Long id) throws Exception{
        Optional<Cart> optionalCart = cartRepository.findById(id);

        if(optionalCart.isPresent()){
            Cart cart = optionalCart.get();

            return cart;
        } throw new CustomEntityNotFoundException(Cart.class);
    }

    public ResponseEntity<Object> addProductToCart(ProductDto productDto, CartDto cartDto){
        long cartId = cartDto.getCartId();
        long productId = productDto.getId();

        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(optionalCart.isPresent() && optionalProduct.isPresent()){
            Cart cart = optionalCart.get();
            Product product = optionalProduct.get();

            cart.addProductToCart(product);

            cartRepository.save(cart);

            return new ResponseEntity<>("saved", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> removeProduct(ProductDto productDto, CartDto cartDto){
        long cartId = cartDto.getCartId();
        long productId = productDto.getId();

        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(optionalCart.isPresent() && optionalProduct.isPresent()){
            Cart cart = optionalCart.get();
            Product product = optionalProduct.get();

            cart.removeProduct(product);

            cartRepository.save(cart);

            return new ResponseEntity<>("saved", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }



}

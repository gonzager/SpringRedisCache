package ar.com.fluxit.cahe.springrediscache.controllers;

import ar.com.fluxit.cahe.springrediscache.domain.Product;
import ar.com.fluxit.cahe.springrediscache.exceptions.NotFoundException;
import ar.com.fluxit.cahe.springrediscache.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
public class Controller {

    @Autowired
    private ProductService productService;


    @GetMapping("/product/{id}")
    public CompletableFuture<Product> getProductById(@PathVariable long id) {
        return productService.findById(id);
    }

    @DeleteMapping("/product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CompletableFuture<Void> removeProductById(@PathVariable long id) {
        return productService.findById(id).thenCompose(productService::deleteProduct);
    }
}

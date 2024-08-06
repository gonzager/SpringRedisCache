package ar.com.fluxit.cahe.springrediscache.controllers;

import ar.com.fluxit.cahe.springrediscache.domain.Product;
import ar.com.fluxit.cahe.springrediscache.exceptions.NotFoundException;
import ar.com.fluxit.cahe.springrediscache.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable long id) {
        return productService.findById(id).orElseThrow( () -> new NotFoundException("No se encontro el producto con el id: " + id));
    }

    @DeleteMapping("/product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProductById(@PathVariable long id) {
        var  product =  productService.findById(id).orElseThrow( () -> new NotFoundException("No se encontro el producto con el id: " + id));
        productService.deleteProduct(product);
    }
}

package ar.com.fluxit.cahe.springrediscache.services;

import ar.com.fluxit.cahe.springrediscache.domain.Product;
import ar.com.fluxit.cahe.springrediscache.exceptions.NotFoundException;
import ar.com.fluxit.cahe.springrediscache.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Cacheable(value = "product", key = "#id", unless="#result == null")
    @Transactional(Transactional.TxType.NEVER)
    public CompletableFuture<Product> findById(Long id) {
        return CompletableFuture.supplyAsync(
                () -> productRepository.findById(id).orElseThrow(
                        ()->new NotFoundException("No se encontro el producto con el id: " + id))
        );
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void saveAll(Set<Product> product) {
        productRepository.saveAll(product);
    }

    @CacheEvict(cacheNames = "product", key = "#product.id", beforeInvocation = true)
    @Transactional(Transactional.TxType.REQUIRED)
    public CompletableFuture<Void> deleteProduct(Product product) {
        return CompletableFuture.runAsync(() -> productRepository.delete(product));
    }

}

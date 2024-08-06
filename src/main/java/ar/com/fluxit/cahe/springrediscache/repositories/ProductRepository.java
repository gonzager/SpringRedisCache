package ar.com.fluxit.cahe.springrediscache.repositories;

import ar.com.fluxit.cahe.springrediscache.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> { }

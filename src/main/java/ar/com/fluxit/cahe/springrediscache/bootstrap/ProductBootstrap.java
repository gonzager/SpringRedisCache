package ar.com.fluxit.cahe.springrediscache.bootstrap;

import ar.com.fluxit.cahe.springrediscache.domain.Product;
import ar.com.fluxit.cahe.springrediscache.services.ProductService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@Service
public class ProductBootstrap implements InitializingBean {

    static final int BATCH_SIZE = 100;
    static final Random random = new SecureRandom();

    @Autowired
    private ProductService productService;
    Set<Product> prodcuts = new HashSet<>();


    public static <T> void with(T obj, Consumer<T> consumer) {
        consumer.accept(obj);
    }

    private Date randomDate() {
        int year = random.nextInt(2025 - 1970) + 1970;
        int month = random.nextInt(12) + 1; // Entre 1 y 12
        int day = random.nextInt(Month.of(month).length(Year.isLeap(year))) + 1;// Días válidos para el mes
        return  Date.valueOf(LocalDate.of(year, month, day));
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        IntStream.range(0,BATCH_SIZE).forEach(i->
            with( new Product(), p-> {
                p.setName(String.format("Product->%d", i));
                p.setCode(UUID.randomUUID());
                p.setAviailable(true);
                p.setReleaseDate(randomDate());
                prodcuts.add(p);
            })
        );
        productService.saveAll(prodcuts);
    }

}

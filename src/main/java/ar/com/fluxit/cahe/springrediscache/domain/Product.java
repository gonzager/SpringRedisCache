package ar.com.fluxit.cahe.springrediscache.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    UUID code;

    @Column(nullable = false)
    String name;

    @Column(columnDefinition = "boolean default true")
    Boolean aviailable;

    @Column(nullable = false)
    Date releaseDate;
}

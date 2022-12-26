package com.shop.phoneshop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_property")
@Getter
@Setter
@NoArgsConstructor
public class ProductProperty {
    @Id
    @Column(name = "product_property_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "productProperty", cascade = CascadeType.ALL)
    private List<Property> properties = new ArrayList<>();

    @OneToMany(mappedBy = "productProperty", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();
}

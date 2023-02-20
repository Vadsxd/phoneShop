package com.shop.phoneshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subcategory")
@Getter
@Setter
@NoArgsConstructor
public class Subcategory {
    @Id
    @Column(name = "subcategory_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Subcategory parentSubcategory;

    @OneToMany(mappedBy = "parentSubcategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subcategory> childSubcategories;

    @Column(name = "title")
    private String title;
}

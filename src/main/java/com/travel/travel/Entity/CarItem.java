package com.travel.travel.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Many;

@Entity
@Getter
@Setter
@Table(name="Cart_item")
public class CarItem {

    @Id
    @GeneratedValue
    @Column(name="cart_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;

    private int count;
 }

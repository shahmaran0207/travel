package com.travel.travel.Entity;

import com.travel.travel.Constant.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus OrderStatus;

    @OneToMany
    private List<OrderItem> orderItems=new ArrayList<>();

    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}

package com.travel.travel.Entity;

import com.travel.travel.Constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Orders")
@Getter
@Setter
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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,   //부모 엔티티 영속성 상태 변화를 자식 엔티티에 전이
            orphanRemoval = true)
    private List<OrderItem> orderItems=new ArrayList<>();

    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}

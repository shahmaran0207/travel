package com.travel.travel.Entity;

import com.travel.travel.Constant.ItemSellStatus;
import com.travel.travel.Repository.ItemRepository;
import com.travel.travel.Repository.MemberRepository;
import com.travel.travel.Repository.OrderItemRepository;
import com.travel.travel.Repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
class OrderTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @PersistenceContext
    EntityManager em;

    public Item createItem(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("상세설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());

        item.setUpdatetime(LocalDateTime.now());
        return item;
    }

    public Order createOrder(){
        Order order=new Order();

        for(int i=0; i<3; i++){
            Item item=createItem();
            itemRepository.save(item);
            OrderItem orderItem=new OrderItem();
            orderItem.setItem(item);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        Member member=new Member();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest() {

        Order order = new Order();

        for(int i=0;i<3;i++){
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        orderRepository.saveAndFlush(order);
        em.clear();

        Order savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(3, savedOrder.getOrderItems().size());
    }
    
    @Test
    @DisplayName("고아객체 제거 테스트")
    public void orphanRemovalTest(){
        Order order=this.createOrder();
        order.getOrderItems().remove(0);
        em.flush(); // 영속성 변경 사항을 데이터베이스에 강제로 반영하는 역할
    }
    
    @Test
    @DisplayName("지연 로딩 테스트") //현재 즉시 로딩 -> 매핑된 테이블이 많아지면 쿼리문을 예측할 수 없게됨 -> 지연로딩을 사용해야 함
    public void LazyLoadingTest(){
        Order order=this.createOrder();
        Long orderItemId=order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();

        OrderItem orderItem=orderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new);

        System.out.println("Order Class: "+orderItem.getOrder().getClass());
        System.out.println("=================================");
        orderItem.getOrder().getOrderDate();
        System.out.println("========================");
    }
}
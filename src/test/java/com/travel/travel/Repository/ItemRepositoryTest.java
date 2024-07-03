package com.travel.travel.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.travel.Constant.ItemSellStatus;
import com.travel.travel.Entity.Item;
import com.travel.travel.Entity.QItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemreposirtory;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("상품 저장 테스트")
    public void creatItemTest() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(1000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdatetime(LocalDateTime.now());
        Item savedItem = itemreposirtory.save(item);
        System.out.println(savedItem);
    }


    public void creatItemList() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(1000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdatetime(LocalDateTime.now());
            Item savedItem = itemreposirtory.save(item);
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest() {
        this.creatItemList();
        List<Item> itemList = itemreposirtory.findByItemNm("테스트 상품1");

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest() {
        this.creatItemList();
        List<Item> itemList =
                itemreposirtory.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest() {
        this.creatItemList();
        List<Item> itemList = itemreposirtory.findByPriceLessThan(1005);

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDesc() {
        this.creatItemList();
        List<Item> itemList =
                itemreposirtory.findByPriceLessThanOrderByPrice(1005);

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest() {
        this.creatItemList();
        List<Item> itemList = itemreposirtory.findByItemDetail("테스트 상품 상세 설명");

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("nativeQurey 속성을 이용한 상품 조회 테스트")
    public void findByItemDetailByNative() {
        this.creatItemList();
        List<Item> itemList =
                itemreposirtory.findByDetailByNative("테스트 상품 상세 설명");

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void queryDslTest() {
        this.creatItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QItem qItem = QItem.item;

        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qItem.price.desc());

        List<Item> itemList = query.fetch();

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    public void createItemList2() {
        for (int i = 1; i <= 5; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(1000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdatetime(LocalDateTime.now());
            itemreposirtory.save(item);
        }

        for (int i = 6; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(1000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLDOUT);
            item.setStockNumber(0);
            item.setRegTime(LocalDateTime.now());
            item.setUpdatetime(LocalDateTime.now());
            itemreposirtory.save(item);
        }
    }

    @Test
    @DisplayName("상품 Queraydsl 조회 테스트2")
    public void queryDslTest2() {
        this.createItemList2();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem item = QItem.item;

        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellStatus = "SELL";

        booleanBuilder.and(item.itemDetail.like("%" + itemDetail + "%"));
        booleanBuilder.and(item.price.gt(price));

        if (StringUtils.equals(itemSellStatus, ItemSellStatus.SELL)) {
            booleanBuilder.and(item.itemSellStatus.eq((ItemSellStatus.SELL)));
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Item> itemPagingResult=
                itemreposirtory.findAll(booleanBuilder, pageable);

        System.out.print("total elements: "+itemPagingResult.getTotalElements());

        List<Item> ResultItemList=itemPagingResult.getContent();

        for(Item resultItem: ResultItemList){
            System.out.println(resultItem.toString());
        }

    }
}
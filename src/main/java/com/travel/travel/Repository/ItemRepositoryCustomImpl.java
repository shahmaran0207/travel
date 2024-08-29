package com.travel.travel.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.travel.Constant.ItemSellStatus;
import com.travel.travel.Entity.QItem;
import jakarta.persistence.EntityManager;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{     //클래스 명 끝에 "Impl"을 필수적으로 붙여야 함

    private JPAQueryFactory queryFactory;           //동적으로 쿼리를 생성하기 위함

    public  ItemRepositoryCustomImpl(EntityManager em){
        this.queryFactory=new JPAQueryFactory(em);
    }
    
    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){
        return searchSellStatus==null?null: QItem.item.itemSellStatus.eq(searchSellStatus);         //조건식?참일때 결과: 거짓일 때 결과
    }

    private BooleanExpression regDtsAfter(String searchDateType){

    }

}

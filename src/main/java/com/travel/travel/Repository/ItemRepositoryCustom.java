package com.travel.travel.Repository;

import com.travel.travel.DTO.ItemSerachDTO;
import com.travel.travel.Entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/*
 - Pagination: 기존의 페이징이라고 생각하면 됨
 - Pageable: Pagination 요청 정보를 담기위한 추상 인터페이스라는 의미 -> 실제로 사용하기 위해서는 구현체가 필요함

*/

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSerachDTO itemSerachDTO, Pageable pageable);
}

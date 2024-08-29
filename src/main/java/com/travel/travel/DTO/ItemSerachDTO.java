package com.travel.travel.DTO;

import com.travel.travel.Constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSerachDTO {

    private String searchDateType;

    private ItemSellStatus searchSellStatus;

    private String searchBy;

    private String searchQuery="";      //검색어 저장 변수 -> ItemNm일 경우 상품명 기준, createdBy일 경우 상품 등록자 아이디 기준
}

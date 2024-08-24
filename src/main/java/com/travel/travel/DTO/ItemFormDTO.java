package com.travel.travel.DTO;

import com.travel.travel.Constant.ItemSellStatus;
import com.travel.travel.Entity.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemFormDTO {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값 입니다.") //NotBlank: String 타입에만 적용됨
    private String itemNm;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "상품 설명은 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDTO> itemImgDTOList=new ArrayList<>();

    private List<Long> itemImgIds=new ArrayList<>();

    private static ModelMapper modelMapper=new ModelMapper();

    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDTO of(Item item){
        return modelMapper.map(item, ItemFormDTO.class);
    }
}
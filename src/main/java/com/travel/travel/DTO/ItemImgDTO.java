package com.travel.travel.DTO;

import com.travel.travel.Entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemImgDTO {

    private Long id;

    private String imgName;

    private String orImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper=new ModelMapper(); //객체 간 매핑 자동화

    public static ItemImgDTO of(ItemImg itemImg){
        return modelMapper.map(itemImg, ItemImgDTO.class);
    }
}

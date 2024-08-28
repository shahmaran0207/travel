package com.travel.travel.Service;

import com.travel.travel.Constant.ItemSellStatus;
import com.travel.travel.DTO.ItemFormDTO;
import com.travel.travel.Entity.Item;
import com.travel.travel.Entity.ItemImg;
import com.travel.travel.Repository.ItemImgRepository;
import com.travel.travel.Repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    List<MultipartFile> createMultipartFiles() throws Exception{
        List<MultipartFile> multipartFileList = new ArrayList<>();

        for(int i=0; i<5; i++){
            String path="C:/Users/wjaud/OneDrive/바탕 화면/MOST IMPORTANT/travel/item";
            String imageName="image"+i+".jpg";
            MockMultipartFile multipartFile=
                    new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1,2,3,4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }
    
    @Test
    @DisplayName("상품 등록 테스트")
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    void saveItem() throws Exception{
        ItemFormDTO itemFormDTO=new ItemFormDTO();
        itemFormDTO.setItemNm("테스트 상품");
        itemFormDTO.setItemSellStatus(ItemSellStatus.SELL);
        itemFormDTO.setItemDetail("테스트 상품 상세 설명");
        itemFormDTO.setPrice(1000);
        itemFormDTO.setStockNumber(100);

        List<MultipartFile> multipartFileList=createMultipartFiles();
        Long itemId= itemService.saveItem(itemFormDTO, multipartFileList);


        List<ItemImg> itemImgList=
                itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        Item item=itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(itemFormDTO.getItemNm(), item.getItemNm());
        assertEquals(itemFormDTO.getItemSellStatus(), item.getItemSellStatus());
        assertEquals(itemFormDTO.getItemDetail(), item.getItemDetail());
        assertEquals(itemFormDTO.getPrice(), item.getPrice());
        assertEquals(itemFormDTO.getStockNumber(), item.getStockNumber());
        assertEquals(multipartFileList.get(0).getOriginalFilename(), itemImgList.get(0).getOriImgName());

    }
}

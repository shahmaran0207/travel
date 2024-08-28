package com.travel.travel.Service;

import com.travel.travel.DTO.ItemFormDTO;
import com.travel.travel.DTO.ItemImgDTO;
import com.travel.travel.Entity.Item;
import com.travel.travel.Entity.ItemImg;
import com.travel.travel.Repository.ItemImgRepository;
import com.travel.travel.Repository.ItemRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    public Long saveItem(ItemFormDTO itemFormDTO,
                         List<MultipartFile> itemImgFileList) throws Exception{

        Item item=itemFormDTO.createItem();
        itemRepository.save(item);

        for(int i=0; i<itemImgFileList.size();i++){
            ItemImg itemImg=new ItemImg();
            itemImg.setItem(item);
            if(i==0) itemImg.setRepimgYn("Y");
            else itemImg.setRepimgYn("N");
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }

    @Transactional(readOnly=true)
    public ItemFormDTO getItemDTL(Long itemId){

        List<ItemImg> itemImgList=
                itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDTO> itemImgDTOList=new ArrayList<>();

        for(ItemImg itemImg: itemImgList){
            ItemImgDTO itemImgDTO=ItemImgDTO.of(itemImg);
            itemImgDTOList.add(itemImgDTO);
        }

        Item item=itemRepository.findById(itemId)
                .orElseThrow(EntityExistsException::new);

        ItemFormDTO itemFormDTO=ItemFormDTO.of(item);
        itemFormDTO.setItemImgDTOList(itemImgDTOList);
        return itemFormDTO;
    }



}
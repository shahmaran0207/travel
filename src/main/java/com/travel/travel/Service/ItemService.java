package com.travel.travel.Service;

import com.travel.travel.DTO.ItemFormDTO;
import com.travel.travel.DTO.ItemImgDTO;
import com.travel.travel.Entity.Item;
import com.travel.travel.Entity.ItemImg;
import com.travel.travel.Repository.ItemImgRepository;
import com.travel.travel.Repository.ItemRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
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


    public Long updateItem(ItemFormDTO itemFormDTO, List<MultipartFile> itemImgFileList) throws Exception{
        Item item=itemRepository.findById(itemFormDTO.getId())
                .orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDTO);

        List<Long> itemImgIds=itemFormDTO.getItemImgIds();

        for(int i=0; i<itemImgFileList.size();i++){                 //상품 이미지 업데이트를 위해 메서드를 통해 아이디, 이미지 파일 전달
            itemImgService.updateItemImg(itemImgIds.get(i),
                    itemImgFileList.get(i));
        }
        return item.getId();
    }
}
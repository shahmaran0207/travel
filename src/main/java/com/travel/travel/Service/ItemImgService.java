package com.travel.travel.Service;

import com.travel.travel.Entity.ItemImg;
import com.travel.travel.Repository.ItemImgRepository;
import com.travel.travel.Repository.ItemRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemImgService {

    private final ItemRepository itemRepository;
    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
        String orImgName=itemImgFile.getOriginalFilename();
        String imgName="";
        String imgUrl="";

        if(!StringUtils.isEmpty(orImgName)){
            imgName=fileService.uploadFile(itemImgLocation, orImgName, itemImgFile.getBytes());
            imgUrl="/images/item"+imgName;
        }

        itemImg.updateItemImg(orImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);

    }


    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
        if(!itemImgFile.isEmpty()){
            ItemImg savedItemImg=itemImgRepository.findById(itemImgId)
                    .orElseThrow(EntityExistsException::new);

            if(!StringUtils.isEmpty(savedItemImg.getImgName())){
                fileService.deleteFile(itemImgLocation+"/"+savedItemImg.getImgName());
            }

            String orImgName=itemImgFile.getOriginalFilename();
            String imgName=fileService.uploadFile(itemImgLocation, orImgName, itemImgFile.getBytes());
            String imgUrl="/images/item/"+imgName;
            savedItemImg.updateItemImg(orImgName, imgName, imgUrl);
        }
    }
}
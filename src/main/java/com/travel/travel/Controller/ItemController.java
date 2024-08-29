package com.travel.travel.Controller;

import com.travel.travel.DTO.ItemFormDTO;
import com.travel.travel.Service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/Admin/Item/new")
    public String itemForm(Model model){
        model.addAttribute("ItemFormDTO", new ItemFormDTO());
        return "Item/ItemForm";
    }

    @PostMapping(value="/Admin/Item/new")
    public String itemNew(@Valid ItemFormDTO itemFormDTO, BindingResult bindingResult,
                          Model model, @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList){

        if(bindingResult.hasErrors()) return "Item/ItemForm";

        if(itemImgFileList.get(0).isEmpty() && itemFormDTO.getId()==null){
            model.addAttribute("errorMessage", "첫 번째 상품 이미지는 필수 입력값입니다.");
            return "Item/ItemForm";
        }

        try{
            itemService.saveItem(itemFormDTO, itemImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
            return "Item/ItemForm";
        }

        return "/thymeleafEX/thymeleafEX07";
    }

    @GetMapping(value = "/Admin/Item/{itemId}")
    public String itemDTL(@PathVariable("itemId") Long itemId, Model model){
        try{
            ItemFormDTO itemFormDTO=itemService.getItemDTL(itemId);
            model.addAttribute("itemFormDto", itemFormDTO);
        } catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDto", new ItemFormDTO());
            return "Item/ItemForm";
        }
        return "Item/ItemForm";
    }

    @PostMapping(value = "/Admin/Item/{itemId}")
    public String itemUpdate(@Valid ItemFormDTO itemFormDTO,
                             BindingResult bindingResult, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model){
        if(bindingResult.hasErrors()) return "Item/ItemForm";

        if(itemImgFileList.get(0).isEmpty() && itemFormDTO.getId()==null){
            model.addAttribute("errorMessage", "첫 번째 상품 이미지는 필수 입력값입니다.");
            return "Item/ItemForm";
        }

        try{
            itemService.updateItem(itemFormDTO, itemImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정중 에러가 발생했습니다.");
            return "Item/ItemForm";
        }
        return "redirect:/thymeleafEX/thymeleafEX07";
    }
}
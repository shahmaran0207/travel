package com.travel.travel.Controller;

import com.travel.travel.DTO.ItemFormDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    @GetMapping(value = "Admin/Item/new")
    public String itemForm(Model model){
        model.addAttribute("ItemFormDTO", new ItemFormDTO());
        return "Item/ItemForm";
    }
}
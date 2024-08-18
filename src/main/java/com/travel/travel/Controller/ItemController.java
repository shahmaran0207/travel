package com.travel.travel.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    @GetMapping(value = "Admin/Item/new")
    public String itemForm(){
        return "Item/ItemForm";
    }
}
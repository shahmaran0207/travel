package com.travel.travel.Entity;

import com.travel.travel.Constant.ItemSellStatus;
import com.travel.travel.DTO.ItemFormDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item {

    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String itemNm;

    @Column(nullable = false, name="price")
    private int price;

    @Column(nullable = false)
    private int stockNumber;

    @Lob
    @Column(nullable = false)
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    private LocalDateTime regTime;

    private LocalDateTime updatetime;

    private String Itempic;

    public void updateItem(ItemFormDTO itemFormDTO){
        this.itemNm=itemFormDTO.getItemNm();
        this.price=itemFormDTO.getPrice();
        this.stockNumber=itemFormDTO.getStockNumber();
        this.itemDetail=itemFormDTO.getItemDetail();
        this.itemSellStatus=itemFormDTO.getItemSellStatus();
    }
}

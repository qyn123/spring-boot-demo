package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qiaoyanan
 * date:2022/07/07 15:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private String img;
    private String text;
    private String price;
    private String shopName;
}

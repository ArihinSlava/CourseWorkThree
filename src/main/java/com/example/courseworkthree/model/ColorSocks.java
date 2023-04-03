package com.example.courseworkthree.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum ColorSocks {

    BLUE("Синий"),
    RED("Красный"),
    BLACK("Черный"),
    WHITE("Белый"),
    GREY("Серый ");

    private String nameColor;
}

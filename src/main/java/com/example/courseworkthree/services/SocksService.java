package com.example.courseworkthree.services;

import com.example.courseworkthree.model.ColorSocks;
import com.example.courseworkthree.model.SizeSocks;
import com.example.courseworkthree.model.Socks;

public interface SocksService {

    String addSocks(Socks socks, int quantity);

    Integer getSocks(ColorSocks color, SizeSocks size, int cottonMin, int cottonMax);

    Integer DeleteOrPutSocks(Socks socks, int quantity);
}

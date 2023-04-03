package com.example.courseworkthree.services.Impl;

import com.example.courseworkthree.model.ColorSocks;
import com.example.courseworkthree.model.SizeSocks;
import com.example.courseworkthree.model.Socks;
import com.example.courseworkthree.services.FilesService;
import com.example.courseworkthree.services.SocksService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class SocksServiceImpl implements SocksService {

    private final FilesService filesService;

    private Map<Socks, Integer> socksMap = new HashMap<>();

    public SocksServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    private Integer getSocks(Socks socks) {
        return socksMap.get(socks);
    }

    @PostConstruct
    private void init() {
        File file = filesService.getDataFile();
        if (file.exists()) {
            readFromFile();
        }
    }

    @Override
    public String addSocks(Socks socks, int quantity) {
        if (quantity > 0 && socks.getCottonPart() >= 0) {
            Integer count = getSocks(socks);
            if (count == null) {
                socksMap.put(socks, quantity);
            } else {
                count += quantity;
                socksMap.put(socks, quantity);
            }
            saveToFile();
            return " Носки : цвет - " + socks.getColorSocks() + ", размер - " + socks.getSizeSocks() + ". Процент хлопка - " + socks.getCottonPart()
                    + ", количества пар - " + quantity;
        }
        return null;
    }

    @Override
    public Integer getSocks(ColorSocks color, SizeSocks size, int cottonMin, int cottonMax) {
        int count = 0;
        if (cottonMin >= 0 && cottonMax >= 0 && cottonMax >= cottonMin) {
            for (Map.Entry<Socks, Integer> entry : socksMap.entrySet()) {
                if (entry.getKey().getColorSocks() == color && entry.getKey().getSizeSocks() == size && entry.getKey().getCottonPart() >= cottonMin
                        && entry.getKey().getCottonPart() <= cottonMax) {
                    count += entry.getValue();
                }
                return count;
            }
        }
        return null;
    }

    @Override
    public Integer DeleteOrPutSocks(Socks socks, int quantity) {
        if (quantity > 0 && socksMap.containsKey(socks)) {
            int number = socksMap.get(socks) - quantity;
            if (number > 0) {
                socksMap.put(socks, number);
                saveToFile();
                return number;
            } else if (number == 0) {
                socksMap.remove(socks);
                saveToFile();
                return number;
            } else {
                return null;
            }
        }
        return null;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(socksMap);
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    void readFromFile() {
        String json = filesService.readFromFile();
        try {
            socksMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Socks, Integer>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}


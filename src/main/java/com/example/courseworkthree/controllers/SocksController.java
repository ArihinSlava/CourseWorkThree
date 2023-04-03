package com.example.courseworkthree.controllers;

import com.example.courseworkthree.model.ColorSocks;
import com.example.courseworkthree.model.SizeSocks;
import com.example.courseworkthree.model.Socks;
import com.example.courseworkthree.services.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/socks")
@Tag(name = "Учет носков на складе", description = "Приход/продажа/списание")
public class SocksController {

    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping
    @Operation(
            summary = "Пополнение носков на складе",
            description = "Необходимо указать количество носков."
    )
    @Parameters(value = {
            @Parameter(
                    name = "quantity", example = "10"
            )
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Удалось добавить приход.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }

            ),
            @ApiResponse(
                    responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат."
            ),
            @ApiResponse(
                    responseCode = "404", description = "Неверный URL или действия не существует."
            ),
            @ApiResponse(
                    responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны."
            )
    })
    public ResponseEntity<String> addSocks(@RequestBody Socks socks, @RequestParam int quantity) {
        String add = socksService.addSocks(socks, quantity);
        if (add == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(add);
    }

    @GetMapping
    @Operation(
            summary = "Вывод носков в рамках минимального и максимального содержания хлопка.",
            description = "Необходимо указать цвет носков, минимальный и максимальный процент хлопка."
    )
    @Parameters(value = {
            @Parameter(
                    name = "cottonMin", example = "0"
            ),
            @Parameter(
                    name = "cottonMax", example = "100"
            )
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = " Запрос выполнен, результат в теле ответа в виде строкового представления целого числа."
            ),
            @ApiResponse(
                    responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат."
            ),
            @ApiResponse(
                    responseCode = "404", description = "Неверный URL или действия не существует."
            ),
            @ApiResponse(
                    responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны."
            )
    })
    public ResponseEntity<String> getSocks(@RequestParam ColorSocks colorSocks, @RequestParam SizeSocks sizeSocks, @RequestParam int cottonMin,
                                           @RequestParam int cottonMax) {
        String getSocks = String.valueOf(socksService.getSocks(colorSocks,sizeSocks,cottonMin,cottonMax));
        if (getSocks == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(getSocks);
    }

    @PutMapping
    @Operation(
            summary = "Отпуск носков со склада.",
            description = "Необходимо указать количество носков. Допустымые размеры: XS(40), S(42), M(44), L(46), XL(48)." +
                    "Допустимые цвета: BLUE(Синий), RED(Красный), BLACK(Черный), WHITE(Белый), GREY(Серый)"
    )
    @Parameters(value = {
            @Parameter(
                    name = "quantity", example = "10"
            )
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Запрос выполнен, результат в теле ответа в виде строкового представления целого числа"
            ),
            @ApiResponse(
                    responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат."
            ),
            @ApiResponse(
                    responseCode = "404", description = "Неверный URL или действия не существует."
            ),
            @ApiResponse(
                    responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны."
            )
    })
    public ResponseEntity<String> editSocks(@RequestBody Socks socks, @RequestParam int quantity) {
        String edit = String.valueOf(socksService.DeleteOrPutSocks(socks, quantity));
        if (edit == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(edit);
    }

    @DeleteMapping
    @Operation(
            summary = "Списание испорченных(бракованных) носков.",
            description = "Необходимо указать количество носков. Допустымые размеры: XS(40), S(42), M(44), L(46), XL(48)." +
                    "Допустимые цвета: BLUE(Синий), RED(Красный), BLACK(Черный), WHITE(Белый), GREY(Серый)"
    )
    @Parameters(value = {
            @Parameter(
                    name = "quantity", example = "10"
            )
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Запрос выполнен, результат в теле ответа в виде строкового представления целого числа"
            ),
            @ApiResponse(
                    responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат."
            ),
            @ApiResponse(
                    responseCode = "404", description = "Неверный URL или действия не существует."
            ),
            @ApiResponse(
                    responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны."
            )
    })
    public ResponseEntity<String> deleteSocks(@RequestBody Socks socks, @RequestParam int quantity) {
        String delete = String.valueOf(socksService.DeleteOrPutSocks(socks, quantity));
        if (delete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(delete);
    }
}

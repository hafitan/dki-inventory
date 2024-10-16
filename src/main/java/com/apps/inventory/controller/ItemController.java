package com.apps.inventory.controller;

import com.apps.inventory.dto.request.ItemRequestDto;
import com.apps.inventory.model.Item;
import com.apps.inventory.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor

public class ItemController {
    private final ItemService itemService;

    @GetMapping("getAll")
    public ResponseEntity<List<Item>> getAllItem() {
        List<Item> res = itemService.getAllItem();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Item> getById(@PathVariable("id") Integer id) {
        Item res = itemService.getById(id);
        if (res != null) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<Item> insertItem(@ModelAttribute ItemRequestDto request) {
        return itemService.insertItem(request);
    }

    @PutMapping(value = "/update/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Item> updateItem(@PathVariable Integer id, @ModelAttribute ItemRequestDto request) {
        return itemService.updateItem(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteItem(@PathVariable(name = "id") Integer id) {
        return itemService.deleteItem(id);
    }
}

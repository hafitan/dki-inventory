package com.apps.inventory.service;

import com.apps.inventory.dto.request.ItemRequestDto;
import com.apps.inventory.model.Item;
import com.apps.inventory.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    @Autowired
    private final ItemRepository itemRepository;

    private final String IMAGE_DIRECTORY = "C:/DKI/inventory/images/";

    private final Logger logger = LoggerFactory.getLogger(ItemService.class);

    public String saveImage(MultipartFile image) {
        try {
            Path imageDir = Paths.get(IMAGE_DIRECTORY);

            if (!Files.exists(imageDir)) {
                Files.createDirectories(imageDir);
            }

            String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

            Path path = imageDir.resolve(imageName);
            image.transferTo(path.toFile());
            return imageName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteImage(String imageName) {
        try {
            Path path = Paths.get(IMAGE_DIRECTORY + imageName);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Item> getAllItem(){
        return itemRepository.findAll();
    }

    public Item getById(Integer id) {
        return itemRepository.findById(id).orElse(null);
    }

    public ResponseEntity<Item> insertItem(ItemRequestDto request) {
        try {
            logger.info("Received ItemRequestDto: {}", request);

            Item item = Item.builder()
                    .nama_barang(request.getNama_barang())
                    .jumlah_stok_barang(request.getJumlah_stok_barang())
                    .nomor_seri_barang(request.getNomor_seri_barang())
                    .build();

            if (request.getGambar_barang() != null && !request.getGambar_barang().isEmpty()) {
                String contentType = request.getGambar_barang().getContentType();
                if (!isValidImageType(contentType)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(null);
                }

                String imagePath = saveImage(request.getGambar_barang());
                if (imagePath != null) {
                    item.setGambar_barang(imagePath);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                }
            }

            item.setAdditional_info(request.getAdditional_info());
            item.setCreated_by("User");
            item.setCreated_at(LocalDateTime.now());
            log.info(item.toString());
            itemRepository.save(item);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            log.info("cek error " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }


    public ResponseEntity<Item> updateItem(Integer id, ItemRequestDto request) {
        try {
            logger.info("Received ItemRequestDto: {}", request);
            Optional<Item> existingItemOptional = itemRepository.findById(id);

            if (!existingItemOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            Item existingItem = existingItemOptional.get();

            existingItem.setNama_barang(request.getNama_barang());
            existingItem.setJumlah_stok_barang(request.getJumlah_stok_barang());
            existingItem.setNomor_seri_barang(request.getNomor_seri_barang());

            if (request.getGambar_barang() != null && !request.getGambar_barang().isEmpty()) {
                String contentType = request.getGambar_barang().getContentType();
                if (!isValidImageType(contentType)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(null);
                }

                if (existingItem.getGambar_barang() != null) {
                    deleteImage(existingItem.getGambar_barang());
                }

                String imagePath = saveImage(request.getGambar_barang());
                if (imagePath != null) {
                    existingItem.setGambar_barang(imagePath);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                }
            }

            existingItem.setUpdated_by("User");
            existingItem.setUpdated_at(LocalDateTime.now());

            itemRepository.save(existingItem);

            return ResponseEntity.ok(existingItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

    public ResponseEntity<?> deleteItem(Integer id) {
        Optional<Item> getItem = itemRepository.findById(id);
        if (!getItem.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data tidak ada");
        }

        itemRepository.deleteById(id);
        return ResponseEntity.ok().body("Data berhasil dihapus");
    }

    private boolean isValidImageType(String contentType) {
        return "image/jpg".equalsIgnoreCase(contentType) || "image/png".equalsIgnoreCase(contentType);
    }

}

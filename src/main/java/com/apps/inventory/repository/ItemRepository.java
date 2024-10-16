package com.apps.inventory.repository;

import com.apps.inventory.model.Item;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

    public interface ItemRepository extends JpaRepository<Item, Long> {
        List<Item> findAll();
        Optional<Item> findById(Integer id);
        void deleteById(Integer id);

//        @Modifying
//        @Transactional
//        @Query(value = "INSERT INTO item (nama_barang, jumlah_stok_barang, nomor_seri_barang, additional_info, gambar_barang, created_at, created_by) " +
//                "VALUES (:nama_barang, :jumlah_stok_barang, :nomor_seri_barang, CAST(:additional_info AS jsonb), :gambar_barang, :created_at, :created_by)",
//                nativeQuery = true)
//        void saveNative(String nama_barang, Integer jumlah_stok_barang, String nomor_seri_barang, String additional_info, String gambar_barang, LocalDateTime created_at, String created_by);
    }

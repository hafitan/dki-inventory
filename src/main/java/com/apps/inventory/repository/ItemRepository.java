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
    }

package com.apps.inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nama_barang")
    private String nama_barang;

    @Column(name = "jumlah_stok_barang")
    private String jumlah_stok_barang;

    @Column(name = "nomor_seri_barang")
    private String nomor_seri_barang;

    @Column(name = "gambar_barang")
    private String gambar_barang;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    @Column(name = "created_by", length = 150, nullable = false, updatable = false)
    private String created_by;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "updated_by", length = 150)
    private String updated_by;

    @Column(name = "additional_info", columnDefinition = "jsonb")
    private String additional_info;

//    @Column(name = "additional_info")
//    private String additional_info;

}

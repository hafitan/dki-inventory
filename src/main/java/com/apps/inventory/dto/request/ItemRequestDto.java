package com.apps.inventory.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemRequestDto {
    Integer id;
    String nama_barang;
    String jumlah_stok_barang;
    String nomor_seri_barang;
    String additional_info;
    MultipartFile gambar_barang;
}

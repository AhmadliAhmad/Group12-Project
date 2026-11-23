package com.retail.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockProduct {
    private String id;
    private String name;
    private String barcode;
    private String category;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private Integer stockQuantity;
    private String ownerId;
    private String createdAt;
    private String updatedAt;
}
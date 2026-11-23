package com.retail.v1;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MockProductRequest {
    private String name;
    private String barcode;
    private String category;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private Integer stockQuantity;
}
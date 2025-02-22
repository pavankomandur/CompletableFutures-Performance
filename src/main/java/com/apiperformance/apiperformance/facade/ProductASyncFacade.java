package com.apiperformance.apiperformance.facade;


import com.apiperformance.apiperformance.dto.ProductDetailDTO;
import com.apiperformance.apiperformance.entity.Inventory;
import com.apiperformance.apiperformance.entity.Price;
import com.apiperformance.apiperformance.entity.Product;
import com.apiperformance.apiperformance.service.InventoryService;
import com.apiperformance.apiperformance.service.PriceService;
import com.apiperformance.apiperformance.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class ProductASyncFacade {

    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private PriceService priceService;

    public CompletableFuture<Product> getProductById(long productId) {
        return CompletableFuture
                .supplyAsync(() -> productService.findById(productId));
    }

    public CompletableFuture<Price> getPriceByProductById(long productId) {
        return CompletableFuture
                .supplyAsync(() -> priceService.getPriceByProductId(productId));
    }

    public CompletableFuture<Inventory> getInventoryByProductId(long productId) {
        return CompletableFuture
                .supplyAsync(() -> inventoryService.getInventoryByProductId(productId));
    }


    public ProductDetailDTO getProductDetails(long productId) {

        //fetch all async
        CompletableFuture<Product> productFuture = getProductById(productId);
        CompletableFuture<Price> priceFuture = getPriceByProductById(productId);
        CompletableFuture<Inventory> inventoryFuture = getInventoryByProductId(productId);

        //wait for all futures to complete
        CompletableFuture.allOf(priceFuture, productFuture, inventoryFuture);

        //combine the result
        Product product = productFuture.join();
        Price price = priceFuture.join();
        Inventory inventory = inventoryFuture.join();

        //build and return

        return new ProductDetailDTO(productId, product.getCategory().getName(),
                product.getName(), product.getDescription(),
                inventory.getAvailableQuantity(), price.getPrice(),
                inventory.getStatus());

    }
}


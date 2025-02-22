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

@Component
@Slf4j
public class ProductSyncFacade {

    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private PriceService priceService;


    public ProductDetailDTO getProductDetails(long productId) {
        log.info("Sync facade for getting product details for the product id {}",productId);

        //fetch product
        Product product = productService.findById(productId);

        //fetch the price
        Price price = priceService.getPriceByProductId(productId);

        //fetch the inventory
        Inventory inventory = inventoryService.getInventoryByProductId(productId);

        //combine result
        return new ProductDetailDTO(productId, product.getCategory().getName(),
                product.getName(), product.getDescription(),
                inventory.getAvailableQuantity(), price.getPrice(),
                inventory.getStatus());
    }

}

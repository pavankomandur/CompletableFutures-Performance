package com.apiperformance.apiperformance.service.impl;

import com.apiperformance.apiperformance.entity.Inventory;
import com.apiperformance.apiperformance.repository.InventoryRepository;
import com.apiperformance.apiperformance.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory getInventoryByProductId(Long productId) {
        log.info("Getting inventory for the productId {}", productId);
        addDelay();
        return inventoryRepository.findByProductId(productId);
    }

    private void addDelay() {
        try {
            // adding 2s delay
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
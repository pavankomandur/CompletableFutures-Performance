package com.apiperformance.apiperformance.service;


import com.apiperformance.apiperformance.entity.Inventory;

import java.util.Optional;

public interface InventoryService {
    Inventory getInventoryByProductId(Long productId);
}


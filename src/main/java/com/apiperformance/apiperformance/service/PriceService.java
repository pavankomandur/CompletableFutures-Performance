package com.apiperformance.apiperformance.service;


import com.apiperformance.apiperformance.entity.Price;

public interface PriceService {
        Price getPriceByProductId(Long productId);
    }


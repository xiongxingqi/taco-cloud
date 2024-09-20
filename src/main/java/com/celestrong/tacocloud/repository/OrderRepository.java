package com.celestrong.tacocloud.repository;

import com.celestrong.tacocloud.pojo.entity.TacoOrder;

public interface OrderRepository{
    TacoOrder save(TacoOrder tacoOrder);
}

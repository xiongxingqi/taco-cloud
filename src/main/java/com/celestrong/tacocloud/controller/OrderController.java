package com.celestrong.tacocloud.controller;

import com.celestrong.tacocloud.pojo.entity.TacoOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController{

    @GetMapping("/current")
    public String orderForm(){

        return "orderForm";
    }

    @PostMapping
    public String processOrder(TacoOrder tacoOrder,
                               SessionStatus sessionStatus){
        log.info("order submit: {}",tacoOrder);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}

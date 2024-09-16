package com.celestrong.tacocloud.controller;

import com.celestrong.tacocloud.pojo.entity.Ingredient;
import com.celestrong.tacocloud.pojo.entity.Ingredient.Type;
import com.celestrong.tacocloud.pojo.entity.Taco;
import com.celestrong.tacocloud.pojo.entity.TacoOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
@Controller
@Slf4j
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    @ModelAttribute
    public void addIngredientsToModel(Model model){
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );
        Type[] types = Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients,type));
        }

    }

    List<Ingredient> filterByType(List<Ingredient> ingredients,Type type){
        return ingredients
                .stream()
                .filter(ingredient -> ingredient.getType().equals(type) )
                .toList();
    }

    @ModelAttribute
    public Taco taco(){
        return new Taco();
    }

    @ModelAttribute
    public TacoOrder order(){
        System.out.println("新的order");
        return new TacoOrder();
    }

    @GetMapping
    public String showDesignForm(){
        return "design";
    }

    @PostMapping
    public String processTaco(Taco taco,
                              @ModelAttribute TacoOrder tacoOrder){
        log.info("process Taco:{}",taco);
        tacoOrder.addTaco(taco);
        return "redirect:/orders/current";
    }
}

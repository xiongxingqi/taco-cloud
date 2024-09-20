package com.celestrong.tacocloud.controller;

import com.celestrong.tacocloud.pojo.entity.Ingredient;
import com.celestrong.tacocloud.pojo.entity.Ingredient.Type;
import com.celestrong.tacocloud.pojo.entity.Taco;
import com.celestrong.tacocloud.pojo.entity.TacoOrder;
import com.celestrong.tacocloud.repository.IngredientRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@Slf4j
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository  ingredientRepository;

    public DesignTacoController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model){


        Iterable<Ingredient> ingredients = ingredientRepository.findAll();
        Type[] types = Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients,type));
        }

    }

    List<Ingredient> filterByType(Iterable<Ingredient> ingredients,Type type){
        // todo 看一看Iterable,StreamSupport
        return StreamSupport
                .stream(ingredients.spliterator(),false)
                .filter(ingredient -> ingredient.getType().equals(type))
                .collect(Collectors.toList());
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
    public String processTaco(@Valid Taco taco, Errors errors,
                              @ModelAttribute TacoOrder tacoOrder){
        if (errors.hasErrors()) {
            return "design";
        }
        log.info("process Taco:{}",taco);
        tacoOrder.addTaco(taco);
        return "redirect:/orders/current";
    }
}

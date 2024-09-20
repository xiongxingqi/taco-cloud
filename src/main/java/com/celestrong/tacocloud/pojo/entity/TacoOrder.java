package com.celestrong.tacocloud.pojo.entity;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TacoOrder implements Serializable {

    @Serial
    private static final  long serialVersionUID=1L;

    private Long id;
    //下单时间
    private Date placedAt;

    @NotBlank(message = "送货地址不能为空")
    private String deliveryName;
    @NotBlank(message = "街道不能为空")
    private String deliveryStreet;
    @NotBlank(message = "城市不能为空")
    private String deliveryCity;
    @NotBlank(message = "州不能为空")
    private String deliveryState;
    @NotBlank(message = "邮政编码不能为空")
    private String deliveryZip;
    @CreditCardNumber(message = "信用卡输入不正确")
    private String ccNumber;
    @Pattern(regexp = "^(0[0-9]|1[0-2])/([2-9][0-9]$)", message = "时间格式必须为: MM/YY")
    private String ccExpiration;
    @Digits(integer = 3,fraction = 0 ,message = "cvv不正确")
    private String ccCVV;
    private List<Taco> tacos =new ArrayList<>();

    public void addTaco(Taco taco){
        this.tacos.add(taco);
    }
}

package br.com.paymentservicepb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Items {

    @NotBlank
    private String item;

    @Positive
    private BigDecimal value;

    @Positive
    private Integer qty;

}

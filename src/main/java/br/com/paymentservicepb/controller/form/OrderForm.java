package br.com.paymentservicepb.controller.form;

import br.com.paymentservicepb.model.Items;
import br.com.paymentservicepb.model.Payment;
import br.com.paymentservicepb.model.enums.CurrencyType;
import br.com.paymentservicepb.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderForm {

    @Length(min = 11, max = 11)
    @NotBlank
    private String cpf;

    @Valid
    @NotEmpty(message = "Adicione pelo menos 1 item ao pedido")
    private List<@Valid Items> items;

    @Positive
    private BigDecimal shipping;

    @Positive
    private BigDecimal discount;

    @Enumerated(EnumType.STRING)
    private PaymentType  payment_type;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency_type;

    @Valid
    private Payment payment;


}

package br.com.paymentservicepb.model;

import br.com.paymentservicepb.model.enums.CurrencyType;
import br.com.paymentservicepb.model.enums.PaymentStatus;
import br.com.paymentservicepb.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Orders" )
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "order_items_id")
    private Long order_id;

    @Length(min = 11, max = 14)
    private String cpf;

    @Transient
    @OneToMany(targetEntity= Order.class, fetch=FetchType.EAGER)
    private List<Items> items;

    private BigDecimal shipping;

    private BigDecimal discount;

    @Enumerated(EnumType.STRING)
    private PaymentType payment_type;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency_type;

    @Transient
    private Payment payment;

    private BigDecimal total = new BigDecimal(0);

    private String payment_id = UUID.randomUUID().toString();

    @Enumerated(EnumType.STRING)
    private PaymentStatus payment_status;

    private String message;

    public Order(String cpf, List<Items> items, BigDecimal shipping, BigDecimal discount, PaymentType payment_type, CurrencyType currency_type, Payment payment) {
        this.cpf = cpf;
        this.items = items;
        this.shipping = shipping;
        this.discount = discount;
        this.payment_type = payment_type;
        this.currency_type = currency_type;
        this.payment = payment;
    }

    public void result(){
        this.total = calculateTotal();
        checkCard();
    }

    private BigDecimal calculateTotal(){
        items.forEach(i -> setTotal(getTotal().add(new BigDecimal(i.getQty()).multiply(i.getValue()))));
        setTotal(getTotal().add(getShipping()));
        return getTotal().subtract(getDiscount());
    }

    private void checkCard(){
        if(payment.getExpiration_year() == (LocalDate.now().getYear() % 100)){
            if(payment.getExpiration_month() >= (Integer) LocalDate.now().getMonthValue()){
                payment_status = PaymentStatus.APPROVED;
                message = "Transaction approved";
                return;
            }
        }else if(payment.getExpiration_year() > (LocalDate.now().getYear() % 100)){
            payment_status = PaymentStatus.APPROVED;
            message = "Transaction approved";
            return;
        }
        payment_status = PaymentStatus.REFUSED;
        message = "Transaction refused, expired card";
    }
}

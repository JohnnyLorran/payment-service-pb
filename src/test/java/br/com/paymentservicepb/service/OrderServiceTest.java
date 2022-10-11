package br.com.paymentservicepb.service;

import br.com.paymentservicepb.controller.dto.OrderDetailsDto;
import br.com.paymentservicepb.controller.form.OrderForm;
import br.com.paymentservicepb.model.Items;
import br.com.paymentservicepb.model.Payment;
import br.com.paymentservicepb.model.enums.Brand;
import br.com.paymentservicepb.model.enums.CurrencyType;
import br.com.paymentservicepb.model.enums.PaymentStatus;
import br.com.paymentservicepb.model.enums.PaymentType;
import br.com.paymentservicepb.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest(classes = OrderService.class)
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @SpyBean
    private ModelMapper modelMapper;


    @Test
    void shouldReturnNewOrderDetailsDto_whenNewRequest_withCorrectInformations() {
        //Cenario
        OrderForm orderForm = getOrderFormApproved();

        //Execução do teste
        OrderDetailsDto result = orderService.newRequest(orderForm);

        //Vericação deoresultado
        Assertions.assertNotNull(result);
        Assertions.assertNull(result.getOrder_id());
        Assertions.assertNotNull(result.getPayment_id());
        Assertions.assertEquals(new BigDecimal("67.13"),result.getTotal());
        Assertions.assertEquals(PaymentStatus.APPROVED, result.getPayment_status());
        Assertions.assertEquals("Transaction approved", result.getMessage());

    }

    @Test
    void shouldReturnNewOrderDetailsDto_whenNewRequest_withCardExpired() {

        OrderForm orderForm = getOrderFormRefused();

        OrderDetailsDto result = orderService.newRequest(orderForm);

        Assertions.assertNotNull(result);
        Assertions.assertNull(result.getOrder_id());
        Assertions.assertNotNull(result.getPayment_id());
        Assertions.assertEquals(PaymentStatus.REFUSED, result.getPayment_status());
        Assertions.assertEquals("Transaction refused, expired card", result.getMessage());

    }


    @Test
    void ShouldReturnPaymentDetailsById_whenExistingId() {

        Mockito.when(orderService.getPaymentDetails(Mockito.anyLong())).thenReturn(getOrderDatilsByOrder_Id());

        OrderDetailsDto orderDetailsDto = orderService.getPaymentDetails(1L);

        Assertions.assertEquals(1L, orderDetailsDto.getOrder_id());

    }

    @Test
    void ShouldReturnAllPayments() {
        List<OrderDetailsDto> detailsDtos = getAllOrdersDetails();

        Mockito.when(orderService.getAllPaymentsDetails()).thenReturn(detailsDtos);

        Assertions.assertNotNull(detailsDtos);
        Assertions.assertEquals((long) detailsDtos.size(),2);

    }


    private OrderDetailsDto getOrderDatilsByOrder_Id(){
        return  new OrderDetailsDto(
                1L, new BigDecimal("150.54"),
                "d3715562-3cbe-4a7e-a43d-83acbc632fd2",
                PaymentStatus.APPROVED, "Transaction approved");
    }

    private List<OrderDetailsDto> getAllOrdersDetails() {
        List<OrderDetailsDto> detailsDtos = new ArrayList<>();
        OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                1L, new BigDecimal("150.54"),
                "d3715562-3cbe-4a7e-a43d-83acbc632fd2",
                PaymentStatus.APPROVED, "Transaction approved");

        OrderDetailsDto orderDetailsDto1 = new OrderDetailsDto(
                2L, new BigDecimal("150.54"),
                "d3715562-acbe-4a7e-a43d-83acbc632fd2",
                PaymentStatus.REFUSED, "Transaction refused, expired card");
        detailsDtos.add(orderDetailsDto);
        detailsDtos.add(orderDetailsDto1);

     return detailsDtos;

    }

    private OrderForm getOrderFormApproved(){
        List<OrderForm> orderForms = new ArrayList<>();
        List<Items> items  = new ArrayList<>();
        Items item = new Items("coca-cola",new BigDecimal("9.50"),10);
        items.add(item);
        Brand brand = Brand.MASTERCARD;
        PaymentType paymentType = PaymentType.CREDIT_CARD;
        CurrencyType currencyType = CurrencyType.BRL;
        Payment payment = new Payment(
                "12345667878979", "JOAO DA SILVA", "140", 10,23,brand);
       return new OrderForm(
                "12345678910", items, new BigDecimal("50"), new BigDecimal("77.87"), paymentType, currencyType, payment);
    }


    private OrderForm getOrderFormRefused(){
        List<OrderForm> orderForms = new ArrayList<>();
        List<Items> items  = new ArrayList<>();
        Items item = new Items("coca-cola",new BigDecimal("9.50"),10);
        items.add(item);
        Brand brand = Brand.MASTERCARD;
        PaymentType paymentType = PaymentType.CREDIT_CARD;
        CurrencyType currencyType = CurrencyType.BRL;
        Payment payment = new Payment(
                "12345667878979", "JOAO DA SILVA", "140", 10,21,brand);
        return new OrderForm(
                "12345678910", items, new BigDecimal("50"), new BigDecimal("77.87"), paymentType, currencyType, payment);
    }

}
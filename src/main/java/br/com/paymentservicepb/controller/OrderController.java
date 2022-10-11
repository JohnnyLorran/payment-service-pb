package br.com.paymentservicepb.controller;

import br.com.paymentservicepb.controller.dto.OrderDetailsDto;
import br.com.paymentservicepb.controller.form.OrderForm;
import br.com.paymentservicepb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/order/payment")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDetailsDto> newRequest(@RequestBody @Valid OrderForm form, UriComponentsBuilder uriBuilder) {
            OrderDetailsDto orderDetailsDto = orderService.newRequest(form);
            URI uri = uriBuilder.path("/api/v1/order/payment/{id}").buildAndExpand(orderDetailsDto.getOrder_id()).toUri();
            return ResponseEntity.created(uri).body(orderDetailsDto);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<OrderDetailsDto> getDetailsPayment(@PathVariable @NotNull Long id){
        OrderDetailsDto orderDetailsDto = orderService.getPaymentDetails(id);
        if(orderDetailsDto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return  ResponseEntity.status(HttpStatus.OK).body(orderDetailsDto);
    }


    @GetMapping
    public ResponseEntity<List<OrderDetailsDto>>  getAllPaymentsDetails(){
        List<OrderDetailsDto> orderDetailsDtos = orderService.getAllPaymentsDetails();
        return ResponseEntity.status(HttpStatus.OK).body(orderDetailsDtos);
    }

}

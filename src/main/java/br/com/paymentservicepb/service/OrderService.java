package br.com.paymentservicepb.service;

import br.com.paymentservicepb.controller.dto.OrderDetailsDto;
import br.com.paymentservicepb.controller.form.OrderForm;
import br.com.paymentservicepb.model.Order;
import br.com.paymentservicepb.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    public OrderDetailsDto newRequest(OrderForm form) {
            Order order = modelMapper.map(form, Order.class);
            order.result();
            orderRepository.save(order);
            return modelMapper.map(order, OrderDetailsDto.class);
    }

    public OrderDetailsDto getPaymentDetails(Long id) {
        return modelMapper.map(orderRepository.findById(id), OrderDetailsDto.class);
    }

    public List<OrderDetailsDto> getAllPaymentsDetails(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(o -> modelMapper.map(o , OrderDetailsDto.class)).collect(Collectors.toList());
    }

}

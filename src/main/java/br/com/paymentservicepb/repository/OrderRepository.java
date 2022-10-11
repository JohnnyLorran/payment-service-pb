package br.com.paymentservicepb.repository;

import br.com.paymentservicepb.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}

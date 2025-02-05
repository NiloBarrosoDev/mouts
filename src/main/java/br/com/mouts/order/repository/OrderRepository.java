package br.com.mouts.order.repository;

import br.com.mouts.order.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByNumeroPedido(Long numeroPedido);
}

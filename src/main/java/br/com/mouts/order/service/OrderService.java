package br.com.mouts.order.service;

import br.com.mouts.order.model.Pedido;
import br.com.mouts.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Pedido> salvarPedidos(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> {
                    Optional<Pedido> pedidoExistente = orderRepository.findByNumeroPedido(pedido.getNumeroPedido());
                    if (pedidoExistente.isPresent()) {
                        return null;
                    } else {
                        pedido.setValorTotal(calcularTotalPedido(pedido));
                        return orderRepository.save(pedido);
                    }
                })
                .filter(pedido -> pedido != null)
                .collect(Collectors.toList());
    }

    private BigDecimal calcularTotalPedido(Pedido pedido) {
        return pedido.getProdutos().stream()
                .map(produto -> produto.getValor().multiply(new BigDecimal(produto.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Optional<Pedido> getPedidoByNumeroPedido(Long numeroPedido) {
        return orderRepository.findByNumeroPedido(numeroPedido);
    }
}

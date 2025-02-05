package br.com.mouts.order.repository;

import br.com.mouts.order.model.Pedido;
import br.com.mouts.order.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        pedido.setNumeroPedido(123L);
        pedido.setValorTotal(new BigDecimal(29.99));
        pedido.setStatus("FINALIZADO");
        pedido.setData(new Date());
        pedido.setProdutos(Arrays.asList(Produto.builder()
                .nome("Camiseta")
                .valor(new BigDecimal(29.99))
                .quantidade(2L)
                .build()));
    }

    @Test
    public void testFindByNumeroPedido() {
        orderRepository.save(pedido);

        Optional<Pedido> pedidoPesquisado = orderRepository.findByNumeroPedido(123L);
        assertTrue(pedidoPesquisado.isPresent());
        assertEquals(pedido.getNumeroPedido(), pedidoPesquisado.get().getNumeroPedido());
    }

    @Test
    public void testFindByNumeroPedidoNotFound() {
        orderRepository.save(pedido);

        Optional<Pedido> pedidoPesquisado = orderRepository.findByNumeroPedido(333L);
        assertFalse(pedidoPesquisado.isPresent());
    }
}

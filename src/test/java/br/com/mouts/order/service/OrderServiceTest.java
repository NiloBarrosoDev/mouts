package br.com.mouts.order.service;

import br.com.mouts.order.model.Pedido;
import br.com.mouts.order.model.Produto;
import br.com.mouts.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pedido = new Pedido();
        pedido.setNumeroPedido(1L);
        pedido.setProdutos(Arrays.asList(new Produto(101L, "Camiseta", new BigDecimal("29.99"), 2L)));
    }

    @Test
    void testSalvarPedidos() {
        when(orderRepository.findByNumeroPedido(anyLong())).thenReturn(Optional.empty());
        when(orderRepository.save(any(Pedido.class))).thenReturn(pedido);

        List<Pedido> pedidos = orderService.salvarPedidos(Arrays.asList(pedido));
        assertEquals(1, pedidos.size());
        assertEquals(pedido.getNumeroPedido(), pedidos.get(0).getNumeroPedido());
    }

    @Test
    void testSalvarPedidosExistente() {
        when(orderRepository.findByNumeroPedido(anyLong())).thenReturn(Optional.of(pedido));

        List<Pedido> pedidos = orderService.salvarPedidos(Arrays.asList(pedido));
        assertTrue(pedidos.isEmpty());
    }

    @Test
    void testGetPedidoByNumeroPedido() {
        when(orderRepository.findByNumeroPedido(anyLong())).thenReturn(Optional.of(pedido));

        Optional<Pedido> pedidoOptional = orderService.getPedidoByNumeroPedido(1L);
        assertTrue(pedidoOptional.isPresent());
        assertEquals(pedido.getNumeroPedido(), pedidoOptional.get().getNumeroPedido());
    }

    @Test
    void testGetPedidoByNumeroPedidoNotFound() {
        when(orderRepository.findByNumeroPedido(anyLong())).thenReturn(Optional.empty());

        Optional<Pedido> pedidoOptional = orderService.getPedidoByNumeroPedido(1L);
        assertFalse(pedidoOptional.isPresent());
    }
}
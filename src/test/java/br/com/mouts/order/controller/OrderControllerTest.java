package br.com.mouts.order.controller;

import br.com.mouts.order.model.Pedido;
import br.com.mouts.order.model.Produto;
import br.com.mouts.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setNumeroPedido(1L);
        pedido.setValorTotal(new BigDecimal(29.99));
        pedido.setStatus("FINALIZADO");
        pedido.setData(new Date());
        pedido.setProdutos(Arrays.asList(new Produto(101L, "Camiseta", new BigDecimal(29.99), 2L)));
    }

    @Test
    void testSalvarPedidos() throws Exception {
        when(orderService.salvarPedidos(Arrays.asList(pedido))).thenReturn(Arrays.asList(pedido));

        mockMvc.perform(post("/orders/salvarLista")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Arrays.asList(pedido))))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetPedidoByNumeroPedido() throws Exception {
        when(orderService.getPedidoByNumeroPedido(1L)).thenReturn(Optional.of(pedido));

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPedidoByNumeroPedidoNotFound() throws Exception {
        when(orderService.getPedidoByNumeroPedido(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/orders/2"))
                .andExpect(status().isNotFound());
    }
}
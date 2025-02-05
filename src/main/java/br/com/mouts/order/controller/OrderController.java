package br.com.mouts.order.controller;

import br.com.mouts.order.model.Pedido;
import br.com.mouts.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/salvarLista")
    public ResponseEntity<List<Pedido>> salvarPedidos(@RequestBody List<Pedido> pedidos) {
        try {
            List<Pedido> pedidosSalvos = orderService.salvarPedidos(pedidos);
            return new ResponseEntity<>(pedidosSalvos, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{numeroPedido}")
    public ResponseEntity<Pedido> getPedidoByNumeroPedido(@PathVariable Long numeroPedido) {
        try {
            Optional<Pedido> pedido = orderService.getPedidoByNumeroPedido(numeroPedido);
            if (pedido.isPresent()) {
                return new ResponseEntity<>(pedido.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

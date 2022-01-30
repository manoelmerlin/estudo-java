package com.udemy.controllers;

import com.udemy.domain.Categoria;
import com.udemy.domain.Pedido;
import com.udemy.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pedido> Find(@PathVariable Integer id) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok().body(pedido);
    }

    @RequestMapping(method = RequestMethod.POST, consumes={"application/json"})
    public ResponseEntity<Void> adicionarPedido(@Valid @RequestBody Pedido pedido) {
        pedido = pedidoService.inserirPedido(pedido);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(pedido.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}

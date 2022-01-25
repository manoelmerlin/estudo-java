package com.udemy.controllers;

import com.udemy.domain.Categoria;
import com.udemy.domain.Pedido;
import com.udemy.services.CategoriaService;
import com.udemy.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

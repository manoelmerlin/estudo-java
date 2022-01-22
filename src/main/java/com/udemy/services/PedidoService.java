package com.udemy.services;

import com.udemy.domain.Pedido;
import com.udemy.repositories.PedidoRepository;
import com.udemy.services.Exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscarPedidoPorId(Integer id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);

        return pedido.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado Id: " + id + ", Tipo: " + Pedido.class.getName()
        ));
    }

    public List<Pedido> listarTodosProdutos() {
        List<Pedido> listaPedidos = pedidoRepository.findAll();

        return listaPedidos;
    }
}

package com.udemy.services;

import com.udemy.domain.Enums.EstadoPagamento;
import com.udemy.domain.ItemPedido;
import com.udemy.domain.PagamentoBoleto;
import com.udemy.domain.Pedido;
import com.udemy.repositories.ItemPedidoRepository;
import com.udemy.repositories.PagamentoRepository;
import com.udemy.repositories.PedidoRepository;
import com.udemy.services.Exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

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

    public Pedido inserirPedido(Pedido pedido) {
        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);
        if (pedido.getPagamento() instanceof PagamentoBoleto) {
            PagamentoBoleto pagamentoBoleto = (PagamentoBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagamentoBoleto, pedido.getInstante());
        }

        pedido = pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());

        for (ItemPedido ip : pedido.getItems()) {
            ip.setDesconto(0.0);
            ip.setPreco(produtoService.buscarProdutoPorId(ip.getProduto().getId()).getPreco());
            ip.setPedido(pedido);
        }

        itemPedidoRepository.saveAll(pedido.getItems());

        return pedido;
    }
}

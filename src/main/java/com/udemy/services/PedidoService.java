package com.udemy.services;

import com.udemy.domain.*;
import com.udemy.domain.Enums.EstadoPagamento;
import com.udemy.domain.Enums.Perfil;
import com.udemy.repositories.ClienteRepository;
import com.udemy.repositories.ItemPedidoRepository;
import com.udemy.repositories.PagamentoRepository;
import com.udemy.repositories.PedidoRepository;
import com.udemy.security.UsuarioSS;
import com.udemy.services.Exceptions.AuthorizationException;
import com.udemy.services.Exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private ClientesService clientesService;

    @Autowired
    private EmailService emailService;

    public Pedido buscarPedidoPorId(Integer id) {
        UsuarioSS usuarioLogado = UsuarioService.getUsuarioLogado();

        if (usuarioLogado.getId() != id && !usuarioLogado.HasRole(Perfil.ADMIN)) {
            throw new AuthorizationException("Você não está autorizado a acessar está área");
        }

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
        pedido.setCliente(clientesService.buscarClientePorId(pedido.getCliente().getId()));
        pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);
        if (pedido.getPagamento() instanceof PagamentoBoleto) {
            PagamentoBoleto pagamentoBoleto = (PagamentoBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagamentoBoleto, pedido.getInstante());
        }

        pedido = pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());

        for (ItemPedido ip : pedido.getItems()) {
            Produto produto = produtoService.buscarProdutoPorId(ip.getProduto().getId());
            ip.setDesconto(0.0);
            ip.setPreco(produto.getPreco());
            ip.setPedido(pedido);
            ip.setProduto(produto);
        }

        itemPedidoRepository.saveAll(pedido.getItems());

        System.out.println(pedido.toString());
        emailService.sendOrderConfirmationHtmlEmail(pedido);

        return pedido;
    }

    public Page<Pedido> buscarPedidos(Integer pagina, Integer linhasPorPagina, String ordernarPor, String direcao) {
        UsuarioSS usuarioLogado = UsuarioService.getUsuarioLogado();

        if (usuarioLogado == null) {
            throw new AuthorizationException("Você não está autorizado a acessar está área");
        }

        PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direcao), ordernarPor);
        Cliente cliente = clientesService.buscarClientePorId(usuarioLogado.getId());

        System.out.println(cliente.getId());

        return pedidoRepository.findByCliente(cliente, pageRequest);
    }
}

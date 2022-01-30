package com.udemy.services;

import com.udemy.domain.Categoria;
import com.udemy.domain.Produto;
import com.udemy.repositories.CategoriaRepository;
import com.udemy.repositories.ProdutoRepository;
import com.udemy.services.Exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto buscarProdutoPorId(Integer id) {
        Optional<Produto> produto = produtoRepository.findById(id);

        return produto.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado Id: " + id + ", Tipo: " + Produto.class.getName()
        ));
    }

    public List<Produto> listarTodosProdutos() {
        List<Produto> listaDeProdutos = produtoRepository.findAll();

        return listaDeProdutos;
    }

    public Page<Produto> buscarProdutos(String nome, List<Integer> Ids, Integer pagina, Integer linhasPorPagina, String ordernarPor, String direcao) {
        PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direcao), ordernarPor);
        List<Categoria> categorias = categoriaRepository.findAllById(Ids);

        return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
    }
}

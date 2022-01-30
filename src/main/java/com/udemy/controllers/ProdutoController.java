package com.udemy.controllers;

import com.udemy.controllers.utils.URL;
import com.udemy.domain.Categoria;
import com.udemy.domain.Produto;
import com.udemy.dto.CategoriaDTO;
import com.udemy.dto.ProdutoDTO;
import com.udemy.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Produto> Find(@PathVariable Integer id) {
        Produto produto = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok().body(produto);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ProdutoDTO>> buscarPagina(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
            @RequestParam(value = "ordernarPor", defaultValue = "nome") String ordernarPor,
            @RequestParam(value = "direcao", defaultValue = "ASC") String direcao) {

        Page<Produto> produtos = produtoService.buscarProdutos(URL.decodificarUrl(nome), URL.converterStringParaLista(categorias), pagina,
                linhasPorPagina, ordernarPor, direcao);

        Page<ProdutoDTO> produtoDTO = produtos.map(produto -> new ProdutoDTO(produto));

        return ResponseEntity.ok().body(produtoDTO);
    }

    @RequestMapping(value = "/teste", method = RequestMethod.GET)
    public ResponseEntity<?> teste() {
        return ResponseEntity.ok().body(URL.converterStringParaLista("1,2,3"));
    }
}

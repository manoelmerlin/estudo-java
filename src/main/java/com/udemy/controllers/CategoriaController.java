package com.udemy.controllers;

import com.udemy.domain.Categoria;
import com.udemy.dto.CategoriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.udemy.services.CategoriaService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Categoria> Find(@PathVariable Integer id) {
        Categoria categoria = categoriaService.buscarCategoriaPorId(id);
        return ResponseEntity.ok().body(categoria);
    }

    @GetMapping()
    public ResponseEntity<List<CategoriaDTO>> listarTodasCategorias() {
        List<Categoria> categorias = categoriaService.listarTodasCategorias();
        List<CategoriaDTO> categoriaDTO = categorias.stream().map(item -> new CategoriaDTO(item)).collect(Collectors.toList());

        return ResponseEntity.ok().body(categoriaDTO);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> adicionarCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaService.converterCategoriaParaDTO(categoriaDTO);
        categoria = categoriaService.inserirCategoria(categoria);

        //MONTA URL COM NOVO ENDEREÇO DO NOVO OBJETO INSERIO
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(categoria.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> atualizarCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO, @PathVariable Integer id) {
        Categoria categoria = categoriaService.converterCategoriaParaDTO(categoriaDTO);
        categoria.setId(id);
        categoriaService.editarCategoria(categoria);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable("id") Integer id) {
        categoriaService.deletarCategoriaPorId(id);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<CategoriaDTO>> buscarPagina(
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
            @RequestParam(value = "ordernarPor", defaultValue = "name") String ordernarPor,
            @RequestParam(value = "direcao", defaultValue = "ASC") String direcao) {
        Page<Categoria> categorias = categoriaService.buscarPagina(pagina, linhasPorPagina, ordernarPor, direcao);
        Page<CategoriaDTO> categoriaDTO = categorias.map(item -> new CategoriaDTO(item));

        return ResponseEntity.ok().body(categoriaDTO);
    }
}

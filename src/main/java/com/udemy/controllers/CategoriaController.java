package com.udemy.controllers;

import com.udemy.domain.Categoria;
import com.udemy.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.udemy.services.CategoriaService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> Find(@PathVariable Integer id) {
        Categoria categoria = categoriaService.buscarCategoriaPorId(id);
        return ResponseEntity.ok().body(categoria);
    }

    @GetMapping(path = "/listar")
    public ResponseEntity listarTodasCategorias() {
        return ResponseEntity.ok().body(categoriaService.listarTodasCategorias());

//        return ResponseEntity.ok().body(this.objectToString(categoriaService.listarTodasCategorias()));
    }

    public List<String> objectToString(List<Categoria> categorias) {
        List<String> nomeDasCategorias = new ArrayList<>();

        for (Categoria c : categorias) {
            nomeDasCategorias.add(c.getName());
        }

        return nomeDasCategorias;
    }
}

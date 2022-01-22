package com.udemy.services;

import com.udemy.domain.Categoria;
import com.udemy.repositories.CategoriaRepository;
import com.udemy.services.Exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria buscarCategoriaPorId(Integer id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado Id: " + id + ", Tipo: " + Categoria.class.getName()
        ));
    }

    public List<Categoria> listarTodasCategorias() {
        List<Categoria> listaCategorias = categoriaRepository.findAll();

        return listaCategorias;
    }
}

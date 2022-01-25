package com.udemy.services;

import com.udemy.domain.Categoria;
import com.udemy.dto.CategoriaDTO;
import com.udemy.repositories.CategoriaRepository;
import com.udemy.services.Exceptions.DataIntegrityException;
import com.udemy.services.Exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Categoria inserirCategoria(Categoria categoria) {
        categoria.setId(null);

        return categoriaRepository.save(categoria);
    }

    public Categoria editarCategoria(Categoria categoria) {
        this.buscarCategoriaPorId(categoria.getId());

        return categoriaRepository.save(categoria);
    }

    public void deletarCategoriaPorId(Integer id) {
        Categoria categoria = this.buscarCategoriaPorId(id);

        try {
            categoriaRepository.deleteById(categoria.getId());
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityException("Não é possível excluir uma categória que possui produtos");
        }
    }

    public Page<Categoria> buscarPagina(Integer pagina, Integer linhasPorPagina, String ordernarPor, String direcao) {
        PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direcao), ordernarPor);

        return categoriaRepository.findAll(pageRequest);
    }

    public Categoria converterCategoriaParaDTO(CategoriaDTO categoriaDTO) {
        return new Categoria(categoriaDTO.getId(), categoriaDTO.getName());
    }
}

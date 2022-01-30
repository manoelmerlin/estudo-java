package com.udemy.controllers;

import com.udemy.domain.Categoria;
import com.udemy.domain.Cliente;
import com.udemy.dto.CategoriaDTO;
import com.udemy.dto.ClienteDTO;
import com.udemy.dto.ClienteNovoDTO;
import com.udemy.services.ClientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    private ClientesService clientesService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer id) {
        Cliente cliente = clientesService.buscarClientePorId(id);
        return ResponseEntity.ok().body(cliente);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> salvarCliente(@Valid @RequestBody ClienteNovoDTO clienteNovoDTO) {
        Cliente cliente = clientesService.converterClientNovoParaDTO(clienteNovoDTO);
        cliente = clientesService.salvarCliente(cliente);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> atualizarCliente(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) {
        Cliente cliente = clientesService.converterClienteParaDTO(clienteDTO);
        cliente.setId(id);
        clientesService.atualizarCliente(cliente);

        return ResponseEntity.noContent().build();
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletarCliente(@PathVariable Integer id) {
        clientesService.deletarClientePorId(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<ClienteDTO>> listarTodosClientes() {
        List<Cliente> clientes = clientesService.listarTodosClientes();
        List<ClienteDTO> ClienteDTO = clientes.stream().map(item -> new ClienteDTO(item)).collect(Collectors.toList());

        return ResponseEntity.ok().body(ClienteDTO);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<ClienteDTO>> buscarPagina(
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
            @RequestParam(value = "ordernarPor", defaultValue = "id") String ordernarPor,
            @RequestParam(value = "direcao", defaultValue = "ASC") String direcao) {
        Page<Cliente> clientes = clientesService.buscarPagina(pagina, linhasPorPagina, ordernarPor, direcao);
        Page<ClienteDTO> clienteDTO = clientes.map(item -> new ClienteDTO(item));

        return ResponseEntity.ok().body(clienteDTO);
    }
}

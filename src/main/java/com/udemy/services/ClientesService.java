package com.udemy.services;

import com.udemy.domain.Cidade;
import com.udemy.domain.Cliente;
import com.udemy.domain.Endereco;
import com.udemy.domain.Enums.Perfil;
import com.udemy.domain.Enums.TipoCliente;
import com.udemy.dto.ClienteDTO;
import com.udemy.dto.ClienteNovoDTO;
import com.udemy.repositories.ClienteRepository;
import com.udemy.repositories.EnderecoRepository;
import com.udemy.security.UsuarioSS;
import com.udemy.services.Exceptions.AuthorizationException;
import com.udemy.services.Exceptions.DataIntegrityException;
import com.udemy.services.Exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientesService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Cliente buscarClientePorId(Integer id) {
        UsuarioSS usuarioLogado = UsuarioService.getUsuarioLogado();

        if (usuarioLogado.getId() != id && !usuarioLogado.HasRole(Perfil.ADMIN)) {
            throw new AuthorizationException("Você não está autorizado a acessar está área");
        }

        Optional<Cliente> obj = clienteRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
   }

    public Cliente converterClienteParaDTO(ClienteDTO clienteDTO) {
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);
    }

    @Transactional
    public Cliente salvarCliente(Cliente cliente) {
        cliente.setId(null);
        clienteRepository.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());

        return cliente;
    }

    public Cliente converterClientNovoParaDTO(ClienteNovoDTO clienteNovoDTO) {
        Cliente cliente = new Cliente(null, clienteNovoDTO.getNome(), clienteNovoDTO.getEmail(),
                clienteNovoDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNovoDTO.getTipo()), bCryptPasswordEncoder.encode(clienteNovoDTO.getSenha()));

        Cidade cidade = new Cidade(clienteNovoDTO.getCidadeId(), null, null);

        Endereco endereco = new Endereco(null, clienteNovoDTO.getLogradouro(), clienteNovoDTO.getNumero(),
                clienteNovoDTO.getComplemento(), clienteNovoDTO.getBairro(), clienteNovoDTO.getCep(), cliente, cidade);

        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(clienteNovoDTO.getTelefone1());

        if (clienteNovoDTO.getTelefone2() != null) {
            cliente.getTelefones().add(clienteNovoDTO.getTelefone2());
        }

        if (clienteNovoDTO.getTelefone3() != null) {
            cliente.getTelefones().add(clienteNovoDTO.getTelefone3());
        }

        return cliente;
    }

    public Cliente atualizarCliente(Cliente cliente) {
        Cliente clienteParaAtualizar = this.buscarClientePorId(cliente.getId());

        clienteParaAtualizar.setNome(cliente.getNome());
        clienteParaAtualizar.setEmail(cliente.getEmail());

        return clienteRepository.save(clienteParaAtualizar);
    }

    public void deletarClientePorId(Integer id) {
        Cliente cliente = this.buscarClientePorId(id);

        try {
            clienteRepository.deleteById(cliente.getId());
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityException("Não é possível excluir porque existem entidades relacionadas");
        }
    }
    public Page<Cliente> buscarPagina(Integer pagina, Integer linhasPorPagina, String ordernarPor, String direcao) {
        PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direcao), ordernarPor);

        return clienteRepository.findAll(pageRequest);
    }

    public List<Cliente> listarTodosClientes() {
        List<Cliente> listaClientes = clienteRepository.findAll();

        return listaClientes;
    }
}

package com.udemy.dto;

import com.udemy.domain.Cliente;
import com.udemy.services.validation.ClienteAtualizacao;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClienteAtualizacao
public class ClienteDTO implements Serializable {
    private Integer id;

    @NotEmpty(message = "Mensagem obrigatória")
    @Length(min=5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private String nome;

    @Email(message = "Email inválido")
    private String email;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente cliente) {
        id = cliente.getId();
        nome = cliente.getNome();
        email = cliente.getEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.udemy.dto;

import com.udemy.services.validation.ClienteInsercao;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClienteInsercao
public class ClienteNovoDTO implements Serializable {

    @NotEmpty(message = "Mensagem obrigatória")
    @Length(min=5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private String nome;

    @NotEmpty(message = "Mensagem obrigatória")
    @Email(message = "Email inválido")
    private String email;

    @NotEmpty(message = "Mensagem obrigatória")
    private String cpfOuCnpj;
    private Integer tipo;

    @NotEmpty(message = "Mensagem obrigatória")
    private String logradouro;

    @NotEmpty(message = "Mensagem obrigatória")
    private String numero;
    private String complemento;
    private String bairro;

    @NotEmpty(message = "Mensagem obrigatória")
    private String cep;

    @NotEmpty(message = "Mensagem obrigatória")
    private String telefone1;
    private String Telefone2;
    private String telefone3;

    private Integer cidadeId;

    public ClienteNovoDTO() {
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

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return Telefone2;
    }

    public void setTelefone2(String telefone2) {
        Telefone2 = telefone2;
    }

    public Integer getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(Integer cidadeId) {
        this.cidadeId = cidadeId;
    }

    public String getTelefone3() {
        return telefone3;
    }

    public void setTelefone3(String telefone3) {
        this.telefone3 = telefone3;
    }
}

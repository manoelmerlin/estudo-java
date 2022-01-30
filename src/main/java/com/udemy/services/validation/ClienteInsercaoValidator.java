package com.udemy.services.validation;

import com.udemy.controllers.ExceptionHandler.FieldMessage;
import com.udemy.domain.Enums.TipoCliente;
import com.udemy.dto.ClienteNovoDTO;
import com.udemy.repositories.ClienteRepository;
import com.udemy.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClienteInsercaoValidator implements ConstraintValidator<ClienteInsercao, ClienteNovoDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInsercao ann) {
    }

    @Override
    public boolean isValid(ClienteNovoDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCodigo()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
            System.out.println("Caiu na validacao de cpf");
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
            System.out.println("Caiu na validacao de cnpj");

            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        if (clienteRepository.findByEmail(objDto.getEmail()) != null) {
            list.add(new FieldMessage("email", "O email já existe meu caro abigo"));
        }

        // inclua os testes aqui, inserindo erros na lista

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }

        return list.isEmpty();
    }
}
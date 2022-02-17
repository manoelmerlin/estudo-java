package com.udemy.services;

import com.udemy.security.UsuarioSS;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    public static UsuarioSS getUsuarioLogado() {
        try {
            return (UsuarioSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}

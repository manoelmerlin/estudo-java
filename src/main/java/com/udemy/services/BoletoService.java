package com.udemy.services;

import com.udemy.domain.PagamentoBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {

    public void preencherPagamentoComBoleto(PagamentoBoleto pagamentoBoleto, Date instantePedido) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(instantePedido);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        pagamentoBoleto.setDataVencimento(calendar.getTime());
    }
}

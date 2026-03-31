package com.example.demo.model.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CantorAsyncServiceMaria {

    @Async
    public void processarNovoCantor(String nome) {
        try {
            System.out.println("Iniciando processamento assíncrono do cantor: " + nome);

            Thread.sleep(5000);

            System.out.println("Processamento concluído para o cantor: " + nome);
        } catch (InterruptedException e) {
            System.out.println("Erro no processamento assíncrono: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
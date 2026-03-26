package com.example.demo.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CantorAsyncService {

    @Async
    public void processarPosCadastro(Long cantorId) {
        try {
            System.out.println("Iniciando processamento do cantor ID: " + cantorId);

            Thread.sleep(3000);

            System.out.println("Processamento concluído para cantor ID: " + cantorId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Erro no processamento do cantor ID: " + cantorId);
        }
    }
}

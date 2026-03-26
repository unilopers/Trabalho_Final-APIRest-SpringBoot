package com.example.demo.model.service;

import com.example.demo.async.CantorAsyncService;
import com.example.demo.model.entity.Cantor;
import com.example.demo.model.entity.Contato;
import com.example.demo.model.entity.Genero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.repository.CantorRepository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CantorService {

    @Autowired
    private CantorAsyncService cantorAsyncService;

    @Autowired
    private CantorRepository cantorRepository;

    public void criarCantor(@RequestBody Cantor cantor) {
        Cantor salvo = cantorRepository.save(cantor);

        cantorAsyncService.processarPosCadastro(salvo.getId());
    }

    public List<Cantor> getCantores() {
        Iterable<Cantor> cantores = cantorRepository.findAll();
        return (List<Cantor>) cantores;
    }

    public boolean deletarCantorPorId(Long id) {
        try {
            cantorRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void atualizarCantor(Long id, Cantor dadosNovos) throws Exception {
        Cantor cantor = cantorRepository.findById(id)
                .orElseThrow(() -> new Exception("Cantor não encontrado"));

        if (dadosNovos.getNome() != null)
            cantor.setNome(dadosNovos.getNome());

        if (dadosNovos.getNacionalidade() != null)
            cantor.setNacionalidade(dadosNovos.getNacionalidade());

        if (dadosNovos.getPerfil() != null) {
            if (cantor.getPerfil() == null) {
                cantor.setPerfil(dadosNovos.getPerfil());
            } else {
                if (dadosNovos.getPerfil().getTipo() != null)
                    cantor.getPerfil().setTipo(dadosNovos.getPerfil().getTipo());

                if (dadosNovos.getPerfil().getPerfil() != null)
                    cantor.getPerfil().setPerfil(dadosNovos.getPerfil().getPerfil());
            }
        }

        if (dadosNovos.getContatos() != null) {
            for (Contato novoContato : dadosNovos.getContatos()) {


                if (novoContato.getId() != null && novoContato.getTipo() == null && novoContato.getContato() == null) {
                    cantor.getContatos().removeIf(c -> c.getId().equals(novoContato.getId()));
                    continue;
                }

                if (novoContato.getId() != null) {
                    cantor.getContatos().stream()
                            .filter(c -> c.getId().equals(novoContato.getId()))
                            .findFirst()
                            .ifPresent(contatoExistente -> {
                                if (novoContato.getTipo() != null)
                                    contatoExistente.setTipo(novoContato.getTipo());
                                if (novoContato.getContato() != null)
                                    contatoExistente.setContato(novoContato.getContato());
                            });

                    continue;
                }

                if (novoContato.getId() == null) {
                    cantor.getContatos().add(novoContato);
                }
            }
        }

        if (dadosNovos.getGeneros() != null) {
            for (Genero genNovo : dadosNovos.getGeneros()) {

                if (genNovo.getId() != null && genNovo.getGenero() == null) {
                    cantor.getGeneros().removeIf(g -> g.getId().equals(genNovo.getId()));
                    continue;
                }

                if (genNovo.getId() != null) {
                    cantor.getGeneros().stream()
                            .filter(g -> g.getId().equals(genNovo.getId()))
                            .findFirst()
                            .ifPresent(generoExistente -> {
                                if (genNovo.getGenero() != null)
                                    generoExistente.setGenero(genNovo.getGenero());
                            });
                    continue;
                }

                if (genNovo.getId() == null) {
                    cantor.getGeneros().add(genNovo);
                }
            }
        }
        cantorRepository.save(cantor);
    }
}

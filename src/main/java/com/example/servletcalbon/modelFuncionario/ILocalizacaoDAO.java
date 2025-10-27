package com.example.servletcalbon.modelFuncionario;

import java.util.List;
import java.util.Optional;

public interface ILocalizacaoDAO {
    Localizacao save(Localizacao localizacao);
    Localizacao update(Localizacao localizacao);
    void delete(Long id);
    List<Localizacao> findAll();
    Optional<Localizacao> findById(Long id);
}

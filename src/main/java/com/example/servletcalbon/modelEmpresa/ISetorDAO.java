package com.example.servletcalbon.modelEmpresa;

import java.util.List;
import java.util.Optional;

public interface ISetorDAO {
    Setor save(Setor setor);
    Setor update(Setor setor);
    void delete(int id);
    List<Setor> findAll();
    Optional<Setor> findById(int id);
    Optional<Setor> findByNome(String nome);
}

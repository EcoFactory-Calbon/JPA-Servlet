package com.example.servletcalbon.modelEmpresa;

import java.util.List;
import java.util.Optional;

public interface ICategoriaEmpresa {
    CategoriaEmpresa save(CategoriaEmpresa categoria);
    CategoriaEmpresa update(CategoriaEmpresa categoria);
    void delete(Long id);
    List<CategoriaEmpresa> findAll();

    static Optional<CategoriaEmpresa> findById(Long id) {
        return null;
    }
}

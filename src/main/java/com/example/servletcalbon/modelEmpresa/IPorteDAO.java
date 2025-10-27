package com.example.servletcalbon.modelEmpresa;

import java.util.List;
import java.util.Optional;

public interface IPorteDAO {
    Porte save(Porte porte);
    Porte update(Porte porte);
    void delete(Long id);
    List<Porte> findAll();
    Optional<Porte> findById(Long id);
}

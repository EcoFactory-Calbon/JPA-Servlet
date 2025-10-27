package com.example.servletcalbon.modelEmpresa;

import java.util.List;
import java.util.Optional;

public interface IEmpresaDAO {
    Empresa save(Empresa empresa);
    Empresa update(Empresa empresa);
    void delete(String cnpj);
    List<Empresa> findAll();
    Optional<Empresa> findById(String cnpj);
}
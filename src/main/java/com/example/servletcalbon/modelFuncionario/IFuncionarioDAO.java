package com.example.servletcalbon.modelFuncionario;

import java.util.List;
import java.util.Optional;

public interface IFuncionarioDAO {
    Funcionario save(Funcionario funcionario);
    Funcionario update(Funcionario funcionario);
    void delete(Long id);
    List<Funcionario> findAll();
    Optional<Funcionario> findById(Long id);
}
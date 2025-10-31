package com.example.servletcalbon.modelFuncionario;

import java.util.List;
import java.util.Optional;

public interface ICargoDAO {
    Cargo save(Cargo cargo);
    Cargo update(Cargo cargo);
    void delete(int id);
    List<Cargo> findAll();
    Optional<Cargo> findById(int id);
    Optional<Cargo> findByNome(String nome);
}

package calbon.modelEmpresa;

import java.util.List;
import java.util.Optional;

public interface IEmpresaDAO {
    Empresa save(Empresa empresa);
    Empresa update(Empresa empresa);
    void delete(Long id);
    List<Empresa> findAll();
    Optional<Empresa> findById(Long id);
}
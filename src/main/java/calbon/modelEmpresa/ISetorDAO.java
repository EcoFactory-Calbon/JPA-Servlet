package calbon.modelEmpresa;

import java.util.List;
import java.util.Optional;

public interface ISetorDAO {
    Setor save(Setor setor);
    Setor update(Setor setor);
    void delete(Long id);
    List<Setor> findAll();
    Optional<Setor> findById(Long id);
}

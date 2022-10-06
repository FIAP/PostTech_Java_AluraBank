package br.com.alura.alurabank.repositorio;

import br.com.alura.alurabank.dominio.Correntista;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface CorrentistaRepository extends Repository<Correntista, Integer> {

    void save(Correntista correntista);

    Optional<Correntista> findByExternalId(String externalId);

    Optional<Correntista> findById(Integer correntistaId);
}

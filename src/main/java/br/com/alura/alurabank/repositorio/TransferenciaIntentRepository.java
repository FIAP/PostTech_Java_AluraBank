package br.com.alura.alurabank.repositorio;

import br.com.alura.alurabank.dominio.TransferenciaIntent;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface TransferenciaIntentRepository extends Repository<TransferenciaIntent, Integer> {
    void save(TransferenciaIntent intent);

    Optional<TransferenciaIntent> findByHash(String hash);
    Optional<TransferenciaIntent> findById(Integer id);
}

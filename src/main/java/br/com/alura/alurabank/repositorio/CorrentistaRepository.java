package br.com.alura.alurabank.repositorio;

import br.com.alura.alurabank.dominio.Correntista;
import org.springframework.data.repository.Repository;

public interface CorrentistaRepository extends Repository<Correntista, Integer> {

    void save(Correntista correntista);

}

package br.com.alura.alurabank.repositorio;

import br.com.alura.alurabank.dominio.ContaCorrente;
import br.com.alura.alurabank.dominio.Correntista;
import br.com.alura.alurabank.dominio.DadosDaConta;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.Repository;


import java.util.Optional;

public interface RepositorioContasCorrente extends Repository<ContaCorrente, Integer> {

    void save(ContaCorrente novaConta);

    @EntityGraph(value = "pipoca")
    Optional<ContaCorrente> findByDadosDaConta(DadosDaConta dadosDaConta);

    Optional<ContaCorrente> findByCorrentista(Correntista correntista);

    void delete(ContaCorrente conta);

}

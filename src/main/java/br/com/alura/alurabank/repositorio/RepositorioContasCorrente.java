package br.com.alura.alurabank.repositorio;

import br.com.alura.alurabank.dominio.ContaCorrente;
import br.com.alura.alurabank.dominio.DadosDaConta;
import org.springframework.data.repository.Repository;


import java.util.Optional;

public interface RepositorioContasCorrente extends Repository<ContaCorrente, Integer> {

    void save(ContaCorrente novaConta);

    Optional<ContaCorrente> findByDadosDaConta(DadosDaConta dadosDaConta);

    void delete(ContaCorrente conta);

}

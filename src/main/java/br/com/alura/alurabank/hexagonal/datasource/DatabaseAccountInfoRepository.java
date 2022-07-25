package br.com.alura.alurabank.hexagonal.datasource;

import br.com.alura.alurabank.dominio.ContaCorrente;
import br.com.alura.alurabank.dominio.DadosDaConta;
import br.com.alura.alurabank.hexagonal.core.entities.AccountInfo;
import br.com.alura.alurabank.hexagonal.core.repository.AccountInfoRepository;
import br.com.alura.alurabank.repositorio.ContasCorrenteRepository;
import br.com.alura.alurabank.repositorio.CorrentistaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DatabaseAccountInfoRepository implements AccountInfoRepository {

    private final CorrentistaRepository correntistaRepository;
    private final ContasCorrenteRepository contasCorrenteRepository;

    private final DadosDaContaToAccountInfo dadosDaContaConverter;

    public DatabaseAccountInfoRepository(CorrentistaRepository correntistaRepository, ContasCorrenteRepository contasCorrenteRepository, DadosDaContaToAccountInfo dadosDaContaConverter) {
        this.correntistaRepository = correntistaRepository;
        this.contasCorrenteRepository = contasCorrenteRepository;
        this.dadosDaContaConverter = dadosDaContaConverter;
    }

    @Override
    public Optional<AccountInfo> findBySubject(String id) {
        Optional<DadosDaConta> dadosDaConta = correntistaRepository
                .findByExternalId(id)
                .flatMap(contasCorrenteRepository::findByCorrentista)
                .map(ContaCorrente::getDadosDaConta);

        return dadosDaConta.map(dadosDaContaConverter::convert);
    }
}

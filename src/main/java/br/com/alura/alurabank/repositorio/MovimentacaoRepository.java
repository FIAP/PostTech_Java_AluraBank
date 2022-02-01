package br.com.alura.alurabank.repositorio;

import br.com.alura.alurabank.dominio.DadosDaConta;
import br.com.alura.alurabank.dominio.MovimentacaoDeConta;
import br.com.alura.alurabank.dominio.Operacao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MovimentacaoRepository extends Repository<MovimentacaoDeConta, Integer> {

    @Query("select m from ContaCorrente cc join cc.movimentacoes m  where cc.dadosDaConta = :dadosDaConta and m.operacao = :operacao and m.data between :inicio and :fim")
    List<MovimentacaoDeConta> filterAllByDadosDaContaAndOperacaoAndDataDeCriacaoBetween(@Param("dadosDaConta") DadosDaConta dadosDaConta,
                                                                                        @Param("operacao") Operacao operacao,
                                                                                        @Param("inicio") LocalDateTime inicio,
                                                                                        @Param("fim") LocalDateTime fim);


    @Query("select m from ContaCorrente cc join cc.movimentacoes m  where cc.dadosDaConta = :dadosDaConta")
    List<MovimentacaoDeConta> filterAllByDadosDaConta(@Param("dadosDaConta") DadosDaConta dadosDaConta);


    void save(MovimentacaoDeConta movimentacaoDeConta);
}

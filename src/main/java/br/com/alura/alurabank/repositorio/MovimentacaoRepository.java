package br.com.alura.alurabank.repositorio;

import br.com.alura.alurabank.dominio.ContaCorrente;
import br.com.alura.alurabank.dominio.DadosDaConta;
import br.com.alura.alurabank.dominio.MovimentacaoDeConta;
import br.com.alura.alurabank.dominio.Operacao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimentacaoRepository extends Repository<MovimentacaoDeConta, Integer> {
    List<MovimentacaoDeConta> findAllByConta(ContaCorrente conta);

    @Query("SELECT m FROM  MovimentacaoDeConta m JOIN m.conta cc WHERE  cc.dadosDaConta = :dadosDaConta AND m.operacao = :operacao AND m.data BETWEEN  :inicio AND :fim")
    List<MovimentacaoDeConta> filterMovimentacoesBy(@Param("dadosDaConta") DadosDaConta dadosDaConta, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim, @Param("operacao") Operacao operacao);

    void save(MovimentacaoDeConta movimentacao);
}

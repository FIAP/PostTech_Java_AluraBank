package br.com.alura.alurabank.service;

import br.com.alura.alurabank.controller.exceptions.SaldoInsuficienteException;
import br.com.alura.alurabank.controller.form.MovimentacaoForm;
import br.com.alura.alurabank.converters.DadosDaContaCoverter;
import br.com.alura.alurabank.converters.ExtratoConverter;
import br.com.alura.alurabank.dominio.*;
import br.com.alura.alurabank.factories.ContaFactory;
import br.com.alura.alurabank.repositorio.ContasCorrenteRepository;
import br.com.alura.alurabank.repositorio.CorrentistaRepository;
import br.com.alura.alurabank.repositorio.MovimentacaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    private static final String BANCO = "1234";
    private static final String AGENCIA = "1234";
    private static final String NUMERO = "1234";
    private static final DadosDaConta DADOS_DA_CONTA = new DadosDaConta(BANCO, AGENCIA, NUMERO);
    private static final Correntista CORRENTISTA = new Correntista("12341234", "Fulano", "email@example.com");
    private static final ContaCorrente CONTA_CORRENTE = new ContaCorrente(BANCO, AGENCIA, NUMERO, CORRENTISTA);


    @Mock
    private ContasCorrenteRepository contasCorrenteRepository ;
    @Mock
    private CorrentistaRepository correntistaRepository;
    @Mock
    private MovimentacaoRepository movimentacaoRepository;
    @Mock
    private ContaFactory factory;
    @Mock
    private DadosDaContaCoverter dadosDaContaConverter;
    @Mock
    private ExtratoConverter extratoConverter;

    @Captor
    private ArgumentCaptor<MovimentacaoDeConta> movimentacaoCaptor;

    @InjectMocks
    private ContaService service;



    @Test
    void deveSalvarUmaMovimentacaoDeDepositoQuandoAContaExistir() {

        MovimentacaoForm form = new MovimentacaoForm();
        form.setValor(BigDecimal.valueOf(10));
        form.setOperacao(Operacao.DEPOSITO);
        form.setDadosDaConta(DADOS_DA_CONTA);

        when(contasCorrenteRepository.findByDadosDaConta(DADOS_DA_CONTA))
                .thenReturn(Optional.of(CONTA_CORRENTE));

        service.movimentar(form);

        verify(movimentacaoRepository, times(1))
                .save(movimentacaoCaptor.capture());
        verify(contasCorrenteRepository, times(1))
                .findByDadosDaConta(DADOS_DA_CONTA);

        MovimentacaoDeConta movimentacaoDeConta = movimentacaoCaptor.getValue();

        Assertions.assertEquals(CONTA_CORRENTE, movimentacaoDeConta.getConta() );
        Assertions.assertEquals(form.getValor(), movimentacaoDeConta.getValor());

        verificaQueNaoTemInteracoesDesnecessariasAoMovimentar();
    }

    @Test
    void deveLancarUmErroAoEfetuarUmaMovimentacaoDeSaqueSemSaldoSuficiente() {
        MovimentacaoForm form = new MovimentacaoForm();
        form.setValor(BigDecimal.valueOf(10));
        form.setOperacao(Operacao.SAQUE);
        form.setDadosDaConta(DADOS_DA_CONTA);

        when(contasCorrenteRepository.findByDadosDaConta(DADOS_DA_CONTA))
                .thenReturn(Optional.of(CONTA_CORRENTE));

        when(movimentacaoRepository.findAllByConta(CONTA_CORRENTE))
                .thenReturn(List.of());

        Assertions.assertThrows(SaldoInsuficienteException.class, () -> service.movimentar(form));

        verify(movimentacaoRepository, never()).save(any());
        verify(contasCorrenteRepository, times(1))
                .findByDadosDaConta(DADOS_DA_CONTA);
        verify(movimentacaoRepository, times(1))
                .findAllByConta(CONTA_CORRENTE);
        verificaQueNaoTemInteracoesDesnecessariasAoMovimentar();
    }

    private void verificaQueNaoTemInteracoesDesnecessariasAoMovimentar() {
        verifyNoMoreInteractions(contasCorrenteRepository, movimentacaoRepository);
        verifyNoInteractions(extratoConverter,dadosDaContaConverter, factory, correntistaRepository);
    }

}

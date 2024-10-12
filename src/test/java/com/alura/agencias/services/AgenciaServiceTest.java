package com.alura.agencias.services;

import com.alura.agencias.domain.Agencia;
import com.alura.agencias.exception.AgenciaNaoAtivaOuNaoEncontradaException;
import com.alura.agencias.repository.AgenciaRepository;
import com.alura.agencias.service.AgenciaService;
import com.alura.agencias.service.http.SituacaoCadastralHttpService;
import com.alura.agencias.utils.AgenciaFixture;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
public class AgenciaServiceTest {

    @InjectMock
    @RestClient
    private SituacaoCadastralHttpService situacaoCadastralHttpService;

    @InjectMock
    private AgenciaRepository agenciaRepository;

    @Inject
    private AgenciaService agenciaService;

    private Agencia agencia;

    @BeforeEach
    public void setUp() {
        agencia = AgenciaFixture.criarAgencia();
        Mockito.doNothing().when(agenciaRepository).persist(Mockito.any(Agencia.class));
    }

    @Test
    public void deveNaoCadastrarQuandoClientRetornarNull() {
        Mockito.when(situacaoCadastralHttpService.buscarPorCnpj("123")).thenReturn(null);

        Assertions.assertThrows(AgenciaNaoAtivaOuNaoEncontradaException.class, () -> agenciaService.cadastrar(agencia));

        Mockito.verify(agenciaRepository, Mockito.never()).persist(agencia);
    }

    @Test
    public void deveCadastrarQuandoClientRetornarSituacaoCadastralAtivo() {
        Mockito.when(situacaoCadastralHttpService.buscarPorCnpj("123")).thenReturn(AgenciaFixture.criarAgenciaHttp());

        agenciaService.cadastrar(agencia);

        Mockito.verify(agenciaRepository).persist(agencia);
    }
}

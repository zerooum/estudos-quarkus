package com.alura.agencias.service;

import com.alura.agencias.domain.Agencia;
import com.alura.agencias.domain.http.AgenciaHttp;
import com.alura.agencias.domain.http.SituacaoCadastral;
import com.alura.agencias.exception.AgenciaNaoAtivaException;
import com.alura.agencias.exception.AgenciaNaoEncontradaException;
import com.alura.agencias.repository.AgenciaRepository;
import com.alura.agencias.service.http.SituacaoCadastralHttpService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class AgenciaService {

    private final AgenciaRepository agenciaRepository;

    AgenciaService(AgenciaRepository agenciaRepository) {
        this.agenciaRepository = agenciaRepository;
    }

    @RestClient
    SituacaoCadastralHttpService situacaoCadastralHttpService;

    public void cadastrar(Agencia agencia) {
            try {
                AgenciaHttp agenciaHttp = situacaoCadastralHttpService.buscarPorCnpj(agencia.getCnpj());
                if(agenciaHttp.getSituacaoCadastral().equals(SituacaoCadastral.ATIVO)) {
                    Log.info("Agencia com CNPJ " + agencia.getCnpj() + " foi adicionada");
                    agenciaRepository.persist(agencia);
                } else {
                    Log.info("Agencia com CNPJ" + agencia.getCnpj() + " não ativa");
                    throw new AgenciaNaoAtivaException();
                }
            } catch (AgenciaNaoAtivaException | AgenciaNaoEncontradaException e) {
                Log.warn("Agência não cadastrada");
                throw e;
            }
    }

    public Agencia buscarPorId(Long id) {
        return agenciaRepository.findById(id);
    }

    public void deletar(Long id) {
        Log.info("A ageência foi deletada");
        agenciaRepository.deleteById(id);
    }

    public void alterar(Agencia agencia) {
        Log.info("A agência com CNPJ " + agencia.getCnpj() + " foi alterada");
        agenciaRepository.update("nome = ?1, razaoSocial = ?2, cnpj = ?3 where id = ?4", agencia.getNome(), agencia.getRazaoSocial(), agencia.getCnpj(), agencia.getId());
    }
}

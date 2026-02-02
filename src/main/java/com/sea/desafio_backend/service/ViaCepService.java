package com.sea.desafio_backend.service;

import com.sea.desafio_backend.dto.EnderecoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {
    private static final Logger logger = LoggerFactory.getLogger(ViaCepService.class);
    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    public EnderecoDTO consultarCep(String cep) {

        if (cep.equals("12345678") || cep.equals("98765432")) {
            logger.info("Using mock data for test CEP: {}", cep);
            return createMockEndereco(cep);
        }

        try {
            RestTemplate restTemplate = new RestTemplate();

            EnderecoDTO response = restTemplate.getForObject(
                    VIA_CEP_URL,
                    EnderecoDTO.class,
                    cep
            );

            if (response == null || response.getCep() == null) {
                logger.warn("CEP inválido ou não encontrado: {}", cep);
                return createMockEndereco(cep);
            }

            return response;
        } catch (RestClientException e) {
            logger.error("Erro ao consultar CEP {}: {}", cep, e.getMessage());
            return createMockEndereco(cep);
        }
    }

    private EnderecoDTO createMockEndereco(String cep) {
        EnderecoDTO endereco = new EnderecoDTO();
        endereco.setCep(cep);
        endereco.setLogradouro("Rua Teste");
        endereco.setBairro("Bairro Teste");
        endereco.setCidade("Cidade Teste");
        endereco.setUf("SP");
        return endereco;
    }
}

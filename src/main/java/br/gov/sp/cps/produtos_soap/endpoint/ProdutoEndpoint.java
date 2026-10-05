package br.gov.sp.cps.produtos_soap.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import br.gov.sp.cps.produtos_soap.model.ConsultarProdutoRequest;
import br.gov.sp.cps.produtos_soap.model.ConsultarProdutoResponse;

@Endpoint
public class ProdutoEndpoint {

    private static final String NAMESPACE = "http://cps.sp.gov.br/produtos";

    @PayloadRoot(namespace = NAMESPACE, localPart = "consultarProdutoRequest")
    @ResponsePayload
    public ConsultarProdutoResponse consultarProduto(@RequestPayload ConsultarProdutoRequest request) {

        ConsultarProdutoResponse response = new ConsultarProdutoResponse();

        switch (request.getCodigo()) {
            case 101:
                response.setNome("Fone de Ouvido Bluetooth");
                response.setDescricao("Fone sem fio com cancelamento de ruído e bateria de 30 horas");
                response.setMarca("JBL");
                response.setEstoque(18);
                break;
            case 102:
                response.setNome("Cafeteira Elétrica");
                response.setDescricao("Cafeteira de 15 xícaras com filtro permanente");
                response.setMarca("Mondial");
                response.setEstoque(12);
                break;
            case 103:
                response.setNome("Garrafa Térmica");
                response.setDescricao("Garrafa de aço inox de 1 litro que mantém a temperatura por 12 horas");
                response.setMarca("Termolar");
                response.setEstoque(55);
                break;
            default:
                response.setNome("Produto não encontrado");
                response.setDescricao("-");
                response.setMarca("-");
                response.setEstoque(0);
        }

        return response;
    }
}
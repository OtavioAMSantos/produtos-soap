# Produtos SOAP

Web Service SOAP feito com Java e Spring Boot para a atividade da aula 5. O serviço recebe o código de um produto e devolve o nome, a descrição, a marca e a quantidade em estoque.

O projeto segue a abordagem *Contract First*: primeiro escrevi o contrato (`produtos.xsd`), depois o Maven gerou as classes Java e por último implementei o endpoint.

## Tecnologias

- Java 17
- Spring Boot
- Spring Web Services
- Maven
- XSD / WSDL / SOAP
- SoapUI (para testar)

## Como executar

Na pasta do projeto, rode:

```powershell
.\mvnw spring-boot:run
```

O servidor sobe na porta 8080. O WSDL fica disponível em:

```
http://localhost:8080/ws/produtos.wsdl
```

## Produtos cadastrados

| Código | Nome                     | Marca    | Estoque |
|--------|--------------------------|----------|---------|
| 101    | Fone de Ouvido Bluetooth | JBL      | 18      |
| 102    | Cafeteira Elétrica       | Mondial  | 12      |
| 103    | Garrafa Térmica          | Termolar | 55      |

Qualquer outro código retorna "Produto não encontrado" com estoque 0.

## Exemplo de requisição

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:prod="http://cps.sp.gov.br/produtos">
   <soapenv:Header/>
   <soapenv:Body>
      <prod:consultarProdutoRequest>
         <prod:codigo>101</prod:codigo>
      </prod:consultarProdutoRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

## Exemplo de resposta

```xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
   <SOAP-ENV:Header/>
   <SOAP-ENV:Body>
      <ns2:consultarProdutoResponse xmlns:ns2="http://cps.sp.gov.br/produtos">
         <ns2:nome>Fone de Ouvido Bluetooth</ns2:nome>
         <ns2:descricao>Fone sem fio com cancelamento de ruído e bateria de 30 horas</ns2:descricao>
         <ns2:marca>JBL</ns2:marca>
         <ns2:estoque>18</ns2:estoque>
      </ns2:consultarProdutoResponse>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

## Estrutura

- `produtos.xsd`: define os dados de entrada e saída
- `WebServiceConfig`: publica o serviço em `/ws` e gera o WSDL
- `ProdutoEndpoint`: recebe a requisição e monta a resposta

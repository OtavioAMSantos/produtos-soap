# Produtos SOAP

Web Service SOAP feito com Java e Spring Boot para a atividade da aula 5, atualizado na atividade 2 com uma camada de segurança (WS-Security), tratamento de exceção e SOAP Fault. O serviço recebe o código de um produto e devolve o nome, a descrição, a marca e a quantidade em estoque.

O projeto segue a abordagem *Contract First*: primeiro escrevi o contrato (`produtos.xsd`), depois o Maven gerou as classes Java e por último implementei o endpoint.

## Tecnologias

- Java 17
- Spring Boot
- Spring Web Services
- WS-Security (WSS4J, UsernameToken)
- Maven
- XSD / WSDL / SOAP

## Como executar

Na pasta do projeto, rode:

```powershell
.\mvnw spring-boot:run
```

O servidor sobe na porta 8080. O WSDL fica disponível em:

```
http://localhost:8080/ws/produtos.wsdl
```

> É necessário ter o JDK 17 configurado (`JAVA_HOME`).

## Produtos cadastrados

| Código | Nome                     | Marca    | Estoque |
|--------|--------------------------|----------|---------|
| 101    | Fone de Ouvido Bluetooth | JBL      | 18      |
| 102    | Cafeteira Elétrica       | Mondial  | 12      |
| 103    | Garrafa Térmica          | Termolar | 55      |

Qualquer outro código retorna um **SOAP Fault** (`Produto com código X não encontrado`).

## Segurança

O serviço usa **WS-Security (UsernameToken)**. Toda requisição precisa enviar o usuário e a senha no header SOAP:

- Usuário: `admin`
- Senha: `123456`

As credenciais ficam no `application.properties` (`seguranca.usuario` e `seguranca.senha`). Requisições sem credenciais, ou com credenciais erradas, retornam um SOAP Fault de segurança.

## Tratamento de erros

- **Produto não encontrado:** o endpoint lança a `ProdutoNaoEncontradoException`, anotada com `@SoapFault`, que o Spring WS converte em um SOAP Fault do tipo `Client`.
- **Falha de autenticação:** o interceptor do WS-Security rejeita a requisição e devolve um SOAP Fault de segurança antes de chegar ao endpoint.

## Exemplo de requisição

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:prod="http://cps.sp.gov.br/produtos"
                  xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
   <soapenv:Header>
      <wsse:Security>
         <wsse:UsernameToken>
            <wsse:Username>admin</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">123456</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soapenv:Header>
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

## Exemplo de resposta de erro (produto não encontrado)

Requisição com `<prod:codigo>999</prod:codigo>`:

```xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
   <SOAP-ENV:Header/>
   <SOAP-ENV:Body>
      <SOAP-ENV:Fault>
         <faultcode>SOAP-ENV:Client</faultcode>
         <faultstring xml:lang="en">Produto com código 999 não encontrado</faultstring>
      </SOAP-ENV:Fault>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

## Estrutura

- `produtos.xsd`: define os dados de entrada e saída
- `WebServiceConfig`: publica o serviço em `/ws`, gera o WSDL e configura o WS-Security
- `ProdutoEndpoint`: recebe a requisição e monta a resposta
- `ProdutoNaoEncontradoException`: exceção convertida em SOAP Fault quando o produto não existe
- `application.properties`: usuário e senha do WS-Security

## Fluxo de trabalho (Git)

A atualização de segurança foi feita na branch `seguranca`, enviada para validação e depois mesclada na `main`.
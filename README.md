# ONG Adoção MVP

Protótipo de uma aplicação web para apresentar animais disponíveis para adoção e coletar informações iniciais de pessoas interessadas.

## Tecnologias configuradas

- Java 17
- Spring Boot 3.5.0
- Spring Web
- Thymeleaf
- Bean Validation
- Bootstrap 5.3.3, carregado por CDN
- CSS próprio para ajustes de estilo e alvos de toque

As dependências estão declaradas em `pom.xml`. O projeto ainda não configura banco de dados, Spring Data JPA ou Spring Security.

## Estado atual

- **Vitrine:** exibe os animais de exemplo Thor e Mel e permite filtrar por espécie.
- **Formulário de pré-triagem:** valida os campos e registra candidaturas em memória.
- **Confirmação:** mostra o animal e o protocolo da candidatura enviada.
- **Armazenamento:** animais e candidaturas ficam em memória e são perdidos ao reiniciar a aplicação. Os animais de exemplo estão definidos em `AdocaoService`.
- **Segurança:** autenticação e autorização ainda não estão implementadas.
- **Testes:** testes de integração cobrem o filtro, o envio e a confirmação, validação do formulário e animal inexistente.

As páginas carregam Bootstrap e imagens de animais por serviços externos (CDN do jsDelivr e Unsplash), portanto esses recursos dependem de conexão com a internet.

## Estrutura

```text
src/
├── main/
│   ├── java/com/ong/adocao/
│   │   ├── Application.java
│   │   ├── Controller.java
│   │   ├── AdocaoService.java
│   │   ├── Animal.java
│   │   ├── Candidatura.java
│   │   └── CandidaturaForm.java
│   └── resources/
│       ├── static/css/custom.css
│       ├── templates/
│       │   ├── vitrine.html
│       │   ├── formulario.html
│       │   └── confirmacao.html
│       └── application.properties
├── test/java/com/ong/adocao/ControllerTest.java
└── pom.xml
```

## Rotas implementadas

- `GET /` redireciona para a vitrine.
- `GET /vitrine` mostra os animais; `?especie=cao` e `?especie=gato` filtram a lista.
- `GET /adotar/{animalId}` abre o formulário do animal.
- `POST /adotar/{animalId}/candidaturas` valida e registra uma candidatura.
- `GET /candidaturas/{protocolo}/confirmacao` exibe a confirmação.

## Como executar

Requisitos: JDK 17 ou superior e Maven instalados e disponíveis no PATH.

Na pasta do projeto, execute:

```bash
mvn spring-boot:run
```

O Spring Boot usa a porta `8080` por padrão. Acesse `http://localhost:8080/` para abrir a vitrine.

Para executar os testes:

```bash
mvn test
```

## Próximos passos

1. Substituir o armazenamento em memória por persistência em banco de dados.
2. Adicionar autenticação e autorização para funcionalidades administrativas.
3. Expandir os testes conforme novas funcionalidades forem implementadas.

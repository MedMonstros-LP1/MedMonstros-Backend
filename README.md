# MedMonstros — Backend

API REST de agendamento medico para criaturas sobrenaturais.
Projeto da disciplina de Programacao Orientada a Objetos.

## Tecnologias

- Java 21
- Spring Boot 3
- Spring Data JPA + H2 (modo arquivo)
- Lombok
- BCrypt (spring-security-crypto)

## Estrutura

```
src/main/java/com/medmonstros/
├── entities/       # classes @Entity
├── enums/          # enumeracoes do dominio
├── repositories/   # Spring Data JPA
├── services/       # regras de negocio
├── controllers/    # endpoints REST
├── dtos/           # objetos de entrada/saida
├── exceptions/     # excecoes personalizadas
└── config/         # CORS, BCrypt, handler de excecoes
```

## Como rodar

Requer JDK 21 e Maven. Instale o plugin Lombok na sua IDE.

```bash
mvn spring-boot:run
```

- API: `http://localhost:8080/api`
- Console H2: `http://localhost:8080/h2-console`
  (URL `jdbc:h2:file:./data/medmonstros`, usuario `sa`, senha vazia)

O schema e gerado automaticamente pelo Hibernate a partir das classes `@Entity` — nao ha SQL manual.
Os dados ficam em `data/medmonstros.mv.db` e sobrevivem ao restart.

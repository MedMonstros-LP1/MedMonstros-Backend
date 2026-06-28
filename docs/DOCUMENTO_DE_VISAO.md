# Documento de Visao — MedMonstros

### Plataforma de Agendamento Medico para Criaturas Sobrenaturais

**Versao:** 1.3
**Data:** 25 de junho de 2026

---

## 1. Introducao

### 1.1 Proposito
Plataforma web para agendamento e gerenciamento de consultas medicas para criaturas
sobrenaturais, conectando pacientes monstruosos a medicos especializados. Serve tambem como
projeto academico de Programacao Orientada a Objetos (POO).

### 1.2 Escopo
O foco e a **qualidade da modelagem OO e das regras de negocio**. O sistema permitira:

- Autenticacao: cadastro de perfil e login com senha criptografada;
- Cadastro e gerenciamento de pacientes (por especie) e medicos;
- Cadastro de especialidades e associacao a medicos;
- Definicao e consulta de horarios disponiveis;
- Agendamento, aceitacao, recusa, cancelamento e realizacao de consultas;
- Registro de tratamentos.


---

## 2. Descricao do Problema
Criaturas sobrenaturais tem necessidades medicas especificas que sistemas convencionais nao
contemplam. O MedMonstros centraliza busca, agendamento e acompanhamento de consultas para
vampiros, lobisomens, fantasmas e afins, respeitando suas restricoes biologicas e magicas.

---

## 3. Objetivos

### 3.1 Geral
Desenvolver uma plataforma web de agendamento medico especializada para criaturas sobrenaturais,
demonstrando os principais conceitos de POO.

### 3.2 Especificos
- Autenticar usuarios (cadastro de perfil + login com senha criptografada);
- Cadastrar usuarios (pacientes por especie e medicos);
- Disponibilizar perfis medicos com especialidades;
- Gerenciar agendas e horarios;
- Controlar o ciclo de vida das consultas;
- Aplicar regras de negocio por especie via polimorfismo e excecoes;
- Registrar tratamentos polimorficos;
- Persistir informacoes entre execucoes do sistema.

---

## 4. Stakeholders

| Stakeholder | Descricao |
|---|---|
| Pacientes | Criaturas que se cadastram, fazem login e agendam consultas. |
| Medicos | Profissionais que se cadastram, ofertam horarios e realizam atendimentos. |

---

## 5. Funcionalidades Principais
- **Autenticacao:** cadastro de perfil (medico/paciente) e login.
- **Gestao de Usuarios:** cadastro e consulta de pacientes e medicos.
- **Gestao de Especialidades:** cadastro e associacao a medicos.
- **Gestao de Agenda:** definicao e consulta de horarios.
- **Gestao de Consultas:** solicitar, aceitar, recusar, cancelar e realizar.
- **Gestao de Tratamentos:** registro vinculado a consulta.

---

## 6. Regras de Negocio

| ID | Regra |
|---|---|
| RN01 | Somente pacientes cadastrados (logados) podem solicitar consultas. |
| RN02 | Somente medicos cadastrados podem ofertar horarios. |
| RN03 | Um medico nao pode ter duas consultas confirmadas no mesmo horario. |
| RN04 | Uma consulta so pode ser realizada apos ser aceita pelo medico. |
| RN05 | Consultas canceladas/recusadas nao podem ser realizadas. |
| RN06 | Consultas realizadas nao podem ser canceladas. |
| RN07 | Vampiros so podem agendar em horarios noturnos. |
| RN08 | Lobisomens nao podem agendar em noites de lua cheia. |
| RN09 | Fantasmas so podem ser atendidos por medicos com especializacao em entidades etereas. |
| RN10 | Um tratamento so pode ser registrado apos a realizacao da consulta. |
| RN11 | A senha do usuario e sempre armazenada criptografada (hash BCrypt). |

---

## 7. Estados da Consulta
**Estados:** `SOLICITADA`, `ACEITA`, `REALIZADA`, `CANCELADA`, `RECUSADA`

**Transicoes permitidas:**
- SOLICITADA → ACEITA | CANCELADA | RECUSADA
- ACEITA → REALIZADA | CANCELADA

**Proibidas (lancam excecao):** REALIZADA→CANCELADA · RECUSADA→ACEITA · CANCELADA→REALIZADA · REALIZADA→ACEITA

---

## 8. Requisitos Funcionais

| ID | Requisito |
|---|---|
| RF00 | Cadastrar perfil e autenticar (login) com senha criptografada. |
| RF01 | Cadastrar pacientes (por especie). |
| RF02 | Cadastrar medicos. |
| RF03 | Criar horarios disponiveis. |
| RF04 | Agendar consultas. |
| RF05 | Cancelar/recusar consultas. |
| RF06 | Registrar tratamentos. |
| RF07 | Consultar historico de consultas/tratamentos. |
| RF08 | Persistir os dados cadastrados. |

---

## 9. Requisitos Nao Funcionais

| ID | Requisito |
|---|---|
| RNF01 | Desenvolvido com POO. |
| RNF02 | Heranca e polimorfismo para usuarios, especies e tratamentos. |
| RNF03 | Tratamento de excecoes para validacao das regras de negocio. |
| RNF04 | Persistencia em banco H2 (modo arquivo), preservada entre execucoes. |
| RNF05 | Comunicacao front-end / back-end via API REST. |
| RNF06 | Senhas criptografadas com BCrypt; nunca armazenadas em texto puro. |

---

## 10. Tecnologias Previstas

| Camada | Tecnologias |
|---|---|
| Front-end | React, Vite, Tailwind CSS, JavaScript |
| Back-end | Java 21, Spring Boot, API REST, Lombok |
| Seguranca | BCrypt (spring-security-crypto) para hash de senha |
| Persistencia | H2 Database (modo arquivo) + Spring Data JPA |
| Controle de versao | Git + GitHub (GitFlow), repositorios separados front/back |

---

## 11. Resultado Esperado
Plataforma funcional com autenticacao, gerenciamento de consultas e tratamentos, demonstrando
heranca, polimorfismo, tratamento de excecoes, estado dinamico, criptografia de senha e
persistencia — os pilares avaliados na disciplina.

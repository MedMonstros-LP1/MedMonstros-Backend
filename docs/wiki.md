# Guia de Contribuicao

## GitFlow

Trabalhamos com duas branches permanentes: `main` (producao/entrega final) e `develop`
(integracao continua). Ninguem commita direto nelas.

Todo trabalho novo sai de `develop` em uma branch temporaria e volta pra `develop` via PR.
Quando a entrega estiver pronta, a `develop` vai pra `main` num PR final.

Branches temporarias seguem o padrao:

```
<tipo>/<numero-da-issue>-<descricao-kebab-case>
```

Tipos aceitos: `feature`, `fix`, `docs`.

Exemplos:
- `feature/3-crud-paciente`
- `fix/7-validacao-horario-noturno`
- `docs/2-atualizar-uml`

## Commits

Seguimos Conventional Commits:

```
<tipo>: <descricao no imperativo> (#numero-da-issue)
```

Tipos: `feat`, `fix`, `docs`, `refactor`, `test`, `chore`.

Exemplos:
- `feat: adicionar cadastro de vampiro (#3)`
- `fix: corrigir validacao de lua cheia (#7)`

## PRs

Titulo segue o mesmo padrao dos commits. O corpo deve ter o numero da issue (`Closes #N`),
uma descricao curta do que foi feito e como testar.

Todo PR vai pra `develop`.

# Disciplina DAO - Padrão de Projeto DAO com JDBC

Atividade de Programação Orientada a Objetos II - IFG  
Padrão DAO (Data Access Object) aplicado à entidade `Disciplina`.

## Estrutura do Projeto

```
disciplina-dao/
├── src/
│   ├── Main.java
│   ├── model/
│   │   └── Disciplina.java
│   ├── dao/
│   │   ├── DisciplinaDAO.java        (interface)
│   │   └── impl/
│   │       └── DisciplinaDAOImp.java (implementação)
│   └── db/
│       ├── DB.java
│       └── DbException.java
├── db.properties        (NÃO versionar - contém senha)
├── script.sql
└── .gitignore
```

## Tecnologias
- Java 17+
- PostgreSQL
- JDBC (PostgreSQL Driver)

## Como executar
Veja o passo a passo completo no guia abaixo.

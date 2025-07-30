# 📌 Autor
- Fábio Simones - https://github.com/FabioSimones
- Desenvolvedor Backend Java

# 📌 Microsserviço: Cadastro de Usuário

Este projeto faz parte de uma série de microsserviços voltados para o agendamento de tarefas. O objetivo principal é permitir que usuários se cadastrem e possam agendar tarefas, 
sendo notificados por e-mail com uma hora de antecedência de cada tarefa.

## 🔍 Objetivo deste repositório

Este microsserviço é responsável apenas pela **API de cadastro de usuários**, contemplando:
- Cadastro com dados pessoais
- Endereços e telefones vinculados
- Segurança com autenticação JWT
- Integração futura com os demais microsserviços do ecossistema

---

## 🛠️ Tecnologias e Ferramentas Utilizadas

- Java 17
- Spring Boot
- PostgreSQL (via Docker)
- Docker
- IntelliJ IDEA
- Postman
- Beekeeper Studio
- Miro (para diagramas de arquitetura)
- Trello (para gerenciamento ágil - Kanban)
- Astah (para diagramas UML)

---

## 🚀 Metodologia Ágil - Kanban

O desenvolvimento é guiado pelo método Kanban. Utilizamos um quadro dividido em:
- 📌 Refinamento
- 🛠️ Em desenvolvimento
- ✅ Concluído

### Exemplo de Tarefas em Desenvolvimento:
- Criar API de cadastro de usuários
- Implementar autenticação (JWT)
- Configurar banco de dados PostgreSQL via Docker
- Testes via Postman

---

## 🧩 Arquitetura e Diagrama

### Visão Geral
- **Frontend (Start)**
- **BFF (Backend for Frontend)** com suporte a agendamento (`Cron`)
- **Microsserviços**: incluindo o de cadastro de usuário e segurança (JWT)

<img width="1171" height="675" alt="image" src="https://github.com/user-attachments/assets/52bb93fb-2bf6-47f2-999e-e436b7acba35" />


### Diagrama UML
Desenhado com Astah, o diagrama mostra:
- Tabela `Usuario` com relação 1..* para `Endereco` e `Telefone`

<img width="595" height="371" alt="image" src="https://github.com/user-attachments/assets/a415a487-7113-469c-b7af-69d87008a999" />

---

## Cronograma de execução

<img width="578" height="765" alt="image" src="https://github.com/user-attachments/assets/3b462f37-fd98-4bd4-a95c-c395886e18a0" />


## 🐳 Docker - Banco de Dados

Utilizamos Docker para instanciar o banco de dados PostgreSQL.

### Comando para subir o container:
```bash
docker-compose up -d




# Pomodoro App
<div style="text-align: center">
    <img src="./Logo.jpeg" alt="Pomodoro Technique" width="350"/>
</div>

Aplicação web para gerenciamento de tarefas e foco usando a técnica Pomodoro, com autenticação de usuários e histórico de sessões por categoria. Exibição de estatísticas e gráficos do progresso e tempo investido pelo usuário nas diferentes tarefas. Além disso, também é possível salvar anotações em uma caixa de texto durante cada sessão gerar um arquivo PDF com as anotações acumuladas, podendo se usada para criar revisões escolares, apresentações, etc.

## Funcionalidades Principais

- 🚀 **Autenticação de Usuários**
  - Registro e login seguro
  - Logout com invalidação de sessão
- ⏱️ **Temporizador Pomodoro**
  - Configuração personalizada de sprints
  - Pausas curtas e longas
  - Sons personalizados
  - Criação de categorias personalizadas
- 📊 **Histórico de Sessões**
  - Visualização gráfica de pomodoros concluídos 
  - Filtragem por usuário
  - Geração de arquivo PDF com anotações da sessão
- 🔒 **Segurança**
  - BCrypt para hash de senhas
  - Proteção de rotas autenticadas

## Tecnologias Utilizadas

### Backend
- Java 17
- Spring Boot 3
- Spring Security
- JPA/Hibernate
- Banco de dados MySQL local (para desenvolvimento)

### Frontend
- JavaScript
- React.js
- Axios para requisições HTTP
- CSS modularizado

## Estrutura do Projeto

```
pomodoro-app/
├── backend/
│   ├── src/main/java/com/example/pomodoroApp/
│   │   ├── config/               # Configurações de segurança
│   │   ├── controller/           # Controladores REST
│   │   ├── model/                # Entidades JPA
│   │   ├── repository/           # Interfaces JPA
│   │   ├── service/              # Lógica de negócio
│   │   └── PomodoroApiApplication.java 
├── frontend/
│   ├── public/
│   └── src/
│       ├── assets/               # 
│       ├── components/           # Componentes React
│       │   ├── Auth/             # Autenticação
│       │   └── Pomodoro/         # Temporizador
│       ├── hooks/                # 
│       ├── styles/               # Estilos CSS
│       │── App.js                # Componente principal
│       │── index.js              # 
│       │── reportWebVitals.js    # 
│       └── setupTests.js         # 
└── README.md
```

## Configuração do Ambiente

### Pré-requisitos
- JDK 17+
- Node.js 16+
- Maven 3.6+

### Instalação
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/pomodoro-app.git
   ```

2. Backend:
   ```bash
   cd pomodoro-app/backend
   mvn spring-boot:run
   ```

3. Frontend:
   ```bash
   cd ../frontend
   npm install
   npm start
   ```

## Rotas da API

| Método | Rota                             | Descrição                         |
|--------|----------------------------------|-----------------------------------|
| POST   | /api/auth/register               | Registrar novo usuário            |
| POST   | /api/auth/login                  | Autenticar usuário                |
| POST   | /api/auth/logout                 | Encerrar sessão                   |
| POST   | /api/pomodoro/start              | Iniciar nova sessão Pomodoro      |
| PUT    | /api/pomodoro/update/{id}        | Atualizar sessão Pomodoro         |
| GET    | /api/pomodoro/completed/{userId} | Listar sessões concluídas         |

## Screenshots

![Tela de Login](screenshots/login.png)
*Tela de autenticação*

![Temporizador](screenshots/timer.png)
*Temporizador Pomodoro em ação*

## Contribuição

Contribuições são bem-vindas! Siga os passos:
1. Faça um fork do projeto
2. Crie uma branch (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

---

Desenvolvido com por Leonardo Lima.
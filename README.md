# Pomodoro App 🍅

<div style="text-align: center">
    <img src="./Logo.jpeg" alt="Pomodoro Technique" width="350"/>
</div>

Uma aplicação moderna para gerenciamento de tempo e produtividade utilizando a técnica Pomodoro. Desenvolvida com tecnologias atuais e uma interface intuitiva, permite que usuários organizem suas tarefas, mantenham o foco e acompanhem seu progresso através de estatísticas detalhadas.

## ✨ Funcionalidades

### 🎯 Gerenciamento de Tempo
- Temporizador Pomodoro personalizável
  - Duração ajustável para sprints (padrão: 25 minutos)
  - Pausas curtas e longas configuráveis
  - Contagem de ciclos por sessão
  - Efeitos sonoros para início e fim dos ciclos

### 👤 Gestão de Usuários
- Sistema de autenticação completo
  - Registro de novos usuários
  - Login seguro
  - Gerenciamento de sessão
  - Proteção de rotas

### 📝 Organização
- Categorização de tarefas
- Histórico de sessões
- Estatísticas de produtividade
- Anotações durante as sessões

## 🛠️ Tecnologias

### Backend
- Java 17
- Spring Boot 3
- Spring Security
- JPA/Hibernate
- MySQL
- Maven

### Frontend
- React.js
- JavaScript (ES6+)
- Axios
- CSS Modules
- React Hooks

## 📚 Detalhes da Implementação

### Backend (Spring Boot)

O backend é construído com Spring Boot 3, oferecendo uma API REST robusta e segura. Principais características:

#### Arquitetura
- **Controllers**: Endpoints REST para autenticação e gerenciamento de pomodoros
- **Services**: Lógica de negócio para gerenciamento de usuários e sessões
- **Repositories**: Persistência de dados com JPA/Hibernate
- **Models**: Entidades que representam usuários, sessões e configurações
- **Security**: Configuração de autenticação e autorização com Spring Security

#### Funcionalidades Backend
- Autenticação JWT
- Validação de dados
- Persistência em banco MySQL
- Gerenciamento de sessões
- Histórico de pomodoros
- Estatísticas de uso

### Frontend (React)

O frontend é desenvolvido em React com uma arquitetura moderna e componentes reutilizáveis.

#### Estrutura de Componentes
- **Auth**: Componentes de autenticação (Login, Register)
- **Pomodoro**: Componentes do temporizador e controle
- **Layout**: Componentes de estrutura da aplicação
- **Common**: Componentes reutilizáveis

#### Funcionalidades Frontend
- Interface responsiva
- Gerenciamento de estado com React Hooks
- Comunicação com API via Axios
- Feedback visual em tempo real
- Animações suaves
- Temas e estilos personalizados

#### Hooks Personalizados
- `usePomodoroTimer`: Gerenciamento do temporizador
- `useAuth`: Gerenciamento de autenticação
- `usePomodoro`: Gerenciamento de sessões

## 📁 Estrutura do Projeto

```
pomodoro-app/
├── pomodoro-api-backend/          # Backend Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   ├── config/       # Configurações
│   │   │   │   ├── controller/   # Controladores REST
│   │   │   │   ├── model/        # Entidades
│   │   │   │   ├── repository/   # Repositórios
│   │   │   │   └── service/      # Serviços
│   │   │   └── resources/        # Configurações
│   │   └── test/                 # Testes
│   └── pom.xml
│
└── pomodoro-api-frontend/         # Frontend React
    ├── public/
    └── src/
        ├── assets/               # Recursos estáticos
        ├── components/           # Componentes React
        │   ├── Auth/            # Componentes de autenticação
        │   └── Pomodoro/        # Componentes do temporizador
        ├── hooks/               # Custom hooks
        ├── styles/              # Estilos CSS
        └── App.js              # Componente principal
```

## 🚀 Como Executar

### Pré-requisitos
- JDK 17 ou superior
- Node.js 16 ou superior
- MySQL 8.0 ou superior
- Maven 3.6 ou superior

### Backend
```bash
cd pomodoro-api-backend/pomodoro-api
mvn spring-boot:run
```

### Frontend
```bash
cd pomodoro-api-frontend/pomodoro-api
npm install
npm start
```

## 🔒 Rotas da API

| Método |             Rota              | Descrição            |
|--------|-------------------------------|-----------           |
| POST   | `/api/auth/register`          | Registro de usuário  |
| POST   | `/api/auth/login`             | Autenticação         |
| POST   | `/api/auth/logout`            | Encerrar sessão      |
| POST   | `/api/pomodoro/create`        | Criar nova sessão    |
| POST   | `/api/pomodoro/finish/{id}`   | Finalizar sessão     |
| GET    | `/api/pomodoro/user/{userId}` | Histórico do usuário |

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

Desenvolvido com ❤️ por Leonardo Lima
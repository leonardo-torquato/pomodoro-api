import React, { useState } from 'react';
import axios from 'axios';
import './AuthSection.css';

const AuthSection = ({ currentUser, setCurrentUser }) => {
  const [formData, setFormData] = useState({ username: '', password: '' });
  const [isLoginView, setIsLoginView] = useState(true);

  const handleAuth = async (e) => {
    e.preventDefault();
    try {
      if (isLoginView) {
        await axios.post('/api/auth/login', formData, { withCredentials: true });
        setCurrentUser({ username: formData.username });
      } else {
        await axios.post('/api/auth/register', formData);
        alert('Registro realizado! Faça login.');
        setIsLoginView(true);
      }
    } catch (error) {
      alert(isLoginView ? 'Credenciais inválidas' : 'Erro no registro');
    }
  };

  const handleLogout = async () => {
    try {
      await axios.post('/api/auth/logout', {}, { withCredentials: true });
      setCurrentUser(null);
      alert('Logout realizado!');
    } catch (error) {
      console.error('Erro no logout:', error);
    }
  };

  return (
    <div className="auth-container">
      {!currentUser ? (
        <div className="auth-card">
          <h2>{isLoginView ? 'Login' : 'Registrar'}</h2>
          <form onSubmit={handleAuth}>
            <input
              type="text"
              placeholder="Usuário"
              value={formData.username}
              onChange={(e) => setFormData({ ...formData, username: e.target.value })}
              className="auth-input"
              required
            />
            <input
              type="password"
              placeholder="Senha"
              value={formData.password}
              onChange={(e) => setFormData({ ...formData, password: e.target.value })}
              className="auth-input"
              required
            />
            <button type="submit" className="auth-button">
              {isLoginView ? 'Entrar' : 'Registrar'}
            </button>
          </form>
          <button 
            onClick={() => setIsLoginView(!isLoginView)} 
            className="auth-toggle"
          >
            {isLoginView ? 'Criar nova conta' : 'Já tenho uma conta'}
          </button>
        </div>
      ) : (
        <div className="welcome-message">
          <p>Olá, <span>{currentUser.username}</span>!</p>
          <button onClick={handleLogout} className="auth-button logout">
            Sair
          </button>
        </div>
      )}
    </div>
  );
};

export default AuthSection;
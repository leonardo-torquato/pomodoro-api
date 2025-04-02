import React, { useState } from 'react';
import axios from 'axios';

const AuthSection = ({ currentUser, setCurrentUser }) => {
  const [formData, setFormData] = useState({ username: '', password: '' });

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      await axios.post('/api/auth/register', formData);
      alert('Registro realizado com sucesso!');
    } catch (error) {
      alert(error.response?.data || 'Erro no registro');
    }
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      await axios.post('/api/auth/login', formData, { withCredentials: true });
      const user = { id: 1, username: formData.username }; // Em produção, obtenha do backend
      setCurrentUser(user);
      alert('Login realizado!');
    } catch (error) {
      alert('Credenciais inválidas');
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
    <div className="auth-section">
      {!currentUser ? (
        <form>
          <input
            type="text"
            placeholder="Usuário"
            value={formData.username}
            onChange={(e) => setFormData({ ...formData, username: e.target.value })}
          />
          <input
            type="password"
            placeholder="Senha"
            value={formData.password}
            onChange={(e) => setFormData({ ...formData, password: e.target.value })}
          />
          <button onClick={handleRegister}>Registrar</button>
          <button onClick={handleLogin}>Login</button>
        </form>
      ) : (
        <div>
          <p>Bem-vindo, {currentUser.username}!</p>
          <button onClick={handleLogout}>Logout</button>
        </div>
      )}
    </div>
  );
};

export default AuthSection;
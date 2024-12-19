import React, { useState } from 'react';
import axios from 'axios';
import '../styles/Login.css';
import { useNavigate, Link } from 'react-router-dom';

interface LoginProps {
  onLoginSuccess: (email: string) => void;
}

const Login: React.FC<LoginProps> = ({ onLoginSuccess }) => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: '',
    senha: '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/usuario/auth/login', null, {
        params: {
          email: formData.email,
          senha: formData.senha,
        },
      });

      if (response.data === true) {
        localStorage.setItem('userEmail', formData.email);
        onLoginSuccess(formData.email);

        navigate('/dashboard');
        alert('Login realizado com sucesso!');
      } else {
        alert('Credenciais inválidas');
      }

      setFormData({
        email: '',
        senha: '',
      });
    } catch (error: unknown) {
      console.error('Erro ao fazer login:', error);
      alert('Erro inesperado ao tentar fazer login');
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit} className="login-form">
        <h1>Logar no Unilaços</h1>
        <div>
          <input
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
            placeholder="E-mail"
            className="login-input"
          />
        </div>
        <div>
          <input
            type="password"
            name="senha"
            value={formData.senha}
            onChange={handleChange}
            placeholder="Senha"
            required
            className="login-input"
          />
        </div>
        <button type="submit" className="login-button">Entrar</button>
        <div>
          <p>Não tem uma conta? <Link to="/cadastrar" className="login-link">Cadastre-se aqui</Link></p>
        </div>
      </form>
    </div>
  );
};

export default Login;

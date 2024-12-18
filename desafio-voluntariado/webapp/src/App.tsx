import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link, Navigate } from 'react-router-dom';
import FormularioCadastro from './components/FormularioCadastro';
import Login from './components/Login';
import AtividadeInteresse from './components/AtividadeInteresse';
import Dashboard from './components/Dashboard';
import UsuarioFilter from './components/UsuarioFilter';
import Atividade from './components/FormularioAtividade';
import { ProtectedRoute } from './components/ProtectedRoute';
import './styles/Header.css';

const App: React.FC = () => {
  const [userEmail, setUserEmail] = useState<string | null>(localStorage.getItem('userEmail'));

  useEffect(() => {
    const handleStorageChange = () => {
      setUserEmail(localStorage.getItem('userEmail'));
    };

    window.addEventListener('storage', handleStorageChange);

    return () => {
      window.removeEventListener('storage', handleStorageChange);
    };
  }, []);

  const handleLoginSuccess = (email: string) => {
    localStorage.setItem('userEmail', email);
    setUserEmail(email);
  };

  const handleLogout = () => {
    localStorage.removeItem('userEmail');
    setUserEmail(null);
  };

  return (
    <Router>
      <div className="app-container">
        <header className="app-header">
          <nav className="app-nav">
            <ul>
              {!userEmail && (
                <>
                  <li>
                    <Link to="/cadastrar">Cadastro</Link>
                  </li>
                  <li>
                    <Link to="/login">Login</Link>
                  </li>
                </>
              )}

              {userEmail && (
                <>
                  <li>
                    <Link to="/dashboard">Inicio</Link>
                  </li>
                  <li>
                    <Link to="/pesquisar">Pesquisar</Link>
                  </li>
                  <li>
                    <Link to="/atividades">Atividades de Interesse</Link>
                  </li>
                  <li>
                    <Link to="/atividade">Criar Atividade</Link>
                  </li>
                  <li>
                    <button 
                      onClick={handleLogout} 
                      className="logout-btn"
                    >
                      Sair
                    </button>
                  </li>
                </>
              )}
            </ul>
          </nav>
        </header>

        <main className="app-main">
          <Routes>
            {!userEmail && (
              <>
                <Route path="/cadastrar" element={<FormularioCadastro />} />
                <Route path="/login" element={<Login onLoginSuccess={handleLoginSuccess} />} />
              </>
            )}

            {userEmail && (
              <>
                <Route 
                  path="/dashboard" 
                  element={
                    <ProtectedRoute>
                      <Dashboard onLogout={handleLogout} />
                    </ProtectedRoute>
                  } 
                />
                <Route 
                  path="/pesquisar" 
                  element={
                    <ProtectedRoute>
                      <UsuarioFilter />
                    </ProtectedRoute>
                  } 
                />
                <Route 
                  path="/atividades" 
                  element={
                    <ProtectedRoute>
                      <AtividadeInteresse />
                    </ProtectedRoute>
                  } 
                />
                <Route path="/atividade" element={<ProtectedRoute><Atividade /></ProtectedRoute>} />
              </>
            )}

            <Route 
              path="/" 
              element={userEmail ? <Navigate to="/dashboard" /> : <Navigate to="/login" />} 
            />
            <Route 
              path="*" 
              element={userEmail ? <Navigate to="/dashboard" /> : <Navigate to="/login" />} 
            />
          </Routes>
        </main>
      </div>
    </Router>
  );
};

export default App;
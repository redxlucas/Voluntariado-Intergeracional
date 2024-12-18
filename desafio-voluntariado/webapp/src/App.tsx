import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link, Navigate } from 'react-router-dom';
import FormularioCadastro from './components/FormularioCadastro';
import Login from './components/Login';
import AtividadeInteresse from './components/AtividadeInteresse';
import Dashboard from './components/Dashboard';
import UsuarioFilter from './components/UsuarioFilter';
import { ProtectedRoute } from './components/ProtectedRoute';

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
      <div>
        <nav>
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
                  <Link to="/dashboard">Dashboard</Link>
                </li>
                <li>
                  <Link to="/pesquisar">Pesquisar</Link>
                </li>
                <li>
                  <Link to="/atividades">Atividades de Interesse</Link>
                </li>
              </>
            )}
          </ul>
        </nav>

        <Routes>
          {!userEmail && (
            <>
              <Route path="/cadastrar" element={<FormularioCadastro />} />
              <Route path="/login" element={<Login onLoginSuccess={handleLoginSuccess} />} />
              <Route path="/atividades" element={<AtividadeInteresse />} />
            </>
          )}

          {userEmail && (
            <>
              <Route path="/dashboard" element={<ProtectedRoute><Dashboard onLogout={handleLogout} /></ProtectedRoute>} />
              <Route path="/pesquisar" element={<ProtectedRoute><UsuarioFilter /></ProtectedRoute>} />
              <Route path="/atividades" element={<ProtectedRoute><AtividadeInteresse /></ProtectedRoute>} />
            </>
          )}

          <Route path="/" element={userEmail ? <Navigate to="/dashboard" /> : <Navigate to="/login" />} />
          <Route path="*" element={userEmail ? <Navigate to="/dashboard" /> : <Navigate to="/login" />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
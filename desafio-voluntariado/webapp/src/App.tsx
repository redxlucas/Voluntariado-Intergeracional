import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';

// Importar os componentes
import FormularioIdoso from './components/FormularioIdoso';
import FormularioVoluntario from './components/FormularioVoluntario';
import Login from './components/Login';  // Importando o novo componente de Login

const App: React.FC = () => {
  return (
    <Router>
      <div>
        <nav>
          <ul>
            <li>
              <Link to="/idoso">Cadastro de Idoso</Link>
            </li>
            <li>
              <Link to="/voluntario">Cadastro de Voluntário</Link>
            </li>
            <li>
              <Link to="/login">Login</Link> {/* Link para a página de login */}
            </li>
          </ul>
        </nav>

        <Routes>
          <Route path="/idoso" element={<FormularioIdoso />} />
          <Route path="/voluntario" element={<FormularioVoluntario />} />
          <Route path="/login" element={<Login />} /> {/* Rota para a página de login */}
          <Route path="/" element={<div>Selecione um tipo de cadastro</div>} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;

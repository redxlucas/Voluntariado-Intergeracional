import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';

import Formulario from './components/FormularioIdoso';

// New Volunteer Form Component
import FormularioVoluntario from './components/FormularioVoluntario';

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
          </ul>
        </nav>

        <Routes>
          <Route path="/idoso" element={<Formulario />} />
          <Route path="/voluntario" element={<FormularioVoluntario />} />
          <Route path="/" element={<div>Selecione um tipo de cadastro</div>} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
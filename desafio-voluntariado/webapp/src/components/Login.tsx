import React, { useState } from 'react';

const FormularioLogin: React.FC = () => {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [userType, setUserType] = useState<'idoso' | 'voluntario'>('idoso'); // Estado para armazenar o tipo de usuário

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log('Email:', email);
    console.log('Senha:', senha);
    console.log('Tipo de Usuário:', userType);
    // Aqui você pode adicionar a lógica para autenticar o usuário, por exemplo, fazer uma requisição ao servidor
  };

  return (
    <div>
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Email:</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Senha:</label>
          <input
            type="password"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
            required
          />
        </div>
        
        <div>
          <label>Tipo de Usuário:</label>
          <div>
            <label>
              <input
                type="radio"
                name="userType"
                value="idoso"
                checked={userType === 'idoso'}
                onChange={() => setUserType('idoso')}
              />
              Idoso
            </label>
            <label>
              <input
                type="radio"
                name="userType"
                value="voluntario"
                checked={userType === 'voluntario'}
                onChange={() => setUserType('voluntario')}
              />
              Voluntário
            </label>
          </div>
        </div>

        <button type="submit">Entrar</button>
      </form>
    </div>
  );
};

export default FormularioLogin;

// import React, { useState } from 'react';

// const FormularioLogin: React.FC = () => {
//   const [email, setEmail] = useState('');
//   const [senha, setSenha] = useState('');
//   const [userType, setUserType] = useState<'idoso' | 'voluntario'>('idoso');

//   const handleSubmit = (e: React.FormEvent) => {
//     e.preventDefault();
//     console.log('Email:', email);
//     console.log('Senha:', senha);
//     console.log('Tipo de Usuário:', userType);
//   };

//   return (
//     <div>
//       <h2>Login</h2>
//       <form onSubmit={handleSubmit}>
//         <div>
//           <label>Email:</label>
//           <input
//             type="email"
//             value={email}
//             onChange={(e) => setEmail(e.target.value)}
//             required
//           />
//         </div>
//         <div>
//           <label>Senha:</label>
//           <input
//             type="password"
//             value={senha}
//             onChange={(e) => setSenha(e.target.value)}
//             required
//           />
//         </div>
        
//         <div>
//           <label>Tipo de Usuário:</label>
//           <div>
//             <label>
//               <input
//                 type="radio"
//                 name="userType"
//                 value="idoso"
//                 checked={userType === 'idoso'}
//                 onChange={() => setUserType('idoso')}
//               />
//               Idoso
//             </label>
//             <label>
//               <input
//                 type="radio"
//                 name="userType"
//                 value="voluntario"
//                 checked={userType === 'voluntario'}
//                 onChange={() => setUserType('voluntario')}
//               />
//               Voluntário
//             </label>
//           </div>
//         </div>

//         <button type="submit">Entrar</button>
//       </form>
//     </div>
//   );
// };

// export default FormularioLogin;

import React, { useState } from 'react';
import '../styles/Login.css';
import axios from 'axios';

interface Login {
    email: string;
    senha: string;
}

function Login() {
  const [formData, setFormData] = useState<Login>({
    email: "",
    senha: "",
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
      const response = await axios.post('http://localhost:8080/', null, {
        params: formData,
      });

      console.log('Resposta do servidor:', response.data);
      
      setFormData({
        email: "",
        senha: "",
      });

      alert('Dados enviados com sucesso!');
    } catch (error: unknown) {
      console.error('Erro ao enviar dados:', error);

      if (axios.isAxiosError(error)) {
        console.log('Erro na resposta do servidor:', error.response?.data);
        alert(`Erro: ${error.response?.data?.message || 'Ocorreu um erro inesperado'}`);
      } else {
        alert('Erro inesperado ao enviar dados');
      }
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
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
          />
        </div>
          <button type="submit">
            Entrar
          </button>
      </form>
    </div>
  );
}

export default Login;

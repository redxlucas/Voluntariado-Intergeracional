import React, { useState } from 'react';
import axios from 'axios';

// Interface para definir a estrutura dos dados que serão enviados
interface Idoso {
  nomeCompleto: string;
  endereco: string;
  email: string;
  senha: string;
  cpf: string;
  telefone: string;
  nomeResponsavel: string;
  telefoneResponsavel: string;
}

const Formulario: React.FC = () => {
  // Estado para armazenar os dados do formulário
  const [formData, setFormData] = useState<Idoso>({
    nomeCompleto: '',
    endereco: '',
    email: '',
    senha: '',
    cpf: '',
    telefone: '',
    nomeResponsavel: '',
    telefoneResponsavel: '',
  });

  // Estado para armazenar a resposta do backend (dados do idoso)
  const [responseData, setResponseData] = useState<any>(null);

  // Função para lidar com a mudança dos valores dos inputs
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    axios
      .post('http://localhost:8080/idoso', formData)
      .then((response) => {
        console.log('Idoso criado com sucesso:', response.data);
        const idosoId = response.data.id;

        axios
          .get(`http://localhost:8080/idoso/${idosoId}`)
          .then((response) => {
            console.log('Idoso recuperado:', response.data);
            setResponseData(response.data);
          })
          .catch((error) => {
            console.error('Erro ao buscar dados do idoso:', error);
          });

        setFormData({
          nomeCompleto: '',
          endereco: '',
          email: '',
          senha: '',
          cpf: '',
          telefone: '',
          nomeResponsavel: '',
          telefoneResponsavel: '',
        });
      })
      .catch((error) => {
        console.error('Erro ao enviar dados para o backend:', error);
      });
  };

  return (
    <div>
      {/* Formulário de Cadastro */}
      <form onSubmit={handleSubmit}>
        <div>
          <label>Nome Completo:</label>
          <input
            type="text"
            name="nomeCompleto"
            value={formData.nomeCompleto}
            onChange={handleChange}
            placeholder="Nome Completo"
            required
          />
        </div>
        <div>
          <label>Endereço:</label>
          <input
            type="text"
            name="endereco"
            value={formData.endereco}
            onChange={handleChange}
            placeholder="Endereço"
          />
        </div>
        <div>
          <label>Email:</label>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="Email"
            required
          />
        </div>
        <div>
          <label>Senha:</label>
          <input
            type="password"
            name="senha"
            value={formData.senha}
            onChange={handleChange}
            placeholder="Senha"
            required
          />
        </div>
        <div>
          <label>CPF:</label>
          <input
            type="text"
            name="cpf"
            value={formData.cpf}
            onChange={handleChange}
            placeholder="CPF"
            required
          />
        </div>
        <div>
          <label>Telefone:</label>
          <input
            type="text"
            name="telefone"
            value={formData.telefone}
            onChange={handleChange}
            placeholder="Telefone"
            required
          />
        </div>
        <div>
          <label>Nome Responsável:</label>
          <input
            type="text"
            name="nomeResponsavel"
            value={formData.nomeResponsavel}
            onChange={handleChange}
            placeholder="Nome Responsável"
            required
          />
        </div>
        <div>
          <label>Telefone Responsável:</label>
          <input
            type="text"
            name="telefoneResponsavel"
            value={formData.telefoneResponsavel}
            onChange={handleChange}
            placeholder="Telefone Responsável"
            required
          />
        </div>
        <button type="submit">Enviar</button>
      </form>

      {/* Exibindo a resposta em JSON */}
      {responseData && (
        <div>
          <h2>Dados do Idoso Criado:</h2>
          <pre>{JSON.stringify(responseData, null, 2)}</pre>
        </div>
      )}
    </div>
  );
};

export default Formulario;

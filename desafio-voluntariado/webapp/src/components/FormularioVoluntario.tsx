import React, { useState } from 'react';
import axios from 'axios';

// Interface to define the structure of volunteer data
interface Voluntario {
  nomeCompleto: string;
  idade: number;
  endereco: string;
  email: string;
  senha: string;
  cpf: string;
  telefone: string;
  areasInteresse: string[];
  disponibilidade: string;
}

const FormularioVoluntario: React.FC = () => {
  // State to store form data
  const [formData, setFormData] = useState<Voluntario>({
    nomeCompleto: '',
    idade: 0,
    endereco: '',
    email: '',
    senha: '',
    cpf: '',
    telefone: '',
    areasInteresse: [],
    disponibilidade: '',
  });

  // State to store backend response
  const [responseData, setResponseData] = useState<any>(null);

  // Handle input changes
  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  // Handle checkbox changes for areas of interest
  const handleInterestChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value, checked } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      areasInteresse: checked
        ? [...prevState.areasInteresse, value]
        : prevState.areasInteresse.filter(area => area !== value)
    }));
  };

  // Handle form submission
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    axios
      .post('http://localhost:8080/voluntario', formData)
      .then((response) => {
        console.log('Voluntário criado com sucesso:', response.data);
        const voluntarioId = response.data.id;

        axios
          .get(`http://localhost:8080/voluntario/${voluntarioId}`)
          .then((response) => {
            console.log('Voluntário recuperado:', response.data);
            setResponseData(response.data);
          })
          .catch((error) => {
            console.error('Erro ao buscar dados do voluntário:', error);
          });

        // Reset form after submission
        setFormData({
          nomeCompleto: '',
          idade: 0,
          endereco: '',
          email: '',
          senha: '',
          cpf: '',
          telefone: '',
          areasInteresse: [],
          disponibilidade: '',
        });
      })
      .catch((error) => {
        console.error('Erro ao enviar dados para o backend:', error);
      });
  };

  return (
    <div>
      {/* Volunteer Registration Form */}
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
          <label>Idade:</label>
          <input
            type="number"
            name="idade"
            value={formData.idade}
            onChange={handleChange}
            placeholder="Idade"
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
          <label>Áreas de Interesse:</label>
          <div>
            <label>
              <input
                type="checkbox"
                value="Cuidados"
                onChange={handleInterestChange}
                checked={formData.areasInteresse.includes('Cuidados')}
              />
              Cuidados
            </label>
            <label>
              <input
                type="checkbox"
                value="Companhia"
                onChange={handleInterestChange}
                checked={formData.areasInteresse.includes('Companhia')}
              />
              Companhia
            </label>
            <label>
              <input
                type="checkbox"
                value="Atividades"
                onChange={handleInterestChange}
                checked={formData.areasInteresse.includes('Atividades')}
              />
              Atividades
            </label>
          </div>
        </div>
        {/* <div>
          <label>Disponibilidade:</label>
          <select
            name="disponibilidade"
            value={formData.disponibilidade}
            onChange={handleChange}
            required
          >
            <option value="">Selecione sua disponibilidade</option>
            <option value="Manhã">Manhã</option>
            <option value="Tarde">Tarde</option>
            <option value="Noite">Noite</option>
            <option value="Fins de Semana">Fins de Semana</option>
          </select>
        </div> */}
        <button type="submit">Enviar</button>
      </form>

      {/* Displaying response in JSON */}
      {responseData && (
        <div>
          <h2>Dados do Voluntário Criado:</h2>
          <pre>{JSON.stringify(responseData, null, 2)}</pre>
        </div>
      )}
    </div>
  );
};

export default FormularioVoluntario;
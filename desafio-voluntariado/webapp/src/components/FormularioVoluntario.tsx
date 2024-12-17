import React, { useState } from 'react';
import axios from 'axios';

interface Voluntario {
    nomeCompleto: string;
    dataDeNascimento: string;
    cep: string;
    bairro: string;
    cidade: string;
    estado: string;
    telefone: string;
    email: string;
    senha: string;
    cpf: string;
}

function FormularioVoluntario() {
  const [formData, setFormData] = useState<Voluntario>({
    nomeCompleto: "",
    dataDeNascimento: "",
    cep: "",
    bairro: "",
    cidade: "",
    estado: "",
    telefone: "",
    email: "",
    senha: "",
    cpf: "",
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleCepChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const cep = e.target.value;
    setFormData((prevState) => ({
      ...prevState,
      cep: cep,
    }));

    if (cep.length === 8) {
      try {
        const response = await axios.get(`https://viacep.com.br/ws/${cep}/json/`);
        if (!response.data.erro) {
          setFormData((prevState) => ({
            ...prevState,
            bairro: response.data.bairro,
            cidade: response.data.localidade,
            estado: response.data.uf,
          }));
        } else {
          alert('CEP não encontrado!');
        }
      } catch (error) {
        console.error('Erro ao buscar dados do CEP:', error);
      }
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/voluntario', null, {
        params: formData,
      });

      console.log('Resposta do servidor:', response.data);
      
      setFormData({
        nomeCompleto: "",
        dataDeNascimento: "",
        cep: "",
        bairro: "",
        cidade: "",
        estado: "",
        telefone: "",
        email: "",
        senha: "",
        cpf: "",
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
      <h1>Cadastrar Voluntários</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="nomeCompleto">Nome Completo</label>
          <input
            type="text"
            id="nomeCompleto"
            name="nomeCompleto"
            value={formData.nomeCompleto}
            onChange={handleChange}
            required
            placeholder="Digite seu nome completo"
          />
        </div>

        <div>
          <label htmlFor="dataDeNascimento">Data de Nascimento:</label>
          <input
            type="date"
            id="dataDeNascimento"
            name="dataDeNascimento"
            value={formData.dataDeNascimento}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label htmlFor="cep">CEP:</label>
          <input
            type="text"
            name="cep"
            value={formData.cep}
            onChange={handleCepChange}
          />
        </div>
        <div>
          <label htmlFor="dataDeNascimento">Bairro:</label>
          <input
            type="text"
            name="bairro"
            value={formData.bairro}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="dataDeNascimento">Cidade:</label>
          <input
            type="text"
            name="cidade"
            value={formData.cidade}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="dataDeNascimento">Estado:</label>
          <input
            type="text"
            name="estado"
            value={formData.estado}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
            placeholder="Digite seu email"
          />
        </div>
        <div>
          <label htmlFor="senha">Senha:</label>
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
          <label htmlFor="cpf">CPF:</label>
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
          <label htmlFor="telefone">Telefone:</label>
          <input
            type="text"
            name="telefone"
            value={formData.telefone}
            onChange={handleChange}
            placeholder="Telefone"
            required
          />
        </div>
          <button type="submit">
            Enviar
          </button>
      </form>
    </div>
  );
}

export default FormularioVoluntario;

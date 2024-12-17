import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

interface Idoso {
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
  nomeResponsavel: string;
  telefoneResponsavel: string;
}

function FormularioIdoso() {
  const [formData, setFormData] = useState<Idoso>({
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
    nomeResponsavel: "",
    telefoneResponsavel: "",
  });

  const [currentStep, setCurrentStep] = useState(1);
  const navigate = useNavigate();

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
    
    if (currentStep < 4) {
      setCurrentStep(currentStep + 1);
      return;
    }

    try {
      const response = await axios.post('http://localhost:8080/idoso', null, {
        params: formData,
      });
      console.log('Resposta do servidor:', response.data);
      
      navigate('/atividades');
      
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
        nomeResponsavel: "",
        telefoneResponsavel: "",
      });
      alert('Cadastro realizado com sucesso!');
    } catch (error: unknown) {
      console.error('Erro ao enviar dados:', error);
      alert('Erro ao enviar dados');
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <h1>Cadastrar Idoso</h1>
        {currentStep === 1 && (
          <>
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
            <button type="button" onClick={() => setCurrentStep(currentStep + 1)}>
              Continuar
            </button>
          </>
        )}
        {currentStep === 2 && (
          <>
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
              <label htmlFor="bairro">Bairro:</label>
              <input
                type="text"
                name="bairro"
                value={formData.bairro}
                onChange={handleChange}
              />
            </div>
            <div>
              <label htmlFor="cidade">Cidade:</label>
              <input
                type="text"
                name="cidade"
                value={formData.cidade}
                onChange={handleChange}
              />
            </div>
            <div>
              <label htmlFor="estado">Estado:</label>
              <input
                type="text"
                name="estado"
                value={formData.estado}
                onChange={handleChange}
              />
            </div>
            <button type="button" onClick={() => setCurrentStep(currentStep - 1)}>
              Voltar
            </button>
            <button type="button" onClick={() => setCurrentStep(currentStep + 1)}>
              Continuar
            </button>
          </>
        )}
        {currentStep === 3 && (
          <>
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

            <button type="button" onClick={() => setCurrentStep(currentStep - 1)}>
              Voltar
            </button>
            <button type="button" onClick={() => setCurrentStep(currentStep + 1)}>
              Continuar
            </button>
          </>
        )}
        {currentStep === 4 && (
          <>
            <div>
              <label htmlFor="nomeResponsavel">Nome Responsável</label>
              <input
                type="text"
                name="nomeResponsavel"
                value={formData.nomeResponsavel}
                onChange={handleChange}
                placeholder="Nome do Responsável"
                required
              />
            </div>

            <div>
              <label htmlFor="telefoneResponsavel">Telefone Responsável</label>
              <input
                type="text"
                name="telefoneResponsavel"
                value={formData.telefoneResponsavel}
                onChange={handleChange}
                placeholder="Telefone do Responsável"
                required
              />
            </div>

            <button type="button" onClick={() => setCurrentStep(currentStep - 1)}>
              Voltar
            </button>
            <button type="submit">Finalizar Cadastro</button>
          </>
        )}
      </form>
    </div>
  );
}

export default FormularioIdoso;

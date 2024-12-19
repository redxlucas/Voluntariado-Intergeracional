import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'

interface Usuario {
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
  nomeResponsavel?: string | null;
  telefoneResponsavel?: string | null;
}

function FormularioCadastro() {
  const [formData, setFormData] = useState<Usuario>({
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
    nomeResponsavel: null,
    telefoneResponsavel: null,
  });

  const [userType, setUserType] = useState<'IDOSO' | 'VOLUNTARIO' | ''>('');
  const [currentStep, setCurrentStep] = useState(1);
  const [errors, setErrors] = useState<{[key: string]: boolean}>({});
  const navigate = useNavigate();

  useEffect(() => {
    if (userType === 'VOLUNTARIO') {
      setFormData(prevData => ({
        ...prevData,
        nomeResponsavel: null,
        telefoneResponsavel: null,
      }));
    }
  }, [userType]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
    
    if (value.trim() !== '') {
      setErrors(prevErrors => ({
        ...prevErrors,
        [name]: false
      }));
    }
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
          // Clear any CEP-related errors
          setErrors(prevErrors => ({
            ...prevErrors,
            cep: false,
            bairro: false,
            cidade: false,
            estado: false
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
      let response;
      if (userType === 'VOLUNTARIO') {
        response = await axios.post('http://localhost:8080/voluntario', null, {
          params: formData,
        });
      } else if (userType === 'IDOSO') {
        response = await axios.post('http://localhost:8080/idoso', null, {
          params: formData,
        });
      }

      console.log('Resposta do servidor:', response?.data);
      
      navigate('/login');
      
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
        nomeResponsavel: null,
        telefoneResponsavel: null,
      });
      setUserType('');
      setErrors({});
      alert('Cadastro realizado com sucesso!');
    } catch (error: unknown) {
      console.error('Erro ao enviar dados:', error);
      alert('Erro ao enviar dados');
    }
  };

  const handleStepChange = () => {
    const stepErrors: {[key: string]: boolean} = {};
    let isValid = true;

    switch (currentStep) {
      case 1:
        if (!userType) {
          isValid = false;
        }
        if (!formData.nomeCompleto.trim()) {
          stepErrors.nomeCompleto = true;
          isValid = false;
        }
        if (!formData.dataDeNascimento.trim()) {
          stepErrors.dataDeNascimento = true;
          isValid = false;
        }
        break;
      case 2:
        if (!formData.cep.trim()) {
          stepErrors.cep = true;
          isValid = false;
        }
        if (!formData.bairro.trim()) {
          stepErrors.bairro = true;
          isValid = false;
        }
        if (!formData.cidade.trim()) {
          stepErrors.cidade = true;
          isValid = false;
        }
        if (!formData.estado.trim()) {
          stepErrors.estado = true;
          isValid = false;
        }
        break;
      case 3:
        if (!formData.email.trim()) {
          stepErrors.email = true;
          isValid = false;
        }
        if (!formData.senha.trim()) {
          stepErrors.senha = true;
          isValid = false;
        }
        if (!formData.cpf.trim()) {
          stepErrors.cpf = true;
          isValid = false;
        }
        if (!formData.telefone.trim()) {
          stepErrors.telefone = true;
          isValid = false;
        }
        break;
      case 4:
        if (!formData.nomeResponsavel?.trim()) {
          stepErrors.nomeResponsavel = true;
          isValid = false;
        }
        if (!formData.telefoneResponsavel?.trim()) {
          stepErrors.telefoneResponsavel = true;
          isValid = false;
        }
        break;
    }

    setErrors(stepErrors);

    if (isValid) {
      if (currentStep < 4) {
        setCurrentStep(currentStep + 1);
      }
    }
  };

  const handleStepBack = (step: number) => {
    if (userType === 'VOLUNTARIO') {
      setFormData(prevData => ({
        ...prevData,
        nomeResponsavel: null,
        telefoneResponsavel: null,
      }));
    }
    setCurrentStep(step);
    setErrors({});
  };

  return (
    <div>
      <form onSubmit={handleSubmit} className='login-form'>
        <h1>Cadastrar</h1>
        
        {currentStep === 1 && (
          <>
            <div>
              <label htmlFor="nomeCompleto">*Nome Completo</label>
              <input
                type="text"
                className='login-input'
                id="nomeCompleto"
                name="nomeCompleto"
                value={formData.nomeCompleto}
                onChange={handleChange}
                required
                placeholder="Digite seu nome completo"
                style={{ 
                  borderColor: errors.nomeCompleto ? 'red' : '',
                  borderWidth: errors.nomeCompleto ? '2px' : '',
                }}
              />
            </div>

            <div>
              <label htmlFor="dataDeNascimento">*Data de Nascimento:</label>
              <input
                type="date"
                id="dataDeNascimento"
                className='login-input'
                name="dataDeNascimento"
                value={formData.dataDeNascimento}
                onChange={handleChange}
                required
                style={{ 
                  borderColor: errors.dataDeNascimento ? 'red' : '',
                  borderWidth: errors.dataDeNascimento ? '2px' : '',
                }}
              />
            </div>
            <div>
              <label htmlFor="tipoUsuario">*Tipo de Usuário</label>
              <div>
                <label>
                  <input
                    type="radio"
                    name="tipoUsuario"
                    className='login-input'
                    value="IDOSO"
                    checked={userType === "IDOSO"}
                    onChange={() => setUserType("IDOSO")}
                    style={{ 
                      color: errors.dataDeNascimento ? 'red' : '',
                    }}
                  />
                  Idoso
                </label>
                <label>
                  <input
                    type="radio"
                    name="tipoUsuario"
                    className='login-input'
                    value="VOLUNTARIO"
                    checked={userType === "VOLUNTARIO"}
                    onChange={() => setUserType("VOLUNTARIO")}
                  />
                  Voluntário
                </label>
              </div>
            </div>
            <button 
              type="button" 
              onClick={handleStepChange}
              className='login-button'
            >
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
                className='login-input'
                value={formData.cep}
                onChange={handleCepChange}
                required
                style={{ 
                  borderColor: errors.cep ? 'red' : '',
                  borderWidth: errors.cep ? '2px' : '',
                }}
              />
            </div>
            <div>
              <label htmlFor="bairro">Bairro:</label>
              <input
                type="text"
                name="bairro"
                className='login-input'
                value={formData.bairro}
                onChange={handleChange}
                required
                style={{ 
                  borderColor: errors.bairro ? 'red' : '',
                  borderWidth: errors.bairro ? '2px' : '',
                }}
              />
            </div>
            <div>
              <label htmlFor="cidade">Cidade:</label>
              <input
                type="text"
                name="cidade"
                className='login-input'
                value={formData.cidade}
                onChange={handleChange}
                required
                style={{ 
                  borderColor: errors.cidade ? 'red' : '',
                  borderWidth: errors.cidade ? '2px' : '',
                }}
              />
            </div>
            <div>
              <label htmlFor="estado">Estado:</label>
              <input
                type="text"
                name="estado"
                className='login-input'
                value={formData.estado}
                onChange={handleChange}
                required
                style={{ 
                  borderColor: errors.estado ? 'red' : '',
                  borderWidth: errors.estado ? '2px' : '',
                }}
              />
            </div>
            <button type="button" onClick={() => handleStepBack(currentStep - 1)} className='login-button'>
              Voltar
            </button>
            <button type="button" onClick={handleStepChange} className='login-button'>
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
                className='login-input'
                name="email"
                value={formData.email}
                onChange={handleChange}
                required
                placeholder="Digite seu email"
                style={{ 
                  borderColor: errors.email ? 'red' : '',
                  borderWidth: errors.email ? '2px' : '',
                }}
              />
            </div>

            <div>
              <label htmlFor="senha">Senha:</label>
              <input
                type="password"
                className='login-input'
                name="senha"
                value={formData.senha}
                onChange={handleChange}
                required
                placeholder="Senha"
                style={{ 
                  borderColor: errors.senha ? 'red' : '',
                  borderWidth: errors.senha ? '2px' : '',
                }}
              />
            </div>

            <div>
              <label htmlFor="cpf">CPF:</label>
              <input
                type="text"
                name="cpf"
                className='login-input'
                value={formData.cpf}
                onChange={handleChange}
                required
                placeholder="CPF"
                style={{ 
                  borderColor: errors.cpf ? 'red' : '',
                  borderWidth: errors.cpf ? '2px' : '',
                }}
              />
            </div>

            <div>
              <label htmlFor="telefone">Telefone:</label>
              <input
                type="text"
                name="telefone"
                className='login-input'
                value={formData.telefone}
                onChange={handleChange}
                required
                placeholder="Telefone"
                style={{ 
                  borderColor: errors.telefone ? 'red' : '',
                  borderWidth: errors.telefone ? '2px' : '',
                }}
              />
            </div>

            <button type="button" onClick={() => handleStepBack(currentStep - 1)} className='login-button'>
              Voltar
            </button>

            {userType === 'VOLUNTARIO' ? (
              <button type="submit" className='login-button'>
                Finalizar Cadastro
              </button>
            ) : (
              <button type="button" onClick={handleStepChange} className='login-button'>
                Continuar
              </button>
            )}
          </>
        )}

        {currentStep === 4 && (
          <>
            <div>
              <label htmlFor="nomeResponsavel">Nome Responsável</label>
              <input
                type="text"
                name="nomeResponsavel"
                className='login-input'
                value={formData.nomeResponsavel || ''}
                onChange={handleChange}
                required
                placeholder="Nome do Responsável"
                style={{ 
                  borderColor: errors.telefone ? 'red' : '',
                  borderWidth: errors.telefone ? '2px' : '',
                }}
              />
            </div>

            <div>
              <label htmlFor="telefoneResponsavel">Telefone Responsável</label>
              <input
                type="text"
                name="telefoneResponsavel"
                className='login-input'
                value={formData.telefoneResponsavel || ''}
                onChange={handleChange}
                required
                placeholder="Telefone do Responsável"
                style={{ 
                  borderColor: errors.telefone ? 'red' : '',
                  borderWidth: errors.telefone ? '2px' : '',
                }}
              />
            </div>

            <button type="button" onClick={() => handleStepBack(currentStep - 1)} className='login-button'>
              Voltar
            </button>
            <button 
              type="submit" 
              onClick={handleStepChange}
              className='login-button'
            >
              Finalizar Cadastro
            </button>
          </>
        )}
      </form>
    </div>
  );
}

export default FormularioCadastro;
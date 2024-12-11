import React, { useState } from 'react';
import axios from 'axios';

// Interface to define the structure of volunteer data
interface Voluntario {
  nomeCompleto: string;
  dataDeNascimento: string;
  cep: string;
  bairro: string;
  cidade: string;
  estado: string;
  email: string;
  senha: string;
  cpf: string;
  telefone: string;
  areasInteresse: string[];
  disponibilidade: string;
}

const FormularioVoluntario: React.FC = () => {
  const [formData, setFormData] = useState<Voluntario>({
    nomeCompleto: "",
    dataDeNascimento: "",
    cep: "",
    bairro: "",
    cidade: "",
    estado: "",
    email: "",
    senha: "",
    cpf: "",
    telefone: "",
    areasInteresse: [],
    disponibilidade: "",
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

  const formatDate = (date: string) => {
    const [year, month, day] = date.split("-");
    return `${day}/${month}/${year}`;
  };
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    const formattedDataDeNascimento = formatDate(formData.dataDeNascimento);

    const dataToSend = {
      ...formData,
      dataDeNascimento: formattedDataDeNascimento,
    };

    axios
      .post('http://localhost:8080/voluntario', dataToSend)
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
          nomeCompleto: "",
          dataDeNascimento: "",
          cep: "",
          bairro: "",
          cidade: "",
          estado: "",
          email: "",
          senha: "",
          cpf: "",
          telefone: "",
          areasInteresse: [],
          disponibilidade: "",
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
          <label>Data de Nascimento:</label>
          <input
            type="date"
            name="dataDeNascimento"
            value={formData.dataDeNascimento}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>CEP:</label>
          <input
            type="text"
            name="cep"
            value={formData.cep}
            onChange={handleCepChange} // Alterado para usar handleCepChange
          />
        </div>
        <div>
          <label>Bairro:</label>
          <input
            type="text"
            name="bairro"
            value={formData.bairro}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Cidade:</label>
          <input
            type="text"
            name="cidade"
            value={formData.cidade}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Estado:</label>
          <input
            type="text"
            name="estado"
            value={formData.estado}
            onChange={handleChange}
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
        <div>
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
        </div>
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
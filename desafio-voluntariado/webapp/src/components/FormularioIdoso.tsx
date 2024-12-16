import React, { useState } from 'react';
import axios from 'axios';

// Interface para definir a estrutura dos dados que serão enviados
interface Idoso {
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
  nomeResponsavel: string;
  telefoneResponsavel: string;
}

const FormularioIdoso: React.FC = () => {
  const [formData, setFormData] = useState<Idoso>({
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
    nomeResponsavel: "",
    telefoneResponsavel: ""
  });

  const [responseData, setResponseData] = useState<any>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
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
      .post('http://localhost:8080/idoso', dataToSend)
      .then((response) => {
        console.log('Idoso criado com sucesso:', response.data);
        const idosoId = response.data.id;

        // Busca os dados do idoso
        axios
          .get(`http://localhost:8080/idoso/${idosoId}`)
          .then((response) => {
            console.log('Idoso recuperado:', response.data);
            setResponseData(response.data);
          })
          .catch((error) => {
            console.error('Erro ao buscar dados do idoso:', error);
          });

        // Limpa os campos após envio
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
          nomeResponsavel: "",
          telefoneResponsavel: "",
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

export default FormularioIdoso;

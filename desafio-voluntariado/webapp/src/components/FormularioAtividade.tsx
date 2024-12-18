import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

interface AtividadeDTO {
  id: number;
  nome: string;
}

interface Atividade {
  nome: string;
  descricao: string;
  status: string;
  dataAtividade: string;
  local: string;
  atividadeDeInteresseId: number;
}

interface FormularioCadastroAtividadeProps {
  atividades: AtividadeDTO[]; // Lista de atividades
}

function FormularioAtividade({ atividades }: FormularioCadastroAtividadeProps) {
  if (!atividades || atividades.length === 0) {
    return <div>Erro: Nenhuma atividade disponível para seleção.</div>;
  }

  const [formData, setFormData] = useState<Atividade>({
    nome: 'Nome Padrão',
    descricao: '',
    status: 'Pendente',
    dataAtividade: '',
    local: '',
    atividadeDeInteresseId: atividades.length > 0 ? atividades[0].id : 1,
  });

  const [errors, setErrors] = useState<{ [key: string]: boolean }>({});
  const navigate = useNavigate();

  // Handle input changes and update the state
  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));

    // If the input field is filled, remove the error
    if (value.trim() !== '') {
      setErrors((prevErrors) => ({
        ...prevErrors,
        [name]: false,
      }));
    }
  };

  // Validate form data
  const validateForm = () => {
    const newErrors: { [key: string]: boolean } = {};
    let isValid = true;

    if (!formData.descricao.trim()) {
      newErrors.descricao = true;
      isValid = false;
    }
    if (!formData.dataAtividade.trim()) {
      newErrors.dataAtividade = true;
      isValid = false;
    }
    if (!formData.local.trim()) {
      newErrors.local = true;
      isValid = false;
    }

    setErrors(newErrors);
    return isValid;
  };

  // Handle form submission
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Validate the form before submitting
    if (validateForm()) {
      try {
        const response = await axios.post('http://localhost:8080/atividade', formData);
        console.log('Resposta do servidor:', response?.data);
        navigate('/atividades'); // Redireciona após cadastro
        alert('Atividade cadastrada com sucesso!');
      } catch (error) {
        console.error('Erro ao cadastrar atividade:', error);
        alert('Erro ao cadastrar atividade');
      }
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <h1>Cadastrar Atividade</h1>

        <div>
          <label htmlFor="atividadeDeInteresseId">*Atividade de Interesse</label>
          <select
            id="atividadeDeInteresseId"
            name="atividadeDeInteresseId"
            value={formData.atividadeDeInteresseId}
            onChange={handleChange}
            required
            style={{ borderColor: errors.atividadeDeInteresseId ? 'red' : '', borderWidth: errors.atividadeDeInteresseId ? '2px' : '' }}
          >
            {atividades.map((atividade) => (
              <option key={atividade.id} value={atividade.id}>
                {atividade.nome}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label htmlFor="descricao">*Descrição</label>
          <textarea
            id="descricao"
            name="descricao"
            value={formData.descricao}
            onChange={handleChange}
            required
            placeholder="Descrição da atividade"
            style={{ borderColor: errors.descricao ? 'red' : '', borderWidth: errors.descricao ? '2px' : '' }}
          />
        </div>

        <div>
          <label htmlFor="dataAtividade">*Data e Hora</label>
          <input
            type="datetime-local"
            id="dataAtividade"
            name="dataAtividade"
            value={formData.dataAtividade}
            onChange={handleChange}
            required
            style={{
              borderColor: errors.dataAtividade ? 'red' : '',
              borderWidth: errors.dataAtividade ? '2px' : '',
            }}
          />
        </div>

        <div>
          <label htmlFor="local">*Local</label>
          <input
            type="text"
            id="local"
            name="local"
            value={formData.local}
            onChange={handleChange}
            required
            placeholder="Local da atividade"
            style={{
              borderColor: errors.local ? 'red' : '',
              borderWidth: errors.local ? '2px' : '',
            }}
          />
        </div>

        <div>
          <button type="submit">
            Finalizar Cadastro
          </button>
        </div>
      </form>
    </div>
  );
}

export default FormularioAtividade;

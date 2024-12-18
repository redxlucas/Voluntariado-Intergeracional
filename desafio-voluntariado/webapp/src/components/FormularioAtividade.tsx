import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

interface Atividade {
  nome: string;
  descricao: string;
  status: string;
  dataAtividade: string;
  local: string;
  atividadeDeInteresseId: number;
}

function FormularioAtividade() {
  const location = useLocation();
  const navigate = useNavigate();

  const [formData, setFormData] = useState<Atividade>({
    nome: 'Nome Padrão',
    descricao: '',
    status: 'Pendente',
    dataAtividade: '',
    local: '',
    atividadeDeInteresseId: 0,
  });
  const [errors, setErrors] = useState<{ [key: string]: boolean }>({});
  const { atividadeIds } = location.state || {}; // IDs passados pelo `navigate`

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));

    if (value.trim() !== '') {
      setErrors((prevErrors) => ({
        ...prevErrors,
        [name]: false,
      }));
    }
  };

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

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (validateForm()) {
      try {
        console.log('Form data:', formData);
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

        {/* Verifica se atividadeIds foi passado */}
        {!atividadeIds || atividadeIds.length === 0 ? (
          <div>Erro: Nenhuma atividade de interesse disponível.</div>
        ) : (
          <div>
            <label htmlFor="atividadeDeInteresseId">*Atividade de Interesse</label>
            <select
              id="atividadeDeInteresseId"
              name="atividadeDeInteresseId"
              value={formData.atividadeDeInteresseId}
              onChange={handleChange}
              required
              style={{
                borderColor: errors.atividadeDeInteresseId ? 'red' : '',
                borderWidth: errors.atividadeDeInteresseId ? '2px' : '',
              }}
            >
              <option value="">Selecione uma atividade</option>
              {atividadeIds.map((id: number) => (
                <option key={id} value={id}>
                  Atividade {id} {/* Mostra o ID da atividade como rótulo */}
                </option>
              ))}
            </select>
          </div>
        )}

        <div>
          <label htmlFor="descricao">*Descrição</label>
          <textarea
            id="descricao"
            name="descricao"
            value={formData.descricao}
            onChange={handleChange}
            required
            placeholder="Descrição da atividade"
            style={{
              borderColor: errors.descricao ? 'red' : '',
              borderWidth: errors.descricao ? '2px' : '',
            }}
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
          <button type="submit">Finalizar Cadastro</button>
        </div>
      </form>
    </div>
  );
}

export default FormularioAtividade;

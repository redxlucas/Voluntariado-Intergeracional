import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';

interface Atividade {
  nome: string;
  descricao: string;
  status: string;
  dataAtividade: string;
  local: string;
  atividadeDeInteresseId: number;
}

interface AtividadeDeInteresse {
  id: number;
  nome: string;
}

function FormularioAtividade() {
  const location = useLocation();
  const navigate = useNavigate();

  const [formData, setFormData] = useState<Atividade>({
    nome: "",
    descricao: "",
    status: "Pendente",
    dataAtividade: "",
    local: "",
    atividadeDeInteresseId: 0,
  });
  const [errors, setErrors] = useState<{ [key: string]: boolean }>({});
  const [atividadesInteresse, setAtividadesInteresse] = useState<AtividadeDeInteresse[]>([]);
  const { usuarioId } = location.state || {}; // Recebe o ID do usuário passado na navegação

  useEffect(() => {
    if (usuarioId) {
      const fetchAtividadesDeInteresse = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/atividade-interesse/usuario/${usuarioId}`);
          const atividades = response.data.map((atividade: any) => ({
            id: atividade.id,
            nome: atividade.nome,
          }));
          setAtividadesInteresse(atividades);
        } catch (error) {
          console.error('Erro ao buscar atividades de interesse:', error);
        }
      };

      fetchAtividadesDeInteresse();
    }
  }, [usuarioId]);

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
        const formatarDataAtividade = (data: string) => {
          const [date, time] = data.split('T');
          const [year, month, day] = date.split('-');
          return `${day}-${month}-${year} ${time}`;
        };

        const params = {
          ...formData,
          dataAtividade: formatarDataAtividade(formData.dataAtividade),
          atividadeDeInteresse: formData.atividadeDeInteresseId,
        };

        console.log('Dados enviados:', params);

        const response = await axios.post('http://localhost:8080/atividade', null, { params });
        console.log('Resposta do servidor:', response?.data);

        const email = localStorage.getItem('userEmail');
        if (email) {
          const userResponse = await axios.get(`http://localhost:8080/usuario/${email}`);
          const usuario = userResponse.data;

          const vincularUrl = `http://localhost:8080/atividade/${response.data.id}/participantes?usuarioUmId=${usuario.id}&usuarioDoisId=${usuarioId}`;

          const vincularResponse = await axios.post(vincularUrl);
          console.log('Vinculação realizada:', vincularResponse.data);
        }

        alert('Atividade cadastrada com sucesso!');
        navigate('/dashboard');
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
          <label htmlFor="descricao">Descrição</label>
          <textarea
            id="descricao"
            name="descricao"
            value={formData.descricao}
            onChange={handleChange}
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

        {atividadesInteresse.length === 0 ? (
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
              {atividadesInteresse.map((atividade) => (
                <option key={atividade.id} value={atividade.id}>
                  {atividade.nome}
                </option>
              ))}
            </select>
          </div>
        )}

        <div>
          <button type="submit">Finalizar Cadastro</button>
        </div>
      </form>
    </div>
  );
}

export default FormularioAtividade;

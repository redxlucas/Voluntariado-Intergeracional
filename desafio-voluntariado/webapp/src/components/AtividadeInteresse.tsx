import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../styles/AtividadeInteresse.css';

interface AtividadeInteresse {
  id: number;
  nome: string;
}

const AtividadeInteresse: React.FC = () => {
  const [atividades, setAtividades] = useState<AtividadeInteresse[]>([]);
  const [selectedAtividades, setSelectedAtividades] = useState<number[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchAtividades = async () => {
      try {
        const response = await axios.get('http://localhost:8080/atividade-interesse');
        setAtividades(response.data);
      } catch (error) {
        console.error('Erro ao buscar atividades de interesse:', error);
        alert('Não foi possível carregar as atividades de interesse');
      }
    };
    fetchAtividades();
  }, []);

  const handleCheckboxChange = (atividadeId: number) => {
    setSelectedAtividades((prev) =>
      prev.includes(atividadeId)
        ? prev.filter((id) => id !== atividadeId)
        : [...prev, atividadeId]
    );
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const userEmail = localStorage.getItem('userEmail');

    if (selectedAtividades.length === 0) {
      alert('Selecione pelo menos uma atividade de interesse');
      return;
    }

    setIsLoading(true);

    try {
      await axios.post('http://localhost:8080/atividade-interesse/vincular', null, {
        params: {
          email: userEmail,
          atividadeDeInteresseList: selectedAtividades.join(','),
        },
      });

      alert('Atividades de interesse salvas com sucesso!');
      navigate('/dashboard');
    } catch (error) {
      console.error('Erro ao salvar atividades de interesse:', error);
      alert('Não foi possível salvar as atividades de interesse');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div>
      <h1>Selecionar Atividades de Interesse</h1>
      <form onSubmit={handleSubmit}>
        {atividades.length === 0 ? (
          <p>Carregando atividades...</p>
        ) : (
          <>
            <div className="checkbox-grid">
              {atividades.map((atividade) => (
                <div key={atividade.id} className="checkbox-item">
                  <input
                    type="checkbox"
                    id={`atividade-${atividade.id}`}
                    name={`atividade-${atividade.id}`}
                    checked={selectedAtividades.includes(atividade.id)}
                    onChange={() => handleCheckboxChange(atividade.id)}
                  />
                  <label htmlFor={`atividade-${atividade.id}`}>{atividade.nome}</label>
                </div>
              ))}
            </div>
            <button type="submit" disabled={isLoading || atividades.length === 0}>
              {isLoading ? 'Salvando...' : 'Salvar Atividades'}
            </button>
          </>
        )}
      </form>
    </div>
  );
};

export default AtividadeInteresse;

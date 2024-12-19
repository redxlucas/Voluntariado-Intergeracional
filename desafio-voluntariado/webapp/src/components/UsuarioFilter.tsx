import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Filter from './Filter';

interface AtividadeDTO {
  id: number;
  nome: string;
}

const UsuarioFilter: React.FC = () => {
  const [atividades, setAtividades] = useState<AtividadeDTO[]>([]);
  const [selectedAtividadesDeInteresse, setSelectedAtividadesDeInteresse] = useState<AtividadeDTO[]>([]);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchAtividades = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/atividade-interesse`);
        setAtividades(response.data || []);
      } catch (error) {
        setError('Erro ao buscar atividades');
        console.error(error);
      }
    };

    fetchAtividades();
  }, []);

  const handleActivityChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const activityId = parseInt(e.target.value, 10);
    const activity = atividades.find((atividade) => atividade.id === activityId);

    if (activity) {
      if (e.target.checked) {
        setSelectedAtividadesDeInteresse((prevSelected) => [...prevSelected, activity]);
      } else {
        setSelectedAtividadesDeInteresse((prevSelected) =>
          prevSelected.filter((atividade) => atividade.id !== activityId)
        );
      }
    }
  };

  return (
    <div>
      <h3>Selecione as Atividades de Interesse</h3>
      {atividades.length > 0 ? (
        <form>
            <div className="checkbox-grid">
              {atividades.map((atividade) => (
                <div key={atividade.id} className="checkbox-item">
              <input
                type="checkbox"
                id={`atividade-${atividade.id}`}
                value={atividade.id}
                onChange={handleActivityChange}
                checked={selectedAtividadesDeInteresse.some((selected) => selected.id === atividade.id)}
              />
              <label htmlFor={`atividade-${atividade.id}`}>{atividade.nome}</label>
            </div>
          ))}
          </div>
        </form>
      ) : (
        <p>Carregando atividades...</p>
      )}

      <h2>Pesquisa de Voluntários por Atividades</h2>

      {error && <p style={{ color: 'red' }}>{error}</p>}

      <Filter atividades={selectedAtividadesDeInteresse} />
    </div>
  );
};

export default UsuarioFilter;

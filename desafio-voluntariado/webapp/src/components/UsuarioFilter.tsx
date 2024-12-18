import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Filter from './Filter'; // Importando o Filter para usar a lógica de filtro
import { useNavigate } from 'react-router-dom';

interface AtividadeDTO {
  id: number;
  nome: string;
}

const UsuarioFilter: React.FC = () => {
  const [atividades, setAtividades] = useState<AtividadeDTO[]>([]); // Lista de atividades
  const [selectedAtividadesDeInteresse, setSelectedAtividadesDeInteresse] = useState<AtividadeDTO[]>([]); // Lista de atividades selecionadas
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchAtividades = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/atividade-interesse`);
        setAtividades(response.data || []); // Setando atividades recebidas
      } catch (error) {
        setError('Erro ao buscar atividades');
        console.error(error);
      }
    };

    fetchAtividades();
  }, []);

  const handleActivityChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const activityId = parseInt(e.target.value, 10);
    const activity = atividades.find((atividade) => atividade.id === activityId); // Encontra a atividade pelo ID

    if (activity) {
      if (e.target.checked) {
        // Adiciona a atividade à lista de atividades selecionadas
        setSelectedAtividadesDeInteresse((prevSelected) => [...prevSelected, activity]);
      } else {
        // Remove a atividade da lista de atividades selecionadas
        setSelectedAtividadesDeInteresse((prevSelected) =>
          prevSelected.filter((atividade) => atividade.id !== activityId)
        );
      }
    }
  };

  // Função para navegar para o formulário de criação de atividade
  const handleCreateActivity = () => {
    navigate('/formulario-cadastro-atividade'); // Navega para o formulário
  };

  return (
    <div>
      <h3>Selecione as Atividades de Interesse</h3>
      {atividades.length > 0 ? (
        <form>
          {atividades.map((atividade) => (
            <div key={atividade.id}>
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
        </form>
      ) : (
        <p>Carregando atividades...</p>
      )}

      {/* Passando as atividades selecionadas como parâmetro para o componente Filter */}
      <h2>Pesquisa de Voluntários por Atividades</h2>

      {error && <p style={{ color: 'red' }}>{error}</p>}

      <Filter atividades={selectedAtividadesDeInteresse} /> {/* Passando a lista de objetos */}

      {/* Botão "Criar Atividade" */}
      <div>
        <button onClick={handleCreateActivity}>Criar Atividade</button>
      </div>
    </div>
  );
};

export default UsuarioFilter;

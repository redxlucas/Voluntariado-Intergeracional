import React, { useState, useEffect } from 'react';
import axios from 'axios';

interface AtividadeInteresse {
  id: number;
  nome: string;
}

const AtividadeInteresse: React.FC = () => {
  const [atividades, setAtividades] = useState<AtividadeInteresse[]>([]);

  useEffect(() => {
    const fetchAtividades = async () => {
      try {
        const response = await axios.get('http://localhost:8080/atividade-interesse');
        setAtividades(response.data);
      } catch (error) {
        console.error('Erro ao buscar atividades de interesse:', error);
      }
    };
    fetchAtividades();
  }, []);

  return (
    <div>
      <h1>Selecionar Atividades de Interesse</h1>
      <form>
        {atividades.length === 0 ? (
          <p>Carregando atividades...</p>
        ) : (
          atividades.map((atividade) => (
            <div key={atividade.id}>
              <input
                type="checkbox"
                id={`atividade-${atividade.id}`}
                name={`atividade-${atividade.id}`}
              />
              <label htmlFor={`atividade-${atividade.id}`}>{atividade.nome}</label>
            </div>
          ))
        )}
      </form>
    </div>
  );
};

export default AtividadeInteresse;

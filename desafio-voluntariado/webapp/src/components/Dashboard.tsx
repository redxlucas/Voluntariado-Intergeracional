import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Filter from './Filter';

interface DashboardProps {
  onLogout: () => void;
}

const Dashboard: React.FC<DashboardProps> = ({ onLogout }) => {
  const [atividades, setAtividades] = useState<{ id: number, nome: string }[]>([]); // Agora é um array de atividades com id e nome
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const userEmail = localStorage.getItem('userEmail');

  useEffect(() => {
    if (!userEmail) {
      setError('Usuário não autenticado');
      return;
    }

    const fetchUserData = async () => {
      try {
        setIsLoading(true);
        const response = await axios.get(`http://localhost:8080/usuario/${userEmail}`);
        
        const atividades = response.data.atividadeDeInteresse.map((atividade: { id: number, nome: string }) => ({
          id: atividade.id,
          nome: atividade.nome,
        }));
        
        setAtividades(atividades);
      } catch (error) {
        setError('Erro ao buscar dados do usuário');
        console.error(error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchUserData();
  }, [userEmail]);

  return (
    <div className="dashboard">
      <header>
        <h1>Início</h1>
        <p>Bem-vindo, {userEmail}</p>
      </header>

      <main>
        <div className="dashboard-content">
          {isLoading && <p>Carregando dados do usuário...</p>}
          {error && <p style={{ color: 'red' }}>{error}</p>}
          <Filter atividades={atividades} />
        </div>
      </main>
    </div>
  );
};

export default Dashboard;

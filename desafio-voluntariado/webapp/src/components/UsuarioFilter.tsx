import React, { useState, useEffect } from 'react';
import axios from 'axios';

// Tipo de dado para o DTO do usuário
interface UsuarioDTO {
  id: number;
  nomeCompleto: string;
  idade: number;
  telefone: string;
  email: string;
  tipo_usuario: 'IDOSO' | 'VOLUNTARIO';
  atividadeDeInteresse: AtividadeDTO[]; // Lista de atividades de interesse associadas
}

// Tipo de dado para a Atividade de Interesse
interface AtividadeDTO {
  id: number;
  nome: string;
}

const UsuarioFilter: React.FC = () => {
  const [usuarios, setUsuarios] = useState<UsuarioDTO[]>([]);
  const [atividades, setAtividades] = useState<AtividadeDTO[]>([]);
  const [selectedAtividadesDeInteresse, setSelectedAtividadesDeInteresse] = useState<Set<number>>(new Set()); // Para armazenar as atividades selecionadas
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  // Handle changes in the activity checkboxes
  const handleActivityChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const activityId = parseInt(e.target.value, 10);
    if (e.target.checked) {
      setSelectedAtividadesDeInteresse((prevSelected) => new Set(prevSelected.add(activityId)));
    } else {
      setSelectedAtividadesDeInteresse((prevSelected) => {
        const newSelected = new Set(prevSelected);
        newSelected.delete(activityId);
        return newSelected;
      });
    }
  };

  const formatTipoUsuario = (tipo: string): string => {
    return tipo.charAt(0).toUpperCase() + tipo.slice(1).toLowerCase();
  };

  // Busca os usuários filtrados pelas atividades selecionadas
  useEffect(() => {
    const fetchUsuarios = async () => {
      // Verifica se há atividades selecionadas
      if (selectedAtividadesDeInteresse.size === 0) {
        setUsuarios([]); // Limpa os resultados quando não há atividades selecionadas
        return;
      }

      try {
        setIsLoading(true);

        // Converte o Set de atividades selecionadas para uma string com os IDs separados por vírgula
        const params = {
          atividadeDeInteresseList: Array.from(selectedAtividadesDeInteresse).join(',')
        };

        const response = await axios.get(`http://localhost:8080/usuario/filtrar`, { params });

        setUsuarios(response.data.content || []);
      } catch (error) {
        setError('Erro ao buscar usuários');
        console.error(error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchUsuarios();
  }, [selectedAtividadesDeInteresse]);

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

  return (
    <div>
      <h2>Pesquisa de Usuários por Atividades</h2>

      {error && <p style={{ color: 'red' }}>{error}</p>}

      {/* Exibe os usuários encontrados */}
      {usuarios.length === 0 && !isLoading && !error && <p>Nenhum usuário encontrado.</p>}

      {usuarios.length > 0 && (
        <ul>
          {usuarios.map((usuario) => (
            <li key={usuario.id}>
              <h3>{usuario.nomeCompleto}</h3>
              <p>Email: {usuario.email}</p>
              <p>Idade: {usuario.idade}</p>
              <p>Telefone: {usuario.telefone}</p>
              <p>Tipo de Usuário: {formatTipoUsuario(usuario.tipo_usuario)}</p>
              
              <h4>Atividades de Interesse</h4>
              {usuario.atividadeDeInteresse.length > 0 ? (
                <ul>
                  {usuario.atividadeDeInteresse.map((atividade) => (
                    <li key={atividade.id}>{atividade.nome}</li>
                  ))}
                </ul>
              ) : (
                <p>Nenhuma atividade vinculada</p>
              )}
            </li>
          ))}
        </ul>
      )}
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
                checked={selectedAtividadesDeInteresse.has(atividade.id)}
              />
              <label htmlFor={`atividade-${atividade.id}`}>{atividade.nome}</label>
            </div>
          ))}
        </form>
      ) : (
        <p>Carregando atividades...</p>
      )}
    </div>
  );
};

export default UsuarioFilter;

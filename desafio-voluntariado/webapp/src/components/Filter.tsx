import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

interface AtividadeDTO {
  id: number;
  nome: string;
}

interface UsuarioDTO {
  id: number;
  nomeCompleto: string;
  idade: number;
  telefone: string;
  email: string;
  tipo_usuario: 'IDOSO' | 'VOLUNTARIO';
  atividadeDeInteresse: AtividadeDTO[];
}

interface UsuarioFilterProps {
  atividades: AtividadeDTO[]; // A lista completa de atividades com id e nome
}

const Filter: React.FC<UsuarioFilterProps> = ({ atividades }) => {
  const [usuarios, setUsuarios] = useState<UsuarioDTO[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [userTipo, setUserTipo] = useState<'IDOSO' | 'VOLUNTARIO' | null>(null);

  const navigate = useNavigate();
  const userEmail = localStorage.getItem('userEmail');

  useEffect(() => {
    const fetchUserTipo = async () => {
      if (!userEmail) return;

      try {
        const response = await axios.get(`http://localhost:8080/usuario/${userEmail}`);
        setUserTipo(response.data.tipo_usuario);
      } catch (error) {
        setError('Erro ao buscar informações do usuário');
        console.error(error);
      }
    };

    fetchUserTipo();
  }, [userEmail]);

  useEffect(() => {
    const fetchUsuarios = async () => {
      if (!atividades.length) {
        setUsuarios([]);
        return;
      }

      try {
        setIsLoading(true);

        // Pegando os ids das atividades
        const atividadeIds = atividades.map((atividade) => atividade.id);
        const params = {
          atividadeDeInteresseList: atividadeIds.join(','),
        };

        const response = await axios.get(`http://localhost:8080/usuario/filtrar`, { params });

        const filteredUsuarios = response.data.content.filter((usuario: UsuarioDTO) => usuario.email !== userEmail);

        if (userTipo) {
          const tipoFiltro = userTipo === 'IDOSO' ? 'VOLUNTARIO' : 'IDOSO';
          const usuariosFiltradosPorTipo = filteredUsuarios.filter((usuario: UsuarioDTO) => usuario.tipo_usuario === tipoFiltro);
          setUsuarios(usuariosFiltradosPorTipo);
        }
      } catch (error) {
        setError('Erro ao buscar usuários');
        console.error(error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchUsuarios();
  }, [atividades, userTipo, userEmail]);

  const handleCreateActivity = (usuario: UsuarioDTO) => {
    // Redirecionar para a página de criação de atividade passando os dados como estado
    navigate('/atividade', {
      state: {
        usuario,
        atividadeIds: usuario.atividadeDeInteresse.map((atividade) => atividade.id)
      }
    });
  };

  return (
    <div>
      <h2>Usuários Filtrados por Atividades</h2>

      {error && <p style={{ color: 'red' }}>{error}</p>}

      {isLoading && <p>Carregando...</p>}

      {usuarios.length === 0 && !isLoading && !error && <p>Nenhum usuário encontrado.</p>}

      {usuarios.length > 0 && (
        <ul>
          {usuarios.map((usuario) => (
            <li key={usuario.id}>
              <h3>{usuario.nomeCompleto}</h3>
              <p>Email: {usuario.email}</p>
              <p>Idade: {usuario.idade}</p>
              <p>Telefone: {usuario.telefone}</p>
              <p>Tipo de Usuário: {usuario.tipo_usuario}</p>

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

              {/* Botão Criar Atividade */}
              <button onClick={() => handleCreateActivity(usuario)}>
                Criar Atividade
              </button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default Filter;

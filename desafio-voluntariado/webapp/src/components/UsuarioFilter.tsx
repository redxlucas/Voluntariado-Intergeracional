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
}

const UsuarioFilter: React.FC = () => {
  const [email, setEmail] = useState<string>('');
  const [usuarios, setUsuarios] = useState<UsuarioDTO[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };

  const formatTipoUsuario = (tipo: string): string => {
    return tipo.charAt(0).toUpperCase() + tipo.slice(1).toLowerCase();
  };

  useEffect(() => {
    const fetchUsuarios = async () => {
      try {
        setIsLoading(true);

        if (email.trim() !== '') {
          const response = await axios.get(`http://localhost:8080/usuario/filtrar`, {
            params: { email } 
          });

          // Verifica a resposta
          setUsuarios(response.data.content || []);
        } else {
          setUsuarios([]);
        }
      } catch (error) {
        setError('Erro ao buscar usuários');
        console.error(error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchUsuarios();
  }, [email]);

  return (
    <div>
      <h2>Pesquisa de Usuários por Email</h2>

      {/* Campo de busca */}
      <input
        type="text"
        placeholder="Digite o email"
        value={email}
        onChange={handleSearchChange}
      />

      {/* Exibe a mensagem de erro */}
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
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default UsuarioFilter;

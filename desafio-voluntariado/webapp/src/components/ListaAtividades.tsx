import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../styles/Modal.css';

interface Atividade {
  id: number;
  nome: string;
  descricao: string;
  status: string;
  dataAtividade: string;
  local: string;
  atividadeDeInteresse: {
    id: number;
    nome: string;
  };
  idoso?: {
    id: number;
    nomeCompleto: string;
  };
  voluntario?: {
    id: number;
    nomeCompleto: string;
  };
  comentario?: string;
  nota?: number;
}

interface Comentario {
  id: number;
  comentario: string;
  nota: number;
  usuario: {
    id: number;
    nomeCompleto: string;
  };
}

const ListaAtividades = () => {
  const [atividades, setAtividades] = useState<Atividade[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [detalhesAtividade, setDetalhesAtividade] = useState<Atividade | null>(null);
  const [comentarios, setComentarios] = useState<Comentario[]>([]); // Estado para armazenar os comentários
  const [step, setStep] = useState<number>(1); // Controle de etapas
  const [comentario, setComentario] = useState<string>('');
  const [nota, setNota] = useState<number>(0);
  const [isConcluida, setIsConcluida] = useState<boolean>(false);
  const [usuarioId, setUsuarioId] = useState<number | null>(null); // Estado para armazenar o ID do usuário
  const [email, setEmail] = useState<string>(''); // Estado para armazenar o email do usuário

  // Buscar o usuário com base no email armazenado no localStorage
  const fetchUsuario = async (email: string) => {
    try {
      const response = await axios.get(`http://localhost:8080/usuario/${email}`);
      setUsuarioId(response.data.id);
    } catch (err) {
      alert('Erro ao buscar o usuário.');
    }
  };

  useEffect(() => {
    const email = localStorage.getItem('userEmail');
    if (email) {
      setEmail(email);
      fetchUsuario(email);
    }
  }, []);

  useEffect(() => {
    const fetchAtividades = async () => {
      if (usuarioId) {
        try {
          const response = await axios.get(`http://localhost:8080/atividade/todos/${usuarioId}`);
          setAtividades(response.data);
        } catch (err) {
          alert('Erro ao carregar as atividades.');
        } finally {
          setLoading(false);
        }
      }
    };

    fetchAtividades();
  }, [usuarioId]);

  const fetchComentarios = async (atividadeId: number) => {
    try {
      const response = await axios.get(`http://localhost:8080/feedback/atividade/${atividadeId}`);
      setComentarios(response.data);
    } catch (err) {
      alert('Erro ao carregar os comentários.');
    }
  };

  const handleConcluirAtividade = async () => {
    if (detalhesAtividade) {
      const confirmacao = window.confirm('Você tem certeza que deseja concluir esta atividade?');
      if (confirmacao) {
        try {
          await axios.put(`http://localhost:8080/atividade/concluir/${detalhesAtividade.id}`);
          setAtividades((prevAtividades) =>
            prevAtividades.map((atividade) =>
              atividade.id === detalhesAtividade.id ? { ...atividade, status: 'Concluída' } : atividade
            )
          );
          setDetalhesAtividade((prevDetalhes) =>
            prevDetalhes ? { ...prevDetalhes, status: 'Concluída' } : null
          );
          setIsConcluida(true);
          setStep(2); // Passa para a etapa 2 (comentário e nota)
        } catch (err) {
          alert('Erro ao concluir a atividade.');
        }
      }
    }
  };

  const showDetalhes = (id: number) => {
    const atividade = atividades.find((atividade) => atividade.id === id);
    if (atividade) {
      setDetalhesAtividade(atividade);
      setIsConcluida(atividade.status === 'Concluída');
      setStep(1); // Começa no passo 1
      fetchComentarios(id); // Busca os comentários ao exibir os detalhes da atividade
    }
  };

  const closeModal = () => {
    setStep(1); // Volta para o passo 1
    setDetalhesAtividade(null);
    setIsConcluida(false);
    setComentarios([]); // Limpa os comentários
    setComentario(''); // Limpa o comentário
    setNota(0); // Limpa a nota
  };

  const handleComentarioSubmit = async () => {
    if (comentario && nota > 0) {
      try {
        // Requisição POST para enviar comentário e nota
        await axios.post(`http://localhost:8080/feedback`, null, {
          params: {
            comentario,
            nota,
            atividade: detalhesAtividade?.id,
            usuario: usuarioId,
          },
        });

        // Criação do novo comentário para garantir que ele tenha o tipo correto
        const novoComentario: Comentario = {
          id: 0, // O id será gerado pelo backend, mas você pode inicializar com 0 ou outro valor caso necessário
          comentario,
          nota,
          usuario: {
            id: usuarioId!,
            nomeCompleto: 'Seu nome', // Altere conforme necessário
          },
        };

        // Atualiza os comentários no estado local
        setComentarios((prevComentarios) => [...prevComentarios, novoComentario]);

        // Exibe um alerta de sucesso
        alert('Comentário e nota enviados com sucesso!');

        // Limpa os campos de comentário e nota após o envio
        setComentario('');
        setNota(0);

        // Fecha o modal
        closeModal();
      } catch (err) {
        alert('Erro ao salvar comentário e nota.');
      }
    } else {
      alert('Por favor, preencha o comentário e/ou a nota.');
    }
  };

  if (loading) {
    return <p>Carregando atividades...</p>;
  }

  return (
    <div>
      <h1>Atividades Vinculadas</h1>

      <p>Email: {email}</p>

      {atividades.length === 0 ? (
        <p>Não há atividades vinculadas a este usuário.</p>
      ) : (
        <div>
          {atividades.map((atividade) => (
            <div key={atividade.id}>
              <a href="#" onClick={() => showDetalhes(atividade.id)}>
                {atividade.nome}
              </a>
            </div>
          ))}
        </div>
      )}

      {/* Modal com os detalhes da atividade */}
      {detalhesAtividade && (
        <div className="modal-overlay">
          <div className="modal-content">
            <h2>Detalhes da Atividade</h2>
            {step === 1 && (
              <div>
                <p>Nome: {detalhesAtividade.nome}</p>
                <p>Descrição: {detalhesAtividade.descricao}</p>
                <p>Status: {detalhesAtividade.status}</p>
                <p>Data: {detalhesAtividade.dataAtividade}</p>
                <p>Local: {detalhesAtividade.local}</p>

                {/* Exibe os feedbacks se houver */}
                {comentarios.length > 0 && (
                  <div>
                    <h4>Comentários:</h4>
                    {comentarios.map((comentario) => (
                      <div key={comentario.id}>
                        <p><strong>{comentario.usuario.nomeCompleto}</strong> - Nota: {comentario.nota}</p>
                        <p>{comentario.comentario}</p>
                      </div>
                    ))}
                  </div>
                )}

                {/* Exibe o botão "Concluir Atividade" apenas se a atividade não foi concluída */}
                {!isConcluida && (
                  <button
                    onClick={handleConcluirAtividade}
                    disabled={detalhesAtividade.status === 'Concluída'}
                  >
                    Concluir Atividade
                  </button>
                )}
              </div>
            )}

            {step === 2 && (
              <div>
                <h3>Comentário e Nota</h3>
                <textarea
                  placeholder="Digite seu comentário aqui..."
                  value={comentario}
                  onChange={(e) => setComentario(e.target.value)}
                />
                <div>
                  <label>Nota: </label>
                  <input
                    type="number"
                    value={nota}
                    onChange={(e) => setNota(Number(e.target.value))}
                    min="1"
                    max="5"
                  />
                </div>
                <button onClick={handleComentarioSubmit}>Salvar Comentário e Nota</button>
              </div>
            )}

            {/* Exibe o botão comentar mesmo que a atividade esteja concluída */}
            {isConcluida && step !== 2 && (
              <button onClick={() => setStep(2)}>Comentar</button>
            )}

            {/* Botões para fechar ou voltar */}
            {step === 2 ? (
              <button onClick={() => setStep(1)}>Voltar</button>
            ) : (
              <button onClick={closeModal}>Fechar</button>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default ListaAtividades;

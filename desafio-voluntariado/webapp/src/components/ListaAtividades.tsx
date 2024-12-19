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
  const [comentarios, setComentarios] = useState<Comentario[]>([]);
  const [step, setStep] = useState<number>(1);
  const [comentario, setComentario] = useState<string>('');
  const [nota, setNota] = useState<number>(0);
  const [isConcluida, setIsConcluida] = useState<boolean>(false);
  const [usuarioId, setUsuarioId] = useState<number | null>(null);
  const [email, setEmail] = useState<string>('');
  const [editandoAtividade, setEditandoAtividade] = useState<boolean>(false);

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
          setStep(2);
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
      setStep(1);
      setEditandoAtividade(false);
      fetchComentarios(id);
    }
  };

  const closeModal = () => {
    setDetalhesAtividade(null);
    setStep(1);
    setIsConcluida(false);
    setComentarios([]);
    setComentario('');
    setNota(0);
    setEditandoAtividade(false);
  };

  const handleVoltar = () => {
    if (editandoAtividade) {
      setEditandoAtividade(false);
    } else if (step === 2) {
      setStep(1);
    }
  };

  const handleEditarAtividade = () => {
    setEditandoAtividade(true);
  };

  const handleSalvarEdicao = async () => {
    if (detalhesAtividade) {
      try {
        await axios.put(`http://localhost:8080/atividade/${detalhesAtividade.id}`, detalhesAtividade);
        setAtividades((prevAtividades) =>
          prevAtividades.map((atividade) =>
            atividade.id === detalhesAtividade.id ? detalhesAtividade : atividade
          )
        );
        alert('Atividade atualizada com sucesso!');
        setEditandoAtividade(false);
      } catch (err) {
        alert('Erro ao salvar as alterações.');
      }
    }
  };

  const handleComentarioSubmit = async () => {
    if (comentario && nota > 0) {
      try {
        await axios.post(`http://localhost:8080/feedback`, null, {
          params: {
            comentario,
            nota,
            atividade: detalhesAtividade?.id,
            usuario: usuarioId,
          },
        });

        const novoComentario: Comentario = {
          id: 0,
          comentario,
          nota,
          usuario: {
            id: usuarioId!,
            nomeCompleto: 'Seu nome',
          },
        };

        setComentarios((prevComentarios) => [...prevComentarios, novoComentario]);
        alert('Comentário e nota enviados com sucesso!');
        setComentario('');
        setNota(0);
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
              <a href="#" onClick={() => showDetalhes(atividade.id)} className="atividade-link">
                {atividade.nome}
              </a>
            </div>
          ))}
        </div>
      )}

      {detalhesAtividade && (
        <div className="modal-overlay">
          <div className="modal-content">
            <h2>Detalhes da Atividade</h2>

            {!editandoAtividade && step === 1 && (
              <>
                <p>Nome: {detalhesAtividade.nome}</p>
                <p>Descrição: {detalhesAtividade.descricao}</p>
                <p>Status: {detalhesAtividade.status}</p>
                <p>Data: {detalhesAtividade.dataAtividade}</p>
                <p>Local: {detalhesAtividade.local}</p>

                {comentarios.length > 0 && (
                  <div>
                    <h4>Comentários:</h4>
                    {comentarios.map((comentario) => (
                      <div key={comentario.id}>
                        <p>
                          <strong>{comentario.usuario.nomeCompleto}</strong> - Nota: {comentario.nota}
                        </p>
                        <p>{comentario.comentario}</p>
                      </div>
                    ))}
                  </div>
                )}

                {!isConcluida && (
                  <button
                    onClick={handleConcluirAtividade}
                    disabled={detalhesAtividade.status === 'Concluída'}
                  >
                    Concluir Atividade
                  </button>
                )}

                <button onClick={handleEditarAtividade}>Editar Atividade</button>

                {isConcluida && (
                  <button onClick={() => setStep(2)}>Comentar</button>
                )}

                <button onClick={closeModal}>Fechar</button>
              </>
            )}

            {editandoAtividade && (
              <>
                <h3>Editar Atividade</h3>
                <div>
                  <label>Descrição:</label>
                  <textarea
                    value={detalhesAtividade.descricao}
                    className="login-input"
                    onChange={(e) =>
                      setDetalhesAtividade({ ...detalhesAtividade, descricao: e.target.value })
                    }
                  />
                </div>
                <div>
                  <label>Data:</label>
                  <input
                    type="date"
                    value={detalhesAtividade.dataAtividade}
                    className="login-input"
                    onChange={(e) =>
                      setDetalhesAtividade({ ...detalhesAtividade, dataAtividade: e.target.value })
                    }
                  />
                </div>
                <div>
                  <label>Local:</label>
                  <input
                    type="text"
                    value={detalhesAtividade.local}
                    className="login-input"
                    onChange={(e) =>
                      setDetalhesAtividade({ ...detalhesAtividade, local: e.target.value })
                    }
                  />
                </div>
                <button onClick={handleSalvarEdicao}>Salvar Alterações</button>
                <button onClick={handleVoltar}>Voltar</button>
              </>
            )}

            {step === 2 && (
              <>
                <h3>Comentário e Nota</h3>
                <div>
                  <label>Comentário:</label>
                  <textarea
                    placeholder="Digite seu comentário aqui..."
                    value={comentario}
                    className="login-input"
                    onChange={(e) => setComentario(e.target.value)}
                  />
                </div>
                <div>
                  <label>Nota: </label>
                  <input
                    type="number"
                    className="login-input"
                    value={nota}
                    onChange={(e) => setNota(Number(e.target.value))}
                    min="1"
                    max="5"
                  />
                </div>
                <button onClick={handleComentarioSubmit}>Salvar Comentário e Nota</button>
                <button onClick={handleVoltar}>Voltar</button>
              </>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default ListaAtividades;
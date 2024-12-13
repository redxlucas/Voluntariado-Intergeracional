package com.db.desafio_voluntariado.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.desafio_voluntariado.entities.Atividade;
import com.db.desafio_voluntariado.entities.AtividadeDTO;
import com.db.desafio_voluntariado.entities.AtividadeDeInteresse;
import com.db.desafio_voluntariado.entities.AtividadeParticipante;
import com.db.desafio_voluntariado.entities.Idoso;
import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.entities.Voluntario;
import com.db.desafio_voluntariado.exception.NotFoundException;
import com.db.desafio_voluntariado.repository.AtividadeDeInteresseRepository;
import com.db.desafio_voluntariado.repository.AtividadeRepository;
import com.db.desafio_voluntariado.repository.IdosoRepository;
import com.db.desafio_voluntariado.repository.VoluntarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private AtividadeDeInteresseRepository atividadeDeInteresseRepository;

    @Autowired
    private IdosoRepository idosoRepository;

    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Transactional
    public Atividade adicionarAtividade(Atividade atividade) {
        AtividadeDeInteresse atividadeDeInteresse = atividadeDeInteresseRepository
                .findById(atividade.getAtividadeDeInteresse().getId())
                .orElseThrow(() -> new NotFoundException("Atividade de interesse não encontrado"));

        atividade.setNome(atividadeDeInteresse.getNome());
        return atividadeRepository.save(atividade);
    }

    public AtividadeDTO getOne(Integer id) {
        Optional<Atividade> atividadeOptional = atividadeRepository.findById(id);

        if (atividadeOptional.isEmpty()) {
            throw new NotFoundException("Atividade não encontrado.");
        }
        Atividade atividade = atividadeOptional.get();
        UsuarioDTO idosoDTO = null;
        UsuarioDTO voluntarioDTO = null;

        if (!atividade.getParticipantes().isEmpty()) {
            AtividadeParticipante participante = atividade.getParticipantes().get(0);
            Idoso idoso = participante.getIdoso();
            Voluntario voluntario = participante.getVoluntario();

            idosoDTO = new UsuarioDTO(idoso.getId(), idoso.getNomeCompleto(), idoso.getIdade(),
                    idoso.getTelefone(), idoso.getEmail(), "IDOSO", idoso.getAtividadeDeInteresseList());

            voluntarioDTO = new UsuarioDTO(voluntario.getId(), voluntario.getNomeCompleto(), voluntario.getIdade(),
                    voluntario.getTelefone(), voluntario.getEmail(), "VOLUNTARIO",
                    voluntario.getAtividadeDeInteresseList());
        }

        return new AtividadeDTO(atividade.getId(), atividade.getNome(), atividade.getDescricao(),
                atividade.getStatus(), atividade.getDataAtividade(), atividade.getLocal(), idosoDTO, voluntarioDTO,
                atividade.getAtividadeDeInteresse());
    }

    public List<AtividadeDTO> getAll() {
        List<Atividade> atividadeList = (List<Atividade>) atividadeRepository.findAll();
        if (atividadeList.isEmpty()) {
            throw new NotFoundException("Atividade(s) não encontrado(s)");
        }
        return atividadeList.stream().map(atividade -> {
            UsuarioDTO idosoDTO = null;
            UsuarioDTO voluntarioDTO = null;

            if (!atividade.getParticipantes().isEmpty()) {
                AtividadeParticipante participante = atividade.getParticipantes().get(0);

                Idoso idoso = participante.getIdoso();
                Voluntario voluntario = participante.getVoluntario();

                idosoDTO = new UsuarioDTO(idoso.getId(), idoso.getNomeCompleto(), idoso.getIdade(), idoso.getTelefone(),
                        idoso.getEmail(), "IDOSO", idoso.getAtividadeDeInteresseList());

                voluntarioDTO = new UsuarioDTO(voluntario.getId(), voluntario.getNomeCompleto(), voluntario.getIdade(),
                        voluntario.getTelefone(), voluntario.getEmail(), "VOLUNTARIO",
                        voluntario.getAtividadeDeInteresseList());
            }

            return new AtividadeDTO(atividade.getId(), atividade.getNome(), atividade.getDescricao(),
                    atividade.getStatus(), atividade.getDataAtividade(), atividade.getLocal(), idosoDTO, voluntarioDTO,
                    atividade.getAtividadeDeInteresse());
        }).collect(Collectors.toList());
    }

    @Transactional
    public AtividadeDTO adicionarParticipante(Integer atividadeId, Integer idosoId, Integer voluntarioId) {
        Optional<Atividade> atividadeOptional = atividadeRepository.findById(atividadeId);
        if (atividadeOptional.isEmpty()) {
            throw new NotFoundException("Atividade não encontrado.");
        }
        Atividade atividade = atividadeOptional.get();

        Idoso idoso = idosoRepository.findById(idosoId)
                .orElseThrow(() -> new EntityNotFoundException("Idoso não encontrado com ID: " + idosoId));

        Voluntario voluntario = voluntarioRepository.findById(voluntarioId)
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado com ID: " + voluntarioId));

        atividade.adicionarParticipante(idoso, voluntario);

        UsuarioDTO idosoDTO = new UsuarioDTO(idoso.getId(), idoso.getNomeCompleto(), idoso.getIdade(),
                idoso.getTelefone(), idoso.getEmail(), "IDOSO", idoso.getAtividadeDeInteresseList());

        UsuarioDTO voluntarioDTO = new UsuarioDTO(voluntario.getId(), voluntario.getNomeCompleto(),
                voluntario.getIdade(), voluntario.getTelefone(), voluntario.getEmail(), "VOLUNTARIO",
                voluntario.getAtividadeDeInteresseList());

        return new AtividadeDTO(atividade.getId(), atividade.getNome(), atividade.getDescricao(), atividade.getStatus(),
                atividade.getDataAtividade(), atividade.getLocal(), idosoDTO, voluntarioDTO,
                atividade.getAtividadeDeInteresse());
    }
}

package br.com.jhisolution.apex.service.impl;

import br.com.jhisolution.apex.service.CadastroService;
import br.com.jhisolution.apex.domain.Cadastro;
import br.com.jhisolution.apex.repository.CadastroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Cadastro.
 */
@Service
@Transactional
public class CadastroServiceImpl implements CadastroService {

    private final Logger log = LoggerFactory.getLogger(CadastroServiceImpl.class);

    private final CadastroRepository cadastroRepository;

    public CadastroServiceImpl(CadastroRepository cadastroRepository) {
        this.cadastroRepository = cadastroRepository;
    }

    /**
     * Save a cadastro.
     *
     * @param cadastro the entity to save
     * @return the persisted entity
     */
    @Override
    public Cadastro save(Cadastro cadastro) {
        log.debug("Request to save Cadastro : {}", cadastro);        return cadastroRepository.save(cadastro);
    }

    /**
     * Get all the cadastros.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cadastro> findAll() {
        log.debug("Request to get all Cadastros");
        return cadastroRepository.findAll();
    }


    /**
     * Get one cadastro by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Cadastro> findOne(Long id) {
        log.debug("Request to get Cadastro : {}", id);
        return cadastroRepository.findById(id);
    }

    /**
     * Delete the cadastro by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cadastro : {}", id);
        cadastroRepository.deleteById(id);
    }
}

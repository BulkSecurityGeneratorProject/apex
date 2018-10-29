package br.com.jhisolution.apex.service;

import br.com.jhisolution.apex.domain.Cadastro;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Cadastro.
 */
public interface CadastroService {

    /**
     * Save a cadastro.
     *
     * @param cadastro the entity to save
     * @return the persisted entity
     */
    Cadastro save(Cadastro cadastro);

    /**
     * Get all the cadastros.
     *
     * @return the list of entities
     */
    List<Cadastro> findAll();


    /**
     * Get the "id" cadastro.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Cadastro> findOne(Long id);

    /**
     * Delete the "id" cadastro.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

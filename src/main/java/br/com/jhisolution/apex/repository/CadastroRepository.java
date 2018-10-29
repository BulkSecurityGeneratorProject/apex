package br.com.jhisolution.apex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jhisolution.apex.domain.Cadastro;


/**
 * Spring Data  repository for the Cadastro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CadastroRepository extends JpaRepository<Cadastro, Long> {

	@Query("select c.gif from Cadastro c where c.id = :id")
	public byte[] findGifByIdCadastro(@Param("id") Long id);
	
}

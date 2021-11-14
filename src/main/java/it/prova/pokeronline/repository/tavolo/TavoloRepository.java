package it.prova.pokeronline.repository.tavolo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.pokeronline.model.Tavolo;


public interface TavoloRepository extends CrudRepository<Tavolo, Long>{
	@Query("from Tavolo t join fetch t.utenteCreazione where t.id = ?1")
	Tavolo findSingleTavoloEager(Long id);
}

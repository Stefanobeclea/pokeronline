package it.prova.pokeronline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;

@Service
public class TavoloServiceImpl implements TavoloService{
	
	@Autowired
	private TavoloRepository repository;

	@Transactional(readOnly = true)
	public List<Tavolo> listAllElements() {
		return (List<Tavolo>) repository.findAll();
	}

	@Transactional(readOnly = true)
	public Tavolo caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public Tavolo caricaSingoloElementoEager(Long id) {
		return repository.findSingleTavoloEager(id);
	}

	@Transactional
	public void aggiorna(Tavolo tavoloInstance) {
		repository.save(tavoloInstance);
	}

	@Transactional
	public void inserisciNuovo(Tavolo tavoloInstance) {
		repository.save(tavoloInstance);
	}

	@Transactional
	public void rimuovi(Tavolo tavoloInstance) {
		repository.delete(tavoloInstance);
	}

	@Override
	public List<Tavolo> findByExample(Tavolo example) {
		
		return repository.findByExample(example);
	}
}

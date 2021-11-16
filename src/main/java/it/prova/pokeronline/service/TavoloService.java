package it.prova.pokeronline.service;

import java.util.List;

import it.prova.pokeronline.model.Tavolo;

public interface TavoloService {
	public List<Tavolo> listAllElements();
	
	public List<Tavolo> listAllElements(Long id);

	public Tavolo caricaSingoloElemento(Long id);
	
	public Tavolo caricaSingoloElementoEager(Long id);

	public void aggiorna(Tavolo tavoloInstance);

	public void inserisciNuovo(Tavolo tavoloInstance);

	public void rimuovi(Tavolo tavoloInstance);
	
	public List<Tavolo> findByExample(Tavolo example);
	
	public List<Tavolo> findByExampleEager(Tavolo example);
	
	public List<Tavolo> findByExampleAdmin(Tavolo example);
	
}

package it.prova.pokeronline.repository.tavolo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.prova.pokeronline.model.Tavolo;

public class CustomTavoloRepositoryImpl implements CustomTavoloRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Tavolo> findByExample(Tavolo example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select distinct t from Tavolo t join fetch t.utenteCreazione u where t.id = t.id ");

		if (StringUtils.isNotEmpty(example.getDenominazione())) {
			whereClauses.add(" t.denominazione  like :denominazione ");
			paramaterMap.put("denominazione", "%" + example.getDenominazione() + "%");
		}
		if (example.getEsperienzaMinima() > 0) {
			whereClauses.add(" t.esperienzaMinima >= :esperienzaMinima ");
			paramaterMap.put("esperienzaMinima",example.getEsperienzaMinima());
		}
		if (example.getCreditoMinimo() > 0) {
			whereClauses.add(" t.creditoMinimo >= :creditoMinimo ");
			paramaterMap.put("creditoMinimo",  example.getCreditoMinimo());
		}
		if (example.getDateCreated() != null) {
			whereClauses.add("t.dateCreated >= :dateCreated ");
			paramaterMap.put("dateCreated", example.getDateCreated());
		}
		whereClauses.add("u.id = :idUtente ");
		paramaterMap.put("idUtente", example.getUtenteCreazione().getId());
		
		queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Tavolo> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tavolo.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}
	
	@Override
	public List<Tavolo> findByExampleEager(Tavolo example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select distinct t from Tavolo t join fetch t.utenteCreazione c where t.id = t.id ");

		if (StringUtils.isNotEmpty(example.getDenominazione())) {
			whereClauses.add(" t.denominazione  like :denominazione ");
			paramaterMap.put("denominazione", "%" + example.getDenominazione() + "%");
		}
		if (example.getEsperienzaMinima() > 0) {
			whereClauses.add(" t.esperienzaMinima >= :esperienzaMinima ");
			paramaterMap.put("esperienzaMinima",example.getEsperienzaMinima());
		}
		if (example.getCreditoMinimo() > 0) {
			whereClauses.add(" t.creditoMinimo >= :creditoMinimo ");
			paramaterMap.put("creditoMinimo",  example.getCreditoMinimo());
		}
		if (example.getDateCreated() != null) {
			whereClauses.add("t.dateCreated >= :dateCreated ");
			paramaterMap.put("dateCreated", example.getDateCreated());
		}
		if (example.getUtenteCreazione() != null) {
			whereClauses.add("c.id = :idUtente ");
			paramaterMap.put("idUtente", example.getUtenteCreazione().getId());
		}
//		if(example.getUtenti() != null && !example.getUtenti().isEmpty()) {
//			whereClauses.add("u in :listaUtenti ");
//			paramaterMap.put("listaUtenti", example.getUtenti());
//		}
//		if(example.getUtenti() != null && example.getUtenti().size() > 0) {
//			int i = 0;
//			for (Utente giocatoreTmp : example.getUtenti()) {
//				if(i == 0)
//					giocatore += " g.id = " + giocatoreTmp.getId();
//				else
//					giocatore += " or g.id = " + giocatoreTmp.getId();
//				
//				i++;
//			}
//		}
		queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Tavolo> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tavolo.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}

}

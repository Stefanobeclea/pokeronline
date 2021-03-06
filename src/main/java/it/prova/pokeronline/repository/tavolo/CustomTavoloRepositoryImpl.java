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
import it.prova.pokeronline.model.Utente;

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
		String giocatore = "";
		
		StringBuilder queryBuilder = new StringBuilder("select distinct t from Tavolo t join fetch t.utenteCreazione c  ");

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
		if ( example.getUtenteCreazione().getId() != null &&  example.getUtenteCreazione() != null) {
			
			whereClauses.add("c.id = :idUtente ");
			paramaterMap.put("idUtente", example.getUtenteCreazione().getId());
		}
		if(example.getUtenti().size() > 0  && example.getUtenti() != null ) {
			
			int i = 0;
			for (Utente giocatoreTmp : example.getUtenti()) {
				if(giocatoreTmp.getId() != null) {
					queryBuilder.append("join fetch t.utenti g ");
					if(i == 0)
						 giocatore += " g.id = " + giocatoreTmp.getId();
					else
						giocatore += " or g.id = " + giocatoreTmp.getId();
					
					i++;
				}
				
			}
		}
		queryBuilder.append(" where t.id = t.id");
		queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Tavolo> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tavolo.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}
	
	@Override
	public List<Tavolo> findByExampleAdmin(Tavolo example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();
		
		StringBuilder queryBuilder = new StringBuilder("select distinct t from Tavolo t join fetch t.utenteCreazione c   ");

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
		if ( example.getUtenteCreazione() != null) {
			whereClauses.add("c.id = :idUtente ");
			paramaterMap.put("idUtente", example.getUtenteCreazione().getId());
		}
		queryBuilder.append(" where t.id = t.id");
		queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Tavolo> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tavolo.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}
	

}

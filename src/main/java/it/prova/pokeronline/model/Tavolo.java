package it.prova.pokeronline.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tavolo")
public class Tavolo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "denominazione")
	private String denominazione;
	@Column(name = "dateCreated")
	private Date dateCreated;
	@Column(name = "esperienzaMinima")
	private int esperienzaMinima;
	@Column(name = "creditoMinimo")
	private int creditoMinimo;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tavolo")
	private Set<Utente> utenti = new HashSet<>(0);

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utente_id", nullable = false)
	private Utente utenteCreazione;
	
	public Tavolo() {
		// TODO Auto-generated constructor stub
	}

	public Tavolo(String denominazione, Date dateCreated, int esperienzaMinima, int creditoMinimo,
			Utente utenteCreazione) {
		super();
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
		this.esperienzaMinima = esperienzaMinima;
		this.creditoMinimo = creditoMinimo;
		this.utenteCreazione = utenteCreazione;
	}
	

	public Tavolo(Long id, String denominazione, Date dateCreated, int esperienzaMinima, int creditoMinimo,
			Utente utenteCreazione) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
		this.esperienzaMinima = esperienzaMinima;
		this.creditoMinimo = creditoMinimo;
		this.utenteCreazione = utenteCreazione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getEsperienzaMinima() {
		return esperienzaMinima;
	}

	public void setEsperienzaMinima(int esperienzaMinima) {
		this.esperienzaMinima = esperienzaMinima;
	}

	public int getCreditoMinimo() {
		return creditoMinimo;
	}

	public void setCreditoMinimo(int creditoMinimo) {
		this.creditoMinimo = creditoMinimo;
	}

	public Set<Utente> getUtenti() {
		return utenti;
	}

	public void setUtenti(Set<Utente> utenti) {
		this.utenti = utenti;
	}

	public Utente getUtenteCreazione() {
		return utenteCreazione;
	}

	public void setUtenteCreazione(Utente utenteCreazione) {
		this.utenteCreazione = utenteCreazione;
	}
}

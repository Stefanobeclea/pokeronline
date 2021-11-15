package it.prova.pokeronline.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.prova.pokeronline.model.Tavolo;



public class TavoloDTO {
	
	private Long id;
	
	@NotBlank(message = "{denominazione.notblank}")
	@Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String denominazione;
	
	private Date dateCreated;
	
	@NotNull(message = "{esperienzaMinima.notnull}")
	@Min(0)
	private Integer esperienzaMinima;
	
	@NotNull(message = "{creditoMinimo.notnull}")
	@Min(0)
	private Integer creditoMinimo;
	
	private Set<UtenteDTO> utenti = new HashSet<>(0);

	private UtenteDTO utenteCreazione;
	
	private UtenteDTO utenteGiocatore;
	
	public TavoloDTO() {
		// TODO Auto-generated constructor stub
	}

	public TavoloDTO(String denominazione,Date dateCreated,  Integer esperienzaMinima, Integer creditoMinimo, UtenteDTO utenteCreazione) {
		super();
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
		this.esperienzaMinima = esperienzaMinima;
		this.creditoMinimo = creditoMinimo;
		this.utenteCreazione = utenteCreazione;
	}
	

	public TavoloDTO(Long id,String denominazione,Integer esperienzaMinima, Integer creditoMinimo) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.esperienzaMinima = esperienzaMinima;
		this.creditoMinimo = creditoMinimo;
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

	public UtenteDTO getUtenteGiocatore() {
		return utenteGiocatore;
	}

	public void setUtenteGiocatore(UtenteDTO utenteGiocatore) {
		this.utenteGiocatore = utenteGiocatore;
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

	public Integer getEsperienzaMinima() {
		return esperienzaMinima;
	}

	public void setEsperienzaMinima(Integer esperienzaMinima) {
		this.esperienzaMinima = esperienzaMinima;
	}

	public Integer getCreditoMinimo() {
		return creditoMinimo;
	}

	public void setCreditoMinimo(Integer creditoMinimo) {
		this.creditoMinimo = creditoMinimo;
	}

	public Set<UtenteDTO> getUtenti() {
		return utenti;
	}

	public void setUtenti(Set<UtenteDTO> utenti) {
		this.utenti = utenti;
	}

	public UtenteDTO getUtenteCreazione() {
		return utenteCreazione;
	}

	public void setUtenteCreazione(UtenteDTO utenteCreazione) {
		this.utenteCreazione = utenteCreazione;
	}
	
	public Tavolo buildTavoloModel() {
		return new Tavolo(this.id, this.denominazione, this.dateCreated, this.esperienzaMinima, this.creditoMinimo);
	}

	public static TavoloDTO buildTavoloDTOFromModel(Tavolo tavoloModel, boolean includeUtente) {
		TavoloDTO result = new TavoloDTO(tavoloModel.getId(), tavoloModel.getDenominazione(),
				tavoloModel.getEsperienzaMinima(), tavoloModel.getCreditoMinimo());

		if (includeUtente)
			result.setUtenteCreazione(UtenteDTO.buildUtenteDTOFromModel(tavoloModel.getUtenteCreazione()));

		return result;
	}

	public static List<TavoloDTO> createTavoloDTOListFromModelList(List<Tavolo> modelListInput, boolean includeUtente) {
		return modelListInput.stream().map(tavoloEntity -> {
			return TavoloDTO.buildTavoloDTOFromModel(tavoloEntity, includeUtente);
		}).collect(Collectors.toList());
	}
	
	
}

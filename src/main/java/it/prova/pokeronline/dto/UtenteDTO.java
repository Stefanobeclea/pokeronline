package it.prova.pokeronline.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.StatoUtente;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.validation.ValidationNoPassword;
import it.prova.pokeronline.validation.ValidationWithPassword;

public class UtenteDTO {

	private Long id;

	@NotBlank(message = "{username.notblank}",groups = {ValidationWithPassword.class,ValidationNoPassword.class})
	@Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String username;

	@NotBlank(message = "{password.notblank}",groups = ValidationWithPassword.class)
	@Size(min = 8, max = 15, message = "Il valore inserito deve essere lungo tra {min} e {max} caratteri")
	private String password;

	private String confermaPassword;

	@NotBlank(message = "{nome.notblank}",groups = {ValidationWithPassword.class,ValidationNoPassword.class})
	private String nome;

	@NotBlank(message = "{cognome.notblank}",groups = {ValidationWithPassword.class,ValidationNoPassword.class})
	private String cognome;

	private Date dateCreated;
	
	private int esperienzaAccumulata;
	
	private int creditoAccumulato;

	private StatoUtente stato;

	private Long[] ruoliIds;
	private Set<RuoloDTO> ruoli = new HashSet<>(0);
	
	private Set<Tavolo> tavoliCreati = new HashSet<>(0);

	private Utente tavolo;

	public UtenteDTO() {
	}

	public UtenteDTO(Long id, String username, String nome, String cognome, StatoUtente stato,
			List<RuoloDTO> ruoliList) {
		super();
		this.id = id;
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
		this.stato = stato;
		this.ruoli = new HashSet<>(ruoliList);
	}

	public Long getId() {
		return id;
	}

	public Set<Tavolo> getTavoliCreati() {
		return tavoliCreati;
	}

	public void setTavoliCreati(Set<Tavolo> tavoliCreati) {
		this.tavoliCreati = tavoliCreati;
	}

	public Utente getTavolo() {
		return tavolo;
	}

	public void setTavolo(Utente tavolo) {
		this.tavolo = tavolo;
	}

	public int getEsperienzaAccumulata() {
		return esperienzaAccumulata;
	}

	public void setEsperienzaAccumulata(int esperienzaAccumulata) {
		this.esperienzaAccumulata = esperienzaAccumulata;
	}

	public int getCreditoAccumulato() {
		return creditoAccumulato;
	}

	public void setCreditoAccumulato(int creditoAccumulato) {
		this.creditoAccumulato = creditoAccumulato;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public StatoUtente getStato() {
		return stato;
	}

	public void setStato(StatoUtente stato) {
		this.stato = stato;
	}

	public Set<RuoloDTO> getRuoli() {
		return ruoli;
	}

	public void setRuoli(Set<RuoloDTO> ruoli) {
		this.ruoli = ruoli;
	}

	public String getConfermaPassword() {
		return confermaPassword;
	}

	public void setConfermaPassword(String confermaPassword) {
		this.confermaPassword = confermaPassword;
	}

	public Long[] getRuoliIds() {
		return ruoliIds;
	}

	public void setRuoliIds(Long[] ruoliIds) {
		this.ruoliIds = ruoliIds;
	}

	public Utente buildUtenteModel(boolean includeIdRoles) {
		Utente result = new Utente(this.id, this.username, this.password, this.nome, this.cognome, this.dateCreated,
				this.stato);
		if (includeIdRoles && ruoliIds != null)
			result.setRuoli(Arrays.asList(ruoliIds).stream().map(id -> new Ruolo(id)).collect(Collectors.toSet()));

		return result;
	}

	public static UtenteDTO buildUtenteDTOFromModel(Utente utenteModel) {
		return new UtenteDTO(utenteModel.getId(), utenteModel.getUsername(), utenteModel.getNome(),
				utenteModel.getCognome(), utenteModel.getStato(),
				RuoloDTO.createRuoloDTOListFromModelSet(utenteModel.getRuoli()));
	}
	
	public static List<UtenteDTO> createUtenteDTOListFromModelList(List<Utente> modelListInput) {
		return modelListInput.stream().map(utenteEntity -> {
			return UtenteDTO.buildUtenteDTOFromModel(utenteEntity);
		}).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "UtenteDTO [id=" + id + ", username=" + username + ", password=" + password + ", confermaPassword="
				+ confermaPassword + ", nome=" + nome + ", cognome=" + cognome + ", esperienzaAccumulata="
				+ esperienzaAccumulata + ", creditoAccumulato=" + creditoAccumulato + ", stato=" + stato + "]";
	}

}

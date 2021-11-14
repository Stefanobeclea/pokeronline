package it.prova.pokeronline;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.StatoUtente;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.RuoloService;
import it.prova.pokeronline.service.UtenteService;

@SpringBootApplication
public class PokeronlineApplication implements CommandLineRunner{

	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(PokeronlineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", "ROLE_ADMIN"));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Classic Player", "ROLE_PLAYER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Classic Player", "ROLE_PLAYER"));
		}
		
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Special Player", "ROLE_SPECIAL_PLAYER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Special Player", "ROLE_SPECIAL_PLAYER"));
		}

		// a differenza degli altri progetti cerco solo per playername perche' se vado
		// anche per password ogni volta ne inserisce uno nuovo, inoltre l'encode della password non lo 
		//faccio qui perche gia lo fa il service di utente, durante inserisciNuovo
		if (utenteServiceInstance.findByUsername("admin") == null) {
			Utente admin = new Utente("admin", "admin", "Mario", "Rossi", new Date());
			admin.setStato(StatoUtente.ATTIVO);
			admin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN"));
			utenteServiceInstance.inserisciNuovo(admin);
		}

		if (utenteServiceInstance.findByUsername("player") == null) {
			Utente classicPlayer = new Utente("player", "player", "Franco", "Verdi", new Date());
			classicPlayer.setStato(StatoUtente.ATTIVO);
			classicPlayer.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic Player", "ROLE_PLAYER"));
			utenteServiceInstance.inserisciNuovo(classicPlayer);
		}

		if (utenteServiceInstance.findByUsername("player1") == null) {
			Utente classicPlayer1 = new Utente("player1", "player1", "Francoo", "Verdii", new Date());
			classicPlayer1.setStato(StatoUtente.ATTIVO);
			classicPlayer1.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic Player", "ROLE_PLAYER"));
			utenteServiceInstance.inserisciNuovo(classicPlayer1);
		}

		if (utenteServiceInstance.findByUsername("special") == null) {
			Utente specialPlayer = new Utente("special", "special", "Francooo", "Verdiii", new Date());
			specialPlayer.setStato(StatoUtente.ATTIVO);
			specialPlayer.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Special Player", "ROLE_SPECIAL_PLAYER"));
			utenteServiceInstance.inserisciNuovo(specialPlayer);
		}

	}

}

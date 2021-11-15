package it.prova.pokeronline.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;

@Controller
@RequestMapping(value = "/game")
public class GameController {
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private TavoloService tavoloService;
	
	@GetMapping("/ricarica")
	public String ricaricaCredito(Model model) {
		return "game/addcredito";		
	}
	
	@PostMapping("/addcredito")
	public String addCredito(Model model, HttpServletRequest request) {
		int creditoDaAggiungere = Integer.parseInt(request.getParameter("ricarica"));
		Utente utenteInSessione = (Utente)request.getSession().getAttribute("userInfo");

		utenteService.addCredito(utenteInSessione, creditoDaAggiungere);		
		return "index";		
	}
	
	@GetMapping
	public ModelAndView listAllTavoli(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		List<Tavolo> tavoli = tavoloService.listAllElements();
		mv.addObject("tavolo_list_attribute", tavoli);
		mv.setViewName("game/list");
		return mv;
	}

	@GetMapping("/search")
	public String searchTavolo(Model model) {
		model.addAttribute("utenti_list_attribute", UtenteDTO.createUtenteDTOListFromModelList(utenteService.listAllUtenti()));
		return "game/search";
	}

	@PostMapping("/list")
	public String listTavoli(@ModelAttribute("insert_tavolo_attr") TavoloDTO tavoloExample, ModelMap model) {
		if (tavoloExample.getUtenteCreazione().getId() != null) {
			tavoloExample.setUtenteCreazione(UtenteDTO
					.buildUtenteDTOFromModel(utenteService.caricaSingoloUtente(tavoloExample.getUtenteCreazione().getId())));
		}
		if (!tavoloExample.getUtenti().isEmpty()) {
			tavoloExample.getUtenti().add(UtenteDTO
					.buildUtenteDTOFromModel(utenteService.caricaSingoloUtente(tavoloExample.getUtenteCreazione().getId())));
		}
		List<Tavolo> tavoli = tavoloService.findByExampleEager(tavoloExample.buildTavoloModel());
		model.addAttribute("tavolo_list_attribute", TavoloDTO.createTavoloDTOListFromModelList(tavoli, false));
		return "game/list";
	}
	
	@GetMapping("/gioca/{idTavolo}")
	public String showTavoloGiocato(@PathVariable(required = true) Long idTavolo, Model model) {
		model.addAttribute("show_tavolo_attr", tavoloService.caricaSingoloElementoEager(idTavolo));
		return "game/gioca";
	}
	
	@PostMapping("/giocata/{idTavolo}")
	public String giocata(@PathVariable(required = true) Long idTavolo, Model model,
			 HttpServletRequest request) {
		Utente utenteInSessione = (Utente)request.getSession().getAttribute("userInfo");
		utenteInSessione = utenteService.caricaSingoloUtente(utenteInSessione.getId());
		Tavolo tavoloPerGiocare = tavoloService.caricaSingoloElementoEager(idTavolo);
		
		model.addAttribute("show_tavolo_attr", tavoloPerGiocare);
		if(utenteInSessione.getTavolo() != tavoloPerGiocare && utenteInSessione.getTavolo() != null) {
			model.addAttribute("errorMessage", "Sei già presente in una partita.");
			return "game/gioca";
		}
		if(utenteInSessione.getCreditoAccumulato() < tavoloPerGiocare.getCreditoMinimo()) {
			model.addAttribute("errorMessage", "Credito insufficiente per giocare.");
			return "game/gioca";
		}
		if(utenteInSessione.getEsperienzaAccumulata() < tavoloPerGiocare.getEsperienzaMinima()) {
			model.addAttribute("errorMessage", "Esperienza insufficiente per giocare.");
			return "game/gioca";
		}
		
		model.addAttribute("successMessage", "Sei In Partita, gioca e tenta la fortuna!");
		return "game/partitajsp";
	}
	
	@PostMapping("/partita/{idTavolo}")
	public String partita(@PathVariable(required = true) Long idTavolo, Model model,
			 HttpServletRequest request) {
		Utente utenteInSessione = (Utente)request.getSession().getAttribute("userInfo");
		utenteInSessione = utenteService.caricaSingoloUtente(utenteInSessione.getId());
		Tavolo tavoloPerGiocare = tavoloService.caricaSingoloElementoEager(idTavolo);
		
		utenteInSessione.setEsperienzaAccumulata(utenteInSessione.getEsperienzaAccumulata()+1);
		utenteInSessione.setCreditoAccumulato(utenteInSessione.getCreditoAccumulato()+10);
		utenteInSessione.setTavolo(tavoloPerGiocare);
		utenteService.aggiorna(utenteInSessione);
		System.out.println("arrivo qui");
		tavoloPerGiocare.getUtenti().add(utenteInSessione);
		tavoloService.aggiorna(tavoloPerGiocare);
		
		model.addAttribute("show_tavolo_attr", tavoloPerGiocare);
		model.addAttribute("successMessage", "Hai Vinto!");
		return "game/partitajsp";
	}
	
	@PostMapping("/exit/{idTavolo}")
	public String exitPartita(@PathVariable(required = true) Long idTavolo, Model model,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {
		Utente utenteInSessione = (Utente)request.getSession().getAttribute("userInfo");
		utenteInSessione = utenteService.caricaSingoloUtente(utenteInSessione.getId());
		Tavolo tavoloPerGiocare = tavoloService.caricaSingoloElementoEager(idTavolo);
		
		utenteInSessione.setTavolo(null);
		utenteService.aggiorna(utenteInSessione);
		
		tavoloPerGiocare.getUtenti().remove(utenteInSessione);
		tavoloService.aggiorna(tavoloPerGiocare);
		
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/game";
	}
	
}

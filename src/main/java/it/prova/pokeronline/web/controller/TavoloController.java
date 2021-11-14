package it.prova.pokeronline.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;

@Controller
@RequestMapping(value = "/tavolo")
public class TavoloController {

	@Autowired
	private TavoloService tavoloService;
	
	@Autowired
	private UtenteService utenteService;
	
	@GetMapping
	public ModelAndView listAllUtenti(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		Utente utenteInSessione = (Utente)request.getSession().getAttribute("userInfo");
		List<Tavolo> tavoli = tavoloService.listAllElements(utenteInSessione.getId());
		mv.addObject("tavolo_list_attribute", tavoli);
		mv.setViewName("tavolo/list");
		return mv;
	}

	@GetMapping("/search")
	public String searchTavolo(Model model) {
		return "tavolo/search";
		
	}

	@PostMapping("/list")
	public String listUtenti(TavoloDTO tavoloExample, ModelMap model, HttpServletRequest request) {
		Utente utenteInSessione = (Utente)request.getSession().getAttribute("userInfo");
		tavoloExample.setUtenteCreazione(UtenteDTO
				.buildUtenteDTOFromModel(utenteService.caricaSingoloUtente(utenteInSessione.getId())));
		List<Tavolo> utenti = tavoloService.findByExample(tavoloExample.buildTavoloModel());
		model.addAttribute("tavolo_list_attribute", utenti);
		return "tavolo/list";
	}
}

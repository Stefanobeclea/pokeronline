package it.prova.pokeronline.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.UtenteService;

@Controller
@RequestMapping(value = "/game")
public class GameController {
	
	@Autowired
	private UtenteService utenteService;
	
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
	
	
}

package it.prova.pokeronline.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
@RequestMapping(value = "/tavolo")
public class TavoloController {

	@Autowired
	private TavoloService tavoloService;
	
	@Autowired
	private UtenteService utenteService;
	
	@GetMapping
	public ModelAndView listAllTavoli(HttpServletRequest request) {
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
	public String listTavoli(TavoloDTO tavoloExample, ModelMap model, HttpServletRequest request) {
		Utente utenteInSessione = (Utente)request.getSession().getAttribute("userInfo");
		tavoloExample.setUtenteCreazione(UtenteDTO
				.buildUtenteDTOFromModel(utenteService.caricaSingoloUtente(utenteInSessione.getId())));
		List<Tavolo> utenti = tavoloService.findByExample(tavoloExample.buildTavoloModel());
		model.addAttribute("tavolo_list_attribute", utenti);
		return "tavolo/list";
	}
	
	@GetMapping("/insert")
	public String createTavolo(Model model) {
		model.addAttribute("insert_tavolo_attr", new TavoloDTO());
		return "tavolo/insert";
	}

	@PostMapping("/save")
	public String saveTavolo(@Valid @ModelAttribute("insert_tavolo_attr") TavoloDTO tavoloDTO, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {
		Utente utenteInSessione = (Utente)request.getSession().getAttribute("userInfo");
		tavoloDTO.setUtenteCreazione(UtenteDTO
				.buildUtenteDTOFromModel(utenteService.caricaSingoloUtente(utenteInSessione.getId())));
		tavoloDTO.setDateCreated(new Date());
		if (result.hasErrors()) {
			return "tavolo/insert";
		}
		
		tavoloService.inserisciNuovo(tavoloDTO.buildTavoloModel());
		
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/tavolo";
	}
	
	@GetMapping("/show/{idTavolo}")
	public String show(@PathVariable(required = true) Long idTavolo, Model model) {
		model.addAttribute("show_tavolo_attr", tavoloService.caricaSingoloElementoEager(idTavolo));
		return "tavolo/show";
	}
	
	@GetMapping("/rimuovi/{idTavolo}")
	public String infodelete(@PathVariable(required = true) Long idTavolo, Model model) {
		model.addAttribute("show_tavolo_attr", tavoloService.caricaSingoloElementoEager(idTavolo));
		return "tavolo/delete";
	}
	
	@PostMapping("/delete/{idTavolo}")
	public String delete(@PathVariable(required = true) Long idTavolo, Model model, RedirectAttributes redirectAttrs) {
		Tavolo tavoloDaRimuovere = tavoloService.caricaSingoloElemento(idTavolo);
		System.out.println("Qua Ci Arriva"+tavoloDaRimuovere.getDenominazione());
		tavoloService.rimuovi(tavoloDaRimuovere);		
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/tavolo";
	}
	
	@GetMapping("/modifica/{idTavolo}")
	public String modifica(@PathVariable(required = true) Long idTavolo, Model model) {
		model.addAttribute("edit_tavolo_attr", TavoloDTO.buildTavoloDTOFromModel(tavoloService.caricaSingoloElemento(idTavolo), true));
		return "tavolo/edit";
	}
	
	@PostMapping("/edit")
	public String edit(@Valid @ModelAttribute("edit_tavolo_attr") TavoloDTO tavoloDTO, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {
		Utente utenteInSessione = (Utente)request.getSession().getAttribute("userInfo");
		Tavolo tavoloDaAggiornare = tavoloService.caricaSingoloElemento(tavoloDTO.getId());
		tavoloDTO.setUtenteCreazione(UtenteDTO
				.buildUtenteDTOFromModel(utenteService.caricaSingoloUtente(utenteInSessione.getId())));
		tavoloDTO.setDateCreated(tavoloDaAggiornare.getDateCreated());
		if (result.hasErrors()) {
			return "tavolo/edit";
		}		
		tavoloService.aggiorna(tavoloDTO.buildTavoloModel());		
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/tavolo";
	}
}

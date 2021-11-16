package it.prova.pokeronline.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.prova.pokeronline.dto.RuoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.RuoloService;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.utility.UtilityForm;
import it.prova.pokeronline.validation.ValidationNoPassword;
import it.prova.pokeronline.validation.ValidationWithPassword;


@Controller
@RequestMapping(value = "/utente")
public class UtenteController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private RuoloService ruoloService;

	@GetMapping
	public ModelAndView listAllUtenti() {
		ModelAndView mv = new ModelAndView();
		List<Utente> utenti = utenteService.listAllUtenti();
		mv.addObject("utente_list_attribute", utenti);
		mv.setViewName("utente/list");
		return mv;
	}

	@GetMapping("/search")
	public String searchUtente(Model model) {
		model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm
				.buildCheckedRolesForPages(RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), null));
		return "utente/search";
		
	}

	@PostMapping("/list")
	public String listUtenti(UtenteDTO utenteExample, ModelMap model) {
		List<Utente> utenti = utenteService.findByExample(utenteExample.buildUtenteModel(true));
		model.addAttribute("utente_list_attribute", utenti);
		return "utente/list";
	}
	
	@GetMapping("/insert")
	public String create(Model model) {
		model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm
				.buildCheckedRolesForPages(RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), null));
		model.addAttribute("insert_utente_attr", new UtenteDTO());
		return "utente/insert";
	}

	//per la validazione devo usare i groups in quanto nella insert devo validare la pwd, nella edit no
	@PostMapping("/save")
	public String save(@Validated({ValidationWithPassword.class,ValidationNoPassword.class}) @ModelAttribute("insert_utente_attr") UtenteDTO utenteDTO, BindingResult result,
			Model model, RedirectAttributes redirectAttrs) {

		if (!result.hasFieldErrors("password") && !utenteDTO.getPassword().equals(utenteDTO.getConfermaPassword()))
			result.rejectValue("confermaPassword", "password.diverse");
		if (result.hasErrors()) {
			model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm.buildCheckedRolesForPages(
					RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), utenteDTO.getRuoliIds()));
			return "utente/insert";
		}
		utenteService.inserisciNuovo(utenteDTO.buildUtenteModel(true));

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/utente";
	}
	
	@GetMapping("/edit/{idUtente}")
	public String edit(@PathVariable(required = true) Long idUtente, Model model) {
		Utente utenteModel = utenteService.caricaSingoloUtenteConRuoli(idUtente);
		model.addAttribute("edit_utente_attr", UtenteDTO.buildUtenteDTOFromModel(utenteModel));
		model.addAttribute("mappaRuoliConSelezionati_attr",
				UtilityForm.buildCheckedRolesFromRolesAlreadyInUtente(
						RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()),
						RuoloDTO.createRuoloDTOListFromModelSet(utenteModel.getRuoli())));
		return "utente/edit";
	}

	@PostMapping("/update")
	public String update(@Validated(ValidationNoPassword.class) @ModelAttribute("edit_utente_attr") UtenteDTO utenteDTO, BindingResult result,
			Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm.buildCheckedRolesForPages(
					RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), utenteDTO.getRuoliIds()));
			return "utente/edit";
		}
		utenteService.aggiorna(utenteDTO.buildUtenteModel(true));

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/utente";
	}

	@PostMapping("/cambiaStato")
	public String cambiaStato(@RequestParam(name = "idUtenteForChangingStato", required = true) Long idUtente) {
		utenteService.changeUserAbilitation(idUtente);
		return "redirect:/utente";
	}
	
	@GetMapping("/show/{idUtente}")
	public String show(@PathVariable(required = true) Long idUtente, Model model) {
		model.addAttribute("show_utente_attr", utenteService.caricaSingoloUtenteConRuoli(idUtente));
		return "utente/show";
	}
	
	@GetMapping("/reset/{idUtente}")
	public String reset(@PathVariable(required = true)Long idUtente, Model model ) {
		Utente utenteDaResettare = utenteService.caricaSingoloUtente(idUtente);
		utenteService.reset(utenteDaResettare);
		return "redirect:/utente";
	}
	
	@GetMapping("/formreset")
	public String formreset(Model model ) {
		model.addAttribute("insert_utente_attr", new UtenteDTO());
		return "utente/formreset";
	}
	
	@PostMapping("/resetpw")
	public String resetpw(Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {
 
		//PER OTTENERE L'OGGETTO PRINCIPAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
		//PER OTTENERE L'USERNAME
		String username = "";
		username = ((UserDetails)principal).getUsername();	
		
		//CARICO L'UTENTE IN SESSIONE PER CONFRONTARE LE DUE PASSWORD, QUELLA IN DB CON QUELLA CHE RICEVO DAL CLIENT
		Utente utenteInSessione = utenteService.findByUsername(username);
		
		utenteService.resettaUtente(request.getParameter("password"),request.getParameter("confermaPassword"),request.getParameter("vecchiaPassword"), utenteInSessione);
		
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/logout";
	}
	
	@GetMapping(value = "/searchUtenteAjax", produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String searchUtente(@RequestParam String term) {

		List<Utente> listaUtenteByTerm = utenteService.cercaByCognomeENomeILike(term);
		return buildJsonResponse(listaUtenteByTerm);
	}

	private String buildJsonResponse(List<Utente> listaRegisti) {
		JsonArray ja = new JsonArray();

		for (Utente utenteItem : listaRegisti) {
			JsonObject jo = new JsonObject();
			jo.addProperty("value", utenteItem.getId());
			jo.addProperty("label", utenteItem.getNome() + " " + utenteItem.getCognome());
			ja.add(jo);
		}

		return new Gson().toJson(ja);
	}
	
	@RequestMapping(value = "/registrazione", method = {RequestMethod.POST,RequestMethod.GET})
	public String showRegistrationForm( Model model) {
	    model.addAttribute("insert_utente_attr", new UtenteDTO());
	    return "signup";
	}
	
	@RequestMapping(value = "/signup", method = {RequestMethod.POST,RequestMethod.GET})
	public String signup(@Validated({ValidationWithPassword.class,ValidationNoPassword.class}) @ModelAttribute("insert_utente_attr") UtenteDTO utenteDTO, BindingResult result,
		Model model, RedirectAttributes redirectAttrs) {
			
		if (utenteService.usernameExist(utenteDTO.getUsername())) {
			redirectAttrs.addFlashAttribute("errorMessage", "Account già presente con questo username.");
			return "redirect:/signup";
		}
		if (!result.hasFieldErrors("password") && !utenteDTO.getPassword().equals(utenteDTO.getConfermaPassword()))
			result.rejectValue("confermaPassword", "password.diverse");
		if (result.hasErrors()) {
			return "signup";
		}
		utenteService.signUp(utenteDTO.buildUtenteModel(true));
		
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/login";

	}
	

}

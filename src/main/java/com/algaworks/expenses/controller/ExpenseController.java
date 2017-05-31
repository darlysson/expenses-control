package com.algaworks.expenses.controller;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.algaworks.expenses.model.Expense;
import com.algaworks.expenses.model.StatusExpense;
import com.algaworks.expenses.repository.filter.ExpenseFilter;
import com.algaworks.expenses.service.CreateExpenseService;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

	// Spring IoC. It is possible because I annotate @service in class Service.
	@Autowired
	private CreateExpenseService createExpenseService;

	@RequestMapping("/new")
	public ModelAndView newExpense() {
		ModelAndView mv = new ModelAndView("createExpense");
		mv.addObject(new Expense());
		return mv;
	}

	// Saving a new Expense
	@RequestMapping(method = RequestMethod.POST)
	public String save(@Validated Expense expense, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return "createExpense";
		}

		try {
			createExpenseService.save(expense);
			// Controller's task - Populate datas that the view will need.
			// Business rule must be done in the service layer
			attributes.addFlashAttribute("message", "Expense successfully saved");
			return "redirect:/expenses/new";
		} catch (IllegalArgumentException e) {
			errors.rejectValue("limitDate", "Null", e.getMessage());
			return "createExpense";
		}

	}

	// Searching
	@RequestMapping
	public ModelAndView search(@ModelAttribute("filter") ExpenseFilter filter) {
		List<Expense> allExpenses = createExpenseService.filter(filter);

		ModelAndView mv = new ModelAndView("searchExpenses");
		// Makes "allExpenses" be available in the view with the name "expenses"
		mv.addObject("expenses", allExpenses);
		return mv;
	}

	// Editing
	// Here I receive the parameter coming from browser i.e: expenses/10
	@RequestMapping("{id}")
	public ModelAndView edit(@PathVariable("id") Expense expense) {
		ModelAndView mv = new ModelAndView("createExpense");
		mv.addObject(expense);
		return mv;
	}

	// Deleting
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Long id, RedirectAttributes attributes) {
		createExpenseService.delete(id);
		attributes.addFlashAttribute("message", "Expense successfully deleted");
		return "redirect:/expenses";
	}
	
	// Receiving
	@RequestMapping(value = "/{id}/receive", method = RequestMethod.PUT)
	public @ResponseBody String receive(@PathVariable Long id) {
		return createExpenseService.receive(id);

	}

	@ModelAttribute("allStatusExpense")
	public List<StatusExpense> allStatusExpense() {
		return Arrays.asList(StatusExpense.values());
	}
}

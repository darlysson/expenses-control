package com.algaworks.expenses.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.expenses.model.Expense;
import com.algaworks.expenses.model.StatusExpense;
import com.algaworks.expenses.repository.ExpensesDAO;
import com.algaworks.expenses.repository.filter.ExpenseFilter;

@Service
public class CreateExpenseService {

	@Autowired // Injecting the repository into layer service
	private ExpensesDAO expenses;

	// Save
	public void save(Expense expense) {
		try {
			expenses.save(expense);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid date format");
		}
	}

	// Delete
	public void delete(Long id) {
		expenses.delete(id);

	}
	
	// Receive
	public String receive(Long id) {
		Expense expense = expenses.findOne(id);
		expense.setStatus(StatusExpense.RECEIVED);
		expenses.save(expense);

		return StatusExpense.RECEIVED.getDescription();

	}

	public List<Expense> filter(ExpenseFilter filter) {
		String description = filter.getDescription() == null ? "%" : filter.getDescription();
		return expenses.findByDescriptionContaining(description);

	}
}

package com.algaworks.expenses.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.expenses.model.Expense;

public interface ExpensesDAO extends JpaRepository<Expense, Long> {
	
	public List<Expense> findByDescriptionContaining(String description);
}

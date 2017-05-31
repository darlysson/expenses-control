package com.algaworks.expenses.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 60, message = "Description cannot be longer than 60 characters")
	@NotEmpty(message = "Description is a must")
	private String description;

	@NotNull(message = "Limit data is a must")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date limitDate;

	@DecimalMin(value = "0.01", message = "Value cannot be less than 0,01")
	@DecimalMax(value = "9999999.99", message = "Value cannot be longer than 9.999.999,99")
	@NotNull(message = "Value is a must")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal value;

	@Enumerated(EnumType.STRING)
	private StatusExpense status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public StatusExpense getStatus() {
		return status;
	}

	public void setStatus(StatusExpense status) {
		this.status = status;
	}

	public boolean isPending() {
		return StatusExpense.PENDING.equals(this.status);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		else if (obj == null) {
			return false;
		}

		else if (getClass() != obj.getClass()) {
			return false;
		}

		Expense other = (Expense) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}

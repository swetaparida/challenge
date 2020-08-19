package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class TransactionDetails {

	@NotNull
	@NotEmpty
	private String senderAccountId;

	@NotNull
	@NotEmpty
	private String recieverAccountId;

	@NotNull
	@Min(value = 1, message = "transaction ammount must be greater than 0")
	private BigDecimal amount;

	
	public TransactionDetails() {}
	public TransactionDetails(String senderAccountId, String recieverAccountId, BigDecimal amount) {
		super();
		this.senderAccountId = senderAccountId;
		this.recieverAccountId = recieverAccountId;
		this.amount = amount;
	}
	
	
	
}

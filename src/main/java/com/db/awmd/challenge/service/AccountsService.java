package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransactionDetails;
import com.db.awmd.challenge.exception.InsufficientAmountException;
import com.db.awmd.challenge.exception.InvalidAccountIdException;
import com.db.awmd.challenge.repository.AccountsRepository;
import lombok.Getter;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

	@Getter
	private final AccountsRepository accountsRepository;
	

	private final NotificationService notificationService;

	@Autowired
	public AccountsService(AccountsRepository accountsRepository,NotificationService notificationService) {
		this.accountsRepository = accountsRepository;
		this.notificationService=notificationService;
	}

	public void createAccount(Account account) {
		this.accountsRepository.createAccount(account);
	}

	public Account getAccount(String accountId) {
		return this.accountsRepository.getAccount(accountId);
	}


	public void transferAmount(TransactionDetails transactionDetails) {

		BigDecimal amount = transactionDetails.getAmount();
		String senderAccountId = transactionDetails.getSenderAccountId();
		String recieverAccountId = transactionDetails.getRecieverAccountId();
		
		this.accountsRepository.debit(senderAccountId,amount);
		this.accountsRepository.credit(recieverAccountId,amount);


	    Account senderAccount = this.accountsRepository.getAccount(senderAccountId);
	    Account recieverAccount = this.accountsRepository.getAccount(recieverAccountId);
		notificationService.notifyAboutTransfer(senderAccount, amount +" Ammount is sent to account "+recieverAccountId);
		notificationService.notifyAboutTransfer(recieverAccount, amount +" Ammount is recieved from account "+senderAccountId);
	}

	public void validateTranscationDetails(TransactionDetails transactionDetails) {
		Account senderAccount=this.accountsRepository.getAccount(transactionDetails.getSenderAccountId());
		Account recieverAccount=this.accountsRepository.getAccount(transactionDetails.getRecieverAccountId());

		if(senderAccount==null) {
			throw new InvalidAccountIdException(
					"sender Account id " + transactionDetails.getSenderAccountId() + " is invalid");
		}
		if(recieverAccount==null) {
			throw new InvalidAccountIdException(
					"sender Account id " + transactionDetails.getRecieverAccountId() + " is invalid");
		}
		
		
		if(senderAccount.getBalance().compareTo(transactionDetails.getAmount()) == -1) {
			throw new InsufficientAmountException("your's balance is Insufficient");
		}
	}
}

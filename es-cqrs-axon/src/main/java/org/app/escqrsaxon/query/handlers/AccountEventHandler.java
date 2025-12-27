package org.app.escqrsaxon.query.handlers;


import lombok.extern.slf4j.Slf4j;

import org.app.escqrsaxon.commands.events.*;
import org.app.escqrsaxon.enums.OperationType;
import org.app.escqrsaxon.query.entities.Account;
import org.app.escqrsaxon.query.entities.AccountOperation;
import org.app.escqrsaxon.query.repositories.AccountRepository;
import org.app.escqrsaxon.query.repositories.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountEventHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    private QueryUpdateEmitter queryUpdateEmitter;

    public AccountEventHandler(AccountRepository accountRepository, OperationRepository operationRepository, QueryUpdateEmitter queryUpdateEmitter) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.queryUpdateEmitter = queryUpdateEmitter;
    }

    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage eventMessage){
        log.info("################# Query Side - AccountCreatedEvent Received ################");
        Account account = Account.builder()
                .id(event.getAccountId())
                .balance(event.getInitialBalance())
                .currency(event.getCurrency())
                .status(event.getStatus())
                .createdAt(eventMessage.getTimestamp())
                .build();
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("################# Query Side - AccountActivatedEvent Received ################");
        Account account = accountRepository.findById(event.getAccountId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountStatusUpdatedEvent event){
        log.info("################# Query Side - AccountStatusUpdatedEvent Received ################");
        Account account = accountRepository.findById(event.getAccountId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent event, EventMessage eventMessage){
        log.info("################# Query Side - AccountDebitedEvent Received ################");
        Account account = accountRepository.findById(event.getAccountId()).get();
        AccountOperation accountOperation = AccountOperation.builder()
                .amount(event.getAmount())
                .date(eventMessage.getTimestamp())
                .type(OperationType.DEBIT)
                .currency(event.getCurrency())
                .account(account)
                .build();
        operationRepository.save(accountOperation);
        account.setBalance(account.getBalance() - accountOperation.getAmount());
        accountRepository.save(account);
        queryUpdateEmitter.emit(e -> true, accountOperation);
    }

    @EventHandler
    public void on(AccountCreditedEvent event, EventMessage eventMessage){
        log.info("################# Query Side - AccountCreditedEvent Received ################");
        Account account = accountRepository.findById(event.getAccountId()).get();
        AccountOperation accountOperation = AccountOperation.builder()
                .amount(event.getAmount())
                .date(eventMessage.getTimestamp())
                .type(OperationType.CREDIT)
                .currency(event.getCurrency())
                .account(account)
                .build();
        operationRepository.save(accountOperation);
        account.setBalance(account.getBalance() + accountOperation.getAmount());
        accountRepository.save(account);
        queryUpdateEmitter.emit(e -> true, accountOperation);
    }

}

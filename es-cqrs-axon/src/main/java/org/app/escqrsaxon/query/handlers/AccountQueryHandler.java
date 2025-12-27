package org.app.escqrsaxon.query.handlers;


import org.app.escqrsaxon.query.dtos.AccountStatementResponseDTO;
import org.app.escqrsaxon.query.entities.Account;
import org.app.escqrsaxon.query.entities.AccountOperation;
import org.app.escqrsaxon.query.queries.GetAccountStatementQuery;
import org.app.escqrsaxon.query.queries.GetAllAccountsQuery;
import org.app.escqrsaxon.query.repositories.AccountRepository;
import org.app.escqrsaxon.query.repositories.OperationRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountQueryHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public AccountQueryHandler(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query){
        return accountRepository.findAll();
    }


    @QueryHandler
    public AccountStatementResponseDTO on(GetAccountStatementQuery query){
        Account account = accountRepository.findById(query.getAccountId()).get();
        List<AccountOperation> operations = operationRepository.findByAccountId(query.getAccountId());
        return new AccountStatementResponseDTO(account, operations);
    }
}
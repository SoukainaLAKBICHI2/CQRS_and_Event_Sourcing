package org.app.escqrsaxon.query.Controllers;



import org.app.escqrsaxon.query.dtos.AccountStatementResponseDTO;
import org.app.escqrsaxon.query.entities.Account;
import org.app.escqrsaxon.query.queries.GetAccountStatementQuery;
import org.app.escqrsaxon.query.queries.GetAllAccountsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query/accounts")
@CrossOrigin("*")
public class AccountQueryController {
    private QueryGateway queryGateway;

    public AccountQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/all")
    public CompletableFuture<List<Account>> getAllAccounts(){
        CompletableFuture<List<Account>> response = queryGateway.query(
                new GetAllAccountsQuery(), ResponseTypes.multipleInstancesOf(Account.class)
        );
        return response;
    }
    @GetMapping("/statement/{accountId}")
    public CompletableFuture<AccountStatementResponseDTO> getAccountStatement(@PathVariable String accountId){
        CompletableFuture<AccountStatementResponseDTO> response = queryGateway.query(new GetAccountStatementQuery(accountId), ResponseTypes.instanceOf(AccountStatementResponseDTO.class));
        return response;
    }
}

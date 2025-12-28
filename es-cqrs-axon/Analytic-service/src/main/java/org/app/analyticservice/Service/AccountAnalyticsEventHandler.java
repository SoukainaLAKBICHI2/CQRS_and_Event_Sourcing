package org.app.analyticservice.Service;



import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.analyticservice.entities.AccountAnalytics;
import org.app.analyticservice.queries.GetAccountAnalyticsByAccountId;
import org.app.analyticservice.queries.GetAllAccountAnalytics;
import org.app.analyticservice.repositories.AccountAnalyticsRepo;
import org.app.escqrsaxon.commands.events.AccountCreatedEvent;
import org.app.escqrsaxon.commands.events.AccountCreditedEvent;
import org.app.escqrsaxon.commands.events.AccountDebitedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AccountAnalyticsEventHandler {
    private AccountAnalyticsRepo accountAnalyticsRepo;
    private QueryUpdateEmitter queryUpdateEmitter;

    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("AccountCreatedEvent received");
        AccountAnalytics accountAnalytics=AccountAnalytics.builder()
                .accountId(event.getAccountId())
                .status(event.getStatus().toString())
                .balance(event.getInitialBalance())
                .totalDebit(0)
                .totalCredit(0)
                .numberDebitOperations(0)
                .numberCreditOperations(0)
                .build();
        accountAnalyticsRepo.save(accountAnalytics);
    }
    @EventHandler
    public void on(AccountCreditedEvent event){
        log.info("AccountCreatedEvent received");
        AccountAnalytics accountAnalytics=accountAnalyticsRepo.findByAccountId(event.getAccountId());
        accountAnalytics.setTotalCredit(accountAnalytics.getTotalCredit()+event.getAmount());
        accountAnalytics.setNumberCreditOperations(accountAnalytics.getNumberCreditOperations()+1);
        accountAnalytics.setBalance(accountAnalytics.getBalance()+event.getAmount());
        accountAnalyticsRepo.save(accountAnalytics);
        queryUpdateEmitter.emit(GetAccountAnalyticsByAccountId.class,(query)->query.getAccountId().equals(event.getAccountId()),accountAnalytics);
    }
    @EventHandler
    public void on(AccountDebitedEvent event){
        log.info("AccountDebitedEvent received");
        AccountAnalytics accountAnalytics=accountAnalyticsRepo.findByAccountId(event.getAccountId());
        accountAnalytics.setTotalDebit(accountAnalytics.getTotalDebit()+event.getAmount());
        accountAnalytics.setNumberDebitOperations(accountAnalytics.getNumberDebitOperations()+1);
        accountAnalytics.setBalance(accountAnalytics.getBalance()-event.getAmount());
        accountAnalyticsRepo.save(accountAnalytics);
        queryUpdateEmitter.emit(GetAccountAnalyticsByAccountId.class,(query)->query.getAccountId().equals(event.getAccountId()),accountAnalytics);
    }
    @QueryHandler
    public List<AccountAnalytics> on(GetAllAccountAnalytics query){
        return accountAnalyticsRepo.findAll();
    }
    @QueryHandler
    public AccountAnalytics on(GetAccountAnalyticsByAccountId query){
        return accountAnalyticsRepo.findByAccountId(query.getAccountId());
    }
}
package org.app.escqrsaxon.commands.aggregates;

import lombok.extern.slf4j.Slf4j;
import org.app.escqrsaxon.commands.command.AddAccountCommand;
import org.app.escqrsaxon.commands.events.AccountCreatedEvent;
import org.app.escqrsaxon.enums.AccountStatus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private AccountStatus status;
    // Pour Axon
    public AccountAggregate(){}

    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        log.info("############### AddAccountCommand Received ###########");
        if(command.getInitialBalance()<=0) throw new IllegalArgumentException("Balance must be positive");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                AccountStatus.CREATED,
                command.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        log.info("############### AccountCreatedEvent Occured ###########");
        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
    }
}
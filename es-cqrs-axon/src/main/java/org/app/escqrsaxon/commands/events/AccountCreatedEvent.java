package org.app.escqrsaxon.commands.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.app.escqrsaxon.enums.AccountStatus;

@Getter @AllArgsConstructor
public class AccountCreatedEvent {
    private String accountId;
    private double initialBalance;
    private AccountStatus status;
    private String currency;
}

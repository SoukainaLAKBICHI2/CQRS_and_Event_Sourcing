package org.app.escqrsaxon.commands.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.app.escqrsaxon.enums.AccountStatus;

@Getter @AllArgsConstructor
public class AccountStatusUpdatedEvent {
    private String accountId;
    private AccountStatus status;
}

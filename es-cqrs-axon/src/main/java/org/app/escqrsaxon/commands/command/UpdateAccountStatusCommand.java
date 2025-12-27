package org.app.escqrsaxon.commands.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.app.escqrsaxon.enums.AccountStatus;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter @AllArgsConstructor
public class UpdateAccountStatusCommand {
    @TargetAggregateIdentifier
    private String id;
    private AccountStatus status;
}

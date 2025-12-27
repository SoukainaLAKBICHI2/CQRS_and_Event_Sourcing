package org.app.escqrsaxon.commands.dtos;


import org.app.escqrsaxon.enums.AccountStatus;

public record UpdateAccountStatusRequestDTO(String accountId, AccountStatus status) {
}

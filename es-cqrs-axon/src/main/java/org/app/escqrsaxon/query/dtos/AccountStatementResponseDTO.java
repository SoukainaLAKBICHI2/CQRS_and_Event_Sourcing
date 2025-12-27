package org.app.escqrsaxon.query.dtos;


import org.app.escqrsaxon.query.entities.Account;
import org.app.escqrsaxon.query.entities.AccountOperation;

import java.util.List;

public record AccountStatementResponseDTO(Account account, List<AccountOperation> operations) {
}
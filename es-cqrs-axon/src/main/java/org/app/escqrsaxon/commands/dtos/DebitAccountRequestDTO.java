package org.app.escqrsaxon.commands.dtos;


public record DebitAccountRequestDTO(String accountId, double amount, String currency) {
}

package com.epam.training.ticketservice.cli;

import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

import org.jline.utils.AttributedString;

@Component
public class TicketServiceCommandPrompt implements PromptProvider {
    @Override
    public AttributedString getPrompt() {
        return new AttributedString("Ticket service> ");
    }

}

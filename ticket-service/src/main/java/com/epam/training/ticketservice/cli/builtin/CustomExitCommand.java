package com.epam.training.ticketservice.cli.builtin;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Quit;

@ShellComponent
public class CustomExitCommand implements Quit.Command {

    @ShellMethod(value = "Exit the shell,", key = {"quit", "exit", "terminate"})
    public void quit() {
        System.out.println("Exiting...");
        System.exit(0);
    }
}

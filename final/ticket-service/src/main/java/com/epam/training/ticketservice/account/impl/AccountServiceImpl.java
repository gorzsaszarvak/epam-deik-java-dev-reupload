package com.epam.training.ticketservice.account.impl;

import com.epam.training.ticketservice.account.AccountService;
import com.epam.training.ticketservice.account.exception.*;
import com.epam.training.ticketservice.account.persistence.AccountRepository;
import com.epam.training.ticketservice.account.persistence.AdminAccount;
import com.epam.training.ticketservice.account.persistence.UserAccount;
import org.springframework.stereotype.Component;


@Component
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

//    @Override
//    public void createAdminAccount(String username, String password) {
//        if(!accountRepository.findAccountByUsername(username).isPresent()){
//            accountRepository.save(new AdminAccount(username, password));
//        } else {
//            throw new UsernameAlreadyExistsException(username);
//        }
//
//    }

    @Override
    public void createUserAccount(String username, String password) {
        if(!accountRepository.findAccountByUsername(username).isPresent()){
            accountRepository.save(new UserAccount(username, password));
        } else {
            throw new UsernameAlreadyExistsException(username);
        }
    }

    @Override
    public void signIn(String username, String password) {
        var accountToLogInto = accountRepository.findAccountByUsername(username);


        if(accountToLogInto.isPresent()) {
            if(!accountRepository.findAccountByActive(true).isPresent()) {
                if (accountToLogInto.get().getPassword().equals(password)) {
                    accountToLogInto.get().setActive(true);
                } else {
                    throw new WrongPasswordException(username);}
            } else {
                    var activeAccount = accountRepository.findAccountByActive(true).get().getUsername();
                    throw new AlreadyLoggedInException(activeAccount);}
        } else {
            throw new AccountDoesntExistException(username);}
    }

    @Override
    public String describeAccount() {
        var activeAccount = accountRepository.findAccountByActive(true);
        if(activeAccount.isPresent()) {
            return activeAccount.get().toString();
        } else {
            throw new NotLoggedInException();
        }
    }

    @Override
    public Boolean loggedInAsAdmin() {
        var activeAccount = accountRepository.findAccountByActive(true);
        return activeAccount.getClass().equals(AdminAccount.class);
    }
}

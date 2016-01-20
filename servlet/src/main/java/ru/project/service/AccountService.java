package ru.project.service;

import ru.project.model.Account;

public interface AccountService {

    int createAccount(Account account);

    Account findByAgentId(int id);

}

package sample.multimodule.service;

import sample.multimodule.domain.AccountEntity;

public interface AccountService {

    /**
     * Finds the account with the provided account number.
     *
     * @param number The account number
     * @return The account
     * @throws AccountNotFoundException If no such account exists.
     */
    AccountEntity findOne(String number) throws AccountNotFoundException;

    /**
     * Creates a new account.
     *
     * @param number
     * @return created account
     */
    AccountEntity createAccountByNumber(String number);
}

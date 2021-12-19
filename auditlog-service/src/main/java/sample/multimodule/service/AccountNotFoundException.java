package sample.multimodule.service;

public class AccountNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3891534644498426670L;

    public AccountNotFoundException(String accountId) {
        super("No such account with id: " + accountId);
    }
}

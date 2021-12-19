package sample.multimodule.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sample.multimodule.domain.AccountEntity;
import sample.multimodule.repository.AccountRepository;
import sample.multimodule.service.AccountNotFoundException;
import sample.multimodule.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Value("${spring.application.dummy.type}")
    private String dummyType;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountEntity findOne(String number) throws AccountNotFoundException {
        if (number.equals("0000")) {
            throw new AccountNotFoundException("0000");
        }
        AccountEntity account = accountRepository.findByNumber(number);
        if (account == null) {
            account = createAccountByNumber(number);
        }
        return account;
    }

    @Override
    public AccountEntity createAccountByNumber(String number) {
        AccountEntity account = new AccountEntity();
        account.setNumber(number);
        account.setType("SAVING");
        return accountRepository.save(account);
    }

    public String getDummyType() {
        return dummyType;
    }

    public void setDummyType(String dummyType) {
        this.dummyType = dummyType;
    }

}

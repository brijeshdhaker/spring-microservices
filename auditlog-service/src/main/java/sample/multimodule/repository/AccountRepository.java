package sample.multimodule.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sample.multimodule.domain.AccountEntity;

@Repository("accountRepository")
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

    AccountEntity findByNumber(String number);
    
}

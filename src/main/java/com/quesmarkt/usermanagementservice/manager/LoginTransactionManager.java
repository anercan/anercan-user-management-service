package com.quesmarkt.usermanagementservice.manager;

import com.quesmarkt.usermanagementservice.data.entity.LoginTransaction;
import com.quesmarkt.usermanagementservice.data.repository.LoginTransactionRepository;
import com.quesmarkt.usermanagementservice.manager.exception.DataAccessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class LoginTransactionManager extends BaseManager {

    private final LoginTransactionRepository loginTransactionRepository;

    public void saveNewLoginTransaction(LoginTransaction loginTransaction) {
        try {
            loginTransactionRepository.save(loginTransaction);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


}

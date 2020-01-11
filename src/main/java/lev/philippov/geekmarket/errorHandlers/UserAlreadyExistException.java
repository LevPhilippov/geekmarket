package lev.philippov.geekmarket.errorHandlers;

import javax.persistence.NonUniqueResultException;

public class UserAlreadyExistException extends NonUniqueResultException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}

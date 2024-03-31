package edu.java.services.interfaces;

import edu.java.services.exceptions.AlreadyExistException;
import edu.java.services.exceptions.NotExistException;
import edu.java.services.exceptions.RepeatedRegistrationException;

public interface TgChatService {
    void register(long tgChatId) throws AlreadyExistException, RepeatedRegistrationException;

    void unregister(long tgChatId) throws NotExistException;
}

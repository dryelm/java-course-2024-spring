package edu.java.services.botSender;

import edu.java.UpdateRequestDto;

public interface BotSender {
    void send(UpdateRequestDto update);
}

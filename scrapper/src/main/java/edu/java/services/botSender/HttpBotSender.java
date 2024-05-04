package edu.java.services.botSender;

import edu.java.UpdateRequestDto;
import edu.java.clients.bot.UpdateBotClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpBotSender implements BotSender {
    private final UpdateBotClient client;

    @Override
    public void send(UpdateRequestDto update) {
        client.setUpdates(update);
    }
}

package edu.java.bot.utils;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.UpdateRequestDto;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UpdateParserUtil {

    public List<SendMessage> toRequestList(UpdateRequestDto updateRequestDto) {
        List<SendMessage> requests = new ArrayList<>();

        for (Long chatID : updateRequestDto.tgChatIds()) {
            String message = String.format(
                "*Пришло обновление!*\n\nСсылка: %s\n%s",
                updateRequestDto.url(),
                updateRequestDto.description()
            );

            requests.add(new SendMessage(chatID, message)
                .parseMode(ParseMode.Markdown)
                .disableWebPagePreview(true));
        }

        return requests;
    }
}

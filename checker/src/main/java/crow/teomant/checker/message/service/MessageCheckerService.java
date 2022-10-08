package crow.teomant.checker.message.service;

import crow.teomant.checker.message.repository.MessageCheckerMongoRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageCheckerService {
    private final MessageCheckerMongoRepository messageCheckerMongoRepository;

    public Boolean checkMessageChatAndAuthor(UUID message, UUID chat, UUID author) {
        return messageCheckerMongoRepository.findById(message)
            .map(m -> m.getChat().equals(chat) && m.getAuthor().equals(author))
            .orElse(false);
    }

    public Boolean checkMessageAndChat(UUID message, UUID chat) {
        return messageCheckerMongoRepository.findById(message)
            .map(m -> m.getChat().equals(chat))
            .orElse(false);
    }
}

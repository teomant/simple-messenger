package crow.teomant.messageservice.message.service;

import crow.teomant.messageservice.message.model.Message;
import crow.teomant.messageservice.message.repository.MessageMongoRepository;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageMongoRepository repository;

    public Collection<Message> getMessages(UUID chatId, LocalDateTime from, LocalDateTime to) {
        return repository.findByChatAndTimestampBetween(chatId, from, to);
    }
}

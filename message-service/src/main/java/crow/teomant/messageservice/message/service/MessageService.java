package crow.teomant.messageservice.message.service;

import crow.teomant.messagecommon.model.Message;
import crow.teomant.messagecommon.repository.MessageMongoRepository;
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

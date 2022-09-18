package crow.teomant.messageservice.message.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import crow.teomant.messageservice.message.model.Message;
import crow.teomant.messageservice.message.repository.MessageMongoRepository;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageMongoRepository repository;
    private final JmsMessagingTemplate jmsMessagingTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public UUID sendTextMessage(UUID from, UUID to, UUID correlationId, String content) {

        jmsMessagingTemplate.convertAndSend("message.text.create",
            objectMapper.writeValueAsString(new TextMessageCreate(from, to, correlationId, content))
        );

        return correlationId;
    }

    public Collection<Message> getMessages(UUID chatId, LocalDateTime from, LocalDateTime to) {
        return repository.findByChatAndTimestampBetween(chatId, from, to);
    }

    @Data
    @AllArgsConstructor
    private static class TextMessageCreate {
        private UUID from;
        private UUID to;
        private UUID correlationId;
        String content;
    }
}

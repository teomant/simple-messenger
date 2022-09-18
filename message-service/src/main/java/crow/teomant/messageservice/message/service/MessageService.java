package crow.teomant.messageservice.message.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import crow.teomant.messageservice.message.model.Message;
import crow.teomant.messageservice.message.model.TextMessage;
import crow.teomant.messageservice.message.repository.MessageMongoRepository;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.annotation.JmsListener;
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
        SearchResponse searchResponse1 =
            objectMapper.readValue(
                jmsMessagingTemplate.convertSendAndReceive("user.search",
                    objectMapper.writeValueAsString(new SearchRequest(from)), String.class),
                SearchResponse.class
            );
        SearchResponse searchResponse2 =
            objectMapper.readValue(
                jmsMessagingTemplate.convertSendAndReceive("chat.search",
                    objectMapper.writeValueAsString(new SearchRequest(to)), String.class),
                SearchResponse.class
            );

        if (!searchResponse1.getResult()) {
            throw new IllegalArgumentException();
        }

        if (!searchResponse2.getResult()) {
            throw new IllegalArgumentException();
        }

        jmsMessagingTemplate.convertAndSend("message.save",
            new TextMessage(UUID.randomUUID(), from, to, LocalDateTime.now(), correlationId, content));

        return correlationId;
    }

    public Collection<Message> getMessages(UUID chatId, LocalDateTime from, LocalDateTime to) {
        return repository.findByChatAndTimestampBetween(chatId, from, to);
    }

    @Data
    private static class SearchResponse {

        private Boolean result;
    }

    @Data
    @AllArgsConstructor
    private static class SearchRequest {
        private UUID id;
    }

    @JmsListener(destination = "message.save")
    public void saveMessage(Message message) {
        repository.save(message);
    }
}

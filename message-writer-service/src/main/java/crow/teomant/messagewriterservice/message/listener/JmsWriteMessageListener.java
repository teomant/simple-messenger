package crow.teomant.messagewriterservice.message.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import crow.teomant.messagewriterservice.message.model.Message;
import crow.teomant.messagewriterservice.message.model.ReplaceMessage;
import crow.teomant.messagewriterservice.message.repository.MessageMongoRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JmsWriteMessageListener {
    private final JmsMessagingTemplate jmsMessagingTemplate;
    private final ObjectMapper objectMapper;
    private final MessageMongoRepository repository;

    @JmsListener(destination = "message.text.create")
    @SneakyThrows
    public void sendTextMessage(String request) {
        TextMessageCreate create = objectMapper.readValue(request, TextMessageCreate.class);
        SearchResponse userSearchResponse =
            objectMapper.readValue(
                jmsMessagingTemplate.convertSendAndReceive("user.search",
                    objectMapper.writeValueAsString(new SearchRequest(create.from)), String.class),
                SearchResponse.class
            );
        SearchResponse chatSearchResponse =
            objectMapper.readValue(
                jmsMessagingTemplate.convertSendAndReceive("chat.search",
                    objectMapper.writeValueAsString(new SearchRequest(create.to)), String.class),
                SearchResponse.class
            );

        if (!userSearchResponse.getResult()) {
            throw new IllegalArgumentException();
        }

        if (!chatSearchResponse.getResult()) {
            throw new IllegalArgumentException();
        }

        repository.save(
            new Message(create.id, create.from, create.to, LocalDateTime.now(),
                new Message.TextMessageContent(create.content))
        );
    }

    @JmsListener(destination = "message.text.replace")
    @SneakyThrows
    public void replaceTextMessage(String request) {
        TextMessageReplaceCreate create = objectMapper.readValue(request, TextMessageReplaceCreate.class);

        Message message = repository.findById(create.getReplaceId()).orElseThrow(IllegalArgumentException::new);

        if (objectMapper.convertValue(message.getMessageContent(),
            Message.MessageContent.class) instanceof Message.TextMessageContent) {

            if (!message.getAuthor().equals(create.getFrom()) || !message.getChat().equals(create.getTo())) {
                throw new IllegalArgumentException();
            }

            repository.save(
                new ReplaceMessage(create.id, create.from, create.to, LocalDateTime.now(),
                    new Message.TextMessageContent(create.content), create.replaceId)
            );
        } else {
            throw new IllegalArgumentException();
        }
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

    @Data
    @AllArgsConstructor
    public static class TextMessageCreate {
        private UUID id;
        private UUID from;
        private UUID to;
        private String content;
    }

    @Data
    @AllArgsConstructor
    public static class TextMessageReplaceCreate {
        private UUID id;
        private UUID replaceId;
        private UUID from;
        private UUID to;
        private String content;
    }
}

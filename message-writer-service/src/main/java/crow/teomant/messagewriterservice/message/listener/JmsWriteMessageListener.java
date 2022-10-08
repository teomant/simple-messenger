package crow.teomant.messagewriterservice.message.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import crow.teomant.checker.chat.service.ChatCheckerService;
import crow.teomant.checker.user.service.UserCheckerService;
import crow.teomant.messagecommon.model.Message;
import crow.teomant.messagecommon.model.ReplaceMessage;
import crow.teomant.messagecommon.repository.MessageMongoRepository;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JmsWriteMessageListener {
    private final ObjectMapper objectMapper;
    private final MessageMongoRepository repository;
    private final UserCheckerService userCheckerService;
    private final ChatCheckerService chatCheckerService;

    @JmsListener(destination = "message.text.create")
    @SneakyThrows
    public void sendTextMessage(String request) {
        TextMessageCreate create = objectMapper.readValue(request, TextMessageCreate.class);

        if (!userCheckerService.checkUser(create.from)) {
            throw new IllegalArgumentException();
        }

        if (!chatCheckerService.chatExistsAndParticipantIn(create.to, create.from)) {
            throw new IllegalArgumentException();
        }

        repository.save(
            new Message(create.id, create.from, create.to, LocalDateTime.now(),
                new Message.TextMessageContent(create.content))
        );
    }

    @JmsListener(destination = "message.vote.create")
    @SneakyThrows
    public void sendVoteMessage(String request) {
        VoteMessageCreate create = objectMapper.readValue(request, VoteMessageCreate.class);

        if (!userCheckerService.checkUser(create.from)) {
            throw new IllegalArgumentException();
        }

        if (!chatCheckerService.chatExistsAndParticipantIn(create.to, create.from)) {
            throw new IllegalArgumentException();
        }

        repository.save(
            new Message(create.id, create.from, create.to, LocalDateTime.now(),
                new Message.VoteMessageContent(create.content))
        );
    }

    @JmsListener(destination = "message.text.replace")
    @SneakyThrows
    public void replaceTextMessage(String request) {
        TextMessageReplaceCreate create = objectMapper.readValue(request, TextMessageReplaceCreate.class);

        Message message = repository.findById(create.getReplaceId()).orElseThrow(IllegalArgumentException::new);

        if (!chatCheckerService.chatExistsAndParticipantIn(message.getChat(), message.getAuthor())) {
            throw new IllegalArgumentException();
        }

        if (!message.getAuthor().equals(create.getFrom()) || !message.getChat().equals(create.getTo())) {
            throw new IllegalArgumentException();
        }

        if (objectMapper.convertValue(message.getMessageContent(),
            Message.MessageContent.class) instanceof Message.TextMessageContent) {

            repository.save(
                new ReplaceMessage(create.id, create.from, create.to, LocalDateTime.now(),
                    new Message.TextMessageContent(create.content), create.replaceId)
            );
        } else {
            throw new IllegalArgumentException();
        }
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
    public static class VoteMessageCreate {
        private UUID id;
        private UUID from;
        private UUID to;
        private Map<Integer, String> content;
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

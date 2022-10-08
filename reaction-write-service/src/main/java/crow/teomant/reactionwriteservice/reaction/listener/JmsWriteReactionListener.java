package crow.teomant.reactionwriteservice.reaction.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import crow.teomant.checker.message.service.MessageCheckerService;
import crow.teomant.checker.user.service.UserCheckerService;
import crow.teomant.reactioncommon.model.Reaction;
import crow.teomant.reactioncommon.repository.ReactionMongoRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JmsWriteReactionListener {
    private final ObjectMapper objectMapper;
    private final ReactionMongoRepository repository;
    private final MessageCheckerService messageCheckerService;
    private final UserCheckerService userCheckerService;

    @JmsListener(destination = "reaction.saw")
    @SneakyThrows
    public void sendTextMessage(String request) {
        SawCreate create = objectMapper.readValue(request, SawCreate.class);

        if (!userCheckerService.checkUser(create.author)) {
            throw new IllegalArgumentException();
        }

        if (!messageCheckerService.checkMessageAndChat(create.message, create.chat)) {
            throw new IllegalArgumentException();
        }

        repository.save(
            new Reaction(create.id, create.author, create.chat, create.message, LocalDateTime.now(),
                new Reaction.SawReaction())
        );
    }

    @JmsListener(destination = "reaction.vote")
    @SneakyThrows
    public void sendVoteMessage(String request) {
        VoteCreate create = objectMapper.readValue(request, VoteCreate.class);

        if (!userCheckerService.checkUser(create.author)) {
            throw new IllegalArgumentException();
        }

        if (!messageCheckerService.checkMessageAndChat(create.message, create.chat)) {
            throw new IllegalArgumentException();
        }

        repository.save(
            new Reaction(create.id, create.author, create.chat, create.message, LocalDateTime.now(),
                new Reaction.VoteReaction(create.option))
        );
    }

    @Data
    @AllArgsConstructor
    public static class SawCreate {
        private UUID id;
        private UUID author;
        private UUID chat;
        private UUID message;
    }

    @Data
    @AllArgsConstructor
    public static class VoteCreate {
        private UUID id;
        private UUID author;
        private UUID chat;
        private UUID message;
        private Integer option;
    }
}

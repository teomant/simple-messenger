package crow.teomant.reactionwriteservice.reaction.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import crow.teomant.reactionwriteservice.reaction.listener.JmsWriteReactionListener;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reaction")
@RequiredArgsConstructor
public class ReactionController {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @PostMapping
    public UUID create(@RequestBody CreateDto dto) {

        UUID id = UUID.randomUUID();
        jmsTemplate.convertAndSend("reaction.saw",
            objectMapper.writeValueAsString(
                new JmsWriteReactionListener.SawCreate(id, dto.author, dto.chat, dto.message))
        );
        return id;
    }

    @SneakyThrows
    @PostMapping("/vote")
    public UUID createVote(@RequestBody CreateVoteDto dto) {

        UUID id = UUID.randomUUID();
        jmsTemplate.convertAndSend("reaction.vote",
            objectMapper.writeValueAsString(
                new JmsWriteReactionListener.VoteCreate(id, dto.author, dto.chat, dto.message, dto.option))
        );
        return id;
    }

    @Data
    public static class CreateDto {
        private UUID author;
        private UUID chat;
        private UUID message;
    }

    @Data
    public static class CreateVoteDto {
        private UUID author;
        private UUID chat;
        private UUID message;
        private Integer option;
    }

}

package crow.teomant.messagewriterservice.message.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import crow.teomant.messagewriterservice.message.listener.JmsWriteMessageListener;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @PostMapping
    public UUID create(@RequestBody CreateDto dto) {

        UUID id = UUID.randomUUID();
        jmsTemplate.convertAndSend("message.text.create",
            objectMapper.writeValueAsString(
                new JmsWriteMessageListener.TextMessageCreate(id, dto.from, dto.to, dto.content))
        );
        return id;
    }

    @SneakyThrows
    @PostMapping("/{replaceId}")
    public UUID replace(@PathVariable("replaceId") UUID replaceId, @RequestBody CreateDto dto) {

        UUID id = UUID.randomUUID();
        jmsTemplate.convertAndSend("message.text.replace",
            objectMapper.writeValueAsString(
                new JmsWriteMessageListener.TextMessageReplaceCreate(id, replaceId, dto.from, dto.to, dto.content))
        );
        return id;
    }

    @Data
    public static class CreateDto {
        private UUID from;
        private UUID to;
        private String content;
    }

}

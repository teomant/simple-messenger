package crow.teomant.messageservice.message.web;

import crow.teomant.messageservice.message.model.Message;
import crow.teomant.messageservice.message.service.MessageService;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public UUID create(@RequestBody CreateDto dto) {
        return messageService.sendTextMessage(dto.from, dto.to, UUID.randomUUID(), dto.content);
    }

    @PostMapping("/{id}")
    public Collection<Message> get(@PathVariable UUID id, @RequestBody GetDto dto) {
        return messageService.getMessages(id, dto.from, dto.to);
    }

    @Data
    public static class CreateDto {
        private UUID from;
        private UUID to;
        private String content;
        private UUID correlationId;
    }

    @Data
    public static class GetDto {
        private LocalDateTime from;
        private LocalDateTime to;
    }
}

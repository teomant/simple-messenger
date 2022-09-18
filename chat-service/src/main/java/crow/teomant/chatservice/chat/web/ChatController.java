package crow.teomant.chatservice.chat.web;

import crow.teomant.chatservice.chat.model.Chat;
import crow.teomant.chatservice.chat.service.ChatService;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/users")
    public Chat createUsers(@RequestBody UsersDto dto) {
        return chatService.createUserChat(dto.user1Id, dto.user2Id);
    }

    @PostMapping("/public")
    public Chat createPublic(@RequestBody PublicDto dto) {
        return chatService.createPublicChat(dto.userId, dto.participants, dto.name, dto.about);
    }

    @Data
    public static class UsersDto {
        private UUID user1Id;
        private UUID user2Id;
    }

    @Data
    public static class PublicDto {
        public String about;
        public Set<UUID> participants;
        public String name;
        private UUID userId;
    }
}

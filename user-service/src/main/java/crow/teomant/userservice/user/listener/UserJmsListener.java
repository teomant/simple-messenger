package crow.teomant.userservice.user.listener;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import crow.teomant.userservice.user.model.User;
import crow.teomant.userservice.user.repository.UserMongoRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserJmsListener {
    private final UserMongoRepository userMongoRepository;
    private final ObjectMapper objectMapper;

    @JmsListener(destination = "user.chat.added")
    @SneakyThrows
    public void chatAdded(String chatAdded) {
        ChatAdded added = objectMapper.readValue(chatAdded, ChatAdded.class);

        User user = userMongoRepository.findById(added.getUserId()).orElseThrow(IllegalStateException::new);
        user.getChatIds().add(added.getChatId());
        userMongoRepository.save(user);
    }

    @Data
    private static class ChatAdded {

        @JsonCreator
        public ChatAdded(@JsonProperty("userId") UUID userId, @JsonProperty("chatId") UUID chatId) {
            this.userId = userId;
            this.chatId = chatId;
        }

        private UUID userId;
        private UUID chatId;
    }
}

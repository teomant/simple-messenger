package crow.teomant.checker.chat.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import crow.teomant.checker.chat.model.Chat;
import crow.teomant.checker.chat.model.UsersChat;
import crow.teomant.checker.chat.repository.ChatCheckerMongoRepository;
import crow.teomant.checker.chat.repository.PublicChatCheckerMongoRepository;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatCheckerService {

    private final ChatCheckerMongoRepository chatCheckerMongoRepository;
    private final PublicChatCheckerMongoRepository publicChatCheckerMongoRepository;
    private final Ignite ignite;
    private final ObjectMapper objectMapper;

    public Boolean chatExists(UUID id) {
        return chatCheckerMongoRepository.findById(id).isPresent();
    }

    @SneakyThrows
    public Boolean chatExistsAndParticipantIn(UUID id, UUID participant) {

        IgniteCache<String, String> cache = ignite.getOrCreateCache("chat-cache");
        String participants = cache.get(id.toString());

        if (Objects.nonNull(participants)) {
            Set<UUID> ids = objectMapper.readValue(participants, new TypeReference<Set<UUID>>() {
            });
            return ids.contains(participant);
        }

        Optional<Chat> chatOptional = chatCheckerMongoRepository.findById(id);
        if (chatOptional.isEmpty()) {
            return false;
        }

        cache.put(chatOptional.get().getId().toString(),
            objectMapper.writeValueAsString(chatOptional.get().getParticipants()));
        return chatOptional.map(chat -> chat.getParticipants().contains(participant))
            .orElse(false);
    }

    public Boolean chatBetweenUsers(UUID user1Id, UUID user2Id) {
        return chatCheckerMongoRepository.findByParticipantsContains(Set.of(user1Id, user2Id)).stream()
            .anyMatch(chat -> chat instanceof UsersChat &&
                chat.getParticipants().containsAll(Arrays.asList(user1Id, user2Id)));
    }

    public Boolean checkPublicChatName(String name) {
        return publicChatCheckerMongoRepository.findByName(name).isPresent();
    }
}

package crow.teomant.checker.chat.service;

import crow.teomant.checker.chat.model.UsersChat;
import crow.teomant.checker.chat.repository.ChatCheckerMongoRepository;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatCheckerService {

    private final ChatCheckerMongoRepository chatCheckerMongoRepository;

    public Boolean chatExists(UUID id) {
        return chatCheckerMongoRepository.findById(id).isPresent();
    }

    public Boolean chatExistsAndParticipantIn(UUID id, UUID participant) {
        return chatCheckerMongoRepository.findById(id).map(chat -> chat.getParticipants().contains(participant))
            .orElse(false);
    }

    public Boolean chatBetweenUsers(UUID user1Id, UUID user2Id) {
        return chatCheckerMongoRepository.findByParticipantsContains(Set.of(user1Id, user2Id)).stream()
            .anyMatch(chat -> chat instanceof UsersChat &&
                chat.getParticipants().containsAll(Arrays.asList(user1Id, user2Id)));
    }
}

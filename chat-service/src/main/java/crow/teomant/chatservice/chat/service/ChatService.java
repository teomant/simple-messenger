package crow.teomant.chatservice.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import crow.teomant.chatservice.chat.model.Chat;
import crow.teomant.chatservice.chat.model.UsersChat;
import crow.teomant.chatservice.chat.repository.ChatMongoRepository;
import crow.teomant.checker.chat.service.ChatCheckerService;
import crow.teomant.checker.user.service.UserCheckerService;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatMongoRepository repository;
    private final JmsMessagingTemplate jmsMessagingTemplate;
    private final ObjectMapper objectMapper;
    private final UserCheckerService userCheckerService;
    private final ChatCheckerService chatCheckerService;

    @SneakyThrows
    public Chat createUserChat(UUID user1Id, UUID user2Id) {

        if (!(userCheckerService.checkUserByids(Arrays.asList(user1Id, user2Id)))) {
            throw new IllegalStateException();
        }

        if (chatCheckerService.chatBetweenUsers(user1Id, user2Id)) {
            throw new IllegalStateException();
        }

        UsersChat chat = repository.save(new UsersChat(UUID.randomUUID(), Set.of(user1Id, user2Id)));

        jmsMessagingTemplate.convertAndSend("user.chat.added",
            objectMapper.writeValueAsString(new ChatAdded(user1Id, chat.getId())));
        jmsMessagingTemplate.convertAndSend("user.chat.added",
            objectMapper.writeValueAsString(new ChatAdded(user2Id, chat.getId())));

        return chat;
    }

    public Chat createPublicChat(UUID userId, Set<UUID> participantsIds, String name, String about) {
//        User creator = userMongoRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
//        Set<User> participants = userMongoRepository.findByIdIn(participantsIds);
//        PublicChat chat = repository.save(
//            new PublicChat(UUID.randomUUID(), participants.stream().map(User::getId).collect(Collectors.toSet()), name,
//                about, Set.of(userId))
//        );
//
//        creator.getChatIds().add(chat.getId());
//        participants.forEach(user -> user.getChatIds().add(chat.getId()));
//
//        userMongoRepository.save(creator);
//        userMongoRepository.saveAll(participants);
//
//        return chat;
        return null;
    }

    @Data
    @AllArgsConstructor
    private static class ChatAdded {
        private UUID userId;
        private UUID chatId;
    }
}

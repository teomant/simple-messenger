package crow.teomant.chatservice.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import crow.teomant.chatservice.chat.model.Chat;
import crow.teomant.chatservice.chat.model.PublicChat;
import crow.teomant.chatservice.chat.model.UsersChat;
import crow.teomant.chatservice.chat.repository.ChatMongoRepository;
import crow.teomant.checker.chat.service.ChatCheckerService;
import crow.teomant.checker.user.service.UserCheckerService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @SneakyThrows
    public Chat createPublicChat(UUID userId, Set<UUID> participantsIds, String name, String about) {

        Set<UUID> uuids = new HashSet<>();
        uuids.add(userId);
        uuids.addAll(participantsIds);
        if (!(userCheckerService.checkUserByids(uuids))) {
            throw new IllegalStateException();
        }

        if (chatCheckerService.checkPublicChatName(name)) {
            throw new IllegalStateException();
        }

        PublicChat chat = repository.save(new PublicChat(UUID.randomUUID(), uuids, name, about, Set.of(userId)));

        uuids.forEach(id -> {
            try {
                jmsMessagingTemplate.convertAndSend("user.chat.added",
                    objectMapper.writeValueAsString(new ChatAdded(id, chat.getId())));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        return chat;
    }

    @SneakyThrows
    @Transactional
    @Retryable
    public Chat addUser(UUID userId, UUID chatId) {

        if (!(userCheckerService.checkUserByids(Arrays.asList(userId)))) {
            throw new IllegalStateException();
        }

        Chat chat = repository.findById(chatId).orElseThrow(IllegalStateException::new);

        if (!(chat instanceof PublicChat) || chat.getParticipants().contains(userId)) {
            throw new IllegalStateException();
        }

        PublicChat publicChat = (PublicChat) chat;

        publicChat.getParticipants().add(userId);
        repository.save(publicChat);

        jmsMessagingTemplate.convertAndSend("user.chat.added",
            objectMapper.writeValueAsString(new ChatAdded(userId, chat.getId())));

        return chat;
    }

    @Data
    @AllArgsConstructor
    private static class ChatAdded {
        private UUID userId;
        private UUID chatId;
    }
}

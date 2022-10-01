package crow.teomant.checker.chat.repository;

import crow.teomant.checker.chat.model.Chat;
import crow.teomant.checker.chat.model.UsersChat;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatCheckerMongoRepository extends MongoRepository<Chat, UUID> {
    List<UsersChat> findByParticipantsContains(Set<UUID> ids);
}

package crow.teomant.checker.chat.repository;

import crow.teomant.checker.chat.model.PublicChat;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicChatCheckerMongoRepository extends MongoRepository<PublicChat, UUID> {
    Optional<PublicChat> findByName(String name);
}

package crow.teomant.chatservice.chat.repository;

import crow.teomant.chatservice.chat.model.Chat;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMongoRepository extends MongoRepository<Chat, UUID> {
}

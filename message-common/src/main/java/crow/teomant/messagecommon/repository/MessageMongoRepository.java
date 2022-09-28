package crow.teomant.messagecommon.repository;

import crow.teomant.messagecommon.model.Message;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageMongoRepository extends MongoRepository<Message, UUID> {

    List<Message> findByChatAndTimestampBetween(UUID chatId,
                                                LocalDateTime from,
                                                LocalDateTime to);
}

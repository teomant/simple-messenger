package crow.teomant.checker.message.repository;

import crow.teomant.checker.message.model.Message;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageCheckerMongoRepository extends MongoRepository<Message, UUID> {

}

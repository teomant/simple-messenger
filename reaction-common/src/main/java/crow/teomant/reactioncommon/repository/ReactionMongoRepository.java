package crow.teomant.reactioncommon.repository;

import crow.teomant.reactioncommon.model.Reaction;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionMongoRepository extends MongoRepository<Reaction, UUID> {

    List<Reaction> findByMessageIn(Collection<UUID> ids);
}

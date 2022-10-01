package crow.teomant.checker.user.repository;

import crow.teomant.checker.user.model.User;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCheckerMongoRepository extends MongoRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Set<User> findByIdIn(Collection<UUID> ids);
}

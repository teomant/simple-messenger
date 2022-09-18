package crow.teomant.userservice.user.repository;

import crow.teomant.userservice.user.model.User;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMongoRepository extends MongoRepository<User, UUID> {

    Optional<User> findByUsername(String username);
    Set<User> findByIdIn(Collection<UUID> ids);
}

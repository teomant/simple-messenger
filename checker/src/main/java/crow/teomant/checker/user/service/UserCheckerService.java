package crow.teomant.checker.user.service;

import crow.teomant.checker.user.model.User;
import crow.teomant.checker.user.repository.UserCheckerMongoRepository;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCheckerService {
    private final UserCheckerMongoRepository repository;

    public Boolean checkUser(UUID user) {
        return repository.findById(user).isPresent();
    }

    public Boolean checkUserByLogin(String username) {
        return repository.findByUsername(username).isPresent();
    }

    public Boolean checkUserByids(List<UUID> ids) {
        Set<User> users = repository.findByIdIn(ids);
        return ids.stream().allMatch(id -> users.stream().anyMatch(user -> user.getId().equals(id)));
    }
}

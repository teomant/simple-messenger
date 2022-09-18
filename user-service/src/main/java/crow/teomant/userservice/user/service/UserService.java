package crow.teomant.userservice.user.service;

import crow.teomant.userservice.user.model.User;
import crow.teomant.userservice.user.repository.UserMongoRepository;
import java.util.HashSet;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMongoRepository repository;

    public User register(String username, String about) {
        if (repository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException();
        }

        return repository.save(new User(UUID.randomUUID(), username, about, new HashSet<>()));
    }
}

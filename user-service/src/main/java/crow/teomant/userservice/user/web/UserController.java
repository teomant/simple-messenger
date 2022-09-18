package crow.teomant.userservice.user.web;

import crow.teomant.userservice.user.model.User;
import crow.teomant.userservice.user.repository.UserMongoRepository;
import crow.teomant.userservice.user.service.UserService;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMongoRepository userMongoRepository;

    @PostMapping
    public User create(@RequestBody CreateDto dto) {
        return userService.register(dto.username, dto.about);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable UUID id) {
        return userMongoRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Data
    public static class CreateDto {
        private String username;
        private String about;
    }
}

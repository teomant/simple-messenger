package crow.teomant.checker.chat.model;

import java.util.Set;
import java.util.UUID;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

@NoArgsConstructor
@TypeAlias("chat.user")
public class UsersChat extends Chat {
    public UsersChat(UUID id, Set<UUID> participants) {
        super(id, participants);
    }
}

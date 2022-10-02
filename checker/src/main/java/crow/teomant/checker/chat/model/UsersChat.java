package crow.teomant.checker.chat.model;

import java.util.Set;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

@NoArgsConstructor
@Data
@TypeAlias("chat.user")
public class UsersChat extends Chat {
    public UsersChat(UUID id, Set<UUID> participants) {
        super(id, participants);
    }
}

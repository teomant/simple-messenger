package crow.teomant.chatservice.chat.model;

import java.util.Set;
import java.util.UUID;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UsersChat extends Chat {
    public UsersChat(UUID id, Set<UUID> participants) {
        super(id, participants);
    }
}

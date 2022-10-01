package crow.teomant.checker.chat.model;

import java.util.Set;
import java.util.UUID;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

@NoArgsConstructor
@TypeAlias("chat.public")
public class PublicChat extends Chat {
    private Set<UUID> admins;

    public PublicChat(UUID id, Set<UUID> participants, Set<UUID> admins) {
        super(id, participants);
        this.admins = admins;
    }
}

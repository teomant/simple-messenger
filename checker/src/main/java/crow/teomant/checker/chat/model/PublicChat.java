package crow.teomant.checker.chat.model;

import java.util.Set;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@Data
@TypeAlias("chat.public")
public class PublicChat extends Chat {
    private Set<UUID> admins;
    @Field("name")
    private String name;

    public PublicChat(UUID id, Set<UUID> participants, Set<UUID> admins, String name) {
        super(id, participants);
        this.admins = admins;
        this.name = name;
    }
}

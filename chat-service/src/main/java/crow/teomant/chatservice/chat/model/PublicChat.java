package crow.teomant.chatservice.chat.model;

import java.util.Set;
import java.util.UUID;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
public class PublicChat extends Chat {
    @Field("name")
    private String name;
    @Field("about")
    private String about;
    @Field("admins")
    private Set<UUID> admins;

    public PublicChat(UUID id, Set<UUID> participants, String name, String about, Set<UUID> admins) {
        super(id, participants);
        this.name = name;
        this.about = about;
        this.admins = admins;
    }
}

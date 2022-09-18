package crow.teomant.userservice.user.model;

import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "SimpleMessengerUser")
public class User {
    @Id
    private UUID id;
    @Indexed(unique = true)
    @Field("username")
    private String username;
    @Field("about")
    private String about;
    @Field("chatIds")
    private Set<UUID> chatIds;
}

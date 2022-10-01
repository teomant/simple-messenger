package crow.teomant.checker.user.model;

import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "SimpleMessengerUser")
@TypeAlias("user")
public class User {
    @Id
    private UUID id;
    @Indexed(unique = true)
    @Field("username")
    private String username;
    @Field("chatIds")
    private Set<UUID> chatIds;
}

package crow.teomant.checker.message.model;

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
@Document(collection = "SimpleMessengerMessage")
public class Message {
    @Id
    private UUID id;
    @Field("author")
    private UUID author;
    @Indexed
    @Field("chat")
    private UUID chat;

}

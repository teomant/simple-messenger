package crow.teomant.messagewriterservice.message.model;

import java.time.LocalDateTime;
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
public abstract class Message {
    @Id
    private UUID id;
    @Field("author")
    private UUID author;
    @Indexed
    @Field("chat")
    private UUID chat;
    @Field("timestamp")
    private LocalDateTime timestamp;

    public abstract Object getContent();
}

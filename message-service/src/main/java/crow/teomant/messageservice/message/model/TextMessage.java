package crow.teomant.messageservice.message.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
public class TextMessage extends Message {
    @Field("content")
    private String content;

    public TextMessage(UUID id, UUID author, UUID chat, LocalDateTime timestamp, UUID correlationId, String content) {
        super(id, author, chat, timestamp, correlationId);
        this.content = content;
    }
}

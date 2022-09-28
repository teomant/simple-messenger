package crow.teomant.messageservice.message.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@TypeAlias("message.replace")
public class ReplaceMessage extends Message {
    @Field("replacedId")
    private UUID replacedId;

    public ReplaceMessage(UUID id, UUID author, UUID chat, LocalDateTime timestamp, MessageContent messageContent,
                          UUID replacedId) {
        super(id, author, chat, timestamp, messageContent);
        this.replacedId = replacedId;
    }
}

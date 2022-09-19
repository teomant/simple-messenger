package crow.teomant.messagewriterservice.message.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@TypeAlias("message.text.replace")
public class ReplaceTextMessage extends TextMessage implements ReplaceMessage {
    @Field("replacedId")
    private UUID replacedId;

    public ReplaceTextMessage(UUID id, UUID author, UUID chat, LocalDateTime timestamp, String content,
                              UUID replacedId) {
        super(id, author, chat, timestamp, content);
        this.replacedId = replacedId;
    }
}

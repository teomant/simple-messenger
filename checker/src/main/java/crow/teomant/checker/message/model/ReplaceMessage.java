package crow.teomant.checker.message.model;

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

    public ReplaceMessage(UUID id, UUID author, UUID chat, UUID replacedId) {
        super(id, author, chat);
        this.replacedId = replacedId;
    }
}

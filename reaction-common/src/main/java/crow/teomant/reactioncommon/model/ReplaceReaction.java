package crow.teomant.reactioncommon.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@TypeAlias("reaction.replace")
public class ReplaceReaction extends Reaction {
    @Field("replacedId")
    private UUID replacedId;

    public ReplaceReaction(UUID id, UUID author, UUID chat, UUID message, LocalDateTime timestamp,
                           ReactionContent reactionContent, UUID replacedId) {
        super(id, author, chat, message, timestamp, reactionContent);
        this.replacedId = replacedId;
    }
}

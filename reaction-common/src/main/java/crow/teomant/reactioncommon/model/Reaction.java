package crow.teomant.reactioncommon.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
@Document(collection = "SimpleMessengerReaction")
public class Reaction {
    @Id
    private UUID id;
    @Field("author")
    private UUID author;
    @Indexed
    @Field("chat")
    private UUID chat;
    @Indexed
    @Field("message")
    private UUID message;
    @Field("timestamp")
    private LocalDateTime timestamp;
    @Field("reactionContent")
    private ReactionContent reactionContent;

    @JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS
    )
    public static class ReactionContent {
    }

    @Data
    @NoArgsConstructor
    public static class SawReaction extends ReactionContent {
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoteReaction extends ReactionContent {
        Integer option;
    }
}

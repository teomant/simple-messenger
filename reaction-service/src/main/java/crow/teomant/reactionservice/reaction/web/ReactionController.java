package crow.teomant.reactionservice.reaction.web;

import crow.teomant.reactioncommon.model.Reaction;
import crow.teomant.reactionservice.reaction.service.ReactionService;
import java.util.Collection;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reaction")
@RequiredArgsConstructor
public class ReactionController {
    private final ReactionService reactionService;

    @PostMapping()
    public Collection<Reaction> get(@RequestBody GetDto dto) {
        return reactionService.getReactionsForMessages(dto.ids);
    }

    @Data
    public static class GetDto {
        private Collection<UUID> ids;
    }
}

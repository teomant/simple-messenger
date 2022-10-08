package crow.teomant.reactionservice.reaction.service;

import crow.teomant.reactioncommon.model.Reaction;
import crow.teomant.reactioncommon.repository.ReactionMongoRepository;
import java.util.Collection;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReactionService {
    private final ReactionMongoRepository repository;

    public Collection<Reaction> getReactionsForMessages(Collection<UUID> ids) {
        return repository.findByMessageIn(ids);
    }
}

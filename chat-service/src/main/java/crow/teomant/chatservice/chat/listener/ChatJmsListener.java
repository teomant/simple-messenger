package crow.teomant.chatservice.chat.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import crow.teomant.chatservice.chat.repository.ChatMongoRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatJmsListener {
    private final ChatMongoRepository chatMongoRepository;
    private final ObjectMapper objectMapper;

    @JmsListener(destination = "chat.search")
    @SneakyThrows
    public String searchForUser(String request) {
        return objectMapper.writeValueAsString(
            new SearchResponse(
                chatMongoRepository.findById(objectMapper.readValue(request, SearchRequest.class).getId()).isPresent()
            )
        );
    }


    @Data
    @AllArgsConstructor
    private static class SearchResponse {
        private Boolean result;
    }

    @Data
    private static class SearchRequest {

        private UUID id;
    }
}

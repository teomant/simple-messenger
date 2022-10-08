package crow.teomant.reactioncommon;

import com.fasterxml.jackson.databind.ObjectMapper;
import crow.teomant.reactioncommon.model.Reaction;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
public class ReactionCommonMongoConfig {

    @Bean
    public MongoCustomConversions customConversions(ObjectMapper mapper) {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new ContentReadConverter(mapper));
        converters.add(new ContentWriteConverter(mapper));
        return new MongoCustomConversions(converters);
    }

    @WritingConverter
    @RequiredArgsConstructor
    public static class ContentWriteConverter implements Converter<Reaction.ReactionContent, String> {
        private final ObjectMapper objectMapper;

        @Override
        @SneakyThrows
        public String convert(Reaction.ReactionContent source) {
            return objectMapper.writeValueAsString(source);
        }
    }

    @WritingConverter
    @RequiredArgsConstructor
    public static class ContentReadConverter implements Converter<String, Reaction.ReactionContent> {
        private final ObjectMapper objectMapper;

        @Override
        @SneakyThrows
        public Reaction.ReactionContent convert(String source) {
            return objectMapper.readValue(source, Reaction.ReactionContent.class);
        }
    }
}

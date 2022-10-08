package crow.teomant.messagecommon;

import com.fasterxml.jackson.databind.ObjectMapper;
import crow.teomant.messagecommon.model.Message;
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
public class MessageCommonMongoConfig {

    @Bean
    public MongoCustomConversions customConversions(ObjectMapper mapper) {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new ContentReadConverter(mapper));
        converters.add(new ContentWriteConverter(mapper));
        return new MongoCustomConversions(converters);
    }

    @WritingConverter
    @RequiredArgsConstructor
    public static class ContentWriteConverter implements Converter<Message.MessageContent, String> {
        private final ObjectMapper objectMapper;

        @Override
        @SneakyThrows
        public String convert(Message.MessageContent source) {
            return objectMapper.writeValueAsString(source);
        }
    }

    @WritingConverter
    @RequiredArgsConstructor
    public static class ContentReadConverter implements Converter<String, Message.MessageContent> {
        private final ObjectMapper objectMapper;

        @Override
        @SneakyThrows
        public Message.MessageContent convert(String source) {
            return objectMapper.readValue(source, Message.MessageContent.class);
        }
    }
}

package crow.teomant.reactionservice;

import crow.teomant.reactioncommon.CommonReactionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
    CommonReactionConfig.class
})
public class ReactionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactionServiceApplication.class, args);
    }

}

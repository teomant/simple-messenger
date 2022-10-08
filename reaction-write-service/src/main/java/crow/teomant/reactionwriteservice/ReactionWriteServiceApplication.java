package crow.teomant.reactionwriteservice;

import crow.teomant.checker.CheckerConfig;
import crow.teomant.reactioncommon.CommonReactionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
    CommonReactionConfig.class,
    CheckerConfig.class
})
public class ReactionWriteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactionWriteServiceApplication.class, args);
    }

}

package crow.teomant.reactioncommon;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("crow.teomant.reactioncommon")
@EntityScan("crow.teomant.reactioncommon")
public class CommonReactionConfig {
}

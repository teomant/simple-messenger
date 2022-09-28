package crow.teomant.messagecommon;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("crow.teomant.messagecommon")
@EntityScan("crow.teomant.messagecommon")
public class CommonMessageConfig {
}

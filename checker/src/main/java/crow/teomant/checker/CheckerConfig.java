package crow.teomant.checker;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("crow.teomant.checker")
@EntityScan("crow.teomant.checker")
public class CheckerConfig {
}

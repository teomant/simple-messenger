package crow.teomant.messagewriterservice;

import crow.teomant.checker.CheckerConfig;
import crow.teomant.messagecommon.CommonMessageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
	CommonMessageConfig.class,
	CheckerConfig.class
})
public class MessageWriterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageWriterServiceApplication.class, args);
	}

}

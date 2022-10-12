package crow.teomant.messagewriterservice;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IgniteConfig {
    @Bean
    public ClientConfiguration clientConfiguration() {
        ClientConfiguration cfg = new ClientConfiguration();
        cfg.setAddresses("127.0.0.1:10800");
        return cfg;
    }

    @Bean
    public Ignite ignite() {
        return Ignition.start(new IgniteConfiguration());
    }
}

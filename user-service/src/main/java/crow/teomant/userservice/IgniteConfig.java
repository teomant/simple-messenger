package crow.teomant.userservice;

import java.util.Arrays;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.ignite.springframework.boot.autoconfigure.IgniteConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IgniteConfig {
    @Bean
    public IgniteConfigurer configurer() {
        return igniteConfiguration -> igniteConfiguration
            .setDiscoverySpi(
                new TcpDiscoverySpi()
                    .setIpFinder(new TcpDiscoveryVmIpFinder()
                        .setAddresses(Arrays.asList(
                            "localhost"
                        ))))
            .setIgniteInstanceName("cache");
    }
}

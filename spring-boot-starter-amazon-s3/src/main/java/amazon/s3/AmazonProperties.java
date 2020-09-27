package amazon.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import lombok.Data;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "amazon")
public class AmazonProperties {

    @NestedConfigurationProperty
    Aws aws;

    @NestedConfigurationProperty
    S3 s3;

    @Value
    public static class Aws {

        String accessKey;

        String secretKey;

        public AWSCredentials getCredentials() {
            return new BasicAWSCredentials(accessKey, secretKey);
        }
    }

    @Value
    public static class S3 {

        String defaultBucket;

        String serviceEndpoint;

        String signingRegion;

        int socketTimeout;

        int requestTimeout;

        int clientExecutionTimeout;

        public AwsClientBuilder.EndpointConfiguration getEndpointConfiguration() {
            return new AwsClientBuilder.EndpointConfiguration(serviceEndpoint, signingRegion);
        }
    }
}

package amazon.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class auto-configures a {@link AmazonS3} bean.
 *
 * @author antimony
 */
@Configuration
@ConditionalOnMissingBean(AmazonS3.class)
@EnableConfigurationProperties(AmazonProperties.class)
public class S3AutoConfiguration {

    private final AmazonProperties amazonProperties;

    @Autowired
    public S3AutoConfiguration(AmazonProperties amazonProperties) {
        this.amazonProperties = amazonProperties;
    }

    @Bean
    AmazonS3 amazonS3() {
        ClientConfiguration clientConfiguration = new ClientConfiguration()
            .withSocketTimeout(amazonProperties.getS3().getSocketTimeout())
            .withRequestTimeout(amazonProperties.getS3().getRequestTimeout())
            .withClientExecutionTimeout(amazonProperties.getS3().getClientExecutionTimeout());
        return AmazonS3ClientBuilder.standard()
            .disableChunkedEncoding()
            .withCredentials(new AWSStaticCredentialsProvider(amazonProperties.getAws().getCredentials()))
            .withEndpointConfiguration(amazonProperties.getS3().getEndpointConfiguration())
            .withClientConfiguration(clientConfiguration).build();
    }
}

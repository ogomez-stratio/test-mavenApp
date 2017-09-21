package com.stratio.scddatanps.config;

import com.stratio.scdcommonutil.util.Constants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = Constants.CONFIG_PROPERTIES_PREFIX)
@Data
public class ScdAppProperties {
    private int limitRecordScoresByChannel = 5;
    private List<Channel> channels;

}


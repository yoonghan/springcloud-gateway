package com.walcron.springcloud.gateway.config;

import com.walcron.springcloud.gateway.filter.IpCustomFilter;
import com.walcron.springcloud.gateway.filter.PostCustomFilter;
import com.walcron.springcloud.gateway.filter.PreCustomFilter;
import com.walcron.springcloud.gateway.property.TgcsPropertyReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class FilterConfig {

//    @Autowired
//    private SimpleDiscoveryClient discoveryClient;

    private TgcsPropertyReader tgcsPropertyReader;

    @Autowired
    public FilterConfig(TgcsPropertyReader tgcsPropertyReader) {
        this.tgcsPropertyReader = tgcsPropertyReader;
    }

    @Bean
    @Order(-1)
    public GlobalFilter ipCustomFilter() {
        return new IpCustomFilter(tgcsPropertyReader);
    }

    @Bean
    @Order(0)
    public GlobalFilter preCustomFilter() {
        return new PreCustomFilter();
    }

    @Bean
    @Order(1)
    public GlobalFilter postCustomFilter() {
        return new PostCustomFilter();
    }
}

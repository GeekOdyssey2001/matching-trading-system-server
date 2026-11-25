package com.jackie.matchingtradingsystemserver;

import com.jackie.matchingtradingsystemserver.config.CounterConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import thirdpart.uuid.GudyUuid;


//localhost:8081 localhost:8090 有跨域问题
@SpringBootApplication
public class MatchingTradingSystemServerApplication {

    @Autowired
    private CounterConfig counterConfig;

    @PostConstruct
    private void init(){
        GudyUuid.getInstance().init(
                counterConfig.getDataCenterId(),
                counterConfig.getWorkerId()
        );
    }







    public static void main(String[] args) {
        SpringApplication.run(MatchingTradingSystemServerApplication.class, args);
    }

}

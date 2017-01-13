package com.shun.liu.quickserver.bootstrap;

import com.shun.liu.quickserver.socksproxy.SocksServer;
import com.shun.liu.quickserver.util.NetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liushun on 16/12/6.
 */
public class Bootstrap {
    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                int sockPort = NetUtils.getAvailablePort(18800);
                logger.info(String.format("socks服务端端口为:%d",sockPort));
                try {
                    SocksServer.start(sockPort);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        executorService.shutdown();

    }
}

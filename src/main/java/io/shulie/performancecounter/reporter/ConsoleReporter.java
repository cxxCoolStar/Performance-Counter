package io.shulie.performancecounter.reporter;

import io.shulie.performancecounter.aggregator.Aggregator;
import io.shulie.performancecounter.model.RequestInfo;
import io.shulie.performancecounter.model.RequestStat;
import io.shulie.performancecounter.storage.MetricsStorage;
import io.shulie.performancecounter.viewer.StatViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component("consoleReporter")
public class ConsoleReporter implements Reporter {

    @Resource
    private MetricsStorage metricsStorage;

    @Resource
    private Aggregator aggregator;

    @Qualifier("consoleViewer")
    @Autowired
    private StatViewer viewer;


    @Override
    public void report() {
        Executors.newScheduledThreadPool(5).scheduleAtFixedRate(() -> {
            long durationInMillis = 1 * 1000;
            long endTimeInMillis = System.currentTimeMillis();
            long startTimeInMillis = endTimeInMillis - durationInMillis;
            Map<String, List<RequestInfo>> requestInfos = metricsStorage.getRequestInfos(startTimeInMillis, endTimeInMillis);
            Map<String, RequestStat> requestStats = aggregator.aggregate(requestInfos, durationInMillis);
            viewer.output(requestStats, startTimeInMillis, endTimeInMillis);

            // 这里放到配置文件
        }, 0L, 1, TimeUnit.SECONDS);
    }
}
package io.shulie.performancecounter.reporter;

import com.google.gson.Gson;
import io.shulie.performancecounter.aggregator.Aggregator;
import io.shulie.performancecounter.model.RequestInfo;
import io.shulie.performancecounter.model.RequestStat;
import io.shulie.performancecounter.storage.MetricsStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConsoleReporter {
    private MetricsStorage metricsStorage;
    private ScheduledExecutorService executor;

    public ConsoleReporter(MetricsStorage metricsStorage) {
        this.metricsStorage = metricsStorage;
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void startRepeatedReport(long periodInSeconds, long durationInSeconds) {
        executor.scheduleAtFixedRate(() -> {
            // 根据给定的时间区间，从数据库中拉取数据；
            long durationInMillis = durationInSeconds * 1000;
            long endTimeInMillis = System.currentTimeMillis();
            long startTimeInMillis = endTimeInMillis - durationInMillis;
            Map<String, List<RequestInfo>> requestInfos =
                    metricsStorage.getRequestInfos(startTimeInMillis, endTimeInMillis);
            Map<String, RequestStat> stats = new HashMap<>();
            for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
                String apiName = entry.getKey();
                List<RequestInfo> requestInfosPerApi = entry.getValue();
                // 第2个代码逻辑：根据原始数据，计算得到统计数据；
                RequestStat requestStat = Aggregator.aggregate(requestInfosPerApi, durationInMillis);
                stats.put(apiName, requestStat);
            }
            // 将统计数据显示到终端（命令行或邮件）；
            System.out.println("Time Span: [" + startTimeInMillis + ", " + endTimeInMillis + "]");
            Gson gson = new Gson();
            System.out.println(gson.toJson(stats));
        }, 0, periodInSeconds, TimeUnit.SECONDS);
    }
}


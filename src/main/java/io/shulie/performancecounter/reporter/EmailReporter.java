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
import java.util.*;

@Component("emailReporter")
public class EmailReporter implements Reporter{
    private static final Long DAY_HOURS_IN_SECONDS = 86400L;

    @Resource
    private MetricsStorage metricsStorage;

    @Resource
    private Aggregator aggregator;

    @Qualifier("emailViewer")
    @Autowired
    private StatViewer viewer;


    public void startDailyReport() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date firstTime = calendar.getTime();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long durationInMillis = DAY_HOURS_IN_SECONDS * 1000;
                long endTimeInMillis = System.currentTimeMillis();
                long startTimeInMillis = endTimeInMillis - durationInMillis;
                Map<String, List<RequestInfo>> requestInfos = metricsStorage.getRequestInfos(startTimeInMillis, endTimeInMillis);
                Map<String, RequestStat> stats = aggregator.aggregate(requestInfos, durationInMillis);
                viewer.output(stats, startTimeInMillis, endTimeInMillis);
            }
        }, firstTime, DAY_HOURS_IN_SECONDS * 1000);
    }

    @Override
    public void report() {
        this.startDailyReport();
    }
}
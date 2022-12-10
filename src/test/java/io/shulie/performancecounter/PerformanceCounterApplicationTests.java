package io.shulie.performancecounter;

import io.shulie.performancecounter.aggregator.Aggregator;
import io.shulie.performancecounter.collector.MetricsCollector;
import io.shulie.performancecounter.model.RequestInfo;
import io.shulie.performancecounter.reporter.ConsoleReporter;
import io.shulie.performancecounter.reporter.EmailReporter;
import io.shulie.performancecounter.storage.MetricsStorage;
import io.shulie.performancecounter.storage.RedisMetricsStorage;
import io.shulie.performancecounter.viewer.ConsoleViewer;
import io.shulie.performancecounter.viewer.EmailViewer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class PerformanceCounterApplicationTests {

    @Test
    void contextLoads() {
        MetricsStorage storage = new RedisMetricsStorage();
        Aggregator aggregator = new Aggregator(); // 定时触发统计并将结果显示到终端
        ConsoleViewer consoleViewer = new ConsoleViewer();
        ConsoleReporter consoleReporter = new ConsoleReporter(storage, aggregator, consoleViewer);
        consoleReporter.startRepeatedReport(1, 6);
        // 定时触发统计并将结果输出到邮件
        EmailViewer emailViewer = new EmailViewer();
        emailViewer.addToAddress("1848505943@qq.com");
        EmailReporter emailReporter = new EmailReporter(storage, aggregator, emailViewer);
        emailReporter.startDailyReport();
        // 收集接口访问数据
        MetricsCollector collector = new MetricsCollector(storage);
        collector.recordRequest(new RequestInfo("register", 123, 10234));
        collector.recordRequest(new RequestInfo("register", 223, 11234));
        collector.recordRequest(new RequestInfo("register", 323, 12334));
        collector.recordRequest(new RequestInfo("login", 23, 12434));
        collector.recordRequest(new RequestInfo("login", 1223, 14234));


        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



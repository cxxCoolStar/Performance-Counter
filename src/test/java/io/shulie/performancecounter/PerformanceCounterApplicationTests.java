package io.shulie.performancecounter;

import io.shulie.performancecounter.collector.MetricsCollector;
import io.shulie.performancecounter.model.RequestInfo;
import io.shulie.performancecounter.reporter.ConsoleReporter;
import io.shulie.performancecounter.reporter.EmailReporter;
import io.shulie.performancecounter.storage.MetricsStorage;
import io.shulie.performancecounter.storage.RedisMetricsStorage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PerformanceCounterApplicationTests {

    @Test
    void contextLoads() {
        MetricsStorage storage = new RedisMetricsStorage();
        ConsoleReporter consoleReporter = new ConsoleReporter(storage);
        consoleReporter.startRepeatedReport(60, 60);
        EmailReporter emailReporter = new EmailReporter(storage);
        emailReporter.addToAddress("1848505943@qq.com");
        emailReporter.startDailyReport();
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



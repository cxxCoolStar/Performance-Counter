package io.shulie.performancecounter.viewer;

import com.google.gson.Gson;
import io.shulie.performancecounter.model.RequestStat;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author chenxingxing
 * @date 2022/12/9 10:55 下午
 * 这是一个控制台视图器，用这个输出到控制台
 */
@Component("consoleViewer")
public class ConsoleViewer implements StatViewer {
    public void output(Map<String, RequestStat> requestStats, long startTimeInMillis, long endTimeInMills) {
        System.out.println("Time Span: [" + startTimeInMillis + ", " + endTimeInMills + "]");
        Gson gson = new Gson();
        System.out.println(gson.toJson(requestStats));
    }
}

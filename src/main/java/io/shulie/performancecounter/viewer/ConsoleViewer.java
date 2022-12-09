package io.shulie.performancecounter.viewer;

import com.google.gson.Gson;
import io.shulie.performancecounter.model.RequestStat;

import java.util.Map;

/**
 * @author chenxingxing
 * @date 2022/12/9 10:55 下午
 */
public class ConsoleViewer implements StatViewer {
    public void output(Map<String, RequestStat> requestStats, long startTimeInMillis, long endTimeInMills) {
        System.out.println("Time Span: [" + startTimeInMillis + ", " + endTimeInMills + "]");
        Gson gson = new Gson();
        System.out.println(gson.toJson(requestStats));
    }
}

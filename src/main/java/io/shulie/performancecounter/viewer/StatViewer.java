package io.shulie.performancecounter.viewer;

import io.shulie.performancecounter.model.RequestStat;

import java.util.Map;

/**
 * @author chenxingxing
 * @date 2022/12/9 9:55 下午
 */
public interface StatViewer {

    /**
     * 输出
     * @param requestStats
     * @param startTimeInMillis
     * @param endTimeInMills
     */
    void output(Map<String, RequestStat> requestStats, long startTimeInMillis, long endTimeInMills);
}

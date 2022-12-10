package io.shulie.performancecounter.aggregator;

import io.shulie.performancecounter.model.RequestInfo;
import io.shulie.performancecounter.model.RequestStat;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author chenxingxing
 * @date 2022/12/9 10:55 下午
 */
@Component
public class Aggregator {

    public Map<String, RequestStat> aggregate(
            Map<String, List<RequestInfo>> requestInfos, long durationInMillis) {
        Map<String, RequestStat> requestStats = new HashMap<>();
        for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
            String apiName = entry.getKey();
            List<RequestInfo> requestInfosPerApi = entry.getValue();
            if (CollectionUtils.isEmpty(requestInfosPerApi)) {
                return requestStats;
            }
            RequestStat requestStat = doAggregate(requestInfosPerApi, durationInMillis);
            requestStats.put(apiName, requestStat);
        }
        return requestStats;
    }

    private RequestStat doAggregate(List<RequestInfo> requestInfos, long durationInMillis) {
        List<Double> respTimes = new ArrayList<>();
        for (RequestInfo requestInfo : requestInfos) {
            double respTime = requestInfo.getResponseTime();
            respTimes.add(respTime);
        }

        RequestStat requestStat = new RequestStat();
        requestStat.setMaxResponseTime(max(respTimes));
        requestStat.setMinResponseTime(min(respTimes));
        requestStat.setAvgResponseTime(avg(respTimes));
        requestStat.setP999ResponseTime(percentile999(respTimes));
        requestStat.setP99ResponseTime(percentile99(respTimes));
        requestStat.setCount(respTimes.size());
        requestStat.setTps((long) tps(respTimes.size(), durationInMillis / 1000));
        return requestStat;
    }

    /**
     * 计算最大值
     *
     * @param dataset
     * @return
     */
    private double max(List<Double> dataset) {
        return dataset.stream().mapToDouble(data -> data).max().orElse(Double.MIN_VALUE);
    }

    /**
     * 计算最小值
     *
     * @param dataset
     * @return
     */
    private double min(List<Double> dataset) {
        return dataset.stream().mapToDouble(data -> data).min().orElse(Double.MAX_VALUE);
    }

    /**
     * 计算平均值
     *
     * @param dataset
     * @return
     */
    private double avg(List<Double> dataset) {
        return dataset.stream().mapToDouble(data -> data).min().orElse(dataset.get(0));
    }

    /**
     * 计算tps
     *
     * @param count
     * @param duration
     * @return
     */
    private double tps(int count, double duration) {
        return count / duration;
    }

    /**
     * 满足千分之九百九十九的网络请求所需要的最低耗时
     *
     * @param dataset
     * @return
     */
    private double percentile999(List<Double> dataset) {
        return 0.0;
    }

    /**
     * 满足百分之九十九的网络请求所需要的最低耗时
     *
     * @param dataset
     * @return
     */
    private double percentile99(List<Double> dataset) {
        return 0.0;
    }
}


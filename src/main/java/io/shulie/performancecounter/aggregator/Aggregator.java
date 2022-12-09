package io.shulie.performancecounter.aggregator;

import io.shulie.performancecounter.model.RequestInfo;
import io.shulie.performancecounter.model.RequestStat;

import java.util.*;


public class Aggregator {

  public Map<String, RequestStat> aggregate(
          Map<String, List<RequestInfo>> requestInfos, long durationInMillis) {
    Map<String, RequestStat> requestStats = new HashMap<>();
    for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
      String apiName = entry.getKey();
      List<RequestInfo> requestInfosPerApi = entry.getValue();
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
    requestStat.setTps((long) tps(respTimes.size(), durationInMillis/1000));
    return requestStat;
  }

  // TODO 实现一下方法
  // 以下的函数的代码实现均省略...
  private double max(List<Double> dataset) {}
  private double min(List<Double> dataset) {}
  private double avg(List<Double> dataset) {}
  private double tps(int count, double duration) {}
  private double percentile999(List<Double> dataset) {}
  private double percentile99(List<Double> dataset) {}
  private double percentile(List<Double> dataset, double ratio) {}
}


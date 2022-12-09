package io.shulie.performancecounter.model;

import lombok.Data;

@Data
public class RequestInfo {
  private String apiName;
  private double responseTime;
  private long timestamp;

  /***
   * 构造器
   * @param apiName api名称
   * @param responseTime 响应时间
   * @param timestamp 时间戳
   */
  public RequestInfo(String apiName, int responseTime, int timestamp) {
    this.apiName = apiName;
    this.responseTime = responseTime;
    this.timestamp = timestamp;
  }
}
package io.shulie.performancecounter.storage;

import io.shulie.performancecounter.model.RequestInfo;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RedisMetricsStorage implements MetricsStorage {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void saveRequestInfo(RequestInfo requestInfo) {

        String apiName = requestInfo.getApiName();
        redisTemplate.opsForValue().set(apiName,requestInfo.toString());
    }

    @Override
    public List<RequestInfo> getRequestInfos(String apiName, long startTimestamp, long endTimestamp) {
        List<RequestInfo> requestInfoList = new ArrayList<>();
        requestInfoList.add((RequestInfo)redisTemplate.opsForValue().get(apiName));
        return requestInfoList;
    }

    @Override
    public Map<String, List<RequestInfo>> getRequestInfos(long startTimestamp, long endTimestamp) {
        return null;
    }
}
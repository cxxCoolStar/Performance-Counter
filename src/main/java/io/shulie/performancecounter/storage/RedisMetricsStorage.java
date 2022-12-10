package io.shulie.performancecounter.storage;

import io.shulie.performancecounter.model.RequestInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RedisMetricsStorage implements MetricsStorage {

    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @Override
    public void saveRequestInfo(RequestInfo requestInfo) {

        String apiName = requestInfo.getApiName();
//        redisTemplate.opsForValue().set(apiName,requestInfo.toString());
    }

    @Override
    public List<RequestInfo> getRequestInfos(String apiName, long startTimestamp, long endTimestamp) {
        List<RequestInfo> requestInfoList = new ArrayList<>();
        requestInfoList.add( new RequestInfo("login",10000,100000000));
//        requestInfoList.add((RequestInfo)redisTemplate.opsForValue().get(apiName));
        return requestInfoList;
    }

    @Override
    public Map<String, List<RequestInfo>> getRequestInfos(long startTimestamp, long endTimestamp) {

        Map<String, List<RequestInfo>> requestInfoMap = new HashMap<>();
        List<RequestInfo> requestInfoList = new ArrayList<>();
        requestInfoList.add( new RequestInfo("login",10000,100000000));
        requestInfoMap.put("login",requestInfoList);
//        requestInfoList.add((RequestInfo)redisTemplate.opsForValue().get(apiName));
        return requestInfoMap;
    }
}
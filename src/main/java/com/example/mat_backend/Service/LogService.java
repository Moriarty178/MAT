//package com.example.mat_backend.Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.domain.Range;
//import org.springframework.data.redis.connection.stream.MapRecord;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class LogService {
//
//    @Autowired
//    @Qualifier("redisTemplateRunFlow")
//    private RedisTemplate<String, Object> redisTemplateRunFlow;
//
//    @Autowired
//    @Qualifier("redisTemplateRestfulApi")
//    private RedisTemplate<String, Object> redisTemplateRestfulApi;
//
//    @Autowired
//    @Qualifier("redisTemplateTestTool")
//    private RedisTemplate<String, Object> redisTemplateTestTool;
//
//    @Autowired
//    @Qualifier("redisTemplateTenserFlow")
//    private RedisTemplate<String, Object> redisTemplateTenserFlow;
//
//    public List<String> getAllFlowIds() {
//        Set<String> allFlowIds = new HashSet<>();
//        allFlowIds.addAll(getFlowIdsFromInstance(redisTemplateRunFlow));
//        allFlowIds.addAll(getFlowIdsFromInstance(redisTemplateRestfulApi));
//        allFlowIds.addAll(getFlowIdsFromInstance(redisTemplateTestTool));
//        allFlowIds.addAll(getFlowIdsFromInstance(redisTemplateTenserFlow));
//
//        return allFlowIds.stream().sorted((flowId1, flowId2) -> {
//            long time1 = getLastUpdatedTime(flowId1);
//            long time2 = getLastUpdatedTime(flowId2);
//            return Long.compare(time2, time1);
//        }).collect(Collectors.toList());
//    }
//
//    private Set<String> getFlowIdsFromInstance(RedisTemplate<String, Object> redisTemplate) {
//        List<MapRecord<String, Object, Object>> records = redisTemplate.opsForStream().range("response_data_stream", Range.unbounded());
//        return records.stream()
//                .map(record -> (String) record.getValue().get("FlowId"))
//                .collect(Collectors.toSet());
//    }
//
//    private long getLastUpdatedTime(String flowId) {
//        // Implement a way to get the last updated time for a given flowId.
//        // This might involve querying Redis or another data source.
//        return 0; // Placeholder
//    }
//
//    public List<Map<Object, Object>> getFilteredRecordsFromInstance(RedisTemplate<String, Object> redisTemplate, String streamName, String flowIdValue, long now, long tenMinutesAgo, int maxRecords) {
//        List<Map<Object, Object>> filteredRecords = new ArrayList<>();
//        //mới đến cũ
//        List<MapRecord<String, Object, Object>> records = redisTemplate.opsForStream().reverseRange(streamName, Range.closed(String.valueOf(tenMinutesAgo), String.valueOf(now)));
//
//        //cũ đến mới
//        //List<MapRecord<String, Object, Object>> records = redisTemplate.opsForStream().range(streamName, Range.closed(String.valueOf(tenMinutesAgo),String.valueOf(now)));
//        if(records != null) {
//            //đảo ngược  danh sách các bản ghi do đang lưu từ cũ -> mới (tenMinutesAgo -> now)
//            //Collections.reverse(records);
//            int countRecords = 0;
//            for (MapRecord<String, Object, Object> record : records) {
//                Map<Object, Object> recordData = record.getValue();
//                if(flowIdValue.equals(recordData.get("FlowId"))) {
//                    filteredRecords.add(recordData);
//                    countRecords++;
//                    if(countRecords >= maxRecords) {
//                        break;
//                    }
//                }
//            }
//        }
//        return filteredRecords;
//    }
//
//    public List<Map<Object, Object>> getAllFilteredRecords(String flowIdValue, List<Map<String, Object>> instances, String streamName, int timeRangeMinutes, int maxRecords) {
//        List<Map<Object, Object>> allFilteredRecords = new ArrayList<>();
//        long now = System.currentTimeMillis();
//
//        while (true) {
//            long tenMinutesAgo = now - timeRangeMinutes * 60 * 1000;
//            for(Map<String, Object> instance : instances) {
//                String instanceName = (String) instance.get("instanceName");//get name instance [runFlow, restfullApi, ...]
//                RedisTemplate<String, Object> redisTemplate = getRedisTemplatedByName(instanceName);//get redistemplate [redisTemplateRestfullApi, redisTemplateTestTool...
//                List<Map<Object, Object>> filteredRecords = getFilteredRecordsFromInstance(redisTemplate, streamName, flowIdValue, now, tenMinutesAgo, maxRecords);
//                allFilteredRecords.addAll(filteredRecords);
//            }
//
//            if(!allFilteredRecords.isEmpty()) {
//                return allFilteredRecords;
//            } else {
//                now = tenMinutesAgo;
////                try {
////                    Thread.sleep(1000);
////                } catch (InterruptedException e) {
////                    Thread.currentThread().interrupt();
////                }
//            }
//        }
//    }
//
//    public RedisTemplate<String, Object> getRedisTemplatedByName(String instanceName) {
//        return switch (instanceName) {
//            case "runFlow" -> redisTemplateRunFlow;
//            case "restfulApi" -> redisTemplateRestfulApi;
//            case "redisTestTool" -> redisTemplateTestTool;
//            case "tenserFlow" -> redisTemplateTenserFlow;
//            default -> throw new IllegalArgumentException("Unknown instance name: " + instanceName);
//        };
//    }
//
//
//
//
//}

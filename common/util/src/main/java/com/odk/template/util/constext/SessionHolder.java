package com.odk.template.util.constext;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * LocalCacheHolder
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/20
 */
public class SessionHolder {

    private static final Cache<String, String> sessionCache = CacheBuilder.newBuilder()
            .expireAfterAccess(30, TimeUnit.MINUTES) // 设置会话过期时间为30分钟
            .recordStats()
            .build();

    // 创建会话并返回会话ID
    public static String createSession(String userId) {
        String sessionId = UUID.randomUUID().toString();
        sessionCache.put(sessionId, userId);
        return sessionId;
    }

    // 根据会话ID获取用户信息
    public static String checkUserSession(String sessionId) {
        return sessionCache.getIfPresent(sessionId);
    }

    // 移除会话
    public static void invalidateSession(String sessionId) {
        sessionCache.invalidate(sessionId);
    }

}

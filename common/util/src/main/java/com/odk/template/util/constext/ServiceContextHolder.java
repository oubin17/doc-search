package com.odk.template.util.constext;

import com.odk.template.util.enums.BizScene;

/**
 * ServiceContextHolder
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/11
 */
public class ServiceContextHolder {

    private static final ThreadLocal<BizScene> sceneCode = new ThreadLocal<>();

    private static final ThreadLocal<String> userId = new ThreadLocal<>();


    /**
     * 设置场景上下文
     *
     * @param bizScene
     */
    public static void setSceneCode(BizScene bizScene) {
        ServiceContextHolder.sceneCode.set(bizScene);
    }

    public static String getLoginId() {
        return userId.get();
    }

    public static void clear() {
        sceneCode.remove();
        userId.remove();

    }

}

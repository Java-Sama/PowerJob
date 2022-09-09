package tech.powerjob.server.monitor;

/**
 * 监视器
 *
 * @author tjq
 * @since 2022/9/6
 */
public interface Monitor {
    /**
     * 记录监控事件
     * 请注意该方法务必异步不阻塞！！！
     * @param event 事件
     */
    void record(Event event);
}
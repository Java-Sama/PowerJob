package com.github.kfcfans.oms.server.web.websocket;

import com.github.kfcfans.oms.server.common.config.OmsEndpointConfigure;
import com.github.kfcfans.oms.server.service.ContainerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * 容器部署 WebSocket 服务
 * 记录一个不错的 WebSocket 测试网站：<a>http://www.easyswoole.com/wstool.html</a>
 *
 * @author tjq
 * @since 2020/5/17
 */
@Slf4j
@Component
@ServerEndpoint(value = "/container/deploy/{name}", configurator = OmsEndpointConfigure.class)
public class ContainerDeployServerEndpoint {

    @Resource
    private ContainerService containerService;

    @OnOpen
    public void onOpen(@PathParam("name") String name, Session session) {

        RemoteEndpoint.Async remote = session.getAsyncRemote();
        remote.sendText("SYSTEM: connected successfully, start to deploy container: " + name);
        try {
            containerService.deploy(name, session);
        }catch (Exception e) {
            log.error("[ContainerDeployServerEndpoint] deploy container {} failed.", name, e);

            remote.sendText("SYSTEM: deploy failed because of the exception");
            remote.sendText(ExceptionUtils.getStackTrace(e));
        }
        try {
            session.close();
        }catch (Exception e) {
            log.error("[ContainerDeployServerEndpoint] close session for {} failed.", name, e);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        try {
            session.close();
        } catch (IOException e) {
            log.error("[ContainerDeployServerEndpoint] close session failed.", e);
        }
        log.warn("[ContainerDeployServerEndpoint] session onError!", throwable);
    }
}
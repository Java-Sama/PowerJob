package tech.powerjob.server.persistence.storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import tech.powerjob.server.extension.dfs.DFsService;
import tech.powerjob.server.persistence.storage.impl.AliOssService;
import tech.powerjob.server.persistence.storage.impl.EmptyDFsService;
import tech.powerjob.server.persistence.storage.impl.GridFsService;

/**
 * Description
 *
 * @author tjq
 * @since 2023/7/30
 */
@Configuration
public class StorageConfiguration {

    @Bean
    @Conditional(GridFsService.GridFsCondition.class)
    public DFsService initGridFs() {
        return new GridFsService();
    }

    @Bean
    @Conditional(AliOssService.AliOssCondition.class)
    public DFsService initAliOssFs() {
        return new AliOssService();
    }

    @Bean
    @Conditional(EmptyDFsService.EmptyCondition.class)
    public DFsService initEmptyDfs() {
        return new EmptyDFsService();
    }
}
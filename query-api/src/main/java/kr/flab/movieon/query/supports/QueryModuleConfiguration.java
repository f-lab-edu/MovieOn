package kr.flab.movieon.query.supports;

import javax.sql.DataSource;
import kr.flab.movieon.query.modules.account.MybatisAccountReader;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Import(value = {
    MybatisAccountReader.class
})
@Configuration
public class QueryModuleConfiguration {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        var factoryBean = new SqlSessionFactoryBean();
        var configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfiguration(configuration);
        factoryBean.setTypeAliasesPackage("kr.flab.movieon.query.modules.*");
        var resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return factoryBean.getObject();
    }
}

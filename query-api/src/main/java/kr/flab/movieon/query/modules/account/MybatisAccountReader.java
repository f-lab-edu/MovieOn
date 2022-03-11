package kr.flab.movieon.query.modules.account;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class MybatisAccountReader implements AccountReader {

    private final SqlSessionTemplate template;

    public MybatisAccountReader(SqlSessionTemplate template) {
        this.template = template;
    }

    @Override
    public AccountReadModel findByAccountId(String accountId) {
        return template.getMapper(AccountReader.class).findByAccountId(accountId);
    }
}

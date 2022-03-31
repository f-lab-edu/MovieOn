package kr.flab.movieon.query.modules.order;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class MybatisOrderReader implements OrderReader {

    private final SqlSessionTemplate template;

    public MybatisOrderReader(SqlSessionTemplate template) {
        this.template = template;
    }

    @Override
    public OrderReadModel findByOrderId(String orderId) {
        return template.getMapper(OrderReader.class).findByOrderId(orderId);
    }
}

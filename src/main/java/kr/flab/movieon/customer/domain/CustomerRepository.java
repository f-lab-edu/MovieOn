package kr.flab.movieon.customer.domain;

public interface CustomerRepository {

    Customer save(Customer entity);

    Customer findById(Long id);
}

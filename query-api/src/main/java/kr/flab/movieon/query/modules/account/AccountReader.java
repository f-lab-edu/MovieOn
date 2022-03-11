package kr.flab.movieon.query.modules.account;

public interface AccountReader {

    AccountReadModel findByAccountId(String accountId);
}

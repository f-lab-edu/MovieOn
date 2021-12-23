package kr.flab.movieon.account.application;

import org.springframework.stereotype.Service;

@Service
public class AccountFacade {

//    private final LoginAccountProcessor loginAccountProcessor;
//    private final RegisterAccountProcessor registerAccountProcessor;
//    private final TokenConverter tokenConverter;
//    private final TokenGenerator tokenGenerator;
//
//    public AccountFacade(LoginAccountProcessor loginAccountProcessor,
//        RegisterAccountProcessor registerAccountProcessor,
//        TokenConverter tokenConverter,
//        TokenGenerator tokenGenerator) {
//        this.loginAccountProcessor = loginAccountProcessor;
//        this.registerAccountProcessor = registerAccountProcessor;
//        this.tokenConverter = tokenConverter;
//        this.tokenGenerator = tokenGenerator;
//    }
//
//    @Transactional(readOnly = true)
//    public AccountTokenResponse login(LoginAccountCommand command) {
//
//        var accountContext = loginAccountProcessor.login(command.getUserId(),
//            command.getPassword());
//
//        var accessToken = tokenGenerator.createAccessToken(accountContext);
//        var refreshToken = tokenGenerator.createRefreshToken(accountContext);
//
//        return AccountTokenResponse.builder()
//            .accessToken(accessToken.getToken())
//            .refreshToken(refreshToken.getToken())
//            .userId(accountContext.getUserId())
//            .email(accountContext.getEmail())
//            .roles(new ArrayList<>(accountContext.getRoles()))
//            .build();
//    }
//
//    @Transactional
//    public RegisterAccountResponse register(RegisterAccountCommand command) {
//        registerAccountProcessor.register(
//            command.getUserId(),
//            command.getPassword(),
//            command.getEmail()
//        );
//
//        return RegisterAccountResponse.builder()
//            .email(command.getEmail())
//            .userId(command.getUserId())
//            .build();
//    }
//
//    public AccountResponse findInfo(AccountAuthentication principal) {
//        if (principal == null) {
//            throw new AccessDeniedException("Anonymous user require login authentication.");
//        }
//
//        var accountContext = (AccountContext) principal;
//        return new AccountResponse(accountContext.getUserId(), accountContext.getEmail());
//    }
//
//    @Transactional
//    public Token refresh(String token) {
//        return tokenConverter.convert(token);
//    }
}

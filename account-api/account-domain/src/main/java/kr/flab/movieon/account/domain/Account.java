package kr.flab.movieon.account.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import kr.flab.movieon.common.KeyGenerator;
import kr.flab.movieon.common.Role;
import kr.flab.movieon.common.domain.model.AbstractAggregateRoot;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "ACCOUNTS")
@DynamicUpdate
public class Account extends AbstractAggregateRoot {

    private static final String PREFIX = "act_";

    protected Account() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String accountKey;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String username;
    private String emailCheckToken;
    private LocalDateTime emailCheckTokenGeneratedAt;
    private boolean emailVerified;
    private LocalDateTime joinedAt;
    @UpdateTimestamp
    private LocalDateTime lastModifiedAt;
    private LocalDateTime withdrawalAt;
    private boolean withdraw;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    private Account(String email, String password, String username) {
        this.accountKey = KeyGenerator.generate(PREFIX);
        this.email = email;
        this.password = password;
        this.username = username;
        this.roles = Set.of(Role.USER);
    }

    public static Account register(String email, String password, String username) {
        var account = new Account(email, password, username);
        account.generateEmailCheckToken();
        return account;
    }

    private void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
        registerEvent(new RegisteredAccountEvent(this));
    }

    public void completeRegister() {
        this.emailVerified = true;
        this.joinedAt = LocalDateTime.now();
        registerEvent(new RegisterCompletedEvent(this));
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void withDraw() {
        this.withdraw = true;
        this.withdrawalAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailCheckToken() {
        return emailCheckToken;
    }

    public String getAccountKey() {
        return accountKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

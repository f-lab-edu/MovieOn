package kr.flab.movieon.account.domain;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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
import kr.flab.movieon.common.Role;
import kr.flab.movieon.common.domain.model.AbstractAggregateRoot;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "ACCOUNTS")
public class Account extends AbstractAggregateRoot {

    protected Account() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
        this.email = email;
        this.password = password;
        this.username = username;
        this.roles = Set.of(Role.USER);
        registerEvent(new RegisteredAccountEvent(this));
    }

    public static Account register(String email, String password, String username) {
        var account = new Account(email, password, username);
        account.generateEmailCheckToken();
        return account;
    }

    private void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    public void completeRegister() {
        this.emailVerified = true;
        this.joinedAt = LocalDateTime.now();
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

    public List<String> getAuthorities() {
        return this.roles.stream()
            .map(r -> "ROLE_" + r.name()).collect(toList());
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

insert into accounts(account_key, email, email_check_token, email_check_token_generated_at,
                     email_verified, joined_at, last_modified_at, password, username, withdraw,
                     withdrawal_at)
values ('act_202203081357591787178783', 'kitty@gmail.com', 'test-token', now(), false,
        null, null, '$2a$10$9ibGcn661rKyysh6Y9R8VuKhXZCQG.gP8JGRMfaoBRMAPT6npq0qe',
        'kitty', false, null),
       ('act_202203081357591787172342', 'solomon@gmail.com', 'test-token', now(), true,
        null, null, '$2a$10$9ibGcn661rKyysh6Y9R8VuKhXZCQG.gP8JGRMfaoBRMAPT6npq0qe',
        'solomon', false, null),
       ('act_202203081357591787172312', 'jiwon@gmail.com', 'test-token', now(), true,
        now(), null, '$2a$10$9ibGcn661rKyysh6Y9R8VuKhXZCQG.gP8JGRMfaoBRMAPT6npq0qe',
        'jiwon', false, null);

insert into account_roles(account_id, roles) values (3, 'ADMIN');

insert into notification_templates(contents, name, type, title, created_at)
values ('<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8">
  <title>MovieOn 회원가입 인증 메일</title>
</head>
<body>
<div>
  <p>안녕하세요. <span th:text="${username}"></span>님</p>

  <h2 th:text="${message}">메시지</h2>

  <div>
    <a th:href="${host + link}" th:text="${linkName}">Link</a>
    <p>링크가 동작하지 않는 경우에는 아래 URL을 웹브라우저에 복사해서 붙여 넣으세요.</p>
    <small th:text="${host + link}"></small>
  </div>
</div>
<footer>
  <small>MovieOn&copy; 2021</small>
</footer>
</body>
</html>', '계정 확인 메일', 'EMAIL', 'MovieOn 회원가입 인증 메일입니다.', now());

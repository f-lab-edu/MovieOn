INSERT INTO notification_templates(content, name, type, title, created_at)
    VALUES('<!DOCTYPE html>
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

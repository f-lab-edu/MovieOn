## Introduce

MovieOn 프로젝트는 [Modular Monolithic](!https://www.youtube.com/watch?v=5OjqD-ow8GE) 기반의 E-Commerce 애플리케이션입니다.

### Architecture

Modular Monolithic 기반의 아키텍처이므로, 다음과 같은 모듈들로 구성되어 있습니다.

| Modules          | Description                                  |
|------------------|----------------------------------------------|
| App              | 각 도메인 별 모듈을 통합하고 실행하는 Main 애플리케이션이 있는 모듈입니다. |
| Common           | 모든 모듈에서 전역적으로 사용하는 공통 개체가 담긴 모듈입니다.          |
| Account-Api      | 인증, 인가, 사용자 관리 기능을 담당하는 모듈입니다.               |
| Notification-Api | 이메일, 푸시 등의 알림을 사용자에게 발송하고 알림 설정을 관리하는 모듈입니다. |
| Order-Api        | 주문을 생성하고, 주문을 관리 및 처리하는 기능을 담당하는 모듈입니다.      |
| Payment-Api      | 주문한 상품에 대한 결제 처리 및 취소를 담당하는 모듈입니다.           |
| Product-Api      | 상품을 관리하고 사용자에게 노출하는 기능을 담당하는 모듈입니다.          |
| Point-Api        | (추가예정)                                       |
| Purchase-Api     | (대체예정)                                       |
| Integration-test | 통합 테스트를 지원하는 테스트 모듈입니다.                      |

각 도메인 모듈은 하위에 아래와 같이 Layered Architecture 형식의 Build Artifacts로 분리되어 구성됩니다.

예시)

|Build Artifacts|Description|
|------|---|
|Order-Presentation|주문 HTTP Api에 대한 EntryPoint를 제공하는 모듈입니다.|
|Order-Application|주문 로직에 대한 흐름을 관리하고 Domain 모듈로 책임을 위임하는 모듈입니다.|
|Order-Domain|주문 로직에 대한 추상화 및 구현이 담겨 있는 핵심 모듈입니다.|
|Order-Infrastructure|외부 구성 요소, Spring, 영속 관리를 DIP를 사용하여 의존성의 방향을 역전시키고 동작을 보장하도록 하는 모듈입니다.|

### Testing

### Run

Build & Testing Command

`./gradlew --parallel clean build`

### Setup

`git config core.hooksPath .githooks`

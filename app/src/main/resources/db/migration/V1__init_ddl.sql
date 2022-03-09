create table account_roles
(
    account_id bigint not null comment '계정 ID',
    roles      varchar(255) comment '권한'
) comment 'account_roles' charset = utf8mb4;

create table accounts
(
    id                             bigint       not null auto_increment comment '계정 ID',
    account_id                     varchar(255) not null comment '계정 대체키',
    email                          varchar(255) not null comment '이메일',
    email_check_token              varchar(255) comment '이메일 검증 토큰',
    email_check_token_generated_at datetime(6) comment '이메일 검증 토큰 생성일',
    email_verified                 bit          not null comment '이메일 검증 여부',
    joined_at                      datetime(6) comment '가입 완료일',
    last_modified_at               datetime(6) comment '마지막 수정일',
    password                       varchar(255) not null comment '비밀번호',
    username                       varchar(255) not null comment '회원명',
    withdraw                       bit          not null comment '계정 탈퇴 여부',
    withdrawal_at                  datetime(6) comment '계정 탈퇴 처리일',
    primary key (id)
) comment 'accounts' charset = utf8mb4;

create table category
(
    id   bigint not null auto_increment comment '카테고리 ID',
    name varchar(255) comment '카테고리명',
    primary key (id)
) comment 'category' charset = utf8mb4;

create table item_options
(
    item_id     bigint not null comment '품목 ID',
    option_name varchar(255) comment '품목 옵션명',
    sales_price bigint comment '품목 할인 가격'
) comment 'item_options' charset = utf8mb4;

create table items
(
    id             bigint not null auto_increment comment '품목 ID',
    available_days integer comment '이용 기간',
    base_price     bigint comment '품목 기본 가격',
    created_at     datetime(6) comment '생성일',
    modified_at    datetime(6) comment '수정일',
    name           varchar(255) comment '품목명',
    product_id     bigint comment '상품 ID',
    type           varchar(255) comment '품목 타입',
    primary key (id)
) comment 'items' charset = utf8mb4;

create table notification_groups
(
    id       bigint not null auto_increment comment '알림 그룹 ID',
    disabled bit    not null comment '알림 비활성화 여부',
    type     varchar(255) comment '알림 그룹 타입',
    primary key (id)
) comment 'notification_groups' charset = utf8mb4;

create table notification_settings
(
    id          bigint not null auto_increment comment '알림 설정 ID',
    created_at  datetime(6) comment '생성일',
    modified_at datetime(6) comment '수정일',
    account_id  varchar(255) not null comment '계정 ID',
    primary key (id)
) comment 'notification_settings' charset = utf8mb4;

create table notification_settings_groups
(
    notification_setting_id bigint not null comment '알림 설정 ID',
    groups_id               bigint not null comment '알림 그룹 ID',
    primary key (notification_setting_id, groups_id)
) comment 'notification_settings_groups' charset = utf8mb4;

create table notification_templates
(
    id          bigint       not null auto_increment comment '알림 템플릿 ID',
    contents    TEXT         not null comment '알림 템플릿',
    created_at  datetime(6) comment '생성일',
    modified_at datetime(6) comment '수정일',
    name        varchar(255) not null comment '알림 템플릿명',
    type        varchar(255) comment '알림 타입',
    title       varchar(255) comment '알림 템플릿 이메일 제목',
    primary key (id)
) comment 'notification_templates' charset = utf8mb4;

create table notification_group_options
(
    notification_group_id bigint not null comment '알림 그룹 ID',
    disabled              bit    not null comment '알림 그룹 옵션 비활성화 여부',
    type                  varchar(255) comment '알림 타입'
) comment 'notification_group_options' charset = utf8mb4;

create table notifications
(
    notification_type varchar(31)  not null comment '알림 타입',
    id                bigint       not null auto_increment comment '알림 ID',
    created_at        datetime(6) comment '알림 생성일',
    message           TEXT         not null comment '알림 메세지',
    account_id        varchar(255) not null comment '계정 ID',
    email             varchar(255) comment '알림 발송 이메일',
    title             varchar(255) comment '알림 발송 이메일 제목',
    primary key (id)
) comment 'notifications' charset = utf8mb4;

create table order_line_items
(
    id         bigint       not null auto_increment comment '주문 품목 ID',
    base_price bigint       not null comment '주문 품목 기본 금액',
    item_id    bigint       not null comment '품목 ID',
    name       varchar(255) not null comment '주문 품목명',
    primary key (id)
) comment 'order_line_items' charset = utf8mb4;

create table order_line_item_options
(
    order_line_item_id bigint not null comment '주문 품목 ID',
    option_name        varchar(255) comment '주문 품목 옵션명',
    sales_price        bigint comment '주문 품목 옵션 할인 금액'
) comment 'order_line_item_options' charset = utf8mb4;

create table orders
(
    id           bigint       not null auto_increment comment '주문 ID',
    completed_at datetime(6) comment '주문 완료일',
    created_at   datetime(6) comment '생성일',
    account_id   varchar(255) not null comment '계정 ID',
    modified_at  datetime(6) comment '수정일',
    order_id     varchar(255) not null comment '주문 대체키',
    pay_method   varchar(255) not null comment '결제 옵션',
    status       varchar(255) comment '주문 상태',
    total_amount bigint       not null comment '주문 합계 금액',
    use_of_point bigint       not null comment '사용된 포인트 금액',
    primary key (id)
) comment 'orders' charset = utf8mb4;

create table orders_line_items
(
    order_id      bigint not null comment '주문 ID',
    line_items_id bigint not null comment '주문 품목 ID'
) comment 'orders_line_items' charset = utf8mb4;

create table product_images
(
    product_id bigint not null comment '상품 ID',
    images     varchar(255) comment '상품 상세 이미지'
) comment 'product_images' charset = utf8mb4;

create table products
(
    id                bigint not null auto_increment comment '상품 ID',
    actors            varchar(255) comment '상품 배우 정보',
    available_devices varchar(255) comment '상품 이용 가능 기기',
    director          varchar(255) comment '상품 감독 정보',
    drm               bit    not null comment 'DRM 여부',
    provided_quality  varchar(255) comment '상품 제공 화질',
    rate              varchar(255) comment '상품 이용 연령가',
    released_at       date comment '상품 출시 년도',
    running_time      integer comment '상품 이용 시간',
    created_at        datetime(6) comment '생성일',
    description       TEXT comment '상품 설명',
    modified_at       datetime(6) comment '수정일',
    name              varchar(255) comment '상품명',
    thumbnails        varchar(255) comment '상품 섬네일 이미지',
    category_id       bigint comment '카테고리 ID',
    primary key (id)
) comment 'products' charset = utf8mb4;

create table refresh_token_infos
(
    id                bigint       not null auto_increment comment '리프레시 토큰 ID',
    created_at        datetime(6) comment '생성일',
    expired           bit          not null comment '리프레시 토큰 만료 여부',
    refresh_token_jti varchar(255) not null comment '리프레시 토큰 JTI',
    primary key (id)
) comment 'refresh_token_infos' charset = utf8mb4;

create table toss_payments
(
    id                      bigint not null auto_increment,
    cancel_amount           integer,
    cancel_reason           varchar(255),
    canceled_at             datetime(6),
    refundable_amount       integer,
    tax_free_amount_cancel  integer,
    acquire_status          varchar(255),
    approve_no              varchar(255),
    card_type               varchar(255),
    company                 varchar(255),
    installment_plan_months integer,
    is_interest_free        bit    not null,
    number                  varchar(255),
    owner_type              varchar(255),
    receipt_url             varchar(255),
    use_card_point          bit    not null,
    created_at              datetime(6),
    approved_at             datetime(6),
    balance_amount          integer,
    mid                     varchar(255),
    order_id                varchar(255),
    order_name              varchar(255),
    payment_key             varchar(255),
    requested_at            datetime(6),
    status                  varchar(255),
    supplied_amount         integer,
    tax_free_amount         integer,
    total_amount            integer,
    transaction_key         varchar(255),
    type                    varchar(255),
    vat                     integer,
    version                 varchar(255),
    modified_at             datetime(6),
    pay_method              varchar(255),
    primary key (id)
);

alter table accounts
    add constraint UK_IDX_ACCOUNT_EMAIL unique (email);

alter table accounts
    add constraint UK_IDX_ACCOUNT_ALTERNATIVE_KEY unique (account_id);

alter table notification_settings_groups
    add constraint UK_IDX_NOTIFICATION_SETTING_GROUP_ID unique (groups_id);

alter table notification_templates
    add constraint UK_IDX_NOTIFICATION_TEMPLATE_NAME unique (name);

alter table orders
    add constraint UK_IDX_ORDER_ALTERNATIVE_KEY unique (order_id);

alter table orders_line_items
    add constraint UK_IDX_ORDER_LINE_ITEM_ID unique (line_items_id);

alter table refresh_token_infos
    add constraint UK_IDX_REFRESH_INFO_JTI unique (refresh_token_jti);

alter table account_roles
    add constraint FK61h48dsir3h82pxbq3cwgp0ce
        foreign key (account_id)
            references accounts (id);

alter table item_options
    add constraint FKhggiv5f76uw3pnx1fu92yxclo
        foreign key (item_id)
            references items (id);

alter table notification_settings_groups
    add constraint FK3octoxf0nwsykqt6n9i9eg3vf
        foreign key (groups_id)
            references notification_groups (id);

alter table notification_settings_groups
    add constraint FKoy25fsn0noxl1vgtwsvw5e8pi
        foreign key (notification_setting_id)
            references notification_settings (id);

alter table notification_group_options
    add constraint FKmc37c7t4tj0myyfwrkqaffl6j
        foreign key (notification_group_id)
            references notification_groups (id);

alter table order_line_item_options
    add constraint FKghwc4v4vowmtncnkwt003fdyg
        foreign key (order_line_item_id)
            references order_line_items (id);

alter table orders_line_items
    add constraint FKjls6d9g3j5snl847tsonfmxdt
        foreign key (line_items_id)
            references order_line_items (id);

alter table orders_line_items
    add constraint FKemt0roi7rjadfixob26kp6u4q
        foreign key (order_id)
            references orders (id);

alter table product_images
    add constraint FKqnq71xsohugpqwf3c9gxmsuy
        foreign key (product_id)
            references products (id);

alter table products
    add constraint FK1cf90etcu98x1e6n9aks3tel3
        foreign key (category_id)
            references category (id);

insert into category(name)
values ('액션'),
       ('코미디'),
       ('드라마'),
       ('멜로'),
       ('공포/스릴러'),
       ('SF/판타지');

insert into products(actors, available_devices, director, drm, provided_quality,
                     rate, released_at, running_time, created_at, description, name, thumbnails,
                     category_id)
values ('장동건, 오다기리 죠, 판빙빙, 김인권', 'PC, 모바일, TV 총 5대', '강제규', false, '최대 1080(FHD), 5.51GB',
        'PARENTAL_GUIDANCE_15', '2011-12-22', 145, now(), '
1938년 경성. 제 2의 손기정을 꿈꾸는 조선청년 준식(장동건)과 일본 최고의 마라톤 대표선수 타츠오(오다기리 조). 어린 시절부터 서로에게 강한 경쟁의식을 가진 두 청년은 각각 조선과 일본을 대표하는 세기의 라이벌로 성장한다. 그러던 어느 날, 준식은 예기치 못한 사건에 휘말려 일본군에 강제 징집되고 그로부터 1년 후, 일본군 대위가 된 타츠오와 운명적인 재회를 하게 된다. 2차 세계대전의 거대한 소용돌이에 던져진 두 청년은 중국과 소련, 독일을 거쳐 노르망디에 이르는 12,000Km의 끝나지 않는 전쟁을 겪으며 점차 서로의 희망이 되어가는데… 적으로 만나 서로의 희망이 된 조선과 일본의 두 청년 국적을 초월한 인간애의 드라마가 시작된다!',
        '마이웨이', 'thumbnails.png', 1),
       ('톰 홀랜드, 젠데이아 콜먼, 베네딕트 컴버배치, 존 파브로, 제이콥 배덜런, 마리사 토메이, 알프리드 몰리나', 'PC, 모바일, TV 총 5대', '존 왓츠',
        true, '최대 1080(FHD), 5.51GB',
        'PARENTAL_GUIDANCE_15', '2021-12-15', 148, now(), '‘미스테리오’의 계략으로 세상에 정체가 탄로난 스파이더맨 ‘피터 파커’는 하루 아침에 평범한 일상을 잃게 된다. 문제를 해결하기 위해 ‘닥터 스트레인지’를 찾아가 도움을 청하지만 뜻하지 않게 멀티버스가 열리면서 각기 다른 차원의 불청객들이 나타난다. ‘닥터 옥토퍼스’를 비롯해 스파이더맨에게 깊은 원한을 가진 숙적들의 강력한 공격에 ‘피터 파커’는 사상 최악의 위기를 맞게 되는데…',
        '스파이더맨: 노 웨이 홈', 'thumbnails.png', 1);

insert into product_images(product_id, images)
values (1, 'image1.png'),
       (1, 'image2.png'),
       (2, 'image1.png'),
       (2, 'image2.png');

insert into items(available_days, base_price, created_at, modified_at, name, product_id, type)
values (5, 11000, now(), now(), '(구매) 마이웨이', 1, 'PURCHASE'),
       (5, 14900, now(), now(), '(구매) 스파이더맨: 노 웨이 홈', 2, 'PURCHASE');

insert into item_options(item_id, option_name, sales_price)
values (1, '720P 화질', 1500),
       (1, '480P 화질', 2000),
       (1, '1080P 화질', 1000),
       (1, '720P 화질', 1500);

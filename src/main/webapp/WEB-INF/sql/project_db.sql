-- 회사 정보 테이블
create table company(
    company_business_no varchar(50) primary key,            -- 사업자번호(123-45-67890)
    company_name varchar(50) not null,                      -- 회사명(4Scar)
    company_ceo_name varchar(50) not null,                  -- 대표이사
    company_addr varchar(50),                           	-- 주소(충청남도 천안시 서북구 불당22대로 92 7층)
    company_phone varchar(50),                           	-- 전화번호(1588-5530)
    company_found_date date,                           		-- 설립일(20220501)
    company_account varchar(50) unique not null             -- 계좌번호(농협은행 352-1588-5530-99)
);

-- 관리자 정보 테이블
create table admin(
    admin_no varchar(50) primary key,                     	-- 관리자번호 ex)A001
    admin_id varchar(50) unique key not null,               -- ID(admin01, admin02, admin03, admin04)
    admin_pwd varchar(50) not null,                         -- 비밀번호(1234)
    admin_name varchar(50) not null,                     	-- 성명
    admin_job varchar(50) not null,                         -- 직책(ceo, site_manager, dealer)
    admin_phone varchar(50) not null                     	-- 전화번호
);

-- 회원 정보 테이블
create table user(
    user_no varchar(50) primary key,                     	-- 회원번호 ex)U20240318001
    user_id varchar(50) unique key not null,                -- ID
    user_pwd varchar(50) not null,                        	-- 비밀번호
    user_name varchar(50) not null,                        	-- 성명
    user_phone varchar(50) not null,                     	-- 전화번호
    user_email varchar(50) not null,                     	-- 이메일
    user_mileage int default 0,                           	-- 마일리지
    user_grade varchar(50) default "bronze",                -- 회원등급
    user_regdate datetime default now()               		-- 가입일
);

-- 차량 스펙 테이블
create table car(
    car_manufact_company varchar(50) not null,              -- 제조사
    car_manufact_year int not null,                        	-- 연식
    car_manufact_month int not null,                        -- 월
    car_type varchar(50) not null,                        	-- 종류
    car_fuel varchar(50) not null,                        	-- 연료
    car_color varchar(50) not null,                        	-- 색상
    car_transmission varchar(50) not null,               	-- 변속기
    car_model varchar(50) not null                     		-- 차량모델
);

-- 차량 판매 요청 게시판(user -> admin)                                                                                                                                                                                                                                                                                                                                                
create table req_sell_board(
    req_sell_board_no int primary key,                      -- 글번호
    req_sell_board_user_no varchar(50) not null,            -- 요청자 회원번호
    req_sell_board_car_std_no varchar(200) not null,        -- 요청차량 구분번호(차량 스펙 조인)
    req_sell_board_car_no varchar(50) unique not null,      -- 차량번호
    req_sell_board_car_distance int not null,               -- 주행거리
    req_sell_board_car_price int not null,                  -- 가격
    req_sell_board_car_detail varchar(2000) not null,       -- 차량 상세 정보(딜러에게 정보 제공)
    req_sell_board_date datetime not null default now(),    -- 요청일자
    req_sell_board_update datetime,                        	-- 수정일자
    req_sell_board_car_file varchar(2000),                  -- 업로드 파일 저장 경로
    req_sell_board_status boolean not null default false,   -- 요청 승인 상태    
    
    foreign key (req_sell_board_user_no) references user (user_no) on update cascade
);

-- 차량 판매 게시판(admin)
create table sell_board(
    sell_board_no int primary key,                        	-- 글번호
    sell_board_admin_no varchar(50) not null,               -- 관리자번호(딜러 정보 조인)
    sell_board_seller_no varchar(50) default null,          -- 판매자 회원번호(구매이력 조인)
    sell_board_car_std_no varchar(200) not null,            -- 등록차량 구분번호(차량 스펙 조인)
    sell_board_car_no varchar(50) not null,                 -- 차량번호(회원 요청 사항 조인)
    sell_board_car_distance int not null,               	-- 차량 주행거리
    sell_board_car_price int not null,                  	-- 가격
    sell_board_car_detail varchar(2000) not null,           -- 차량 상세 정보(홍보 내용)
    sell_board_date datetime not null default now(),        -- 등록일자
    sell_board_update datetime,                           	-- 수정일자
    sell_board_car_file varchar(2000),                      -- 업로드 파일 저장 경로
    sell_board_status boolean not null default false,       -- 판매 상태
    sell_board_hit int default 0,							-- 판매 글 조회수
    
    foreign key (sell_board_admin_no) references admin (admin_no) on update cascade,
    foreign key (sell_board_seller_no) references user (user_no) on update cascade,
    foreign key (sell_board_car_no) references req_sell_board (req_sell_board_car_no) on update cascade on delete cascade
);

-- 차량 거래 이력 테이블
create table transaction(
   transaction_no int primary key,                          -- 거래번호
   transaction_seller_no varchar(50) not null,              -- 판매자 번호
   transaction_buyer_no varchar(50) not null,               -- 구매자 번호
   transaction_dealer_no varchar(50) not null,              -- 딜러 번호
   transaction_car_std_no varchar(200) not null,            -- 거래 차량 구분번호
   transaction_car_no varchar(50) not null,                 -- 거래 차량번호
   transaction_car_distance int not null,                   -- 거래 차량 주행거리
   transaction_car_price int not null,                      -- 가격
   transaction_date datetime not null default now(),        -- 거래일자
   transaction_sell_review_status boolean default false,	-- 판매자 후기글 등록 상태
   transaction_buy_review_status boolean default false,		-- 구매자 후기글 등록 상태
   
   foreign key (transaction_seller_no) references user (user_no) on update cascade,
   foreign key (transaction_buyer_no) references user (user_no) on update cascade,
   foreign key (transaction_dealer_no) references admin (admin_no) on update cascade
);

create table mileage(
   mileage_user_no varchar(50) not null,            	-- 적립 회원번호
   mileage_date datetime default now(),            		-- 적립 일시
    mileage_cont varchar(50) not null,               	-- 적립 내용
    mileage_type varchar(50) not null,               	-- 적립(+), 차감(-) 구분
    mileage_amount int not null,                  		-- 적립 마일리지
    mileage_status int not null,                  		-- 현재 마일리지
    mileage_grade varchar(50) not null               	-- 현재 등급
);

-- 찜 목록 테이블
create table favorite(
    favorite_no int,                      				-- 찜목록 번호
    favorite_user_no varchar(50) not null,              -- 찜한 회원번호
    favorite_sell_board_no int not null,                -- 찜한 차량 판매 글번호
    
    foreign key (favorite_user_no) references user (user_no) on update cascade,
    foreign key (favorite_sell_board_no) references sell_board (sell_board_no) on delete cascade on update cascade
);

-- 결제 테이블
create table payment(
    payment_no varchar(50) primary key,                     	 -- 결제번호 ex)P20230318001
    payment_buyer_no varchar(50) not null,						 -- 구매자 정보
    payment_method varchar(50) not null,                  		 -- 결제방식(현금, 카드)
    payment_card_company varchar(50),                     		 -- 카드사(카드결제 선택 시 not null)
    payment_card_no varchar(50),                        		 -- 카드번호(카드결제 선택 시 not null)
    payment_card_div_month int,                           		 -- 할부개월수(0~12)(카드결제 선택 시 not null)
    payment_card_pwd varchar(50),                        		 -- 카드 비밀번호(카드결제 선택 시 not null)
    payment_amount int not null,			                   	 -- 결제금액
    payment_sell_board_no int not null							 -- 판매 테이블 넘버
);

-- 판매 후기 게시판 테이블
create table sell_review_board(
    sell_review_board_no int primary key,                        -- 글번호
    sell_review_board_writer_id varchar(50) not null,            -- 작성자
    sell_review_board_title varchar(50) not null,                -- 제목
    sell_review_board_cont varchar(50) not null,                 -- 내용
    sell_review_board_date datetime not null default now(),      -- 등록일시
    sell_review_board_update datetime,                           -- 수정일시
    sell_review_board_hit int default 0,                         -- 조회수
    sell_review_board_file varchar(2000),                   	 -- 파일 업로드 경로
    sell_review_transaction_no int,                        		 -- 거래내역 테이블 기본키
    
    foreign key (sell_review_transaction_no) references transaction (transaction_no) on update cascade
);

-- 자유 게시판 테이블
create table community_board(
    community_board_no int primary key,                     	-- 글번호
    community_board_writer_id varchar(50) not null,            	-- 작성자
    community_board_title varchar(50) not null,               	-- 제목
    community_board_cont varchar(2000) not null,               	-- 내용
    community_board_date datetime not null default now(),       -- 등록일시
    community_board_update datetime,                    		-- 수정일시
    community_board_hit int default 0                           -- 조회수
);

-- 구매 후기 게시판 테이블
create table buy_review_board(
    buy_review_board_no int primary key,                        -- 글번호
    buy_review_board_writer_id varchar(50) not null,            -- 작성자
    buy_review_board_title varchar(50) not null,                -- 제목
    buy_review_board_cont varchar(50) not null,                 -- 내용
    buy_review_board_date datetime not null default now(),      -- 등록일시
    buy_review_board_update datetime,                           -- 수정일시
    buy_review_board_hit int default 0,                         -- 조회수
    buy_review_board_file varchar(2000),                        -- 파일 업로드 경로
    buy_review_transaction_no int,                              -- 거래내역 테이블 기본키
    
    foreign key (buy_review_transaction_no) references transaction (transaction_no) on update cascade
);

create table qna_board(
    qna_board_no int primary key,                  			-- 글번호
    qna_board_type varchar(50) not null,            		-- 문의 유형
    qna_board_writer_id varchar(50) not null,         		-- 작성자
    qna_board_title varchar(50) not null,            		-- 제목
    qna_board_cont varchar(2000) not null,            		-- 내용
    qna_board_date datetime not null default now(),      	-- 게시일자
    qna_board_update datetime,                     			-- 수정일자
    qna_board_file varchar(2000),                  			-- 업로드 파일
    qna_board_status boolean not null default false     	-- 문의 상태
);

create table notice_board(
    notice_board_no int primary key,                  		-- 글번호
    notice_board_writer_id varchar(50) not null,         	-- 작성자
    notice_board_title varchar(50) not null,            	-- 제목
    notice_board_cont varchar(2000) not null,               -- 내용
    notice_board_date datetime not null default now(),      -- 게시일자
    notice_board_update datetime,                     		-- 수정일자
    notice_board_hit int default 0,                     	-- 조회수
    notice_board_file varchar(2000)                     	-- 업로드 파일
);

create table event_board(
    event_board_no int primary key,                     	-- 글번호
    event_board_writer_id varchar(50) not null,            	-- 작성자
    event_board_title varchar(50) not null,               	-- 제목
    event_board_cont varchar(2000) not null,   				-- 내용
    event_board_date datetime not null default now(),		-- 게시일자
    event_board_update datetime,                     		-- 수정일자
    event_board_hit int default 0,                     		-- 조회수
    event_board_file varchar(2000)                     		-- 업로드 파일
);

create table log(
    log_date datetime not null default now(),
    log_user_no varchar(50) not null,
    log_user_id varchar(50) not null,
    log_type varchar(50) not null
);

-- admin insert
insert into admin values("A001", "admin01", "1234", "박성민", "ceo", "010-0000-0000");
insert into admin values("A002", "admin02", "1234", "길호성", "site_manager", "010-0000-0000");
insert into admin values("A003", "admin03", "1234", "이솔", "dealer", "010-0000-0000");
insert into admin values("A004", "admin04", "1234", "구연서", "dealer", "010-0000-0000");

-- company info insert
insert into company values("123-45-67890", "(주)4Scar", "박성민", "충청남도 천안시 서북구 불당22대로 92 7층", "1588-5530", "20220501", "농협은행 352-1588-5530-99");

-- notice insert
insert into notice_board values(1, "A002", "안녕하세요 4Scar입니다.", "공지사항", default, null, default, null);
insert into notice_board values(2, "A002", "사이트 점검 일시 안내.", "공지사항", default, null, default, null);
insert into notice_board values(3, "A002", "결제 시스템 점검 안내.", "공지사항", default, null, default, null);
insert into notice_board values(4, "A002", "최근 이슈 관련 안내.", "공지사항", default, null, default, null);
insert into notice_board values(5, "A002", "금주 안내사항.", "공지사항", default, null, default, null);
insert into notice_board values(6, "A002", "금주 안내사항.ㄱ", "공지사항", default, null, default, null);
insert into notice_board values(7, "A002", "금주 안내사항.ㄴ", "공지사항", default, null, default, null);
insert into notice_board values(8, "A002", "금주 안내사항.ㄷ", "공지사항", default, null, default, null);
insert into notice_board values(9, "A002", "금주 안내사항.ㄹ", "공지사항", default, null, default, null);
insert into notice_board values(10, "A002", "금주 안내사항.ㅁ", "공지사항", default, null, default, null);
insert into notice_board values(11, "A002", "금주 안내사항.ㅂ", "공지사항", default, null, default, null);
insert into notice_board values(12, "A002", "금주 안내사항.ㅅ", "공지사항", default, null, default, null);
insert into notice_board values(13, "A002", "금주 안내사항.ㅇ", "공지사항", default, null, default, null);
insert into notice_board values(14, "A002", "금주 안내사항.ㅈ", "공지사항", default, null, default, null);
insert into notice_board values(15, "A002", "금주 안내사항.ㅊ", "공지사항", default, null, default, null);
insert into notice_board values(16, "A002", "금주 안내사항.ㅌ", "공지사항", default, null, default, null);
insert into notice_board values(17, "A002", "금주 안내사항.ㅍ", "공지사항", default, null, default, null);
insert into notice_board values(18, "A002", "금주 안내사항.ㅎ", "공지사항", default, null, default, null);
insert into notice_board values(19, "A002", "금주 안내사항.1", "공지사항", default, null, default, null);
insert into notice_board values(20, "A002", "금주 안내사항.2", "공지사항", default, null, default, null);
insert into notice_board values(21, "A002", "금주 안내사항.3", "공지사항", default, null, default, null);
insert into notice_board values(22, "A002", "금주 안내사항.4", "공지사항", default, null, default, null);
insert into notice_board values(23, "A002", "금주 안내사항.5", "공지사항", default, null, default, null);
insert into notice_board values(24, "A002", "금주 안내사항.6", "공지사항", default, null, default, null);
insert into notice_board values(25, "A002", "금주 안내사항.7", "공지사항", default, null, default, null);
insert into notice_board values(26, "A002", "금주 안내사항.8", "공지사항", default, null, default, null);
insert into notice_board values(27, "A002", "금주 안내사항.9", "공지사항", default, null, default, null);
insert into notice_board values(28, "A002", "금주 안내사항.10", "공지사항", default, null, default, null);
insert into notice_board values(29, "A002", "금주 안내사항.11", "공지사항", default, null, default, null);
insert into notice_board values(30, "A002", "금주 안내사항.12", "공지사항", default, null, default, null);
insert into notice_board values(31, "A002", "금주 안내사항.13", "공지사항", default, null, default, null);
insert into notice_board values(32, "A002", "금주 안내사항.14", "공지사항", default, null, default, null);
insert into notice_board values(33, "A002", "금주 안내사항.15", "공지사항", default, null, default, null);
insert into notice_board values(34, "A002", "금주 안내사항.16", "공지사항", default, null, default, null);
insert into notice_board values(35, "A002", "금주 안내사항.17", "공지사항", default, null, default, null);
insert into notice_board values(36, "A002", "금주 안내사항.18", "공지사항", default, null, default, null);
insert into notice_board values(37, "A002", "금주 안내사항.a", "공지사항", default, null, default, null);
insert into notice_board values(38, "A002", "금주 안내사항.b", "공지사항", default, null, default, null);
insert into notice_board values(39, "A002", "금주 안내사항.c", "공지사항", default, null, default, null);
insert into notice_board values(40, "A002", "금주 안내사항.22d", "공지사항", default, null, default, null);
insert into notice_board values(41, "A002", "금주 안내사항.23e", "공지사항", default, null, default, null);
insert into notice_board values(42, "A002", "금주 안내사항.24f", "공지사항", default, null, default, null);
insert into notice_board values(43, "A002", "금주 안내사항.25g", "공지사항", default, null, default, null);

-- event insert
insert into event_board values(1, "A002", "이벤트 리스트1.", "공지1사항", default, null, default, null);
insert into event_board values(2, "A002", "이벤트 리스트2.", "공지2사항", default, null, default, null);
insert into event_board values(3, "A002", "이벤트 리스트3.", "공지3사항", default, null, default, null);
insert into event_board values(4, "A002", "이벤트 리스트4.", "공지4사항", default, null, default, null);
insert into event_board values(5, "A002", "이벤트 리스트5.", "공지5사항", default, null, default, null);
insert into event_board values(6, "A002", "이벤트 리스트6.", "공지6사항", default, null, default, null);
insert into event_board values(7, "A002", "이벤트 리스트7.", "공지7사항", default, null, default, null);
insert into event_board values(8, "A002", "이벤트 리스트8.", "공지8사항", default, null, default, null);
insert into event_board values(9, "A002", "이벤트 리스트9.", "공지9사항", default, null, default, null);
insert into event_board values(10, "A002", "이벤트 리스트10a.", "공지ㄱ사항", default, null, default, null);
insert into event_board values(11, "A002", "이벤트 리스트11b.", "공지ㄴ사항", default, null, default, null);
insert into event_board values(12, "A002", "이벤트 리스트12c.", "공지ㄷ사항", default, null, default, null);
insert into event_board values(13, "A002", "이벤트 리스트1ㅋd.", "공지ㄹ사항", default, null, default, null);
insert into event_board values(14, "A002", "이벤트 리스트ㅌe.", "공지ㅁ사항", default, null, default, null);
insert into event_board values(15, "A002", "이벤트 리스트ㅠf.", "공지ㅂ사항", default, null, default, null);
insert into event_board values(16, "A002", "이벤트 리스트ㄱ.", "공지ㅅ사항", default, null, default, null);
insert into event_board values(17, "A002", "이벤트 리스트ㄴ.", "공지ㅇㅇ사항", default, null, default, null);
insert into event_board values(18, "A002", "이벤트 리스트ㄷ.", "공지ㅋㅌ사항", default, null, default, null);
insert into event_board values(19, "A002", "이벤트 리스트ㄹ.", "공지ㅈㅈ사항", default, null, default, null);
insert into event_board values(20, "A002", "이벤트 리스트ㅁ.", "공지ㅂㄷ사항", default, null, default, null);
insert into event_board values(21, "A002", "이벤트 리스트ㅂ.", "공지ㅌ사항", default, null, default, null);
insert into event_board values(22, "A002", "이벤트 리스트ㅅ.", "공지ㅁㅇㅈ사항", default, null, default, null);
insert into event_board values(23, "A002", "이벤트 리스트ㅇ.", "공지asd사항", default, null, default, null);
insert into event_board values(24, "A002", "이벤트 리스트ㅈ.", "공지qwe사항", default, null, default, null);
insert into event_board values(25, "A002", "이벤트 리스트ㅊ.", "공지zxc사항", default, null, default, null);

-- user insert
insert into user values ("U20230518001", "psm9024", 1234, "박성민", "010-1234-5678", "이@메.일", 10000, default, default);
insert into user values ("U20230518002", "rlfghtjd", 1234, "길호성", "010-1234-5678", "이@메.일", 10000, default, default);
insert into user values ("U20230618003", "qnq11", 1234, "구연서", "010-1234-5678", "이@메.일", 10000, default, default);
insert into user values ("U20231018004", "lmo05200", 1234, "이솔", "010-1234-5678", "이@메.일", 10000, default, default);
insert into user values ("U20231118005", "qwer1", 1234, "권오성", "010-0000-1234", "이@메.일", 10000, default, default);
insert into user values ("U20231118006", "asdf1", 1234, "권용진", "010-0000-1234", "이@메.일", 10000, default, default);
insert into user values ("U20231218007", "zxcv1", 1234, "정동화", "010-0000-1234", "이@메.일", 10000, default, default);
insert into user values ("U20231218008", "qwer2", 1234, "염종원", "010-0000-1234", "이@메.일", 10000, default, default);
insert into user values ("U20231218009", "asdf3", 1234, "이동훈", "010-0000-1234", "이@메.일", 10000, default, default);
insert into user values ("U20240218010", "asdf2", 1234, "이재성", "010-0000-1234", "이@메.일", 10000, default, default);
insert into user values ("U20240218011", "zxcv2", 1234, "이찬우", "010-0000-1234", "이@메.일", 10000, default, default);
insert into user values ("U20240418012", "qwer3", 1234, "천재희", "010-0000-1234", "이@메.일", 10000, default, default);
insert into user values ("U20240518013", "qwer4", 1234, "최유경", "010-0000-1234", "이@메.일", 10000, default, default);


-- req_sell_board insert
insert into req_sell_board values (1, "U20230518001", "현대_대형_2022_3_LPG_자동_검정색_그랜저", "11가1111", 10000, 34000000, "req_sell_board insert1", "2024-04-18", null, "../images/temp/현대_그랜저_검정색_22년_03월.jpg", default);
insert into req_sell_board values (2, "U20230518001", "제네시스_대형_2021_5_가솔린_자동_회색_G90", "22나2222", 20000, 30000000, "req_sell_board insert2", "2024-04-18", null, "../images/temp/제네시스_G90_회색_21년_05월.jpg", default);
insert into req_sell_board values (3, "U20230518001", "기아_SUV_2010_12_디젤_자동_검정색_쏘렌토", "33다3333", 30000, 35000000, "req_sell_board insert3", "2024-04-18", null, "../images/temp/기아_쏘렌토_검정색_10년_12월.jpg", default);
insert into req_sell_board values (4, "U20230518001", "쉐보레_중형_2013_11_가솔린_자동_은색_임팔라", "44라4444", 40000, 20000000, "req_sell_board insert4", "2024-04-18", null, "../images/temp/쉐보레_임팔라_은색_13년_11월.jpg", default);
insert into req_sell_board values (5, "U20230518001", "르노코리아_중형_2020_7_가솔린_자동_은색_SM5", "55마5555", 50000, 15000000, "req_sell_board insert5", "2024-04-18", null, "../images/temp/르노_SM5_흰색_20년_07월.jpg", default);
insert into req_sell_board values (6, "U20230518001", "대우_화물_2018_3_디젤_자동_파란색_다마스", "66바6666", 60000, 18000000, "req_sell_board insert6", "2024-04-18", null, "../images/temp/대우_다마스_파란색_18년_03월.jpg", default);
insert into req_sell_board values (7, "U20230518001", "쌍용_SUV_2019_3_가솔린_자동_빨간색_티볼리", "77사7777", 70000, 28500000, "req_sell_board insert7", "2024-04-18", null, "../images/temp/쌍용_티볼리_빨간색_19년_03월.jpg", default);
insert into req_sell_board values (8, "U20230518001", "벤츠_대형_2015_6_하이브리드_자동_파란색_S560", "88아8888", 80000, 160000000, "req_sell_board insert8", "2024-04-18", null, "../images/temp/벤츠_S560_검정색_15년_06월.jpg", default);
insert into req_sell_board values (9, "U20230518001", "BMW_대형_2022_1_가솔린_자동_은색_528I", "99자9999", 90000, 56000000, "req_sell_board insert9", "2024-04-18", null, "../images/temp/BMW_528i_은색_22년_01월.jpg", default);
insert into req_sell_board values (10, "U20230518001", "아우디_대형_2023_4_디젤_자동_흰색_A8", "111차1111", 100000, 110000000, "req_sell_board insert10", "2024-04-18", null, "../images/temp/아우디_A8_흰색_23년_04월.jpg", default);
insert into req_sell_board values (11, "U20230518001", "폭스바겐_SUV_2009_9_디젤_자동_검정색_티구안", "222카2222", 110000, 44000000, "req_sell_board insert11", "2024-04-18", null, "../images/temp/폭스바겐_티구안_검정색_09년_09월.jpg", default);
insert into req_sell_board values (12, "U20230518001", "미니_소형_2016_12_가솔린_수동_검정색_컨트리맨", "333타3333", 120000, 35000000, "req_sell_board insert12", "2024-04-18", null, "../images/temp/미니_컨트리맨_검정색_16년_12월.jpg", default);
insert into req_sell_board values (13, "U20230518001", "볼보_SUV_2018_10_디젤_자동_빨간색_XC90", "444파4444", 130000, 85000000, "req_sell_board insert13", "2024-04-18", null, "../images/temp/볼보_XC90_은색_18년_10월.jpg", default);
insert into req_sell_board values (14, "U20230518001", "폴스타_대형_2017_1_가솔린_자동_검정색_폴스타6", "555하5555", 140000, 75000000, "req_sell_board insert14", "2024-04-18", null, "../images/temp/폴스타_폴스타6_파란색_17년_01월.jpg", default);
insert into req_sell_board values (15, "U20230518001", "포르쉐_SUV_2020_2_디젤_자동_검정색_카이엔", "666거6666", 150000, 105000000, "req_sell_board insert15", "2024-04-18", null, "../images/temp/포르쉐_카이엔_검정색_20년_02월.jpg", default);
insert into req_sell_board values (16, "U20230518001", "도요타_중형_2019_9_LPG_자동_회색_프리우스", "777너7777", 160000, 31000000, "req_sell_board insert16", "2024-04-18", null, "../images/temp/도요타_프리우스_회색_19년_09월.jpg", default);
insert into req_sell_board values (17, "U20230518001", "벤츠_대형_2021_8_전기_자동_회색_E350", "888더8888", 170000, 56000000, "req_sell_board insert17", "2024-04-18", null, "../images/temp/벤츠_E350_회색_21년_08월.jpg", default);
insert into req_sell_board values (18, "U20230518001", "BMW_스포츠_2017_2_가솔린_수동_은색_M4", "999러9999", 180000, 60000000, "req_sell_board insert18", "2024-04-18", null, "../images/temp/BMW_M4_은색_17년_02월.jpg", default);
insert into req_sell_board values (19, "U20230518001", "아우디_중형_2019_5_전기_자동_흰색_A6", "123머1234", 190000, 70000000, "req_sell_board insert19", "2024-04-18", null, "../images/temp/아우디_A6_흰색_19년_05월.jpg", default);
insert into req_sell_board values (20, "U20230518001", "폭스바겐_중형_2018_9_가솔린_자동_검정색_아테온", "456버4567", 200000, 44000000, "req_sell_board insert20", "2024-04-18", null, "../images/temp/폭스바겐_아테온_검정색_18년_09월.jpg", default);
insert into req_sell_board values (21, "U20230518002", "기아_중형_2023_1_가솔린_자동_흰색_캐스퍼", "123아0007", 1200, 34000000, "req_sell_board insert21", "2024-04-18", null, "../images/temp/기아_캐스퍼_흰색_23년_01월.jpg", default);
insert into req_sell_board values (22, "U20230618003", "쉐보레_소형_2020_10_가솔린_자동_파란색_스파크", "009자1230", 2000, 9000000, "req_sell_board insert22", "2024-04-18", null, "../images/temp/쉐보레_스파크_파란색_20년_10월.jpg", default);
insert into req_sell_board values (23, "U20230618003", "기아_중형_2021_3_가솔린_자동_흰색_레이", "001차0067", 2000, 22400000, "req_sell_board insert23", "2024-04-18", null, "../images/temp/기아_레이_흰색_21년_03월.jpg", default);
insert into req_sell_board values (24, "U20231018004", "현대_소형_2018_9_전기_자동_검정색_코나", "010허8520", 12400, 15000000, "req_sell_board insert24", "2024-04-18", null, "../images/temp/현대_코나_검정색_18년_09월.jpg", default);
insert into req_sell_board values (25, "U20231018004", "현대_중형_2017_5_하이브리드_자동_빨간색_아이오닉", "02지1113", 13000, 17800000, "req_sell_board insert25", "2024-04-18", null, "../images/temp/현대_아이오닉_빨간색_17년_05월.jpg", default);
insert into req_sell_board values (26, "U20230518001", "현대_소형_2017_2_가솔린_자동_검정색_아반떼", "119가1111", 15000, 24000000, "req_sell_board insert26", "2024-04-18", null, "../images/temp/현대_아반떼_검정색_17년_02월.jpg", default);
insert into req_sell_board values (27, "U20230518001", "현대_중형_2018_7_디젤_자동_회색_소나타", "229나2222", 25000, 40000000, "req_sell_board insert7", "2024-04-18", null, "../images/temp/현대_소나타_회색_18년_07월.jpg", default);
insert into req_sell_board values (28, "U20230518001", "현대_SUV_2015_12_디젤_자동_검정색_투싼", "339다3333", 35000, 30000000, "req_sell_board insert28", "2024-04-18", null, "../images/temp/현대_투싼_검정색_15년_12월.jpg", default);
insert into req_sell_board values (29, "U20230518001", "현대_SUV_2020_12_가솔린_자동_은색_팰리세이드", "449라4444", 45000, 25000000, "req_sell_board insert29", "2024-04-18", null, "../images/temp/현대_팰리세이드_은색_20년_12월.jpg", default);
insert into req_sell_board values (30, "U20230518001", "기아_소형_2021_6_가솔린_자동_흰색_K3", "559마5555", 55000, 25000000, "req_sell_board insert30", "2024-04-18", null, "../images/temp/기아_K3_흰색_21년_06월.jpg", default);
insert into req_sell_board values (31, "U20230518001", "기아_중형_2011_6_전기_자동_검정색_K5", "669바6666", 65000, 28000000, "req_sell_board insert31", "2024-04-18", null, "../images/temp/기아_K5_검정색_11년_06월.jpg", default);
insert into req_sell_board values (32, "U20230518001", "기아_대형_2012_1_LPG_자동_회색_K7", "779사7777", 75000, 19500000, "req_sell_board insert32", "2024-04-18", null, "../images/temp/기아_K7_회색_12년_01월.jpg", default);
insert into req_sell_board values (33, "U20230518001", "기아_대형_2022_3_하이브리드_자동_검정색_K8", "889아8888", 85000, 25000000, "req_sell_board insert33", "2024-04-18", null, "../images/temp/기아_K8_검정색_22년_03월.jpg", default);
insert into req_sell_board values (34, "U20230518001", "기아_대형_2013_3_가솔린_자동_은색_K9", "999자9999", 95000, 48000000, "req_sell_board insert34", "2024-04-18", null, "../images/temp/기아_K9_은색_13년_03월.jpg", default);
insert into req_sell_board values (35, "U20230518001", "기아_SUV_2019_4_디젤_자동_흰색_스포티지", "118차1111", 150000, 21000000, "req_sell_board insert35", "2024-04-18", null, "../images/temp/기아_스포티지_흰색_19년_04월.jpg", default);
insert into req_sell_board values (36, "U20230518001", "기아_SUV_2010_6_디젤_자동_검정색_카니발", "228카2222", 111000, 30000000, "req_sell_board insert36", "2024-04-18", null, "../images/temp/기아_카니발_검정색_10년_06월.jpg", default);
insert into req_sell_board values (37, "U20230518001", "제네시스_중형_2018_1_전기_수동_검정색_G70", "338타3333", 125000, 46000000, "req_sell_board insert37", "2024-04-18", null, "../images/temp/제네시스_G70_검정색_18년_01월.jpg", default);
insert into req_sell_board values (38, "U20230518001", "제네시스_대형_2022_11_가솔린_자동_빨간색_G80", "448파4444", 135000, 66000000, "req_sell_board insert38", "2024-04-18", null, "../images/temp/제네시스_G80_빨간색_22년_11월.jpg", default);
insert into req_sell_board values (39, "U20230518001", "제네시스_SUV_2023_3_하이브리드_자동_검정색_GV70", "558하5555", 145000, 69000000, "req_sell_board insert39", "2024-04-18", null, "../images/temp/제네시스_GV70_검정색_23년_03월.jpg", default);
insert into req_sell_board values (40, "U20230518001", "제네시스_SUV_2019_3_디젤_자동_검정색_GV80", "668거6666", 155000, 95000000, "req_sell_board insert40", "2024-04-18", null, "../images/temp/제네시스_GV80_검정색_19년_03월.jpg", default);
insert into req_sell_board values (41, "U20230518001", "아우디_중형_2016_9_LPG_자동_회색_A5", "778너7777", 165000, 24000000, "req_sell_board insert41", "2024-04-18", null, "../images/temp/아우디_A5_회색_16년_09월.jpg", default);
insert into req_sell_board values (42, "U20230518001", "아우디_중형_2016_8_가솔린_자동_흰색_A6", "887더8888", 175000, 46000000, "req_sell_board insert42", "2024-04-18", null, "../images/temp/아우디_A6_흰색_19년_05월.jpg", default);
insert into req_sell_board values (43, "U20230518001", "아우디_대형_2015_2_가솔린_수동_검정색_A7", "998러9999", 185000, 50000000, "req_sell_board insert43", "2024-04-18", null, "../images/temp/아우디_A7_은색_15년_02월.jpg", default);
insert into req_sell_board values (44, "U20230518001", "르노코리아_소형_2020_5_가솔린_자동_흰색_SM3", "128머1234", 195000, 10000000, "req_sell_board insert44", "2024-04-18", null, "../images/temp/르노_SM3_흰색_20년_05월.jpg", default);
insert into req_sell_board values (45, "U20230518001", "르노코리아_중형_2015_9_LPG_자동_검정색_SM6", "458버4567", 250000, 23000000, "req_sell_board insert45", "2024-04-18", null, "../images/temp/르노_SM6_검정색_15년_09월.jpg", default);
insert into req_sell_board values (46, "U20230518002", "르노코리아_대형_2021_1_LPG_자동_회색_SM7", "128아0007", 95000, 34000000, "req_sell_board insert46", "2024-04-18", null, "../images/temp/르노_SM7_회색_21년_01월.jpg", default);
insert into req_sell_board values (47, "U20230618003", "르노코리아_SUV_2017_11_가솔린_자동_파란색_QM3", "008자1230", 80000, 11000000, "req_sell_board insert47", "2024-04-18", null, "../images/temp/르노_QM3_파란색_17년_11월.jpg", default);
insert into req_sell_board values (48, "U20230618003", "르노코리아_SUV_2014_3_디젤_자동_흰색_QM5", "008차0067", 50000, 22600000, "req_sell_board insert48", "2024-04-18", null, "../images/temp/르노_QM5_흰색_14년_03월.jpg", default);
insert into req_sell_board values (49, "U20231018004", "볼보_중형_2022_10_전기_자동_빨간색_S60", "018허8520", 80000, 35000000, "req_sell_board insert49", "2024-04-18", null, "../images/temp/볼보_S60_빨강색_22년_10월.jpg", default);
insert into req_sell_board values (50, "U20231018004", "볼보_대형_2021_4_하이브리드_자동_검정색_S90", "028지1113", 58000, 114000000, "req_sell_board insert50", "2024-04-18", null, "../images/temp/볼보_S90_검정색_21년_04월.jpg", default);

-- sell_board insert
insert into sell_board values (1, "A001", "U20230518001", "현대_대형_2022_3_LPG_자동_검정색_그랜저", "11가1111", 10000, 34000000, "sell_board insert1", "2023-06-18", null, "../images/temp/현대_그랜저_검정색_22년_03월.jpg", default, 10);
insert into sell_board values (2, "A001", "U20230518001", "제네시스_대형_2021_5_가솔린_자동_회색_G90", "22나2222", 20000, 30000000, "sell_board insert2", "2023-06-18", null, "../images/temp/제네시스_G90_회색_21년_05월.jpg", default, default);
insert into sell_board values (3, "A001", "U20230518001", "기아_SUV_2010_12_디젤_자동_검정색_쏘렌토", "33다3333", 30000, 35000000, "sell_board insert3", "2023-06-18", null, "../images/temp/기아_쏘렌토_검정색_10년_12월.jpg", default, default);
insert into sell_board values (4, "A001", "U20230518001", "쉐보레_중형_2013_11_가솔린_자동_은색_임팔라", "44라4444", 40000, 20000000, "sell_board insert4", "2023-06-18", null, "../images/temp/쉐보레_임팔라_은색_13년_11월.jpg", default, 1004);
insert into sell_board values (5, "A001", "U20230518001", "르노코리아_중형_2020_7_가솔린_자동_은색_SM5", "55마5555", 50000, 15000000, "sell_board insert5", "2023-06-18", null, "../images/temp/르노_SM5_흰색_20년_07월.jpg", default, default);
insert into sell_board values (6, "A001", "U20230518001", "대우_화물_2018_3_디젤_자동_파란색_다마스", "66바6666", 60000, 18000000, "sell_board insert6", "2023-07-18", null, "../images/temp/대우_다마스_파란색_18년_03월.jpg", default, 999);
insert into sell_board values (7, "A001", "U20230518001", "쌍용_SUV_2019_3_가솔린_자동_빨간색_티볼리", "77사7777", 70000, 28500000, "sell_board insert7", "2023-07-18", null, "../images/temp/쌍용_티볼리_빨간색_19년_03월.jpg", default, default);
insert into sell_board values (8, "A001", "U20230518001", "벤츠_대형_2015_6_하이브리드_자동_검정색_S560", "88아8888", 80000, 160000000, "sell_board insert8", "2023-07-18", null, "../images/temp/벤츠_S560_검정색_15년_06월.jpg", default, default);
insert into sell_board values (9, "A001", "U20230518001", "BMW_대형_2022_1_가솔린_자동_은색_528I", "99자9999", 90000, 56000000, "sell_board insert9", "2023-08-18", null, "../images/temp/BMW_528i_은색_22년_01월.jpg", default, default);
insert into sell_board values (10, "A001", "U20230518001", "아우디_대형_2023_4_디젤_자동_흰색_A8", "111차1111", 100000, 110000000, "sell_board insert10", "2023-08-18", null, "../images/temp/아우디_A8_흰색_23년_04월.jpg", default, 2222);
insert into sell_board values (11, "A001", "U20230518001", "폭스바겐_SUV_2009_9_디젤_자동_검정색_티구안", "222카2222", 110000, 44000000, "sell_board insert11", "2023-08-18", null, "../images/temp/폭스바겐_티구안_검정색_09년_09월.jpg", default, default);
insert into sell_board values (12, "A001", "U20230518001", "미니_소형_2016_12_가솔린_수동_검정색_컨트리맨", "333타3333", 120000, 35000000, "sell_board insert12", "2023-08-18", null, "../images/temp/미니_컨트리맨_검정색_16년_12월.jpg", default, default);
insert into sell_board values (13, "A001", "U20230518001", "볼보_SUV_2018_10_디젤_자동_은색_XC90", "444파4444", 130000, 85000000, "sell_board insert13", "2023-09-18", null, "../images/temp/볼보_XC90_은색_18년_10월.jpg", default, 1000);
insert into sell_board values (14, "A001", "U20230518001", "폴스타_대형_2017_1_가솔린_자동_파란색_폴스타6", "555하5555", 140000, 75000000, "sell_board insert14", "2023-10-18", null, "../images/temp/폴스타_폴스타6_파란색_17년_01월.jpg", default, default);
insert into sell_board values (15, "A001", "U20230518001", "포르쉐_SUV_2020_2_디젤_자동_검정색_카이엔", "666거6666", 150000, 105000000, "sell_board insert15", "2023-10-18", null, "../images/temp/포르쉐_카이엔_검정색_20년_02월.jpg", default, default);
insert into sell_board values (16, "A001", "U20230518001", "도요타_중형_2019_9_LPG_자동_회색_프리우스", "777너7777", 160000, 31000000, "sell_board insert16", "2023-10-18", null, "../images/temp/도요타_프리우스_회색_19년_09월.jpg", default, default);
insert into sell_board values (17, "A001", "U20230518001", "벤츠_대형_2021_8_전기_자동_회색_E350", "888더8888", 170000, 56000000, "sell_board insert17", "2023-10-18", null, "../images/temp/벤츠_E350_회색_21년_08월.jpg", default, default);
insert into sell_board values (18, "A001", "U20230518001", "BMW_스포츠_2017_2_가솔린_수동_은색_M4", "999러9999", 180000, 60000000, "sell_board insert18", "2023-10-18", null, "../images/temp/BMW_M4_은색_17년_02월.jpg", default, 777);
insert into sell_board values (19, "A001", "U20230518001", "아우디_중형_2019_5_전기_자동_흰색_A6", "123머1234", 190000, 70000000, "sell_board insert19", "2023-11-18", null, "../images/temp/아우디_A6_흰색_19년_05월.jpg", default, 785);
insert into sell_board values (20, "A001", "U20230518001", "폭스바겐_중형_2018_9_가솔린_자동_검정색_아테온", "456버4567", 200000, 44000000, "sell_board insert20", "2023-12-18", null, "../images/temp/폭스바겐_아테온_검정색_18년_09월.jpg", default, 101);
insert into sell_board values (21, "A002", "U20230518002", "기아_중형_2023_1_가솔린_자동_흰색_캐스퍼", "123아0007", 1200, 34000000, "sell_board insert21", "2024-01-18", null, "../images/temp/기아_캐스퍼_흰색_23년_01월.jpg", default, 90);
insert into sell_board values (22, "A003", "U20230618003", "쉐보레_소형_2020_10_가솔린_자동_파란색_스파크", "009자1230", 2000, 9000000, "sell_board insert22", "2024-01-18", null, "../images/temp/쉐보레_스파크_파란색_20년_10월.jpg", default, 233);
insert into sell_board values (23, "A003", "U20230618003", "기아_중형_2021_3_가솔린_자동_흰색_레이", "001차0067", 2000, 22400000, "sell_board insert23", "2024-01-18", null, "../images/temp/기아_레이_흰색_21년_03월.jpg", default, 100);
insert into sell_board values (24, "A004", "U20231018004", "현대_소형_2018_9_전기_자동_검정색_코나", "010허8520", 12400, 15000000, "sell_board insert24", "2024-01-18", null, "../images/temp/현대_코나_검정색_18년_09월.jpg", default, 5210);
insert into sell_board values (25, "A004", "U20231018004", "현대_중형_2017_5_하이브리드_자동_빨간색_아이오닉", "02지1113", 13000, 17800000, "sell_board insert25", "2024-02-18", null, "../images/temp/현대_아이오닉_빨간색_17년_05월.jpg", default, 300);
insert into sell_board values (26, "A003", "U20230518001", "현대_소형_2017_2_가솔린_자동_검정색_아반떼", "119가1111", 15000, 24000000, "sell_board insert26", "2024-02-18", null, "../images/temp/현대_아반떼_검정색_17년_02월.jpg", default, default);
insert into sell_board values (27, "A003", "U20230518001", "현대_중형_2018_7_디젤_자동_회색_소나타", "229나2222", 25000, 40000000, "sell_board insert7", "2024-03-18", null, "../images/temp/현대_소나타_회색_18년_07월.jpg", default, default);
insert into sell_board values (28, "A003", "U20230518001", "현대_SUV_2015_12_디젤_자동_검정색_투싼", "339다3333", 35000, 30000000, "sell_board insert28", "2024-03-18", null, "../images/temp/현대_투싼_검정색_15년_12월.jpg", default, default);
insert into sell_board values (29, "A003", "U20230518001", "현대_SUV_2020_12_가솔린_자동_은색_팰리세이드", "449라4444", 45000, 25000000, "sell_board insert29", "2024-03-18", null, "../images/temp/현대_팰리세이드_은색_20년_12월.jpg", default, default);
insert into sell_board values (30, "A003", "U20230518001", "기아_소형_2021_6_가솔린_자동_흰색_K3", "559마5555", 55000, 25000000, "sell_board insert30", "2024-03-18", null, "../images/temp/기아_K3_흰색_21년_06월.jpg", default, 30);
insert into sell_board values (31, "A003", "U20230518001", "기아_중형_2011_6_전기_자동_검정색_K5", "669바6666", 65000, 28000000, "sell_board insert31", "2024-03-18", null, "../images/temp/기아_K5_검정색_11년_06월.jpg", default, default);
insert into sell_board values (32, "A003", "U20230518001", "기아_대형_2012_1_LPG_자동_회색_K7", "779사7777", 75000, 19500000, "sell_board insert32", "2024-04-18", null, "../images/temp/기아_K7_회색_12년_01월.jpg", default, default);
insert into sell_board values (33, "A003", "U20230518001", "기아_대형_2022_3_하이브리드_자동_검정색_K8", "889아8888", 85000, 25000000, "sell_board insert33", "2024-04-18", null, "../images/temp/기아_K8_검정색_22년_03월.jpg", default, 80);
insert into sell_board values (34, "A002", "U20230518001", "기아_대형_2013_3_가솔린_자동_은색_K9", "999자9999", 95000, 48000000, "sell_board insert34", "2024-04-18", null, "../images/temp/기아_K9_은색_13년_03월.jpg", default, default);
insert into sell_board values (35, "A002", "U20230518001", "기아_SUV_2019_4_디젤_자동_흰색_스포티지", "118차1111", 150000, 21000000, "sell_board insert35", "2024-04-18", null, "../images/temp/기아_스포티지_흰색_19년_04월.jpg", default, default);
insert into sell_board values (36, "A002", "U20230518001", "기아_SUV_2010_6_디젤_자동_검정색_카니발", "228카2222", 111000, 30000000, "sell_board insert36", "2024-04-18", null, "../images/temp/기아_카니발_검정색_10년_06월.jpg", default, 985);
insert into sell_board values (37, "A002", "U20230518001", "제네시스_중형_2018_1_전기_수동_검정색_G70", "338타3333", 125000, 46000000, "sell_board insert37", "2024-04-18", null, "../images/temp/제네시스_G70_검정색_18년_01월.jpg", default, default);
insert into sell_board values (38, "A002", "U20230518001", "제네시스_대형_2022_11_가솔린_자동_빨간색_G80", "448파4444", 135000, 66000000, "sell_board insert38", "2024-04-18", null, "../images/temp/제네시스_G80_빨간색_22년_11월.jpg", default, default);
insert into sell_board values (39, "A002", "U20230518001", "제네시스_SUV_2023_3_하이브리드_자동_검정색_GV70", "558하5555", 145000, 69000000, "sell_board insert39", "2024-04-18", null, "../images/temp/제네시스_GV70_검정색_23년_03월.jpg", default, default);
insert into sell_board values (40, "A002", "U20230518001", "제네시스_SUV_2019_3_디젤_자동_검정색_GV80", "668거6666", 155000, 95000000, "sell_board insert40", "2024-04-18", null, "../images/temp/제네시스_GV80_검정색_19년_03월.jpg", default, default);
insert into sell_board values (41, "A004", "U20230518001", "아우디_중형_2016_9_LPG_자동_회색_A5", "778너7777", 165000, 24000000, "sell_board insert41", "2024-04-18", null, "../images/temp/아우디_A5_회색_16년_09월.jpg", default, 555);
insert into sell_board values (42, "A004", "U20230518001", "아우디_중형_2016_8_가솔린_자동_흰색_A6", "887더8888", 175000, 46000000, "sell_board insert42", "2024-04-18", null, "../images/temp/아우디_A6_흰색_19년_05월.jpg", default, 666);
insert into sell_board values (43, "A004", "U20230518001", "아우디_대형_2015_2_가솔린_수동_검정색_A7", "998러9999", 185000, 50000000, "sell_board insert43", "2024-04-18", null, "../images/temp/아우디_A7_은색_15년_02월.jpg", default, 777);
insert into sell_board values (44, "A004", "U20230518001", "르노코리아_소형_2020_5_가솔린_자동_흰색_SM3", "128머1234", 195000, 10000000, "sell_board insert44", "2024-05-18", null, "../images/temp/르노_SM3_흰색_20년_05월.jpg", default, default);
insert into sell_board values (45, "A004", "U20230518001", "르노코리아_중형_2015_9_LPG_자동_검정색_SM6", "458버4567", 250000, 23000000, "sell_board insert45", "2024-05-18", null, "../images/temp/르노_SM6_검정색_15년_09월.jpg", default, default);
insert into sell_board values (46, "A004", "U20230518002", "르노코리아_대형_2021_1_LPG_자동_회색_SM7", "128아0007", 95000, 34000000, "sell_board insert46", "2024-05-18", null, "../images/temp/르노_SM7_회색_21년_01월.jpg", default, default);
insert into sell_board values (47, "A004", "U20230618003", "르노코리아_SUV_2017_11_가솔린_자동_파란색_QM3", "008자1230", 80000, 11000000, "sell_board insert47", "2024-05-18", null, "../images/temp/르노_QM3_파란색_17년_11월.jpg", default, 111);
insert into sell_board values (48, "A004", "U20230618003", "르노코리아_SUV_2014_3_디젤_자동_흰색_QM5", "008차0067", 50000, 22600000, "sell_board insert48", "2024-05-18", null, "../images/temp/르노_QM5_흰색_14년_03월.jpg", default, 222);
insert into sell_board values (49, "A004", "U20231018004", "볼보_중형_2022_10_전기_자동_빨간색_S60", "018허8520", 80000, 35000000, "sell_board insert49", "2024-05-18", null, "../images/temp/볼보_S60_빨강색_22년_10월.jpg", default, 501);
insert into sell_board values (50, "A004", "U20231018004", "볼보_대형_2021_4_하이브리드_자동_검정색_S90", "028지1113", 58000, 114000000, "sell_board insert50", "2024-05-18", null, "../images/temp/볼보_S90_검정색_21년_04월.jpg", default, 1000);

-- qna_board insert
insert into qna_board values(1, "서비스", "psm9024", "문의합니다.", "문의문의1234", default, null, null, 0);
insert into qna_board values(2, "내차팔기", "psm9024", "팔래요.", "문의문의1234", default, null, null, 1);
insert into qna_board values(3, "내차사기", "rlfghtjd", "문의합니다.", "문의문의1234", default, null, null, 0);
insert into qna_board values(4, "불편접수", "rlfghtjd", "불-편", "문의문의1234", default, null, null, 0);
insert into qna_board values(5, "오류신고", "psm9024", "문의합니다.", "문의문의1234", default, null, null, 0);
insert into qna_board values(6, "오류신고", "rlfghtjd", "오류발견", "문의문의1234", default, null, null, 1);
insert into qna_board values(7, "서비스", "qnq11", "서비스불만족", "문의문의1234", default, null, null, 0);
insert into qna_board values(8, "내차팔기", "qnq11", "배고프다.", "문의문의1234", default, null, null, 1);
insert into qna_board values(9, "내차사기", "qnq11", "고생.", "문의문의1234", default, null, null, 1);
insert into qna_board values(10, "내차사기", "qnq11", "질문해요.", "문의문의1234", default, null, null, 0);
insert into qna_board values(11, "불편접수", "lmo05200", "이건.", "문의문의1234", default, null, null, 0);
insert into qna_board values(12, "서비스", "lmo05200", "어떤가요.", "문의문의1234", default, null, null, 0);
insert into qna_board values(13, "내차사기", "lmo05200", "사고싶어요.", "문의문의1234", default, null, null, 1);
insert into qna_board values(14, "내차팔기", "lmo05200", "팔고싶어요.", "문의문의1234", default, null, null, 1);

-- transaction insert
insert into transaction values(1, "U20230518001", "U20230518002", "A001", "현대_대형_2022_3_LPG_자동_검정색_그랜저", "11가1111", 10000, 34000000, "2023-06-11", default, default);
insert into transaction values(2, "U20230518001", "U20230518002", "A001", "폭스바겐_중형_2018_9_가솔린_자동_검정색_아테온", "456버4567", 200000, 44000000, "2023-06-11", default, default);
insert into transaction values(3, "U20230518002", "U20230618003", "A001", "아우디_중형_2019_5_전기_자동_흰색_A6", "123머1234", 190000, 70000000, "2023-07-11", default, default);
insert into transaction values(4, "U20230518002", "U20230618003", "A001", "BMW_스포츠_2017_2_가솔린_수동_은색_M4", "999러9999", 180000, 60000000, "2023-07-11", default, default);
insert into transaction values(5, "U20231018004", "U20230518001", "A001", "벤츠_대형_2021_8_전기_자동_회색_E350", "888더8888", 170000, 56000000, "2023-08-11", default, default);
insert into transaction values(6, "U20231018004", "U20230518001", "A001", "도요타_중형_2019_9_LPG_자동_회색_프리우스", "777너7777", 160000, 31000000, "2023-08-11", default, default);
insert into transaction values(7, "U20230618003", "U20231018004", "A001", "포르쉐_SUV_2020_2_디젤_자동_검정색_카이엔", "666거6666", 150000, 105000000, "2023-08-11", default, default);
insert into transaction values(8, "U20230618003", "U20231018004", "A001", "폴스타_대형_2017_1_가솔린_자동_검정색_폴스타6", "555하5555", 140000, 75000000, "2023-09-11", default, default);
insert into transaction values(9, "U20230518002", "U20231018004", "A002", "기아_중형_2023_1_가솔린_자동_흰색_캐스퍼", "123아0007", 1200, 34000000, "2023-09-11", default, default);
insert into transaction values(10, "U20230618003", "U20230518002", "A003", "쉐보레_소형_2020_10_가솔린_자동_파란색_스파크", "009자1230", 2000, 9000000, "2023-10-11", default, default);
insert into transaction values(11, "U20230618003", "U20230518001", "A003", "기아_중형_2021_3_가솔린_자동_흰색_레이", "001차0067", 2000, 22400000, "2023-10-11", default, default);
insert into transaction values(12, "U20231018004", "U20230618003", "A004", "현대_소형_2018_9_전기_자동_검정색_코나", "010허8520", 12400, 15000000, "2023-10-11", default, default);
insert into transaction values(13, "U20231018004", "U20230618003", "A004", "제네시스_SUV_2023_3_하이브리드_자동_검정색_GV70", "558하5555", 12400, 69000000, "2023-11-11", default, default);
insert into transaction values(14, "U20231018004", "U20230618003", "A004", "제네시스_SUV_2019_3_디젤_자동_검정색_GV80", "668거6666", 12400, 95000000, "2023-11-11", default, default);
insert into transaction values(15, "U20231018004", "U20230618003", "A004", "아우디_중형_2016_9_LPG_자동_회색_A5", "778너7777", 12400, 24000000, "2023-12-11", default, default);
insert into transaction values(16, "U20231018004", "U20230618003", "A004", "아우디_중형_2016_8_가솔린_자동_흰색_A6", "887더8888", 12400, 46000000, "2023-12-11", default, default);
insert into transaction values(17, "U20231018004", "U20230618003", "A004", "아우디_대형_2015_2_가솔린_수동_검정색_A7", "998러9999", 12400, 50000000, "2023-12-11", default, default);
insert into transaction values(18, "U20231018004", "U20230618003", "A004", "르노코리아_소형_2020_5_가솔린_자동_흰색_SM3", "128머1234", 12400, 10000000, "2024-01-11", default, default);
insert into transaction values(19, "U20231018004", "U20230618003", "A004", "르노코리아_중형_2015_9_LPG_자동_검정색_SM6", "458버4567", 12400, 23000000, "2024-01-11", default, default);
insert into transaction values(20, "U20231018004", "U20230618003", "A004", "르노코리아_대형_2021_1_LPG_자동_회색_SM7", "128아0007", 12400, 34000000, "2024-02-11", default, default);
insert into transaction values(21, "U20231018004", "U20230618003", "A004", "르노코리아_SUV_2017_11_가솔린_자동_파란색_QM3", "008자1230", 12400, 11000000, "2024-03-11", default, default);
insert into transaction values(22, "U20231018004", "U20230618003", "A004", "르노코리아_SUV_2014_3_디젤_자동_흰색_QM5", "008차0067", 12400, 22600000, "2024-03-11", default, default);
insert into transaction values(23, "U20231018004", "U20230618003", "A004", "볼보_중형_2022_10_전기_자동_빨간색_S60", "018허8520", 12400, 35000000, "2024-04-11", default, default);
insert into transaction values(24, "U20231018004", "U20230618003", "A004", "볼보_대형_2021_4_하이브리드_자동_검정색_S90", "028지1113", 12400, 114000000, "2024-04-11", default, default);
insert into transaction values(25, "U20231018004", "U20230618003", "A004", "현대_소형_2018_9_전기_자동_검정색_코나", "010허8520", 12400, 15000000, "2024-04-11", default, default);
insert into transaction values(26, "U20231018004", "U20230618003", "A004", "현대_소형_2018_9_전기_자동_검정색_코나", "010허8520", 12400, 15000000, "2024-05-11", default, default);
insert into transaction values(27, "U20231018004", "U20230618003", "A004", "현대_소형_2018_9_전기_자동_검정색_코나", "010허8520", 12400, 15000000, "2024-05-11", default, default);
insert into transaction values(28, "U20231018004", "U20230618003", "A004", "현대_소형_2018_9_전기_자동_검정색_코나", "010허8520", 12400, 15000000, "2024-05-11", default, default);
insert into transaction values(29, "U20231018004", "U20230618003", "A004", "현대_소형_2018_9_전기_자동_검정색_코나", "010허8520", 12400, 15000000, "2024-05-11", default, default);
insert into transaction values(30, "U20231018004", "U20230618003", "A004", "현대_소형_2018_9_전기_자동_검정색_코나", "010허8520", 12400, 15000000, "2024-05-11", default, default);

-- sell_board_status 변경
update sell_board set sell_board_status = true where sell_board_no = 1;
update sell_board set sell_board_status = true where sell_board_no = 14;
update sell_board set sell_board_status = true where sell_board_no = 15;
update sell_board set sell_board_status = true where sell_board_no = 16;
update sell_board set sell_board_status = true where sell_board_no = 17;
update sell_board set sell_board_status = true where sell_board_no = 18;
update sell_board set sell_board_status = true where sell_board_no = 19;
update sell_board set sell_board_status = true where sell_board_no = 20;
update sell_board set sell_board_status = true where sell_board_no = 21;
update sell_board set sell_board_status = true where sell_board_no = 22;
update sell_board set sell_board_status = true where sell_board_no = 23;
update sell_board set sell_board_status = true where sell_board_no = 24;

-- buy_review_board insert
insert into buy_review_board values(1, "psm9024", "구매후기1", "구매후기.ㄱ", default, null, default, null, 5);
insert into buy_review_board values(2, "psm9024", "구매후기2", "구매후기.ㄴ", default, null, default, null, 6);
insert into buy_review_board values(3, "rlfghtjd", "구매후기3.dㅐ", "구매후기.ㄷ", default, null, default, null, 1);
insert into buy_review_board values(4, "rlfghtjd", "구매후기4.ㅋm", "구매후기.ㄹ", default, null, default, null, 2);
insert into buy_review_board values(5, "qnq11", "구매후기5.g", "구매후기.a", default, null, default, null, 3);
insert into buy_review_board values(6, "qnq11", "구매후기6.e", "구매후기.b", default, null, default, null, 4);
insert into buy_review_board values(7, "lmo05200", "구매후기7.ㄹ", "구매후기.c", default, null, default, null, 7);
insert into buy_review_board values(8, "lmo05200", "구매후기8.ㅁ", "구매후기.d", default, null, default, null, 8);
insert into buy_review_board values(9, "psm9024", "구매후기9.ㅂ", "구매후기.db", default, null, default, null, 11);
insert into buy_review_board values(10, "rlfghtjd", "구매후기10.ㅏs", "구매후기.3d", default, null, default, null, 10);
insert into buy_review_board values(11, "qnq11", "구매후기11", "구매후기.ㅔd", default, null, default, null, 12);
insert into buy_review_board values(12, "lmo05200", "구매후기12", "구매후기.ㄴd", default, null, default, null, 9);

-- sell_review_board insert
insert into sell_review_board values(1, "psm9024", "판매후기1", "판매후기.ㄱ", default, null, default, null, 1);
insert into sell_review_board values(2, "psm9024", "판매후기2.gㅑ", "판매후기.ㄴ", default, null, default, null, 2);
insert into sell_review_board values(3, "rlfghtjd", "판매후기3.aㅠ", "판매후기.ㄷ", default, null, default, null, 3);
insert into sell_review_board values(4, "rlfghtjd", "판매후기4.ㅎ", "판매후기.ㄹ", default, null, default, null, 4);
insert into sell_review_board values(5, "qnq11", "판매후기5.d", "판매후기.a", default, null, default, null, 7);
insert into sell_review_board values(6, "qnq11", "판매후기6.c", "판매후기.b", default, null, default, null, 8);
insert into sell_review_board values(7, "lmo05200", "판매후기7.b", "판매후기.c", default, null, default, null, 5);
insert into sell_review_board values(8, "lmo05200", "판매후기8.a", "판매후기.d", default, null, default, null, 6);
insert into sell_review_board values(9, "qnq11", "판매후기9.ㄹ", "판매후기.d", default, null, default, null, 10);
insert into sell_review_board values(10, "rlfghtjd", "판매후기10.ㄷ", "판매후기.d", default, null, default, null, 9);
insert into sell_review_board values(11, "qnq11", "판매후기11.ㄴ", "판매후기.d", default, null, default, null, 11);
insert into sell_review_board values(12, "lmo05200", "판매후기12.ㄱ", "판매후기.d", default, null, default, null, 12);

-- update transaction sell, buy _board_review_status 변경
update transaction set transaction_sell_review_status = true, transaction_buy_review_status = true where transaction_no = 1;
update transaction set transaction_sell_review_status = true, transaction_buy_review_status = true where transaction_no = 2;
update transaction set transaction_sell_review_status = true, transaction_buy_review_status = true where transaction_no = 3;
update transaction set transaction_sell_review_status = true, transaction_buy_review_status = true where transaction_no = 4;
update transaction set transaction_sell_review_status = true, transaction_buy_review_status = true where transaction_no = 5;
update transaction set transaction_sell_review_status = true, transaction_buy_review_status = true where transaction_no = 6;
update transaction set transaction_sell_review_status = true, transaction_buy_review_status = true where transaction_no = 7;
update transaction set transaction_sell_review_status = true, transaction_buy_review_status = true where transaction_no = 8;
update transaction set transaction_sell_review_status = true, transaction_buy_review_status = true where transaction_no = 9;
update transaction set transaction_sell_review_status = true, transaction_buy_review_status = true where transaction_no = 10;
update transaction set transaction_sell_review_status = true, transaction_buy_review_status = true where transaction_no = 11;
update transaction set transaction_sell_review_status = true, transaction_buy_review_status = true where transaction_no = 12;

-- community_board insert
insert into community_board values(1, "psm9024", "동해물과 백두산이", "마르고 닳도록", default, null, default);
insert into community_board values(2, "psm9024", "마르고 닳도록", "하느님이 보우하사", default, null, default);
insert into community_board values(3, "psm9024", "하느님이 보우하사", "우리나라 만세", default, null, default);
insert into community_board values(4, "psm9024", "우리나라 만세", "무궁화 삼천리", default, null, default);
insert into community_board values(5, "psm9024", "무궁화 삼천리", "화려 강산", default, null, default);
insert into community_board values(6, "psm9024", "화려 강산", "대한 사람 대한으로", default, null, default);
insert into community_board values(7, "psm9024", "대한 사람 대한으로", "길이 보전하세", default, null, default);
insert into community_board values(8, "psm9024", "길이 보전하세", "남산 위에 저 소나무", default, null, default);
insert into community_board values(9, "rlfghtjd", "남산 위에 저 소나무", "철갑을 두른 듯", default, null, default);
insert into community_board values(10, "rlfghtjd", "철갑을 두른 듯", "바람 서리 불변함은", default, null, default);
insert into community_board values(11, "rlfghtjd", "바람 서리 불변함은", "우리 기상일세", default, null, default);
insert into community_board values(12, "rlfghtjd", "우리 기상일세", "무궁화 삼천리", default, null, default);
insert into community_board values(13, "rlfghtjd", "무궁화 삼천리", "화려 강산", default, null, default);
insert into community_board values(14, "rlfghtjd", "화려 강산", "대한 사람 대한으로", default, null, default);
insert into community_board values(15, "rlfghtjd", "대한 사람 대한으로", "길이 보전하세", default, null, default);
insert into community_board values(16, "rlfghtjd", "길이 보전하세", "가을 하늘 공활한데", default, null, default);
insert into community_board values(17, "qnq11", "가을 하늘 공활한데", "높고 구름 없이", default, null, default);
insert into community_board values(18, "qnq11", "높고 구름 없이", "밝은 달은 우리 가슴", default, null, default);
insert into community_board values(19, "qnq11", "밝은 달은 우리 가슴", "일편단심일세", default, null, default);
insert into community_board values(20, "qnq11", "일편단심일세", "무궁화 삼천리", default, null, default);
insert into community_board values(21, "qnq11", "무궁화 삼천리", "화려 강산", default, null, default);
insert into community_board values(22, "qnq11", "화려 강산", "대한 사람 대한으로", default, null, default);
insert into community_board values(23, "qnq11", "대한 사람 대한으로", "길이 보전하세", default, null, default);
insert into community_board values(24, "qnq11", "길이 보전하세", "이 기상과 이 맘으로", default, null, default);
insert into community_board values(25, "lmo05200", "이 기상과 이 맘으로", "충성을 다하여", default, null, default);
insert into community_board values(26, "lmo05200", "충성을 다하여", "괴로우나 즐거우나", default, null, default);
insert into community_board values(27, "lmo05200", "괴로우나 즐거우나", "나라 사랑하세", default, null, default);
insert into community_board values(28, "lmo05200", "나라 사랑하세", "무궁화 삼천리", default, null, default);
insert into community_board values(29, "lmo05200", "무궁화 삼천리", "화려 강산", default, null, default);
insert into community_board values(30, "lmo05200", "화려 강산", "대한 사람 대한으로", default, null, default);
insert into community_board values(31, "lmo05200", "대한 사람 대한으로", "길이 보전하세", default, null, default);
insert into community_board values(32, "lmo05200", "길이 보전하세", "애국가", default, null, default);

-- favorite insert
insert into favorite values(1, "U20230518001", 30);
insert into favorite values(2, "U20230518001", 31);
insert into favorite values(3, "U20230518001", 32);
insert into favorite values(4, "U20230518001", 33);
insert into favorite values(5, "U20230518001", 34);
insert into favorite values(6, "U20230518001", 35);
insert into favorite values(7, "U20230518001", 36);
insert into favorite values(8, "U20230518001", 37);
insert into favorite values(9, "U20230518001", 38);
insert into favorite values(10, "U20230518001", 39);
insert into favorite values(11, "U20230518001", 40);
insert into favorite values(12, "U20230518001", 41);
insert into favorite values(13, "U20230518001", 42);
insert into favorite values(14, "U20230518001", 43);
insert into favorite values(15, "U20230518001", 44);
insert into favorite values(16, "U20230518001", 45);
insert into favorite values(17, "U20230518001", 46);
insert into favorite values(18, "U20230518001", 47);
insert into favorite values(19, "U20230518001", 48);
insert into favorite values(20, "U20230518001", 49);
insert into favorite values(21, "U20230518001", 50);

insert into favorite values(22, "U20230518002", 30);
insert into favorite values(23, "U20230518002", 31);
insert into favorite values(24, "U20230518002", 32);
insert into favorite values(25, "U20230518002", 33);
insert into favorite values(26, "U20230518002", 34);
insert into favorite values(27, "U20230518002", 35);
insert into favorite values(28, "U20230518002", 36);
insert into favorite values(29, "U20230518002", 37);
insert into favorite values(30, "U20230518002", 38);
insert into favorite values(31, "U20230518002", 39);
insert into favorite values(32, "U20230518002", 40);
insert into favorite values(33, "U20230518002", 41);
insert into favorite values(34, "U20230518002", 42);
insert into favorite values(35, "U20230518002", 43);
insert into favorite values(36, "U20230518002", 44);
insert into favorite values(37, "U20230518002", 45);
insert into favorite values(38, "U20230518002", 46);
insert into favorite values(39, "U20230518002", 47);
insert into favorite values(40, "U20230518002", 48);
insert into favorite values(41, "U20230518002", 49);
insert into favorite values(42, "U20230518002", 50);

insert into favorite values(43, "U20230618003", 30);
insert into favorite values(44, "U20230618003", 31);
insert into favorite values(45, "U20230618003", 32);
insert into favorite values(46, "U20230618003", 33);
insert into favorite values(47, "U20230618003", 34);
insert into favorite values(48, "U20230618003", 35);
insert into favorite values(49, "U20230618003", 36);
insert into favorite values(50, "U20230618003", 37);
insert into favorite values(51, "U20230618003", 38);
insert into favorite values(52, "U20230618003", 39);
insert into favorite values(53, "U20230618003", 40);
insert into favorite values(54, "U20230618003", 41);
insert into favorite values(55, "U20230618003", 42);
insert into favorite values(56, "U20230618003", 43);
insert into favorite values(57, "U20230618003", 44);
insert into favorite values(58, "U20230618003", 45);
insert into favorite values(59, "U20230618003", 46);
insert into favorite values(60, "U20230618003", 47);
insert into favorite values(61, "U20230618003", 48);
insert into favorite values(62, "U20230618003", 49);
insert into favorite values(63, "U20230618003", 50);

insert into favorite values(64, "U20231018004", 30);
insert into favorite values(65, "U20231018004", 31);
insert into favorite values(66, "U20231018004", 32);
insert into favorite values(67, "U20231018004", 33);
insert into favorite values(68, "U20231018004", 34);
insert into favorite values(69, "U20231018004", 35);
insert into favorite values(70, "U20231018004", 36);
insert into favorite values(71, "U20231018004", 37);
insert into favorite values(72, "U20231018004", 38);
insert into favorite values(73, "U20231018004", 39);
insert into favorite values(74, "U20231018004", 40);
insert into favorite values(75, "U20231018004", 41);
insert into favorite values(76, "U20231018004", 42);
insert into favorite values(77, "U20231018004", 43);
insert into favorite values(78, "U20231018004", 44);
insert into favorite values(79, "U20231018004", 45);
insert into favorite values(80, "U20231018004", 46);
insert into favorite values(81, "U20231018004", 47);
insert into favorite values(82, "U20231018004", 48);
insert into favorite values(83, "U20231018004", 49);
insert into favorite values(84, "U20231018004", 50);

SET SQL_SAFE_UPDATES = 0;

commit;
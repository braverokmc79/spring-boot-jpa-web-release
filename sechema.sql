

-- sbootgodcoder
-- 1111

CREATE TABLE sbootgodcoder.board (
                                     id BIGINT auto_increment NULL,
                                     title varchar(50) NULL,
                                     content TEXT NULL,
                                     CONSTRAINT board_pk PRIMARY KEY (id)
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci
COMMENT='게시판';

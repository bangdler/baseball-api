-- BaseballNumber 테이블 (정답 숫자 3개를 저장)
CREATE TABLE IF NOT EXISTS baseball_number (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number1 INT NOT NULL,
    number2 INT NOT NULL,
    number3 INT NOT NULL
);

-- BaseballGame 테이블
CREATE TABLE IF NOT EXISTS baseball_game (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    is_end BOOLEAN NOT NULL DEFAULT FALSE
    cur_player_idx INT NOT NULL DEFAULT 0,
    answer_id BIGINT,
    CONSTRAINT fk_baseballgame_answer
    FOREIGN KEY (answer_id) REFERENCES baseball_number(id)
);

-- Player 테이블
CREATE TABLE IF NOT EXISTS player (
    id BIGINT PRIMARY KEY, -- 직접 할당 방식
    is_winner BOOLEAN NOT NULL,
    game_id BIGINT NOT NULL,
    CONSTRAINT fk_player_game
    FOREIGN KEY (game_id) REFERENCES baseball_game(id) ON DELETE CASCADE
);

-- Player의 history 값 타입 컬렉션 테이블
CREATE TABLE IF NOT EXISTS player_history (
    player_id BIGINT NOT NULL,
    id BIGINT NOT NULL,
    input VARCHAR(255) NOT NULL,
    strike INT NOT NULL,
    ball INT NOT NULL,
    CONSTRAINT fk_history_player
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE
);
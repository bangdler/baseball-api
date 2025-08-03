package com.baseballgame.api.test

import com.baseballgame.api.repository.BaseballGameRepository
import com.baseballgame.api.repository.PlayerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class BaseballGameSpringTest {
    @Autowired
    protected lateinit var baseballGameRepository: BaseballGameRepository

    @Autowired
    protected lateinit var playerRepository: PlayerRepository
}
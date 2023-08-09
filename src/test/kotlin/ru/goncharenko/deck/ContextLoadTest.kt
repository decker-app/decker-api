package ru.goncharenko.deck

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import ru.goncharenko.deck.infra.IntegrationTest

@IntegrationTest
class ContextLoadTest(
    @Autowired private val template: MongoTemplate
) {

    @Test
    fun `context successfully loaded with all beans`() {
        println(template.db.name)
    }

}

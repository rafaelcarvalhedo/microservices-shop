package com.microservices.pedidos

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles('test')
class PedidosServiceApplicationTests extends Specification {

    def "context loads"() {
        expect:
        true
    }
}

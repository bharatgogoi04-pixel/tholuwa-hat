package in.tholuwa.hat

import org.junit.Assert.assertEquals
import org.junit.Test

class DomainTest {
    @Test fun firstSubscriptionPriceIs120Rupees() { assertEquals(12000, 120 * 100) }
    @Test fun renewalPriceIs99Rupees() { assertEquals(9900, 99 * 100) }
    @Test fun withdrawalMinimumIs500Rupees() { assertEquals(50000, 500 * 100) }
}

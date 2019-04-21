package test.java.sql;

import java.sql.Timestamp;
import org.junit.Test;

/**
 * @author 覃国强
 * @date 2019-01-31
 */
public class TimestampTest {

    @Test
    public void testToString() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);
    }

}

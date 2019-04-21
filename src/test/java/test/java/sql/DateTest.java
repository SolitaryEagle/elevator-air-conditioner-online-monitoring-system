package test.java.sql;

import java.sql.Date;
import org.junit.Test;

/**
 * @author 覃国强
 * @date 2019-01-31
 */
public class DateTest {

    @Test
    public void testToString() {
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date);
    }

}

package test.org.springframework.beans;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author 覃国强
 * @date 2019/5/13 16:45
 */
public class BeanUtilsTest {

    @Test
    public void testcopyProperties() {
        DateBody dateBody = new DateBody(new Date(System.currentTimeMillis()));
        TimestampBody timestampBody = new TimestampBody();
        BeanUtils.copyProperties(dateBody, timestampBody);
        System.out.println("timestamp body ：" + timestampBody);

        timestampBody = new TimestampBody(new Timestamp(System.currentTimeMillis() + 86400000));
        dateBody = new DateBody();
        BeanUtils.copyProperties(timestampBody, dateBody);
        System.out.println("date body : " + dateBody);
    }

}

class DateBody {

    private Date time;

    public DateBody() {
    }

    public DateBody(Date time) {
        this.time = time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

}

class TimestampBody {

    private Timestamp time;

    public TimestampBody() {
    }

    public TimestampBody(Timestamp time) {
        this.time = time;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

}

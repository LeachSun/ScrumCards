package org.leach.scrumcards.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Leach
 * @date 2017/8/25
 */
public class KeyUtil {

    public synchronized static String generateMeetingKey() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return "T" + dateFormat.format(new Date());
    }

    public synchronized static String generateMemberKey() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return "U" + dateFormat.format(new Date());
    }
}

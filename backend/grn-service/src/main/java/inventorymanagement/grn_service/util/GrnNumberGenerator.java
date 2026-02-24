package inventorymanagement.grn_service.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class GrnNumberGenerator {

    private static final AtomicInteger SEQ = new AtomicInteger(0);

    // Simple generator: GRN-YYYYMMDD-0001
    public static String generate() {
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        int num = SEQ.incrementAndGet();
        return "GRN-" + date + "-" + String.format("%04d", num);
    }
}
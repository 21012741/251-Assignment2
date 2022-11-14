package org.example.layout;


import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.time.LocalDateTime;

public class VelocityLayout extends PatternLayout {

    private VelocityContext context;
    private VelocityEngine engine;


    public VelocityLayout() {
        this(DEFAULT_CONVERSION_PATTERN);
        this.context = new VelocityContext();
        this.engine = new VelocityEngine();
    }

    public VelocityLayout(String pattern) {
        super(pattern);
    }
    /**
     * i.   c (category)
     * ii.  d (date using the default toString() representation)
     * iii. m (message)
     * iv.  p (priority)
     * v.   t (thread)
     * vi.  n (line separator)
     */
    @Override
    public String format(LoggingEvent event) {
        engine.init();

        context.put("c", event.getLoggerName());
        context.put("d", LocalDateTime.now().toString());
        context.put("m", event.getMessage());
        context.put("p", event.getLevel().toString());
        context.put("t", event.getThreadName());
        context.put("n", System.lineSeparator());

        StringWriter Writer = new StringWriter();

        engine.evaluate(context, Writer, "", getConversionPattern());
        return Writer.toString();
    }
}

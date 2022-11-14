package org.example.appender;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MemAppender extends AppenderSkeleton {


    private Integer maxSize;
    private long discardedLogCount = 0L;
    private List<LoggingEvent> eventList;

    public MemAppender(){
    }

    public void printLogs(){
        if(eventList!=null){
            for(LoggingEvent event: eventList){
                System.out.println(layout.format(event));
            }
            eventList.clear();
        }

    }

    public List<String> getEventStrings(){
        if(eventList==null){
            eventList = new LinkedList<>();
        }
        return Collections.unmodifiableList(
                eventList.stream()
                        .map(event -> this.layout.format(event)).collect(Collectors.toList())
        );

    }


    public List<LoggingEvent> getCurrentLogs(){
        if(eventList==null){
            eventList = new LinkedList<>();
        }
        return Collections.unmodifiableList(eventList);
    }



    @Override
    protected void append(LoggingEvent loggingEvent) {
        if(eventList==null){
            eventList = new ArrayList<>(maxSize);
        }
        if(eventList.size()>=maxSize){
            eventList.remove(0);
            discardedLogCount++;
        }
        eventList.add(loggingEvent);
    }



    @Override
    public Layout getLayout() {
        return super.getLayout();
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public long getDiscardedLogCount() {
        return discardedLogCount;
    }
}

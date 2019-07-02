package dev.mvvasilev.eventlog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "eventlog")
public class EventLogProperties {

    @Value("{eventlog.exchange}")
    private String exchange;

    @Value("{eventlog.publish-queue}")
    private String publishQueue;

    @Value("{eventlog.submit-queue}")
    private String submitQueue;

    @Value("{eventlog.submit-queue.routing-key}")
    private String submitRoutingKey;

    @Value("{eventlog.publish-queue.routing-key}")
    private String publishRoutingKey;

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setPublishQueue(String publishQueue) {
        this.publishQueue = publishQueue;
    }

    public void setSubmitQueue(String submitQueue) {
        this.submitQueue = submitQueue;
    }

    public void setSubmitRoutingKey(String submitRoutingKey) {
        this.submitRoutingKey = submitRoutingKey;
    }

    public void setPublishRoutingKey(String publishRoutingKey) {
        this.publishRoutingKey = publishRoutingKey;
    }

    public String getExchange() {
        return exchange;
    }

    public String getPublishQueue() {
        return publishQueue;
    }

    public String getSubmitQueue() {
        return submitQueue;
    }

    public String getSubmitRoutingKey() {
        return submitRoutingKey;
    }

    public String getPublishRoutingKey() {
        return publishRoutingKey;
    }
}

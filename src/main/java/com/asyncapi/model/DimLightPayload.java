package com.asyncapi.model;

import javax.validation.constraints.*;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;
import java.util.Objects;


public class DimLightPayload {
    
    private @Valid Integer percentage;
    
    private @Valid java.time.OffsetDateTime sentAt;
    

    

    /**
     * Percentage to which the light should be dimmed to.
     */
    @JsonProperty("percentage")@Max(100)
    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
    

    /**
     * Date and time when the message was sent.
     */
    @JsonProperty("sentAt")
    public java.time.OffsetDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(java.time.OffsetDateTime sentAt) {
        this.sentAt = sentAt;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DimLightPayload dimLightPayload = (DimLightPayload) o;
        return 
            Objects.equals(this.percentage, dimLightPayload.percentage) &&
            Objects.equals(this.sentAt, dimLightPayload.sentAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(percentage, sentAt);
    }

    @Override
    public String toString() {
        return "class DimLightPayload {\n" +
        
                "    percentage: " + toIndentedString(percentage) + "\n" +
                "    sentAt: " + toIndentedString(sentAt) + "\n" +
                "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
           return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
package com.asyncapi.model;

import javax.validation.constraints.*;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;
import java.util.Objects;


public class AnonymousSchema7 {
    
    private @Valid Integer myAppHeader;
    

    

    
    @JsonProperty("my-app-header")@Max(100)
    public Integer getMyAppHeader() {
        return myAppHeader;
    }

    public void setMyAppHeader(Integer myAppHeader) {
        this.myAppHeader = myAppHeader;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnonymousSchema7 anonymousSchema7 = (AnonymousSchema7) o;
        return 
            Objects.equals(this.myAppHeader, anonymousSchema7.myAppHeader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myAppHeader);
    }

    @Override
    public String toString() {
        return "class AnonymousSchema7 {\n" +
        
                "    myAppHeader: " + toIndentedString(myAppHeader) + "\n" +
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
package model;

import ViewKaas.View;
import com.fasterxml.jackson.annotation.JsonView;

import java.security.Principal;

/**
 * Created by roger on 10-1-2016.
 */
public class TestObject implements Principal {
    @JsonView(View.Public.class)
    private String testString;

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    @Override
    public String getName() {
        return testString;
    }
}

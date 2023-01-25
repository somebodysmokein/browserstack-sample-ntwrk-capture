package browserstack;

import com.fasterxml.jackson.annotation.*;


import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "automation_build"
})
@Generated("jsonschema2pojo")
public class Build {

    @JsonProperty("automation_build")
    private AutomationBuild automationBuild;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("automation_build")
    public AutomationBuild getAutomationBuild() {
        return automationBuild;
    }

    @JsonProperty("automation_build")
    public void setAutomationBuild(AutomationBuild automationBuild) {
        this.automationBuild = automationBuild;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
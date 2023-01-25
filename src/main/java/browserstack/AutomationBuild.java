package browserstack;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "duration",
        "status",
        "hashed_id",
        "build_tag"
})
@Generated("jsonschema2pojo")
public class AutomationBuild {

    @JsonProperty("name")
    private String name;
    @JsonProperty("duration")
    private Integer duration;
    @JsonProperty("status")
    private String status;
    @JsonProperty("hashed_id")
    private String hashedId;
    @JsonProperty("build_tag")
    private Object buildTag;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("duration")
    public Integer getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("hashed_id")
    public String getHashedId() {
        return hashedId;
    }

    @JsonProperty("hashed_id")
    public void setHashedId(String hashedId) {
        this.hashedId = hashedId;
    }

    @JsonProperty("build_tag")
    public Object getBuildTag() {
        return buildTag;
    }

    @JsonProperty("build_tag")
    public void setBuildTag(Object buildTag) {
        this.buildTag = buildTag;
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

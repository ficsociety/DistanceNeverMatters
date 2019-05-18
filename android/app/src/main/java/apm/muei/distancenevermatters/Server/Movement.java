package apm.muei.distancenevermatters.Server;

import java.io.Serializable;
import java.util.Map;

public class Movement implements Serializable {

    private String user;
    private String target;
    private Map<String, Float> distance;
    private Map<String, Float> rotation;

    public Movement(){
    }

    public Movement(String user, String target, Map<String, Float> distance, Map<String, Float> rotation) {
        this.user = user;
        this.target = target;
        this.distance = distance;
        this.rotation = rotation;
    }

    public String getUser() {
        return user;
    }

    public String getTarget() {
        return target;
    }

    public Map<String, Float> getDistance() { return distance; }

    public Map<String, Float> getRotation() {
        return rotation;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setDistance(Map<String, Float> distance) { this.distance = distance; }

    public void setRotation(Map<String, Float> rotation) {
        this.rotation = rotation;
    }

    @Override
    public String toString() {
        return "Movement{" +
                "user='" + user + '\'' +
                ", target='" + target + '\'' +
                ", distance=" + distance +
                ", rotation=" + rotation +
                "}";
    }
}

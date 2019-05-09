package apm.muei.distancenevermatters.Server;

import java.io.Serializable;
import java.util.Map;

public class Movement implements Serializable {

    private String user;
    private String target;
    private float x;
    private float y;
    private float z;
    private boolean active;
    private Map<String, Object> rotation;

    public Movement(){
    }

    public Movement(String user, String target, float x, float y, float z, boolean active, Map<String, Object> rotation) {
        this.user = user;
        this.target = target;
        this.x = x;
        this.y = y;
        this.z = z;
        this.active = active;
        this.rotation = rotation;
    }

    public String getUser() {
        return user;
    }

    public String getTarget() {
        return target;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public boolean isActive() {
        return active;
    }

    public Map<String, Object> getRotation() {
        return rotation;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setRotation(Map<String, Object> rotation) {
        this.rotation = rotation;
    }

    @Override
    public String toString() {
        return "Movement{" +
                "user='" + user + '\'' +
                ", target='" + target + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", active=" + active +
                ", rotation=" + rotation +
                '}';
    }
}

package apm.muei.distancenevermatters.entities.dto;

public class JoinGameDto {
    private long markerId;
    private long modelId;
    private long code;

    public JoinGameDto() {

    }

    public JoinGameDto(long markerId, long modelId, long code) {
        super();
        this.markerId = markerId;
        this.modelId = modelId;
        this.code = code;
    }

    public long getMarkerId() {
        return markerId;
    }

    public long getModelId() {
        return modelId;
    }

    public long getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "JoinGameDto{" +
                "markerId=" + markerId +
                ", modelId=" + modelId +
                ", code=" + code +
                '}';
    }
}

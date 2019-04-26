package apm.muei.ficsociety.backend.controller.dto;

import java.util.Map;

public class CreateGameDto {

	private String name;
	private String description;
	private long mapId;
	private Map<String, Long> markersModels;

	public CreateGameDto() {

	}

	public CreateGameDto(String name, String description, long mapId, Map<String, Long> markersModels) {
		this.name = name;
		this.description = description;
		this.mapId = mapId;
		this.markersModels = markersModels;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public long getMapId() {
		return mapId;
	}

	public Map<String, Long> getMarkersModels() {
		return markersModels;
	}

}

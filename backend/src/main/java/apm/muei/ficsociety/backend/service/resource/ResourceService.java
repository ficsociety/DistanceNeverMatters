package apm.muei.ficsociety.backend.service.resource;

import java.util.List;

import apm.muei.ficsociety.backend.domain.map.Map;
import apm.muei.ficsociety.backend.domain.marker.Marker;
import apm.muei.ficsociety.backend.domain.model.Model;

public interface ResourceService {

	List<Map> getMaps();

	List<Marker> getMarkers();

	List<Model> getModels();

}

package apm.muei.ficsociety.backend.service.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apm.muei.ficsociety.backend.domain.map.Map;
import apm.muei.ficsociety.backend.domain.map.MapRepository;
import apm.muei.ficsociety.backend.domain.marker.Marker;
import apm.muei.ficsociety.backend.domain.marker.MarkerRepository;
import apm.muei.ficsociety.backend.domain.model.Model;
import apm.muei.ficsociety.backend.domain.model.ModelRepository;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private MapRepository mapResourceRepository;

	@Autowired
	private MarkerRepository markerRepository;

	@Autowired
	private ModelRepository modelRepository;

	@Override
	public List<Map> getMaps() {
		return this.mapResourceRepository.findAll();
	}

	@Override
	public List<Marker> getMarkers() {
		return this.markerRepository.findAll();
	}

	@Override
	public List<Model> getModels() {
		return this.modelRepository.findAll();
	}

}

package apm.muei.ficsociety.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apm.muei.ficsociety.backend.domain.map.Map;
import apm.muei.ficsociety.backend.domain.marker.Marker;
import apm.muei.ficsociety.backend.domain.model.Model;
import apm.muei.ficsociety.backend.service.resource.ResourceService;

@RestController
public class ResourceController {

	@Autowired
	private ResourceService resourceService;

	@RequestMapping("/maps")
	public List<Map> getMaps() {
		return this.resourceService.getMaps();
	}

	@RequestMapping("/markers")
	public List<Marker> getMarkers() {
		return this.resourceService.getMarkers();
	}

	@RequestMapping("/models")
	public List<Model> getModels() {
		return this.resourceService.getModels();
	}
}

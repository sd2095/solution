package com.sd.solution.core.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.factory.ModelFactory;

import com.sd.solution.core.models.AtomComponentModel;
import com.sd.solution.core.models.MoleculeComponentModel;
import com.sd.solution.core.models.OrganismComponentModel;

public class SlingModelHelper {

	private static final Map<String, Class<?>> resourceModelMap;
	static {
		Map<String, Class<?>> map = new HashMap<>();
		map.put(AtomComponentModel.RESOURCE_TYPE, AtomComponentModel.class);
		map.put(MoleculeComponentModel.RESOURCE_TYPE, MoleculeComponentModel.class);
		map.put(OrganismComponentModel.RESOURCE_TYPE, OrganismComponentModel.class);
		resourceModelMap = Collections.unmodifiableMap(map);
	}

	public static Map<String, Object> getAttributes(Resource resource, ModelFactory modelFactory,
			SlingHttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		if (resource.hasChildren()) {
			Iterator<Resource> childItems = resource.listChildren();
			while (childItems.hasNext()) {
				Resource childItem = childItems.next();
				map.put(childItem.getName(), modelFactory.getModelFromWrappedRequest(request, childItem,
						resourceModelMap.get(childItem.getResourceType())));
			}
		}
		return map;
	}
}

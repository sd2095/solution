package com.sd.solution.core.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.factory.ModelFactory;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.style.ComponentStyleInfo;
import com.day.cq.wcm.api.designer.Style;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Model(adaptables = SlingHttpServletRequest.class, adapters = {
		CustomContainerModel.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = CustomContainerModel.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
@Slf4j
public class CustomContainerModel implements ComponentExporter {

	public static final String RESOURCE_TYPE = "solution/components/custom-container";

	@SlingObject
	private Resource currentResource;

	@SlingObject
	private Style currentStyle;

	@OSGiService
	private ModelFactory modelFactory;

	@Self
	private SlingHttpServletRequest request;

	public Map<String, ComponentExporter> getAttributes() {

		Map<String, ComponentExporter> map = new HashMap<>();
		if (currentResource.hasChildren()) {
			Iterator<Resource> childItems = currentResource.listChildren();
			while (childItems.hasNext()) {
				Resource childItem = childItems.next();
				TestComponentModel model = modelFactory.getModelFromWrappedRequest(request, childItem,
						TestComponentModel.class);
				log.debug("Model value - {}", model.getText());
				map.put(childItem.getName(), model);
			}
		}
		return map;
	}

	public String getStyle() {
		return Optional.ofNullable(currentResource.adaptTo(ComponentStyleInfo.class))
				.map(ComponentStyleInfo::getAppliedCssClasses).filter(StringUtils::isNotBlank).orElse(null);
	}

	@Override
	public String getExportedType() {
		return RESOURCE_TYPE;
	}
}

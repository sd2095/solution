package com.sd.solution.core.models;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.style.ComponentStyleInfo;

import lombok.Getter;

@Model(adaptables = SlingHttpServletRequest.class, adapters = {
		AtomComponentModel.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = AtomComponentModel.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class AtomComponentModel implements ComponentExporter {

	public static final String RESOURCE_TYPE = "solution/components/atom-component";

	@Getter
	@ValueMapValue
	private String text;
	
	@Getter
	@ValueMapValue
	private String url;
	
	@SlingObject
	private Resource currentResource;
	
	@Override
	public String getExportedType() {
		return RESOURCE_TYPE;
	}
	
	public String getStyle() {
		return Optional.ofNullable(currentResource.adaptTo(ComponentStyleInfo.class))
				.map(ComponentStyleInfo::getAppliedCssClasses).filter(StringUtils::isNotBlank).orElse(null);
	}
}

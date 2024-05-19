package com.sd.solution.core.models;

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
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.factory.ModelFactory;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.style.ComponentStyleInfo;
import com.sd.solution.core.utils.SlingModelHelper;

import lombok.Getter;

@Model(adaptables = SlingHttpServletRequest.class, adapters = {
		OrganismComponentModel.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = OrganismComponentModel.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class OrganismComponentModel implements ComponentExporter {

	public static final String RESOURCE_TYPE = "solution/components/organism-component";
	
	@Getter
	@ValueMapValue
	private String text;
	
	@Getter
	@ValueMapValue
	private String url;	
	
	@Override
	public String getExportedType() {
		return RESOURCE_TYPE;
	}
	
	@SlingObject
	private Resource currentResource;

	@OSGiService
	private ModelFactory modelFactory;

	@Self
	private SlingHttpServletRequest request;

	public Map<String, Object> getAttributes() {		
		return SlingModelHelper.getAttributes(currentResource, modelFactory, request);
	}

	public String getStyle() {
		return Optional.ofNullable(currentResource.adaptTo(ComponentStyleInfo.class))
				.map(ComponentStyleInfo::getAppliedCssClasses).filter(StringUtils::isNotBlank).orElse(null);
	}
}

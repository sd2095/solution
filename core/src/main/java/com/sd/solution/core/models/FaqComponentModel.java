package com.sd.solution.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.style.ComponentStyleInfo;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.designer.Style;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;

@Model(adaptables = SlingHttpServletRequest.class, adapters = {
		FaqComponentModel.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = FaqComponentModel.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
@JsonPropertyOrder({"styles", "attributes-id", "attributes-titles", ":type"})
public class FaqComponentModel implements ComponentExporter {

	public static final String RESOURCE_TYPE = "solution/components/faq";

	@SlingObject
	private Resource currentResource;
	
	@ScriptVariable
	private Style currentStyle;
	
	@SlingObject
	private ResourceResolver resolver;

	@Self
	private SlingHttpServletRequest request;

	@Override	
	public String getExportedType() {
		return RESOURCE_TYPE;
	}

	@ValueMapValue
	@Getter
	@JsonProperty(value = "attributes-id")
	private String[] tags;

	@JsonProperty(value = "styles")
	public String getStyle() {
		return Optional.ofNullable(currentResource.adaptTo(ComponentStyleInfo.class))
				.map(ComponentStyleInfo::getAppliedCssClasses).filter(StringUtils::isNotBlank).orElse(null);
	}
	
	@JsonProperty(value = "attributes-titles")
	public List<String> getAttributeNames() {
		TagManager tm = resolver.adaptTo(TagManager.class);
		List<String> tagTitles = new ArrayList<>();
		for(String tag : tags) {
			Tag tagNode = tm.resolve(tag);
			tagTitles.add(tagNode.getTitle());
		}
		return tagTitles;
	}
}

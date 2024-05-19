package com.sd.solution.core.models;

import java.util.List;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.models.ContainerItem;
import com.adobe.cq.wcm.core.components.models.LayoutContainer;
import com.adobe.cq.wcm.core.components.models.ListItem;
import com.adobe.cq.wcm.core.components.models.datalayer.ComponentData;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Model(adaptables = SlingHttpServletRequest.class, adapters = { ContainerModel.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = ContainerModel.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ContainerModel implements LayoutContainer {

	public static final String RESOURCE_TYPE = "solution/components/container";

	@Self
	@Via(type = ResourceSuperType.class)
	private LayoutContainer container;

	@JsonIgnore
	@Override
	public @Nullable String getId() {
		return container.getId();
	}

	@JsonIgnore
	@Override
	public @Nullable ComponentData getData() {
		return container.getData();
	}

	@JsonIgnore
	@Override
	public @NotNull LayoutType getLayout() {
		return container.getLayout();
	}

	@JsonIgnore
	@Override
	public String getAccessibilityLabel() {
		return container.getAccessibilityLabel();
	}

	@JsonIgnore
	@Override
	public @Nullable String getAppliedCssClasses() {
		return container.getAppliedCssClasses();
	}

	@JsonIgnore
	@Override
	public String getRoleAttribute() {
		return container.getRoleAttribute();
	}

	@Override
	public @NotNull String getExportedType() {
		return container.getExportedType();
	}

	@JsonIgnore
	@Override
	public @NotNull List<ListItem> getItems() {
		return container.getItems();
	}

	@JsonIgnore
	@Override
	public @NotNull List<? extends ContainerItem> getChildren() {
		return container.getChildren();
	}

	@JsonIgnore
	@Override
	public @Nullable String getBackgroundStyle() {
		return container.getBackgroundStyle();
	}

	@JsonProperty(value = "attributes")
	@Override
	public @NotNull Map<String, ? extends ComponentExporter> getExportedItems() {
		return container.getExportedItems();
	}

	@JsonIgnore
	@Override
	public @NotNull String[] getExportedItemsOrder() {
		return container.getExportedItemsOrder();
	}

}

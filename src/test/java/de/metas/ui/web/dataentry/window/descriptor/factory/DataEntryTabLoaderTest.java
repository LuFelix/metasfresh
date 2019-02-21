package de.metas.ui.web.dataentry.window.descriptor.factory;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;

import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntryGroupId;
import de.metas.dataentry.DataEntryListValueId;
import de.metas.dataentry.DataEntrySectionId;
import de.metas.dataentry.DataEntrySubGroupId;
import de.metas.dataentry.FieldType;
import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryGroup;
import de.metas.dataentry.layout.DataEntryGroup.DocumentLinkColumnName;
import de.metas.dataentry.layout.DataEntryListValue;
import de.metas.dataentry.layout.DataEntrySection;
import de.metas.dataentry.layout.DataEntrySubGroup;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutTab;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class DataEntryTabLoaderTest
{

	private JSONOptions jsonOptions;
	private DataEntryTabLoader dataEntryTabLoader;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init(); // ..because at one point in the code under test, we use IMsgBL

		jsonOptions = JSONOptions.builder(null/* userSession */).setAD_LanguageIfNotEmpty("en_US").build();

		dataEntryTabLoader = DataEntryTabLoader.createInstanceForUnitTesting();
	}

	@BeforeClass
	public static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@AfterClass
	public static void afterAll()
	{
		validateSnapshots();
	}

	@Test
	public void createLayoutDescriptors_verify_DocumentLayoutDetailDescriptor() throws JsonProcessingException
	{
		final DataEntryGroup dataEntryGroup = createSimpleDataEntryGroup();

		// invoke the method under test
		final List<DocumentLayoutDetailDescriptor> descriptors = dataEntryTabLoader
				.createLayoutDescriptors(ImmutableList.of(dataEntryGroup));

		expect(descriptors).toMatchSnapshot();
	}

	@Test
	public void createLayoutDescriptors_verify_JSONDocumentLayoutTab() throws JsonProcessingException
	{
		final DataEntryGroup dataEntryGroup = createSimpleDataEntryGroup();

		// invoke the method under test
		final List<DocumentLayoutDetailDescriptor> descriptors = dataEntryTabLoader
				.createLayoutDescriptors(ImmutableList.of(dataEntryGroup));

		final List<JSONDocumentLayoutTab> jsonTabs = JSONDocumentLayoutTab.ofList(descriptors, jsonOptions);
		expect(jsonTabs).toMatchSnapshot();
	}

	@Test
	public void createGroupEntityDescriptors_verify_DocumentEntityDescriptor()
	{
		final DataEntryGroup dataEntryGroup = createSimpleDataEntryGroup();

		// invoke the method under test
		final List<DocumentEntityDescriptor> descriptors = dataEntryTabLoader
				.createGroupEntityDescriptors(ImmutableList.of(dataEntryGroup));

		expect(descriptors).toMatchSnapshot();
	}

	public static DataEntryGroup createSimpleDataEntryGroup()
	{
		final DataEntryFieldId dataEntryListFieldId = DataEntryFieldId.ofRepoId(1123);

		final DataEntryGroup dataEntryGroup = DataEntryGroup
				.builder()
				.id(DataEntryGroupId.ofRepoId(1))
				.documentLinkColumnName(DocumentLinkColumnName.of("documentLinkColumnName"))
				.internalName("dataEntryGroup_internalName")
				.caption(ImmutableTranslatableString.constant("dataEntryGroup_caption"))
				.description(ImmutableTranslatableString.constant("dataEntryGroup_description"))
				.dataEntrySubGroup(DataEntrySubGroup.builder()
						.id(DataEntrySubGroupId.ofRepoId(11))
						.internalName("dataEntrySubGroup_internalName")
						.caption(ImmutableTranslatableString.constant("dataEntrySubGroup_caption"))
						.description(ImmutableTranslatableString.constant("dataEntrySubGroup_description"))

						.dataEntrySection(DataEntrySection.builder()
								.id(DataEntrySectionId.ofRepoId(111))
								.caption(ImmutableTranslatableString.constant("dataEntrySection1_caption"))
								.description(ImmutableTranslatableString.constant("dataEntrySection1_description"))
								.internalName("dataEntrySection1_internalName")
								.initallyClosed(false)

								.dataEntryField(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(1111))
										.caption(ImmutableTranslatableString.constant("textField1_caption"))
										.description(ImmutableTranslatableString.constant("textField1_description"))
										.type(FieldType.TEXT)
										.build())
								.dataEntryField(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(1112))
										.caption(ImmutableTranslatableString.constant("longTextField1_caption"))
										.description(ImmutableTranslatableString.constant("longTextField1_description"))
										.type(FieldType.LONG_TEXT)
										.build())
								.dataEntryField(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(1113))
										.caption(ImmutableTranslatableString.constant("numberField1_caption"))
										.description(ImmutableTranslatableString.constant("numberField1_description"))
										.type(FieldType.NUMBER)
										.build())
								.build())

						.dataEntrySection(DataEntrySection.builder()
								.id(DataEntrySectionId.ofRepoId(112))
								.caption(ImmutableTranslatableString.constant("dataEntrySection2_caption"))
								.description(ImmutableTranslatableString.constant("dataEntrySection2_description"))
								.internalName("dataEntrySection2_internalName")
								.initallyClosed(true)

								.dataEntryField(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(1121))
										.caption(ImmutableTranslatableString.constant("dateField1_caption"))
										.description(ImmutableTranslatableString.constant("dateField1_description"))
										.type(FieldType.DATE)
										.build())
								.dataEntryField(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(1122))
										.caption(ImmutableTranslatableString.constant("yesNoField1_caption"))
										.description(ImmutableTranslatableString.constant("yesNoField1_description"))
										.type(FieldType.YESNO)
										.build())

								.dataEntryField(DataEntryField.builder()
										.id(dataEntryListFieldId)
										.caption(ImmutableTranslatableString.constant("listField1_caption"))
										.description(ImmutableTranslatableString.constant("listField1_description"))
										.type(FieldType.LIST)
										.listValue(new DataEntryListValue(
												DataEntryListValueId.ofRepoId(11231),
												dataEntryListFieldId,
												ImmutableTranslatableString.constant("listValue1_name"),
												ImmutableTranslatableString.constant("listValue1_description")))
										.listValue(new DataEntryListValue(
												DataEntryListValueId.ofRepoId(11232),
												dataEntryListFieldId,
												ImmutableTranslatableString.constant("listValue2_name"),
												ImmutableTranslatableString.constant("listValue2_description")))
										.build())
								.build())
						.build())
				.build();
		return dataEntryGroup;
	}

}

/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.lar;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.bookmarks.util.BookmarksTestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.runner.RunWith;

/**
 * @author Mate Thurzo
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class BookmarksEntryStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@Override
	protected StagedModel addStagedModel(
			Group group, Map<String, List<StagedModel>> stagedModelsMap)
		throws Exception {

		List<StagedModel> stagedModels = stagedModelsMap.get(
			BookmarksFolder.class.getName());

		BookmarksFolder folder = (BookmarksFolder)stagedModels.get(0);

		return BookmarksTestUtil.addEntry(
			group.getGroupId(), folder.getFolderId(), true);
	}

	@Override
	protected Map<String, List<StagedModel>> addStagedModels(Group group)
		throws Exception {

		Map<String, List<StagedModel>> stagedModelsMap =
			new HashMap<String, List<StagedModel>>();

		List<StagedModel> stagedModels = new ArrayList<StagedModel>();

		stagedModels.add(
			BookmarksTestUtil.addFolder(
				group.getGroupId(), ServiceTestUtil.randomString()));

		stagedModelsMap.put(BookmarksFolder.class.getName(), stagedModels);

		return stagedModelsMap;
	}

	@Override
	protected String getElementName() {
		return "entry";
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return BookmarksEntryLocalServiceUtil.
				getBookmarksEntryByUuidAndGroupId(uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected String getStagedModelClassName() {
		return BookmarksEntry.class.getName();
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> stagedModelsMap, Group group)
		throws Exception {

		List<StagedModel> stagedModels = stagedModelsMap.get(
			BookmarksFolder.class.getName());

		Assert.assertEquals(1, stagedModels.size());

		BookmarksFolder folder = (BookmarksFolder)stagedModels.get(0);

		BookmarksFolderLocalServiceUtil.getBookmarksFolderByUuidAndGroupId(
			folder.getUuid(), group.getGroupId());
	}

}
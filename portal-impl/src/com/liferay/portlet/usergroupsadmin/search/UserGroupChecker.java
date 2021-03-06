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

package com.liferay.portlet.usergroupsadmin.search;

import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.MembershipPolicyUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.Set;

import javax.portlet.RenderResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class UserGroupChecker extends RowChecker {

	public UserGroupChecker(RenderResponse renderResponse, Group group) {
		super(renderResponse);

		_group = group;
	}

	@Override
	public boolean isChecked(Object obj) {
		User user = null;

		if (obj instanceof User) {
			user = (User)obj;
		}
		else if (obj instanceof Object[]) {
			user = (User)((Object[])obj)[0];
		}
		else {
			throw new IllegalArgumentException(obj + " is not a User");
		}

		try {
			return UserLocalServiceUtil.hasGroupUser(
				_group.getGroupId(), user.getUserId());
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	@Override
	public boolean isDisabled(Object obj) {
		User user = (User)obj;

		Set<Group> mandatoryGroups = MembershipPolicyUtil.getMandatoryGroups(
			user);

		if ((isChecked(user) && mandatoryGroups.contains(_group)) ||
			(!isChecked(user) &&
			 !MembershipPolicyUtil.isMembershipAllowed(_group, user))) {

			return true;
		}
		else {
			try {
				PermissionChecker permissionChecker =
					PermissionThreadLocal.getPermissionChecker();

				if (MembershipPolicyUtil.isMembershipProtected(
						permissionChecker, _group, user)) {

					return true;
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return super.isDisabled(obj);
	}

	private static Log _log = LogFactoryUtil.getLog(UserGroupChecker.class);

	private Group _group;

}
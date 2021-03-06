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

package com.liferay.portalweb.portlet.directory.organizations.advancedsearchorganization;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AdvancedSearchOrganizationRegionTest extends BaseTestCase {
	public void testAdvancedSearchOrganizationRegion()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Directory Test Page",
					RuntimeVariables.replace("Directory Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace("Organizations"));
				selenium.waitForPageToLoad("30000");

				boolean advancedVisible = selenium.isVisible(
						"//a[.='Advanced \u00bb']");

				if (!advancedVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//a[.='Advanced \u00bb']",
					RuntimeVariables.replace("Advanced \u00bb"));

			case 2:
				selenium.waitForVisible("//select[@id='_11_andOperator']");
				selenium.select("//select[@id='_11_andOperator']",
					RuntimeVariables.replace("All"));
				selenium.waitForPartialText("//select[@id='_11_countryId']",
					"United States");
				selenium.select("//select[@id='_11_countryId']",
					RuntimeVariables.replace("United States"));
				selenium.waitForPartialText("//select[@id='_11_regionId']",
					"California");
				selenium.select("//select[@id='_11_regionId']",
					RuntimeVariables.replace("California"));
				selenium.clickAt("xPath=(//input[@value='Search'])[2]",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForPartialText("//select[@id='_11_regionId']",
					"California");
				selenium.select("//select[@id='_11_regionId']",
					RuntimeVariables.replace(""));
				selenium.waitForPartialText("//select[@id='_11_countryId']",
					"United States");
				selenium.select("//select[@id='_11_countryId']",
					RuntimeVariables.replace(""));
				assertEquals(RuntimeVariables.replace("Test Organization"),
					selenium.getText(
						"//tr[contains(.,'Test Organization')]/td[1]/a"));
				selenium.waitForPartialText("//select[@id='_11_countryId']",
					"United States");
				selenium.select("//select[@id='_11_countryId']",
					RuntimeVariables.replace("United States"));
				selenium.waitForPartialText("//select[@id='_11_regionId']",
					"Alabama");
				selenium.select("//select[@id='_11_regionId']",
					RuntimeVariables.replace("Alabama"));
				selenium.clickAt("xPath=(//input[@value='Search'])[2]",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_11_regionId']",
					RuntimeVariables.replace(""));
				selenium.waitForPartialText("//select[@id='_11_countryId']",
					"United States");
				selenium.select("//select[@id='_11_countryId']",
					RuntimeVariables.replace(""));
				selenium.select("//select[@id='_11_andOperator']",
					RuntimeVariables.replace("All"));
				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace("\u00ab Basic"));
				assertFalse(selenium.isTextPresent("Test Organization"));

			case 100:
				label = -1;
			}
		}
	}
}
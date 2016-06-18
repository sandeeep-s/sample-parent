package com.liferay.docs.guestbook.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;

import com.liferay.docs.guestbook.model.Entry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Http.Response;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.tutorial.guestbook.model.Guestbook;
import com.tutorial.guestbook.service.EntryLocalServiceUtil;
import com.tutorial.guestbook.service.GuestbookLocalServiceUtil;

/**
 * Portlet implementation class GuestbookPortlet
 */
public class GuestbookPortlet extends MVCPortlet {

	public void addGuestbook(ActionRequest actionRequest, ActionResponse actionResponse)
			throws PortalException, SystemException {
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Guestbook.class.getName(), actionRequest);

		String name = ParamUtil.getString(actionRequest, "name");

		try {
			GuestbookLocalServiceUtil.addGuestbook(serviceContext.getUserId(), name, serviceContext);
			SessionMessages.add(actionRequest, "guestbookAdded");
		} catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass().getName());
			actionResponse.setRenderParameter("mvcPath", "/html/guestbook/edit_guestbook.jsp");
		}

	}

	public void addEntry(ActionRequest actionRequest, ActionResponse actionResponse)
			throws PortalException, SystemException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(Entry.class.getName(), actionRequest);

		String userName = ParamUtil.getString(actionRequest, "name");
		String email = ParamUtil.getString(actionRequest, "email");
		String message = ParamUtil.getString(actionRequest, "message");
		long guestbookId = ParamUtil.getLong(actionRequest, "guestbookId");

		try {
			EntryLocalServiceUtil.addEntry(serviceContext.getUserId(), guestbookId, userName, email, message,
					serviceContext);
			SessionMessages.add(actionRequest, "entryAdded");

			actionResponse.setRenderParameter("guestbookId", Long.toString(guestbookId));

		} catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass().getName());
			PortalUtil.copyRequestParameters(actionRequest, actionResponse);
			actionResponse.setRenderParameter("mvcPath", "/html/guestbook/edit_entry.jsp");
		}

	}

	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws PortletException, IOException {

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(Guestbook.class.getName(), renderRequest);

			long groupId = serviceContext.getScopeGroupId();
			long guestbookId = ParamUtil.getLong(renderRequest, "guestbookId");

			List<Guestbook> guestbooks = GuestbookLocalServiceUtil.getGuestbooks(groupId);

			if (guestbooks.size() == 0) {
				Guestbook guestbook = GuestbookLocalServiceUtil.addGuestbook(serviceContext.getUserId(), "Main",
						serviceContext);
				guestbookId = guestbook.getGuestbookId();
			}

			if (guestbookId <= 0) {
				guestbookId = guestbooks.get(0).getGuestbookId();
			}

			renderRequest.setAttribute("guestbookId", guestbookId);

		} catch (Exception e) {
			throw new PortletException();
		}

		super.render(renderRequest, renderResponse);

	}

}

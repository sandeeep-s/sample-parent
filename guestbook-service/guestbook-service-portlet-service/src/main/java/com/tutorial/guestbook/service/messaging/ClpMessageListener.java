package com.tutorial.guestbook.service.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;

import com.tutorial.guestbook.service.ClpSerializer;
import com.tutorial.guestbook.service.EntryLocalServiceUtil;
import com.tutorial.guestbook.service.EntryServiceUtil;
import com.tutorial.guestbook.service.GuestbookLocalServiceUtil;


public class ClpMessageListener extends BaseMessageListener {
    public static String getServletContextName() {
        return ClpSerializer.getServletContextName();
    }

    @Override
    protected void doReceive(Message message) throws Exception {
        String command = message.getString("command");
        String servletContextName = message.getString("servletContextName");

        if (command.equals("undeploy") &&
                servletContextName.equals(getServletContextName())) {
            EntryLocalServiceUtil.clearService();

            EntryServiceUtil.clearService();
            GuestbookLocalServiceUtil.clearService();
        }
    }
}

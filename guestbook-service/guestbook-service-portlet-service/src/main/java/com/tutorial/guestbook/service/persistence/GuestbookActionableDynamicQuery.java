package com.tutorial.guestbook.service.persistence;

import com.liferay.portal.kernel.dao.orm.BaseActionableDynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.tutorial.guestbook.model.Guestbook;
import com.tutorial.guestbook.service.GuestbookLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class GuestbookActionableDynamicQuery
    extends BaseActionableDynamicQuery {
    public GuestbookActionableDynamicQuery() throws SystemException {
        setBaseLocalService(GuestbookLocalServiceUtil.getService());
        setClass(Guestbook.class);

        setClassLoader(com.tutorial.guestbook.service.ClpSerializer.class.getClassLoader());

        setPrimaryKeyPropertyName("guestbookId");
    }
}

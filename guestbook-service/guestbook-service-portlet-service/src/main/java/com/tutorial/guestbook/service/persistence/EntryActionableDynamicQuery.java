package com.tutorial.guestbook.service.persistence;

import com.liferay.portal.kernel.dao.orm.BaseActionableDynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.tutorial.guestbook.model.Entry;
import com.tutorial.guestbook.service.EntryLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class EntryActionableDynamicQuery
    extends BaseActionableDynamicQuery {
    public EntryActionableDynamicQuery() throws SystemException {
        setBaseLocalService(EntryLocalServiceUtil.getService());
        setClass(Entry.class);

        setClassLoader(com.tutorial.guestbook.service.ClpSerializer.class.getClassLoader());

        setPrimaryKeyPropertyName("entryId");
    }
}

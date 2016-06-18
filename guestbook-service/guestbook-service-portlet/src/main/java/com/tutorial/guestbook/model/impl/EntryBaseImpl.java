package com.tutorial.guestbook.model.impl;

import com.liferay.portal.kernel.exception.SystemException;

import com.tutorial.guestbook.model.Entry;
import com.tutorial.guestbook.service.EntryLocalServiceUtil;

/**
 * The extended model base implementation for the Entry service. Represents a row in the &quot;GB_Entry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link EntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EntryImpl
 * @see com.tutorial.guestbook.model.Entry
 * @generated
 */
public abstract class EntryBaseImpl extends EntryModelImpl implements Entry {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify or reference this class directly. All methods that expect a entry model instance should use the {@link Entry} interface instead.
     */
    @Override
    public void persist() throws SystemException {
        if (this.isNew()) {
            EntryLocalServiceUtil.addEntry(this);
        } else {
            EntryLocalServiceUtil.updateEntry(this);
        }
    }
}
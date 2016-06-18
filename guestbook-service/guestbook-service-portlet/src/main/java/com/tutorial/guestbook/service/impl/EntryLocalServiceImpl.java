package com.tutorial.guestbook.service.impl;

import java.util.Date;
import java.util.List;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.tutorial.guestbook.EntryEmailException;
import com.tutorial.guestbook.EntryMessageException;
import com.tutorial.guestbook.EntryNameException;
import com.tutorial.guestbook.model.Entry;
import com.tutorial.guestbook.service.base.EntryLocalServiceBaseImpl;

/**
 * The implementation of the entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.tutorial.guestbook.service.EntryLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.tutorial.guestbook.service.base.EntryLocalServiceBaseImpl
 * @see com.tutorial.guestbook.service.EntryLocalServiceUtil
 */
public class EntryLocalServiceImpl extends EntryLocalServiceBaseImpl {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this interface directly. Always use {@link com.tutorial.guestbook.service.EntryLocalServiceUtil} to access the entry local service.
     */
	public List<Entry> getEntries(long groupId, long guestbookId) throws SystemException {

	    return entryPersistence.findByG_G(groupId, guestbookId);
	}

	public List<Entry> getEntries(long groupId, long guestbookId, int start, int end)
	     throws SystemException {

	    return entryPersistence.findByG_G(groupId, guestbookId, start, end);
	}
	
	protected void validate (String name, String email, String entry) 
	        throws PortalException {
	    if (Validator.isNull(name)) {
	        throw new EntryNameException();
	    }

	    if (!Validator.isEmailAddress(email)) {
	        throw new EntryEmailException();
	    }

	    if (Validator.isNull(entry)) {
	        throw new EntryMessageException();
	    }
	}

	public Entry addEntry(long userId, long guestbookId, String name,
	        String email, String message, ServiceContext serviceContext)
	         throws PortalException, SystemException {
	    long groupId = serviceContext.getScopeGroupId();

	    User user = userPersistence.findByPrimaryKey(userId);

	    Date now = new Date();

	    validate(name, email, message);

	    long entryId = counterLocalService.increment();

	    Entry entry = entryPersistence.create(entryId);

	    entry.setUuid(serviceContext.getUuid());
	    entry.setUserId(userId);
	    entry.setGroupId(groupId);
	    entry.setCompanyId(user.getCompanyId());
	    entry.setUserName(user.getFullName());
	    entry.setCreateDate(serviceContext.getCreateDate(now));
	    entry.setModifiedDate(serviceContext.getModifiedDate(now));
	    entry.setExpandoBridgeAttributes(serviceContext);
	    entry.setGuestbookId(guestbookId);
	    entry.setName(name);
	    entry.setEmail(email);
	    entry.setMessage(message);

	    entryPersistence.update(entry);

	    return entry;

	}
}

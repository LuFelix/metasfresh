package org.adempiere.user;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.compiere.model.I_AD_User;
import org.springframework.stereotype.Repository;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class UserRepository
{
	public User ofRecord(@NonNull final I_AD_User userRecord)
	{
		return new User(
				UserId.ofRepoId(userRecord.getAD_User_ID()),
				userRecord.getName(),
				userRecord.getEMail());
	}

	public User save(@NonNull final User user)
	{
		final I_AD_User userRecord;
		if (user.getId() == null)
		{
			userRecord = newInstance(I_AD_User.class);
		}
		else
		{
			userRecord = load(user.getId().getRepoId(), I_AD_User.class);
		}

		userRecord.setName(user.getName());
		userRecord.setEMail(user.getEmailAddress());

		saveRecord(userRecord);

		return user
				.toBuilder()
				.id(UserId.ofRepoId(userRecord.getAD_User_ID()))
				.build();
	}

}

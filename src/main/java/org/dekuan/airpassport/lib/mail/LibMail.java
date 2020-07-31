package org.dekuan.airpassport.lib.mail;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

/**
 *	@class	LibMail
 */
public class LibMail
{
	/**
	 *	check for EMail address
	 *	@param	sEMail		-
	 *	@return	boolean
	 */
	public static boolean isValidEMail( String sEMail )
	{
		return ! StringUtils.isBlank( sEMail ) &&
			EmailValidator.getInstance().isValid( sEMail );
	}
}
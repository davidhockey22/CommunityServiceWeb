package org.CommunityService.util;

public class Gravatar {
	// see https://en.gravatar.com/site/implement/images/ for details
	public static String getGravatarImage(String email) {
		// this is the default avatar used by gravatar when a gravatar doesn't
		// exist for this email
		String defaultImage = "?d=identicon";

		// send null hash in the event there is no volunteer
		String emailHash = "00000000000000000000000000000000";
		if (email != null)
			emailHash = MD5Util.md5Hex(email);

		StringBuffer avatar = new StringBuffer("http://www.gravatar.com/avatar/");
		avatar.append(emailHash);
		avatar.append(defaultImage);
		return avatar.toString();
	}

}

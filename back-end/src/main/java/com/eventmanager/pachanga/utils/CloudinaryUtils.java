package com.eventmanager.pachanga.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryUtils {
	
	private CloudinaryUtils() {
	}
	
	public static Cloudinary getCloudinaryCredentials() {
		return new Cloudinary(ObjectUtils.asMap("cloud_name", "htctb0zmi", "api_key",
				"394878942327975", "api_secret", "7TUi18Spa9WIwm43iagrn1xhrco"));
	}

}

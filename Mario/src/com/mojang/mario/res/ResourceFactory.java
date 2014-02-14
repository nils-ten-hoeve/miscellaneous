package com.mojang.mario.res;

import java.io.InputStream;

public class ResourceFactory {
	public static InputStream getResourceStream(String path) {
		return ResourceFactory.class.getResourceAsStream(path);
	}
}

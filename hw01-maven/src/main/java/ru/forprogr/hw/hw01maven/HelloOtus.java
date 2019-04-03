package ru.forprogr.hw.hw01maven;

import com.google.common.base.Joiner;
public class HelloOtus {

	public static String get_guava_joiner(){
		Joiner joiner = Joiner.on(" - ");
		return joiner.join("hw","01","maven");
	}
}

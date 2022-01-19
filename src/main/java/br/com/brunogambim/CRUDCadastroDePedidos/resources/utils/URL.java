package br.com.brunogambim.CRUDCadastroDePedidos.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
	public static List<Long> decodeLongList(String list) {
		try {
			return Arrays.asList(list.split(",")).stream().map(element -> Long.parseLong(element)).collect(Collectors.toList());
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	public static String decodeParam(String param) {
		try {
			return URLDecoder.decode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}

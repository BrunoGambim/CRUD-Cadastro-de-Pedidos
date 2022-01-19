package br.com.brunogambim.CRUDCadastroDePedidos.resources.utils;

import org.springframework.data.domain.Sort;

import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.InvalidURIParamsException;

public class PaginationUtils {
	public static String getSortDirectionFromURIParam(String[] sort) {
		try {
			return sort[1].toUpperCase();
		} catch (Exception e) {
			throw new InvalidURIParamsException();
		}
	}
	public static String getSortOrderByFromURIParam(String[] sort) {
		try {
			return sort[0];
		} catch (Exception e) {
			throw new InvalidURIParamsException();
		}
	}
	public static String[] sortToURIParam(Sort sort) {
		String URISort[] = sort.toString().split(": ");
		URISort[1] = URISort[1].toLowerCase();
		return URISort;
	}
}

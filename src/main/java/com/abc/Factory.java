package com.abc;

/**
 * 
 * @author Accenture, rrana
 * @version v2.0
 *
 */
public interface Factory<T,V,G> {
	T create(V v, G g);
}

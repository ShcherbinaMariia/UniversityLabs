package app.util;

import java.util.Collection;

public interface BasicDAO<K, V> {
    Collection<V> list();

    V get(K key);
    MutationResult add(V value);
    MutationResult delete(K key);
}

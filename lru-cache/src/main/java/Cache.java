import java.util.Map;

public interface Cache<K, V> {
    void add(K key, V value);

    V get(K key);

    boolean exists(K key, V value);

    int size();

    Map<K, V> all();
}

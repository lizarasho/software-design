import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LRUCache<K, V> implements Cache<K, V> {
    private final HashMap<K, V> cache;
    private final LinkedList<K> leastRecentlyUsed;
    private final int maxSize;
    private int currentSize;

    public LRUCache(int maxSize) {
        assert maxSize > 0 : String.format("expected maximum cache size more than 0, found %s", maxSize);

        this.cache = new HashMap<>();
        this.leastRecentlyUsed = new LinkedList<>();
        this.currentSize = 0;
        this.maxSize = maxSize;
    }

    @Override
    public void add(K key, V value) {
        assertNotNull(key, "key");
        assertNotNull(value, "value");

        int initSize = currentSize;

        if (cache.containsKey(key)) {
            cache.put(key, value);
            leastRecentlyUsed.remove(key);
            leastRecentlyUsed.addFirst(key);
        } else {
            cache.put(key, value);
            leastRecentlyUsed.addFirst(key);
            if (currentSize < maxSize) {
                currentSize++;
            } else {
                cache.remove(leastRecentlyUsed.getLast());
                leastRecentlyUsed.removeLast();
            }
        }

        int finalSize = currentSize;

        assertSize(finalSize);
        assert finalSize >= initSize :
                String.format("current size can not decrease: was %s, became %s", initSize, finalSize);

    }

    @Override
    public V get(K key) {
        assertNotNull(key, "key");

        if (!cache.containsKey(key)) {
            return null;
        }

        leastRecentlyUsed.remove(key);
        leastRecentlyUsed.addFirst(key);

        return cache.get(key);
    }

    @Override
    public boolean exists(K key, V value) {
        assertNotNull(key, "key");
        assertNotNull(value, "value");

        if (!cache.containsKey(key)) {
            return false;
        }
        return cache.get(key) == value;
    }

    @Override
    public int size() {
        assertSize(currentSize);
        return currentSize;
    }

    @Override
    public Map<K, V> all() {
        assertSize(cache.size());
        return cache;
    }

    private void assertSize(int size) {
        assert size >= 0 && size <= maxSize :
                String.format("expected size to be >= 0 and <= %s, found %s", maxSize, size);
    }

    private <P> void assertNotNull(P paramValue, String paramName) {
        assert paramValue != null : String.format("%s must not be null", paramName);
    }
}

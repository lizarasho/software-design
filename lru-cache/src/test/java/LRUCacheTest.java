import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LRUCacheTest {
    private final int MAX_SIZE = 5;
    private final int SAMPLE_KEY = 123;
    private final String SAMPLE_VALUE = "abc";

    @Test
    public void testNegativeMaxSize() {
        assertThrows(AssertionError.class, () -> new LRUCache<Integer, String>(-1));
    }

    @Test
    public void testZeroMaxSize() {
        assertThrows(AssertionError.class, () -> new LRUCache<Integer, String>(0));
    }

    @Test
    public void testNullKey() {
        Cache<Integer, String> cache = new LRUCache<>(MAX_SIZE);
        assertThrows(AssertionError.class, () -> cache.add(null, SAMPLE_VALUE));
        assertThrows(AssertionError.class, () -> cache.exists(null, SAMPLE_VALUE));
        assertThrows(AssertionError.class, () -> cache.get(null));
    }

    @Test
    public void testNullValue() {
        Cache<Integer, String> cache = new LRUCache<>(MAX_SIZE);
        assertThrows(AssertionError.class, () -> cache.add(SAMPLE_KEY, null));
        assertThrows(AssertionError.class, () -> cache.exists(SAMPLE_KEY, null));
    }

    @Test
    public void testOneAddition() {
        Cache<Integer, String> cache = new LRUCache<>(MAX_SIZE);
        cache.add(SAMPLE_KEY, SAMPLE_VALUE);

        assertEquals(1, cache.size());
        assertEquals(SAMPLE_VALUE, cache.get(SAMPLE_KEY));
        assertEquals(Map.of(SAMPLE_KEY, SAMPLE_VALUE), cache.all());
        assertTrue(cache.exists(SAMPLE_KEY, SAMPLE_VALUE));
    }

    @Test
    public void testEqualKeyAddition() {
        Cache<Integer, String> cache = new LRUCache<>(MAX_SIZE);
        cache.add(SAMPLE_KEY, SAMPLE_VALUE);
        String updatedValue = SAMPLE_VALUE + SAMPLE_VALUE;
        cache.add(SAMPLE_KEY, updatedValue);
        assertEquals(1, cache.size());
        assertEquals(updatedValue, cache.get(SAMPLE_KEY));
        assertTrue(cache.exists(SAMPLE_KEY, updatedValue));
        assertEquals(Map.of(SAMPLE_KEY, updatedValue), cache.all());
    }

    @Test
    public void testManyAdditions() {
        Cache<Integer, String> cache = new LRUCache<>(MAX_SIZE);
        for (int key = 0; key < MAX_SIZE * 2; key++) {
            String value = Integer.toString(key);
            cache.add(key, value);

            assertEquals(Math.min(MAX_SIZE, key + 1), cache.size());
            assertEquals(value, cache.get(key));
            assertTrue(cache.exists(key, value));

            Map<Integer, String> expectedMap = new HashMap<>();
            for (int i = Math.max(0, key - MAX_SIZE + 1); i <= key; i++) {
                expectedMap.put(i, Integer.toString(i));
            }
            assertEquals(expectedMap, cache.all());
        }
        for (int key = 0; key < MAX_SIZE; key++) {
            String value = Integer.toString(key);
            assertFalse(cache.exists(key, value));
            assertNull(cache.get(key));
        }
    }
}

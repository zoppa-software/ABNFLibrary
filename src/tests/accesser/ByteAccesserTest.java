package tests.accesser;

import jp.co.zoppa.abnf.accesser.ByteAccesser;
import jp.co.zoppa.abnf.accesser.IByteAccesser;
import jp.co.zoppa.abnf.accesser.Span;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ByteAccesserのテストクラス。
 */
public class ByteAccesserTest {

    @Test
    public void testGetPosition() {
        byte[] data = {1, 2, 3};
        ByteAccesser accesser = new ByteAccesser(data);
        assertEquals(0, accesser.getPosition());
    }

    @Test
    public void testPeek() {
        byte[] data = {1, 2, 3};
        ByteAccesser accesser = new ByteAccesser(data);
        assertEquals(1, accesser.peek());
        accesser.read();
        assertEquals(2, accesser.peek());
    }

    @Test
    public void testRead() {
        byte[] data = {1, 2, 3};
        ByteAccesser accesser = new ByteAccesser(data);
        assertEquals(1, accesser.read());
        assertEquals(2, accesser.read());
        assertEquals(3, accesser.read());
        assertEquals(-1, accesser.read());
    }

    @Test
    public void testReadBuffer() {
        byte[] data = {1, 2, 3, 4, 5};
        ByteAccesser accesser = new ByteAccesser(data);
        byte[] buffer = new byte[3];
        int bytesRead = accesser.read(buffer, 0, 3);
        assertEquals(3, bytesRead);
        assertArrayEquals(new byte[]{1, 2, 3}, buffer);
        bytesRead = accesser.read(buffer, 0, 3);
        assertEquals(2, bytesRead);
        assertArrayEquals(new byte[]{4, 5, 3}, buffer);
    }

    @Test
    public void testSpan() {
        byte[] data = {1, 2, 3, 4, 5};
        ByteAccesser accesser = new ByteAccesser(data);
        Span span = accesser.span(1, 3);
        assertArrayEquals(new byte[]{2, 3}, span.getData());
        assertEquals(1, span.getStart());
        assertEquals(3, span.getEnd());
    }

    @Test
    public void testMarkAndRestore() {
        byte[] data = {1, 2, 3};
        ByteAccesser accesser = new ByteAccesser(data);
        IByteAccesser.IPosition mark = accesser.mark();
        accesser.read();
        accesser.read();
        mark.restore();
        assertEquals(0, accesser.getPosition());
    }
}
package tests.accesser;

import org.junit.jupiter.api.Test;

import jp.co.zoppa.abnf.accesser.ByteAccesserFromStream;
import jp.co.zoppa.abnf.accesser.IByteAccesser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ByteAccesserFromStreamのテストクラス。
 */
public class ByteAccesserFromStreamTest {

    @Test
    public void testReadSingleByte() throws IOException {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        ByteAccesserFromStream accesser = new ByteAccesserFromStream(inputStream);

        assertEquals(1, accesser.read());
        assertEquals(2, accesser.read());
        assertEquals(3, accesser.read());
        assertEquals(-1, accesser.read()); // ストリームの終端
    }

    @Test
    public void testReadBuffer() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        ByteAccesserFromStream accesser = new ByteAccesserFromStream(inputStream);

        byte[] buffer = new byte[3];
        int bytesRead = accesser.read(buffer, 0, 3);
        assertEquals(3, bytesRead);
        assertArrayEquals(new byte[]{1, 2, 3}, buffer);

        bytesRead = accesser.read(buffer, 0, 3);
        assertEquals(2, bytesRead);
        assertArrayEquals(new byte[]{4, 5, 3}, buffer); // 残りのデータを読み込む
    }

    @Test
    public void testPeek() throws IOException {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        ByteAccesserFromStream accesser = new ByteAccesserFromStream(inputStream);

        assertEquals(1, accesser.peek());
        assertEquals(1, accesser.peek()); // peekでは位置が進まない
        accesser.read();
        assertEquals(2, accesser.peek());
    }

    @Test
    public void testGetPosition() throws IOException {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        ByteAccesserFromStream accesser = new ByteAccesserFromStream(inputStream);

        assertEquals(0, accesser.getPosition());
        accesser.read();
        assertEquals(1, accesser.getPosition());
        accesser.read();
        assertEquals(2, accesser.getPosition());
    }

    @Test
    public void testMarkAndRestore() throws IOException {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        ByteAccesserFromStream accesser = new ByteAccesserFromStream(inputStream);

        IByteAccesser.IPosition mark = accesser.mark();
        accesser.read();
        accesser.read();
        mark.restore();
        assertEquals(0, accesser.getPosition());
        assertEquals(1, accesser.read());
    }
}
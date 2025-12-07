package tests.expression;

import jp.co.zoppa.abnf.accesser.ByteAccesser;
import jp.co.zoppa.abnf.accesser.IByteAccesser;
import jp.co.zoppa.abnf.accesser.Span;
import jp.co.zoppa.abnf.expression.CommentExpression;
import jp.co.zoppa.abnf.expression.ExpressionRange;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CommentExpressionのテストクラス。
 */
public class CommentExpressionTest {

    @Test
    public void testValidComment() {
        byte[] data = "; This is a comment\r\n".getBytes();
        IByteAccesser accesser = new ByteAccesser(data);
        CommentExpression expression = new CommentExpression();

        ExpressionRange range = expression.match(accesser);
        assertTrue(range.isEnable());
        Span span = range.getSpan();
        assertEquals("; This is a comment", span.toString());
    }

    @Test
    public void testInvalidComment() {
        byte[] data = "This is not a comment\r\n".getBytes();
        IByteAccesser accesser = new ByteAccesser(data);
        CommentExpression expression = new CommentExpression();

        ExpressionRange range = expression.match(accesser);
        assertFalse(range.isEnable());
    }

    @Test
    public void testEmptyInput() {
        byte[] data = "".getBytes();
        IByteAccesser accesser = new ByteAccesser(data);
        CommentExpression expression = new CommentExpression();

        ExpressionRange range = expression.match(accesser);
        assertFalse(range.isEnable());
    }

    @Test
    public void testCommentWithoutNewline() {
        byte[] data = "; Comment without newline".getBytes();
        IByteAccesser accesser = new ByteAccesser(data);
        CommentExpression expression = new CommentExpression();

        ExpressionRange range = expression.match(accesser);
        assertTrue(range.isEnable());
        Span span = range.getSpan();
        assertEquals("; Comment without newline", span.toString());
    }
}
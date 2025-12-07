package tests.expression;

import jp.co.zoppa.abnf.accesser.ByteAccesser;
import jp.co.zoppa.abnf.accesser.IByteAccesser;
import jp.co.zoppa.abnf.accesser.Span;
import jp.co.zoppa.abnf.expression.CommentNewLineExpression;
import jp.co.zoppa.abnf.expression.ExpressionRange;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CommentNewLineExpressionのテストクラス。
 */
public class CommentNewLineExpressionTest {

    @Test
    public void testValidCommentWithNewline() {
        byte[] data = "; This is a comment\r\n".getBytes();
        IByteAccesser accesser = new ByteAccesser(data);
        CommentNewLineExpression expression = new CommentNewLineExpression();

        ExpressionRange range = expression.match(accesser);
        assertTrue(range.isEnable());
        Span span = range.getSpan();
        assertEquals("; This is a comment", span.toString());
    }

    @Test
    public void testInvalidCommentWithoutNewline() {
        byte[] data = "; This is a comment".getBytes();
        IByteAccesser accesser = new ByteAccesser(data);
        CommentNewLineExpression expression = new CommentNewLineExpression();

        ExpressionRange range = expression.match(accesser);
        assertFalse(range.isEnable());
    }

    @Test
    public void testEmptyInput() {
        byte[] data = "".getBytes();
        IByteAccesser accesser = new ByteAccesser(data);
        CommentNewLineExpression expression = new CommentNewLineExpression();

        ExpressionRange range = expression.match(accesser);
        assertFalse(range.isEnable());
    }

}
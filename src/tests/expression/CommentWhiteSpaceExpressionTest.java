package tests.expression;

import jp.co.zoppa.abnf.accesser.ByteAccesser;
import jp.co.zoppa.abnf.accesser.IByteAccesser;
import jp.co.zoppa.abnf.accesser.Span;
import jp.co.zoppa.abnf.expression.CommentWhiteSpaceExpression;
import jp.co.zoppa.abnf.expression.ExpressionRange;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CommentWhiteSpaceExpressionのテストクラス。
 */
public class CommentWhiteSpaceExpressionTest {

    @Test
    public void testValidCommentWithWhiteSpace() {
        byte[] data = "; This is a comment with spaces\r\n".getBytes();
        IByteAccesser accesser = new ByteAccesser(data);
        CommentWhiteSpaceExpression expression = new CommentWhiteSpaceExpression();

        ExpressionRange range = expression.match(accesser);
        assertTrue(range.isEnable());
        Span span = range.getSpan();
        assertEquals("; This is a comment with spaces", span.toString());
    }

    @Test
    public void testEmptyInput() {
        byte[] data = "".getBytes();
        IByteAccesser accesser = new ByteAccesser(data);
        CommentWhiteSpaceExpression expression = new CommentWhiteSpaceExpression();

        ExpressionRange range = expression.match(accesser);
        assertFalse(range.isEnable());
    }
}
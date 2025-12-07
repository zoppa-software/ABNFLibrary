package jp.co.zoppa.abnf.expression;

/**
 * 表現式列挙型クラス。
 */
public enum ExpressionEnum {

    /** 選択式。 */
    ALTERNATIVE,

    /** 文字値式。 */
    CHARVAL,

    /** コメント式。 */
    COMMENT,

    /** コメント改行。 */
    COMMENT_NEWLINE,

    /** コメント空白。 */
    COMMENT_WHITESPACE,

    /** 連結式。 */
    CONCATENATION,

    /** 改行式。 */
    CR_LF,

    /** 要素式。 */
    ELEMENTS,

    /** グループ式。 */
    GROUP,

    /** 数値値式。 */
    NUMVAL,

    /** 数値範囲式。 */
    NUMVAL_RANGE,

    /** 数値連結式。 */
    NUMVAL_CONCAT,

    /** オプション式。 */
    OPTION,

    /** 逐語式。 */
    PROSEVAL,

    /** 繰り返し式。 */
    REPETITION,

    /** ルール式。 */
    RULE,

    /** ルールリスト。 */
    RULELIST,

    /** ルール名式。 */
    RULENAME,

    /** 空白式。 */
    SPACE,

}

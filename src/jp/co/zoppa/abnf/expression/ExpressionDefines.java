package jp.co.zoppa.abnf.expression;

/**
 * 各種表現式の定義を提供するクラス。
 */
public final class ExpressionDefines {

    /** 選択式。 */
    private static AlternativeExpression alterExpr;

    /** 文字値式。 */
    private static CharvalExpression charExpr;

    /** コメント式。 */
    private static CommentExpression commentExpr;

    /** コメント改行式。 */
    private static CommentNewLineExpression commentNlExpr;

    /** コメント空白式。 */
    private static CommentWhiteSpaceExpression commentWspExpr;

    /** 結合式。 */
    private static ConcatenationExpression concatExpr;

    /** 改行式。 */
    private static CrLfExpression crlfExpr;

    /** 複数要素式。 */
    private static ElementsExpression elesExpr;

    /** グループ式 */
    private static GroupExpression groupExpr;

    /** 数値値式。 */
    private static NumvalExpression numvalExpr;

    /** 数値値式（結合） */
    private static NumvalExpression.Concat numConcatExpr;

    /** 数値値式（範囲） */
    private static NumvalExpression.Range numRangeExpr;

    /** オプション式。 */
    private static OptionExpression optExpr;

    /** 逐語式。 */
    private static ProsevalExpression proseExpr;

    /** 反復式。 */
    private static RepetitionExpression repetExpr;

    /** ルール式。 */
    private static RuleExpression ruleExpr;

    /** ルールリスト式。 */
    private static RuleListExpression ruleListExpr;

    /** 識別子式。 */
    private static RulenameExpression rlnameExpr;

    /** 空白式。 */
    private static SpaceExpression spaceExpr;

    /**
     * 選択式を取得する。
     * @return 選択式。
     */
    public static AlternativeExpression getAlterExpr() {
        if (alterExpr == null) {
            synchronized (AlternativeExpression.class) {
                if (alterExpr == null) {
                    alterExpr = new AlternativeExpression();
                }
            }
        }
        return alterExpr;
    }

    /**
     * 文字値式を取得する。
     * @return 文字値式。
     */
    public static CharvalExpression getCharExpr() {
        if (charExpr == null) {
            synchronized (CharvalExpression.class) {
                if (charExpr == null) {
                    charExpr = new CharvalExpression();
                }
            }
        }
        return charExpr;
    }

    /**
     * コメント式を取得する。
     * @return コメント式。
     */
    public static CommentExpression getCommentExpr() {
        if (commentExpr == null) {
            synchronized (CommentExpression.class) {
                if (commentExpr == null) {
                    commentExpr = new CommentExpression();
                }
            }
        }
        return commentExpr;
    }

    /**
     * コメント改行式を取得する。
     * @return コメント改行式。
     */
    public static CommentNewLineExpression getCommentNlExpr() {
        if (commentNlExpr == null) {
            synchronized (CommentNewLineExpression.class) {
                if (commentNlExpr == null) {
                    commentNlExpr = new CommentNewLineExpression();
                }
            }
        }
        return commentNlExpr;
    }

    /**
     * コメントまたは空白式を取得する。
     * @return コメントまたは空白式。
     */
    public static CommentWhiteSpaceExpression getCommentWspExpr() {
        if (commentWspExpr == null) {
            synchronized (CommentWhiteSpaceExpression.class) {
                if (commentWspExpr == null) {
                    commentWspExpr = new CommentWhiteSpaceExpression();
                }
            }
        }
        return commentWspExpr;
    }

    /**
     * 結合式を取得する。
     * @return 結合式。
     */
    public static ConcatenationExpression getConcatExpr() {
        if (concatExpr == null) {
            synchronized (ConcatenationExpression.class) {
                if (concatExpr == null) {
                    concatExpr = new ConcatenationExpression();
                }
            }
        }
        return concatExpr;
    }

    /**
     * 改行式を取得する。
     * @return CRLF式。
     */
    public static CrLfExpression getCrlfExpr() {
        if (crlfExpr == null) {
            synchronized (CrLfExpression.class) {
                if (crlfExpr == null) {
                    crlfExpr = new CrLfExpression();
                }
            }
        }
        return crlfExpr;
    }

    /**
     * 複数要素式を取得する。
     * @return 定義式。
     */
    public static ElementsExpression getElementsExpr() {
        if (elesExpr == null) {
            synchronized (ElementsExpression.class) {
                if (elesExpr == null) {
                    elesExpr = new ElementsExpression();
                }
            }
        }
        return elesExpr;
    }

    /**
     * グループ式を取得する。
     * @return グループ式。
     */
    public static GroupExpression getGroupExpr() {
        if (groupExpr == null) {
            synchronized (GroupExpression.class) {
                if (groupExpr == null) {
                    groupExpr = new GroupExpression();
                }
            }
        }
        return groupExpr;
    }

    /**
     * 数値値式を取得する。
     * @return 数値値式。
     */
    public static NumvalExpression getNumvalExpr() {
        if (numvalExpr == null) {
            synchronized (NumvalExpression.class) {
                if (numvalExpr == null) {
                    numvalExpr = new NumvalExpression();
                }
            }
        }
        return numvalExpr;
    }

    /**
     * 数値値式（結合）を取得する。
     * @return 数値値式（結合）。
     */
    public static NumvalExpression.Concat getNumvalConcatExpr() {
        if (numConcatExpr == null) {
            synchronized (NumvalExpression.Concat.class) {
                if (numConcatExpr == null) {
                    numConcatExpr = new NumvalExpression.Concat();
                }
            }
        }
        return numConcatExpr;
    }

    /**
     * 数値値式（範囲）を取得する。
     * @return 数値値式（範囲）。
     */
    public static NumvalExpression.Range getNumvalRangeExpr() {
        if (numRangeExpr == null) {
            synchronized (NumvalExpression.Range.class) {
                if (numRangeExpr == null) {
                    numRangeExpr = new NumvalExpression.Range();
                }
            }
        }
        return numRangeExpr;
    }

    /**
     * オプション式を取得する。
     * @return オプション式。
     */
    public static OptionExpression getOptionExpr() {
        if (optExpr == null) {
            synchronized (OptionExpression.class) {
                if (optExpr == null) {
                    optExpr = new OptionExpression();
                }
            }
        }
        return optExpr;
    }

    /**
     * 逐語式を取得する。
     * @return 逐語式。
     */
    public static ProsevalExpression getProseExpr() {
        if (proseExpr == null) {
            synchronized (ProsevalExpression.class) {
                if (proseExpr == null) {
                    proseExpr = new ProsevalExpression();
                }
            }
        }
        return proseExpr;
    }

    /**
     * 反復式を取得する。
     * @return 反復式。
     */
    public static RepetitionExpression getRepeatExpr() {
        if (repetExpr == null) {
            synchronized (RepetitionExpression.class) {
                if (repetExpr == null) {
                    repetExpr = new RepetitionExpression();
                }
            }
        }
        return repetExpr;
    }

    /**
     * ルール式を取得する。
     * @return ルール式。
     */
    public static RuleExpression getRuleExpr() {
        if (ruleExpr == null) {
            synchronized (RuleExpression.class) {
                if (ruleExpr == null) {
                    ruleExpr = new RuleExpression();
                }
            }
        }
        return ruleExpr;
    }

    /**
     * ルールリスト式を取得する。
     * @return ルールリスト式。
     */
    public static RuleListExpression getRuleListExpr() {
        if (ruleListExpr == null) {
            synchronized (RuleListExpression.class) {
                if (ruleListExpr == null) {
                    ruleListExpr = new RuleListExpression();
                }
            }
        }
        return ruleListExpr;
    }

    /**
     * 識別子式を取得する。
     * @return 識別子式。
     */
    public static RulenameExpression getRuleNameExpr() {
        if (rlnameExpr == null) {
            synchronized (RulenameExpression.class) {
                if (rlnameExpr == null) {
                    rlnameExpr = new RulenameExpression();
                }
            }
        }
        return rlnameExpr;
    }

    /**
     * 空白を表す式を取得する。
     * @return 空白を表す式。
     */
    public static SpaceExpression getSpaceExpr() {
        if (spaceExpr == null) {
            synchronized (SpaceExpression.class) {
                if (spaceExpr == null) {
                    spaceExpr = new SpaceExpression();
                }
            }
        }
        return spaceExpr;
    }

}

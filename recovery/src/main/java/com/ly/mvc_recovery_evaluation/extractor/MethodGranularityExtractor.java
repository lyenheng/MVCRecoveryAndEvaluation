package com.ly.mvc_recovery_evaluation.extractor;

import com.github.javaparser.ast.stmt.BlockStmt;
import com.ly.mvc_recovery_evaluation.entity.MethodGranularityDescription;
import org.springframework.stereotype.Component;

/**
 * @author liuyue
 * @date 2022/6/11 21:47
 * 提取方法粒度信息
 */
@Component
public class MethodGranularityExtractor {

    public MethodGranularityDescription extract(BlockStmt blockStmt){
        MethodGranularityDescription methodGranularityDescription = new MethodGranularityDescription(0,0,0,0);

        if (blockStmt == null){
            return methodGranularityDescription;
        }

        String bodyStr = blockStmt.getTokenRange().toString();
        String[] lines = bodyStr.split("\n");
        Integer totalLine, blankLine, commentLine, codeLine;
        totalLine = lines.length;
        blankLine = commentLine = codeLine = 0;

        Boolean isComment = false;
        for (String line : lines) {
            String lineTrim = line.trim();
            if (lineTrim.length() == 0){
                // 空行
                blankLine ++;
            }else if (!isComment && lineTrim.startsWith("/*")){
                // 注释
                isComment = true;
                commentLine ++;
            }else if (isComment && !lineTrim.endsWith("*/")){
                // 注释
                commentLine ++;
            }else if (isComment && lineTrim.endsWith("*/")){
                // 注释
                commentLine ++;
                isComment = false;
            }else if (!isComment && lineTrim.startsWith("//")){
                // 注释
                commentLine ++;
            }else {
                // 有效代码
                codeLine ++;
            }
        }

        methodGranularityDescription.setBlankLine(blankLine);
        methodGranularityDescription.setCommentLine(commentLine);
        methodGranularityDescription.setCodeLine(codeLine);
        methodGranularityDescription.setTotalLine(totalLine);
        return methodGranularityDescription;
    }


}

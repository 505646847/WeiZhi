package com.xiaoyan.xylibrary.common.tools.font;

import java.util.Arrays;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉子转拼音
 *
 * @author xiejinxiong
 *
 */
public class HanziToPinyin {

    public static void changeToPY(String strHanzi){
        String str = "单赵钱孙李周吴郑王冯陈褚卫abc";
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
        for(int i = 0 ; i < str.length() ; i++)
        {
            char c = str.charAt(i);

            String[] vals = new String[0];
            try {
                vals = PinyinHelper.toHanyuPinyinStringArray(c, format);
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
            System.out.print(Arrays.toString(vals));
            System.out.println("hha");
        }
    }
}

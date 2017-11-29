package com.xiaoyan.xylibrary.common.tools;

import android.webkit.WebView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * WebView工具类
 */

public class WebViewUtil {


  /**
   * 使用WebView加载网页类型字符串
   * @param content
   * @param webView
   */
  public static void loadHtml(final String content, WebView webView) {
    String html = "<div style=\"width:100%\">" + content + "</div>";
    html = delHTMLTag(html);
    webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
  }

  private static String delHTMLTag(String htmlStr) {
    htmlStr = "<div style=\\\"word-wrap:break-word;word-break:break-all;\\\">" + htmlStr
        + "</div><script>var pic = document.getElementsByTagName(\\\"img\\\");for (var i = 0; i < pic.length; i++) {pic[i].style.maxWidth = \\\"100%%\\\";pic[i].style.maxHeight = \\\"100%%\\\";};</script>";
    String img = "<img[^>]+>";
    Pattern imgp = Pattern.compile(img);
    Matcher html = imgp.matcher(htmlStr);
    while (html.find()) {
      Matcher m = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(
          html.group());
      while (m.find()) {
        String ig =
            "<img style=\"box-sizing: border-box; width: 100%; height: auto !important;\" src=\""
                + m.group(1) + "\" />";
        htmlStr = htmlStr.replace(html.group(), ig);
      }
    }
    return htmlStr; // 返回文本字符串
  }

//  @Override
//  protected void onDestroy() {
//    super.onDestroy();
//    if (webView != null) {
//      webView.removeAllViews();
//      webView.destroy();
//    }
//  }
}

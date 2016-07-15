package com.jxnugo.listener;

/**
 * Created by zpauly on 16-5-31.
 */
public interface OnSearchListener {
    boolean onTextSubmit(String query);

    boolean onTextChange(String newText);

    void onShown();

    void onClosed();
}

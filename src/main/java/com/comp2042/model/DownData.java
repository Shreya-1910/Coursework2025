package com.comp2042.model;

import com.comp2042.view.BoardViewData;

public final class DownData {
    private final ClearRow clearRow;
    private final BoardViewData boardViewData;

    public DownData(ClearRow clearRow, BoardViewData boardViewData) {
        this.clearRow = clearRow;
        this.boardViewData = boardViewData;
    }

    public ClearRow getClearRow() {
        return clearRow;
    }

    public BoardViewData getViewData() {
        return boardViewData;
    }
}

package com.example.barmaster.ui.rutinasGrupos;

import android.graphics.Bitmap;

public class CardRowDataModelGR {
    private Bitmap imageId;
    private String titleId;

    public CardRowDataModelGR(Bitmap imageId, String titleId) {
        this.imageId = imageId;
        this.titleId = titleId;
    }

    public Bitmap getImageId() {
        return imageId;
    }

    public String getTitle() {
        return titleId;
    }
}

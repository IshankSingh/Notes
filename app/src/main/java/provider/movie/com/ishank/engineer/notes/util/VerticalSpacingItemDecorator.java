package provider.movie.com.ishank.engineer.notes.util;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalSpacingItemDecorator extends RecyclerView.ItemDecoration {

    private final int VerticalSpaceHeight;

    public VerticalSpacingItemDecorator(int verticalSpaceHeight) {
        VerticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = VerticalSpaceHeight;
    }
}

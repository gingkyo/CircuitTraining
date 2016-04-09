package com.eaton.chris.circuittraining;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;



public class ImageCellAdapter extends BaseAdapter
{

    public ViewGroup parentView = null;
    private Context context;

    public ImageCellAdapter (Context c){
        context = c;
    }

    public int getCount()
    {
        Resources res = context.getResources();
        return res.getInteger (R.integer.num_images);
    }

    public Object getItem(int position)
    {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView (int position, View convertView, ViewGroup parent)
    {
        Resources res = context.getResources ();
        int cellWidth = res.getDimensionPixelSize (R.dimen.grid_cell_width);
        int cellHeight = res.getDimensionPixelSize (R.dimen.grid_cell_height);
        int cellPad = res.getDimensionPixelSize (R.dimen.grid_cell_padding);
        parentView = parent;

        ImageCell v;
        if (convertView == null) {
            // If it's not recycled, create a new ImageCell.
            v = new ImageCell (context);
            v.setLayoutParams(new GridView.LayoutParams (cellWidth, cellHeight));
            v.setScaleType(ImageView.ScaleType.CENTER_CROP);
            v.setPadding (cellPad, cellPad, cellPad, cellPad);

        } else {
            v = (ImageCell) convertView;
            v.setImageDrawable (null);     // Important. Otherwise, recycled views show old image.
        }

        v.cellNumber = position;
        //v.mGrid = (GridView) mParentView;
        v.isEmpty = true;
        v.setOnDragListener ((View.OnDragListener)context);
        v.setBackgroundResource (R.color.cell_empty);

        v.setOnTouchListener((View.OnTouchListener)context);
       // v.setOnClickListener ((View.OnClickListener) context);

        return v;
    }


} // end class

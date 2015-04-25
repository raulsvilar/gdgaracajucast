package raulvilar.gdgaracaju;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.bitmap.Transform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raul on 23/03/15.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>{

    private List<MediaInfo> list = new ArrayList<>();
    private Context mContext;
    OnItemClickListener mItemClickListener;

    public VideoAdapter() {

    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView thumbnail;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                int position = getAdapterPosition();
                MediaInfo item = list.get(position);
                mItemClickListener.onItemClick(v, item, position);
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view , MediaInfo item, int position);
    }

    public void setOnItemClickListener(VideoAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_adapter, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transform t = new Transform() {
            @Override
            public Bitmap transform(Bitmap b) {
                return Bitmap.createBitmap(b, 0, 45, b.getWidth(), b.getHeight() - 90);
            }

            @Override
            public String key() {
                return null;
            }
        };
        holder.title.setText(list.get(position).getMetadata().getString(MediaMetadata.KEY_TITLE));
        Ion.with(holder.thumbnail).resize(300, 225).load(list.get(position).getMetadata().getImages().get(0).getUrl().toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItemsList(List<MediaInfo> collection) {
        int start = list.size();
        list.addAll(collection);
        this.notifyItemRangeInserted(start, collection.size());
    }
}

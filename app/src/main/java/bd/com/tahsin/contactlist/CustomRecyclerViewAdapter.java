package bd.com.tahsin.contactlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tahsin on 3/30/2017.
 */
public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_ITEM = 1;

    private ArrayList<String> mName;
    private ArrayList<String> mNum;
    private Context mContext;


    public CustomRecyclerViewAdapter(Context mContext, ArrayList<String> mName,  ArrayList<String> mNum) {
        this.mContext = mContext;
        this.mName = mName;
        this.mNum = mNum;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_recycle, parent, false);
            return new ItemViewHolder(itemView);
        }  else if (viewType == TYPE_FOOTER) {
            //Inflating footer view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_recycler, parent, false);
            return new FooterViewHolder(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        String num = "";
        if (holder instanceof FooterViewHolder) {
             FooterViewHolder footerHolder = (FooterViewHolder) holder;
            Log.d("mName->", mName.get(position-1).toString());
            if(mNum.get(position-1).toString().contains("end")) {
                 footerHolder.btLoad.setText("No more results to show");
                 footerHolder.btLoad.setEnabled(false);
            }
             footerHolder.btLoad.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                         ((MainActivity)mContext).setAdapter();
                 }
             });
         }
        else if (holder instanceof ItemViewHolder) {
            if(mNum.get(position).toString().contains("end")){
                String[] split = mNum.get(position).split(":");
                num = split[0];
            }
            else{
                num = mNum.get(position).toString();
            }
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tvName.setText(mName.get(position).toString());
            itemViewHolder.tvNum.setText(num);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mName.size()) {
            return TYPE_FOOTER;
        }
        else return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mName.size()+1;
    }


    private class FooterViewHolder extends RecyclerView.ViewHolder {
        Button btLoad;

        public FooterViewHolder(View view) {
            super(view);
            btLoad = (Button) view.findViewById(R.id.btLoad);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvNum;
        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvContactName);
            tvNum = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
        }
    }
}
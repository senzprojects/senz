package com.score.senzservices.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.score.senzservices.R;
import com.score.senzservices.pojos.AppInfo;

import java.util.ArrayList;

/**
 * Created by Anesu on 1/10/2016.
 */
public class AppInfoListAdapter extends ArrayAdapter {
    ArrayList<AppInfo> apps;
    Context context;
    int resource;

    public AppInfoListAdapter(Context context, int resource, ArrayList<AppInfo> apps) {
        super(context, resource);
        this.apps = apps;
        this.apps = apps;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return apps.size();
    }

    public void initUi(View convertView, AppInfo info, ViewHolder holder) {
        holder.icon = (ImageView) convertView.findViewById(R.id.icon);
        holder.action_btn = (Button) convertView.findViewById(R.id.action);
        holder.description = (TextView) convertView.findViewById(R.id.descr);
        holder.name = (TextView) convertView.findViewById(R.id.name);
        holder.ratingBar = (RatingBar) convertView.findViewById(R.id.rating);

        holder.icon.setImageBitmap(info.getIcon());
        holder.name.setText(info.getName());
        holder.description.setText(info.getDescription());
        if (info.getRating() == -1) {
            holder.ratingBar.setVisibility(View.GONE);
        } else {
            holder.ratingBar.setRating((float) info.getRating());
        }

        if (info.isInstalled()) {
            holder.action_btn.setText("UNINSTALL");
            holder.action_btn.setBackgroundResource(R.color.Red);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AppInfo info = apps.get(position);
        final ViewHolder holder;
        final View view;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        view = convertView;
        initUi(convertView, info, holder);

        holder.action_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.isInstalled()) {
                    Intent intent = new Intent(Intent.ACTION_DELETE);
                    intent.setData(Uri.parse("package:" + info.getPackageName()));
                    context.startActivity(intent);
                } else {
                    try {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + info.getPackageName())));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + info.getPackageName())));
                    }
                }
                if (!info.isInstalled()) {
                    holder.action_btn.setText("INSTALL");
                    holder.action_btn.setBackgroundResource(R.color.DodgerBlue);
                }else{
                    holder.action_btn.setText("UNINSTALL");
                    holder.action_btn.setBackgroundResource(R.color.Red);
                }
            }

        });

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        Button action_btn;
        RatingBar ratingBar;
        ImageView icon;
        TextView description;
    }
}

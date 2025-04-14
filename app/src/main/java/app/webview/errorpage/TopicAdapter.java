package app.webview.errorpage;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.List;

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/14/2025
 */

public class TopicAdapter extends ArrayAdapter<ExcelTopic> {
    private Context context;
    private List<ExcelTopic> topics;

    public TopicAdapter(Context context, List<ExcelTopic> topics) {
        super(context, R.layout.list_item_topic, topics);
        this.context = context;
        this.topics = topics;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_topic, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.cardView = convertView.findViewById(R.id.card_view);
            viewHolder.titleText = convertView.findViewById(R.id.text_title);
            viewHolder.subtitleText = convertView.findViewById(R.id.text_subtitle);
            viewHolder.descriptionText = convertView.findViewById(R.id.text_description);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ExcelTopic topic = topics.get(position);

        viewHolder.titleText.setText(topic.getTitle());
        viewHolder.subtitleText.setText(topic.getSubtitle());
        viewHolder.descriptionText.setText(topic.getDescription());

        // Set card background color based on topic color code
        try {
            viewHolder.cardView.setCardBackgroundColor(Color.parseColor(topic.getColor()));
        } catch (IllegalArgumentException e) {
            // Fallback to default color if there's an issue with the color string
            viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#2196F3"));
        }

        return convertView;
    }

    static class ViewHolder {
        CardView cardView;
        TextView titleText;
        TextView subtitleText;
        TextView descriptionText;
    }

    public static void flipCard(Context context, View front, View back) {
        ObjectAnimator flipOut = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.flip_out);
        ObjectAnimator flipIn = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.flip_in);

        if (front.getVisibility() == View.VISIBLE) {
            flipOut.setTarget(front);
            flipIn.setTarget(back);

            flipOut.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    front.setVisibility(View.GONE);
                    back.setVisibility(View.VISIBLE);
                    flipIn.start();
                }
            });

            flipOut.start();
        } else {
            flipOut.setTarget(back);
            flipIn.setTarget(front);

            flipOut.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    back.setVisibility(View.GONE);
                    front.setVisibility(View.VISIBLE);
                    flipIn.start();
                }
            });

            flipOut.start();
        }
    }
}

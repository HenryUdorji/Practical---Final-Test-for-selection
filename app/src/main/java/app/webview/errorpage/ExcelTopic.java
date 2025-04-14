package app.webview.errorpage;

import androidx.annotation.Keep;

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/14/2025
 */

@Keep
public class ExcelTopic {
    private int id;
    private String title;
    private String subtitle;
    private String description;
    private String category;
    private String detailUrl;
    private String color;

    public ExcelTopic() {
        // Required empty constructor
    }

    public ExcelTopic(int id, String title, String subtitle, String description, String category, String detailUrl, String color) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.category = category;
        this.detailUrl = detailUrl;
        this.color = color;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

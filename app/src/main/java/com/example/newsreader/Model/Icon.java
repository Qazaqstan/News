package com.example.newsreader.Model;

public class Icon {

    private String url;
    private int height;
    private int weight;
    private int bytes;
    private String format;
    private String sha1sum;
    private Object object;

    public Icon() {
    }

    public Icon(String url, int height, int weight, int bytes, String format, String sha1sum, Object object) {
        this.url = url;
        this.height = height;
        this.weight = weight;
        this.bytes = bytes;
        this.format = format;
        this.sha1sum = sha1sum;
        this.object = object;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSha1sum() {
        return sha1sum;
    }

    public void setSha1sum(String sha1sum) {
        this.sha1sum = sha1sum;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}

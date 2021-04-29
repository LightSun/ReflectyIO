package com.heaven7.java.reflectyio.temp;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class ColorPaletteInfo {

    @SerializedName("rect")
    private GraphInfo rectInfo;
    @SerializedName("polygon")
    private GraphInfo polygonInfo;
    @SerializedName("tempGraph")
    private TempGraphInfo tempGraphInfo;

    public GraphInfo getRectInfo() {
        return rectInfo;
    }

    public void setRectInfo(GraphInfo rectInfo) {
        this.rectInfo = rectInfo;
    }

    public GraphInfo getPolygonInfo() {
        return polygonInfo;
    }

    public void setPolygonInfo(GraphInfo polygonInfo) {
        this.polygonInfo = polygonInfo;
    }

    public TempGraphInfo getTempGraphInfo() {
        return tempGraphInfo;
    }

    public void setTempGraphInfo(TempGraphInfo tempGraphInfo) {
        this.tempGraphInfo = tempGraphInfo;
    }

    public static class GraphInfo{
        float lineWidth;
        float dotRadius;
        int color;
    }
    public static class TempGraphInfo{
        int color;
        float lineWidth;
        int highlightColor;
    }

    public static void main(String[] args) {
        ColorPaletteInfo info = new ColorPaletteInfo();
        info.setPolygonInfo(new GraphInfo());
        info.setRectInfo(new GraphInfo());
        info.setTempGraphInfo(new TempGraphInfo());

        System.out.println(new Gson().toJson(info));
    }
}

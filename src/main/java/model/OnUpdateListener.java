package model;

import java.util.Map;

public interface OnUpdateListener {
    void onUpdate(Map.Entry<String, Cell> entry);
}
